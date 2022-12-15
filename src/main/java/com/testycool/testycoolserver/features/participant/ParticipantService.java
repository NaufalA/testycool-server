package com.testycool.testycoolserver.features.participant;

import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.features.exam.interfaces.IExamService;
import com.testycool.testycoolserver.features.participant.dtos.ParticipantDto;
import com.testycool.testycoolserver.features.participant.dtos.RegisterParticipantRequest;
import com.testycool.testycoolserver.features.participant.dtos.UpdateParticipantRequest;
import com.testycool.testycoolserver.features.participant.entities.Participant;
import com.testycool.testycoolserver.features.participant.entities.ParticipantRegistration;
import com.testycool.testycoolserver.features.participant.interfaces.IParticipantRepository;
import com.testycool.testycoolserver.features.participant.interfaces.IParticipantService;
import com.testycool.testycoolserver.features.participant.interfaces.IRegistrationRepository;
import com.testycool.testycoolserver.shared.classes.GenericSpecification;
import com.testycool.testycoolserver.shared.classes.GenericSpecificationBuilder;
import com.testycool.testycoolserver.shared.classes.SearchCriteria;
import com.testycool.testycoolserver.shared.constants.QueryOperator;
import com.testycool.testycoolserver.shared.constants.SearchOperation;
import com.testycool.testycoolserver.shared.exceptions.DataExistException;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService implements IParticipantService {
    private final IParticipantRepository participantRepository;
    private final IRegistrationRepository registrationRepository;
    private final IExamService examService;

    public ParticipantService(
            IParticipantRepository participantRepository,
            IRegistrationRepository registrationRepository,
            IExamService examService
    ) {
        this.participantRepository = participantRepository;
        this.registrationRepository = registrationRepository;
        this.examService = examService;
    }

    @Transactional
    @Override
    public ParticipantDto registerParticipant(RegisterParticipantRequest request) {
        Exam exam = examService.getById(request.getExamId());

        Optional<Participant> existingParticipant = participantRepository.findByName(request.getParticipantName());
        Participant participant;
        if (existingParticipant.isPresent()) {
            participant = existingParticipant.get();
        } else {
            participant = new Participant();
            participant.setName(request.getParticipantName());
            participant = participantRepository.save(participant);
        }

        ParticipantRegistration registration = new ParticipantRegistration();
        registration.setExam(exam);
        registration.setParticipant(participant);
        registration.setCode(request.getParticipantCode());

        registration = registrationRepository.save(registration);

        return mapToDto(registration.getExam().getId(), registration);
    }

    @Transactional
    @Override
    public ParticipantDto updateParticipant(UpdateParticipantRequest request) {
        Optional<ParticipantRegistration> oldRegistration =
                registrationRepository.findById(request.getRegistrationId());
        if (oldRegistration.isEmpty()) {
            throw new NotFoundException("Participant Data Not Found");
        }

        List<ParticipantDto> allExamParticipant = getAllExamParticipant(oldRegistration.get().getExam().getId());

        Optional<ParticipantDto> existingParticipant = allExamParticipant.stream().filter(p ->
                p.getParticipantName().equals(request.getParticipantName()) ||
                        p.getParticipantCode().equals(request.getParticipantCode())
        ).findFirst();

        if (existingParticipant.isPresent()) {
            throw new DataExistException("Duplicate Participant Name or Code is not allowed");
        }

        Optional<Participant> oldParticipant =
                participantRepository.findById(oldRegistration.get().getParticipant().getId());
        if (oldParticipant.isEmpty()) {
            throw new NotFoundException("Participant Data Not Found");
        }

        Participant participant = oldParticipant.get();
        if (!participant.getName().equals(request.getParticipantName())) {
            participant.setName(request.getParticipantName());
            participant = participantRepository.save(participant);
        }

        ParticipantRegistration registration = oldRegistration.get();
        registration.setParticipant(participant);
        if (!registration.getCode().equals(request.getParticipantCode())) {
            registration.setCode(request.getParticipantCode());
            registration = registrationRepository.save(registration);
        }

        return mapToDto(registration.getExam().getId(), registration);
    }

    @Override
    public List<ParticipantDto> getAllExamParticipant(Long examId) {
        Specification<ParticipantRegistration> specification = new GenericSpecification<>(
                new SearchCriteria("id", examId, QueryOperator.EQUALS, SearchOperation.AND),
                (root, criteria) -> root.join("participant").get(criteria.getField())
        );

        List<ParticipantRegistration> registrations = registrationRepository.findAll(specification);

        return registrations.stream().map(registration -> {
            ParticipantDto response = new ParticipantDto();
            response.setExamId(examId);
            response.setRegistrationId(registration.getId());
            response.setParticipantName(registration.getParticipant().getName());
            response.setParticipantCode(registration.getCode());

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public ParticipantDto getForExam(Long examId, String passcode) {
        Specification<ParticipantRegistration> specification =
                new GenericSpecificationBuilder<ParticipantRegistration>().build(
                        List.of(
                                new SearchCriteria("examId", examId, QueryOperator.EQUALS, SearchOperation.AND),
                                new SearchCriteria("code", passcode, QueryOperator.EQUALS, SearchOperation.AND)
                        ),
                        (root, criteria) -> {
                            if (Objects.equals(criteria.getField(), "examId")) {
                                return root.join("exam").get("id");
                            } else {
                                return root.get(criteria.getField());
                            }
                        }
                );

        Optional<ParticipantRegistration> registration = registrationRepository.findOne(specification);

        if (registration.isEmpty()) {
            throw new NotFoundException("Participant Data Not Found");
        }

        return mapToDto(examId, registration.get());
    }

    @Override
    public Long unregisterParticipant(Long registrationId) {
        Optional<ParticipantRegistration> registration = registrationRepository.findById(registrationId);
        if (registration.isEmpty()) {
            throw new NotFoundException("Participant Data Not Found");
        }

        registrationRepository.delete(registration.get());

        return registration.get().getId();
    }


    @Override
    public ParticipantRegistration getById(Long id) {
        Optional<ParticipantRegistration> registration = registrationRepository.findById(id);

        if (registration.isEmpty()) {
            throw new NotFoundException("Participant Data Not Found");
        }

        return registration.get();
    }

    private ParticipantDto mapToDto(Long examId, ParticipantRegistration registration) {
        ParticipantDto response = new ParticipantDto();
        response.setExamId(examId);
        response.setRegistrationId(registration.getId());
        response.setParticipantName(registration.getParticipant().getName());
        response.setParticipantCode(registration.getCode());

        return response;
    }
}

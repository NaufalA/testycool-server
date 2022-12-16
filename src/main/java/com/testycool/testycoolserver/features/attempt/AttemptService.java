package com.testycool.testycoolserver.features.attempt;

import com.testycool.testycoolserver.features.attempt.dtos.CreateAttemptRequest;
import com.testycool.testycoolserver.features.attempt.entities.Attempt;
import com.testycool.testycoolserver.features.attempt.interfaces.IAttemptRepository;
import com.testycool.testycoolserver.features.attempt.interfaces.IAttemptService;
import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.features.exam.interfaces.IExamService;
import com.testycool.testycoolserver.features.participant.entities.ParticipantRegistration;
import com.testycool.testycoolserver.features.participant.interfaces.IParticipantService;
import com.testycool.testycoolserver.shared.classes.GenericSpecification;
import com.testycool.testycoolserver.shared.classes.SearchCriteria;
import com.testycool.testycoolserver.shared.constants.QueryOperator;
import com.testycool.testycoolserver.shared.constants.SearchOperation;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class AttemptService implements IAttemptService {
    private final IAttemptRepository attemptRepository;
    private final IExamService examService;
    private final IParticipantService participantService;

    public AttemptService(
            IAttemptRepository attemptRepository,
            IExamService examService,
            IParticipantService participantService
    ) {
        this.attemptRepository = attemptRepository;
        this.examService = examService;
        this.participantService = participantService;
    }

    @Override
    public Attempt create(CreateAttemptRequest request) {
        ParticipantRegistration registration = participantService.getById(request.getParticipantId());

        Attempt attempt = new Attempt();
        attempt.setParticipantRegistration(registration);
        attempt.setCreatedAt(request.getCreatedAt());
        attempt.setUpdatedAt(attempt.getCreatedAt());
        attempt.setCorrects(0);
        attempt.setWrongs(0);
        attempt.setUnanswered(0);
        attempt.setStatus(Attempt.Status.UNFINISHED);

        return attemptRepository.save(attempt);
    }

    @Override
    public Attempt getByParticipantId(Long participantId) {
        Specification<Attempt> specification = new  GenericSpecification<Attempt>(
                new SearchCriteria("participantId", participantId, QueryOperator.EQUALS, SearchOperation.AND),
                (root, criteria) -> root.join("participantRegistration").get("id")
        );

        Optional<Attempt> attempt = attemptRepository.findOne(specification);

        if (attempt.isEmpty()) {
            throw new NotFoundException("Attempt Data Not Found");
        }

        return attempt.get();
    }

    @Override
    public Attempt update(Long id, Attempt attempt) {
        Attempt existingAttempt = getById(id);

        existingAttempt.setCorrects(attempt.getCorrects());
        existingAttempt.setWrongs(attempt.getWrongs());
        existingAttempt.setUnanswered(attempt.getUnanswered());
        existingAttempt.setStatus(attempt.getStatus());
        existingAttempt.setUpdatedAt(new Date(System.currentTimeMillis()));

        return attemptRepository.save(existingAttempt);
    }

    @Override
    public Long delete(Attempt item) {
        Attempt existingAttempt = getById(item.getId());
        attemptRepository.delete(item);

        return existingAttempt.getId();
    }

    @Override
    public Long deleteById(Long id) {
        Attempt existingAttempt = getById(id);
        attemptRepository.deleteById(existingAttempt.getId());

        return existingAttempt.getId();
    }

    @Override
    public Attempt getById(Long id) {
        Optional<Attempt> attempt = attemptRepository.findById(id);

        if (attempt.isEmpty()) {
            throw new NotFoundException("Attempt Data Not Found");
        }

        return attempt.get();
    }

    @Override
    public Collection<Attempt> getAll() {
        return attemptRepository.findAll();
    }

    @Override
    public Page<Attempt> getAll(Pageable pageable) {
        return attemptRepository.findAll(pageable);
    }

    @Override
    public Page<Attempt> getAll(Specification<Attempt> specification, Pageable pageable) {
        return attemptRepository.findAll(specification, pageable);
    }
}

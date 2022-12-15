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
import com.testycool.testycoolserver.shared.exceptions.DataExistException;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {
    @Mock
    private IParticipantRepository mockParticipantRepository;
    @Mock
    private IRegistrationRepository mockRegistrationRepository;
    @Mock
    private IExamService mockExamService;
    private IParticipantService participantService;

    @BeforeEach
    void setup() {
        participantService = new ParticipantService(
                mockParticipantRepository, mockRegistrationRepository, mockExamService
        );
    }

    @Test
    void itShouldSuccess_WithResult_WhenRegisterParticipant() {
        RegisterParticipantRequest request = new RegisterParticipantRequest();
        request.setExamId(1L);
        request.setParticipantName("Participant 1");
        request.setParticipantName("participant1");

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setName(request.getParticipantName());

        ParticipantRegistration registration = new ParticipantRegistration();
        registration.setId(1L);
        registration.setExam(exam);
        registration.setParticipant(participant);
        registration.setCode(request.getParticipantCode());

        when(mockExamService.getById(anyLong())).thenReturn(exam);
        when(mockParticipantRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(mockParticipantRepository.save(any(Participant.class))).thenReturn(participant);
        when(mockRegistrationRepository.save(any(ParticipantRegistration.class))).thenReturn(registration);

        ParticipantDto res = participantService.registerParticipant(request);

        assertNotNull(res);
        assertEquals(request.getParticipantName(), res.getParticipantName());
        assertEquals(request.getParticipantCode(), res.getParticipantCode());
    }

    @Test
    void itShouldSuccess_WithResult_WhenUpdateParticipant() {
        UpdateParticipantRequest request = new UpdateParticipantRequest();
        request.setRegistrationId(1L);
        request.setParticipantName("Participant 1 Updated");
        request.setParticipantCode("participant1updated");

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        Participant existingParticipant = new Participant();
        existingParticipant.setId(1L);
        existingParticipant.setName("Participant 1");

        ParticipantRegistration existingRegistration = new ParticipantRegistration();
        existingRegistration.setId(1L);
        existingRegistration.setExam(exam);
        existingRegistration.setParticipant(existingParticipant);
        existingRegistration.setCode("participant1");

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setName(request.getParticipantName());

        ParticipantRegistration registration = new ParticipantRegistration();
        registration.setId(1L);
        registration.setExam(exam);
        registration.setParticipant(participant);
        registration.setCode(request.getParticipantCode());

        List<ParticipantRegistration> examRegistrations = List.of(existingRegistration);

        when(mockRegistrationRepository.findById(anyLong())).thenReturn(Optional.of(existingRegistration));
        when(mockRegistrationRepository.findAll(any(Specification.class))).thenReturn(examRegistrations);
        when(mockParticipantRepository.findById(anyLong())).thenReturn(Optional.of(existingParticipant));
        when(mockParticipantRepository.save(any(Participant.class))).thenReturn(participant);
        when(mockRegistrationRepository.save(any(ParticipantRegistration.class))).thenReturn(registration);

        ParticipantDto res = participantService.updateParticipant(request);

        assertNotNull(res);
        assertEquals(request.getParticipantName(), res.getParticipantName());
        assertEquals(request.getParticipantCode(), res.getParticipantCode());
    }

    @Test
    void itShouldThrow_NotFoundException_WhenUpdateParticipantWithInvalidId() {
        UpdateParticipantRequest request = new UpdateParticipantRequest();
        request.setRegistrationId(1L);
        request.setParticipantName("Participant 1 Updated");
        request.setParticipantCode("participant1updated");

        Participant existingParticipant = new Participant();
        existingParticipant.setId(1L);
        existingParticipant.setName("Participant 1");

        ParticipantRegistration existingRegistration = new ParticipantRegistration();
        existingRegistration.setId(1L);
        existingRegistration.setParticipant(existingParticipant);
        existingRegistration.setCode("participant1");

        when(mockRegistrationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> participantService.updateParticipant(request));
    }

    @Test
    void itShouldThrow_NotFoundException_WhenUpdateParticipantWithDuplicateNameAndCode() {
        UpdateParticipantRequest request = new UpdateParticipantRequest();
        request.setRegistrationId(1L);
        request.setParticipantName("Participant 1 Updated");
        request.setParticipantCode("participant1updated");

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        Participant existingParticipant = new Participant();
        existingParticipant.setId(1L);
        existingParticipant.setName(request.getParticipantName());

        ParticipantRegistration existingRegistration = new ParticipantRegistration();
        existingRegistration.setId(1L);
        existingRegistration.setExam(exam);
        existingRegistration.setParticipant(existingParticipant);
        existingRegistration.setCode(request.getParticipantCode());

        List<ParticipantRegistration> examRegistrations = List.of(existingRegistration);

        when(mockRegistrationRepository.findById(anyLong())).thenReturn(Optional.of(existingRegistration));
        when(mockRegistrationRepository.findAll(any(Specification.class))).thenReturn(examRegistrations);

        assertThrows(DataExistException.class, () -> participantService.updateParticipant(request));
    }

    @Test
    void itShouldSuccess_WithResult_WhenGetForExam() {
        Long examId = 1L;
        String passcode = "participant1";

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        Participant existingParticipant = new Participant();
        existingParticipant.setId(1L);
        existingParticipant.setName("Participant 1");

        ParticipantRegistration existingRegistration = new ParticipantRegistration();
        existingRegistration.setId(1L);
        existingRegistration.setExam(exam);
        existingRegistration.setParticipant(existingParticipant);
        existingRegistration.setCode("participant1");

        when(mockRegistrationRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.of(existingRegistration));

        ParticipantDto res = participantService.getForExam(examId, passcode);

        assertNotNull(res);
        assertEquals(passcode, res.getParticipantCode());
    }

    @Test
    void itShouldThrow_NotFoundException_WhenGetForExamWithInvalidData() {
        Long examId = 1L;
        String passcode = "participant1";

        when(mockRegistrationRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->participantService.getForExam(examId, passcode));
    }

    @Test
    void itShouldSuccess_WithResult_WhenUnregisterParticipant() {
        Long registrationId = 1L;

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        Participant existingParticipant = new Participant();
        existingParticipant.setId(1L);
        existingParticipant.setName("Participant 1");

        ParticipantRegistration existingRegistration = new ParticipantRegistration();
        existingRegistration.setId(1L);
        existingRegistration.setExam(exam);
        existingRegistration.setParticipant(existingParticipant);
        existingRegistration.setCode("participant1");

        when(mockRegistrationRepository.findById(anyLong())).thenReturn(Optional.of(existingRegistration));
        doNothing().when(mockRegistrationRepository).delete(any(ParticipantRegistration.class));

        Long res = participantService.unregisterParticipant(registrationId);

        assertEquals(registrationId, res);
    }

    @Test
    void itShouldThrow_NotFoundException_WhenUnregisterParticipantWithInvalidId() {
        Long registrationId = 1L;

        when(mockRegistrationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> participantService.unregisterParticipant(registrationId));
    }
}
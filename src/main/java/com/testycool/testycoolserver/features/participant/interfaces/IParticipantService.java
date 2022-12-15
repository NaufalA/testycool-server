package com.testycool.testycoolserver.features.participant.interfaces;

import com.testycool.testycoolserver.features.participant.dtos.ParticipantDto;
import com.testycool.testycoolserver.features.participant.dtos.RegisterParticipantRequest;
import com.testycool.testycoolserver.features.participant.dtos.UpdateParticipantRequest;
import com.testycool.testycoolserver.features.participant.entities.ParticipantRegistration;

import java.util.List;

public interface IParticipantService {
    ParticipantDto registerParticipant(RegisterParticipantRequest request);
    ParticipantDto updateParticipant(UpdateParticipantRequest request);
    List<ParticipantDto> getAllExamParticipant(Long examId);
    ParticipantDto getForExam(Long examId, String passcode);
    Long unregisterParticipant(Long registrationId);
    ParticipantRegistration getById(Long participantId);
}

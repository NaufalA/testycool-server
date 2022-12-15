package com.testycool.testycoolserver.features.attempt.interfaces;

import com.testycool.testycoolserver.features.attempt.dtos.CreateAttemptRequest;
import com.testycool.testycoolserver.features.attempt.entities.Attempt;
import com.testycool.testycoolserver.shared.interfaces.ServiceDeleteExecutor;
import com.testycool.testycoolserver.shared.interfaces.ServiceGetExecutor;
import com.testycool.testycoolserver.shared.interfaces.ServiceUpdateExecutor;

public interface IAttemptService extends ServiceGetExecutor<Attempt, Long>, ServiceUpdateExecutor<Attempt, Long>, ServiceDeleteExecutor<Attempt, Long> {
    Attempt create(CreateAttemptRequest request);
    Attempt getByParticipantId(Long participantId);

}

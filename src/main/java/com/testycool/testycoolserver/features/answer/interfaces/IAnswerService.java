package com.testycool.testycoolserver.features.answer.interfaces;

import com.testycool.testycoolserver.features.answer.dtos.AnswerSummary;
import com.testycool.testycoolserver.features.answer.dtos.CreateAnswerRequest;
import com.testycool.testycoolserver.features.answer.entities.Answer;
import com.testycool.testycoolserver.shared.interfaces.ServiceGetExecutor;
import com.testycool.testycoolserver.shared.interfaces.ServiceUpdateExecutor;
import org.springframework.data.domain.Page;

public interface IAnswerService extends ServiceGetExecutor<Answer, Long>, ServiceUpdateExecutor<Answer, Long> {
    Answer create(CreateAnswerRequest request);
    Page<Answer> getByQuestionId(Long questionId);
    AnswerSummary getAnswerSummary(Long questionId) throws NoSuchFieldException, IllegalAccessException, Exception;
}

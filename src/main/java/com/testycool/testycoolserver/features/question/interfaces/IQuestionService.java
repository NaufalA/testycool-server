package com.testycool.testycoolserver.features.question.interfaces;

import com.testycool.testycoolserver.features.question.dtos.CreateQuestionRequest;
import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.shared.interfaces.ServiceDeleteExecutor;
import com.testycool.testycoolserver.shared.interfaces.ServiceGetExecutor;

public interface IQuestionService extends ServiceGetExecutor<Question, Long>, ServiceDeleteExecutor<Question, Long> {
    Question create(CreateQuestionRequest request);
    Question update(Long id, Question question);
}

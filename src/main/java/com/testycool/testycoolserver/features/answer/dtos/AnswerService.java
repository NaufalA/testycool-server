package com.testycool.testycoolserver.features.answer.dtos;

import com.testycool.testycoolserver.features.answer.entities.Answer;
import com.testycool.testycoolserver.features.answer.interfaces.IAnswerRepository;
import com.testycool.testycoolserver.features.answer.interfaces.IAnswerService;
import com.testycool.testycoolserver.features.attempt.entities.Attempt;
import com.testycool.testycoolserver.features.attempt.interfaces.IAttemptService;
import com.testycool.testycoolserver.features.choice.interfaces.IChoiceService;
import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.features.question.interfaces.IQuestionService;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Optional;

public class AnswerService implements IAnswerService {
    private final IAnswerRepository answerRepository;
    private final IAttemptService attemptService;
    private final IQuestionService questionService;
    private final IChoiceService choiceService;

    public AnswerService(IAnswerRepository answerRepository, IAttemptService attemptService, IQuestionService questionService, IChoiceService choiceService) {
        this.answerRepository = answerRepository;
        this.attemptService = attemptService;
        this.questionService = questionService;
        this.choiceService = choiceService;
    }

    @Override
    public Answer create(CreateAnswerRequest request) {
        Attempt attempt = attemptService.getByParticipantId(request.getParticipantId());
        Question question = questionService.getById(request.getQuestionId());

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setAttempt(attempt);
        if (question.getType().equals(Question.Type.MULTIPLE_CHOICE)) {
            answer.setChoice(request.getChoiceId() != null ? choiceService.getById(request.getChoiceId()) : null);
        } else {
            answer.setEssayAnswer(request.getEssay());
        }

        return answerRepository.save(answer);
    }

    @Override
    public Answer update(Long id, Answer answer) {
        Answer existingAnswer = getById(id);

        existingAnswer.setChoice(answer.getChoice());
        existingAnswer.setEssayAnswer(answer.getEssayAnswer());

        return answerRepository.save(existingAnswer);
    }

    @Override
    public Answer getById(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);

        if (answer.isEmpty()) {
            throw new NotFoundException("Answer Data Not Found");
        }

        return answer.get();
    }

    @Override
    public Collection<Answer> getAll() {
        return answerRepository.findAll();
    }

    @Override
    public Page<Answer> getAll(Pageable pageable) {
        return answerRepository.findAll(pageable);
    }

    @Override
    public Page<Answer> getAll(Specification<Answer> specification, Pageable pageable) {
        return answerRepository.findAll(specification, pageable);
    }
}

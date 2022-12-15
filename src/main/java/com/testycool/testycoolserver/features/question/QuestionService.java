package com.testycool.testycoolserver.features.question;

import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.features.exam.interfaces.IExamService;
import com.testycool.testycoolserver.features.question.interfaces.IQuestionRepository;
import com.testycool.testycoolserver.features.question.interfaces.IQuestionService;
import com.testycool.testycoolserver.features.question.dtos.CreateQuestionRequest;
import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class QuestionService implements IQuestionService {

    private final IQuestionRepository questionRepository;
    private final IExamService examService;

    public QuestionService(IQuestionRepository questionRepository, IExamService examService) {
        this.questionRepository = questionRepository;
        this.examService = examService;
    }

    @Override
    public Question create(CreateQuestionRequest request) {
        Exam exam = examService.getById(request.getExamId());

        Question question = new Question();
        question.setExam(exam);
        question.setContent(request.getContent());
        question.setTextFormat(request.getTextFormat());
        question.setType(request.getType());

        return questionRepository.save(question);
    }

    @Override
    public Question update(Long id, Question question) {
        Question existingQuestion = getById(id);

        existingQuestion.setContent(question.getContent());
        existingQuestion.setTextFormat(question.getTextFormat());
        existingQuestion.setType(question.getType());

        return questionRepository.save(existingQuestion);
    }

    @Override
    public Long delete(Question item) {
        Question existingQuestion = getById(item.getId());
        questionRepository.delete(item);

        return existingQuestion.getId();
    }

    @Override
    public Long deleteById(Long id) {
        Question existingQuestion = getById(id);
        questionRepository.deleteById(existingQuestion.getId());

        return existingQuestion.getId();
    }

    @Override
    public Question getById(Long id) {
        Optional<Question> question = questionRepository.findById(id);

        if (question.isEmpty()) {
            throw new NotFoundException("Question Data Not Found");
        }

        return question.get();
    }

    @Override
    public Collection<Question> getAll() {
        return questionRepository.findAll();
    }

    @Override
    public Page<Question> getAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public Page<Question> getAll(Specification<Question> specification, Pageable pageable) {
        return questionRepository.findAll(specification, pageable);
    }
}

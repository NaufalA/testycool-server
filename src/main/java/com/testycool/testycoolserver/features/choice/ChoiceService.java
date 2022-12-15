package com.testycool.testycoolserver.features.choice;

import com.testycool.testycoolserver.features.choice.interfaces.IChoiceRepository;
import com.testycool.testycoolserver.features.choice.interfaces.IChoiceService;
import com.testycool.testycoolserver.features.choice.dtos.CreateChoiceRequest;
import com.testycool.testycoolserver.features.choice.entities.Choice;
import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.features.question.interfaces.IQuestionService;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import com.testycool.testycoolserver.shared.exceptions.UnsuitableQuestionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ChoiceService implements IChoiceService {

    private final IChoiceRepository choiceRepository;
    private final IQuestionService questionService;

    public ChoiceService(IChoiceRepository choiceRepository, IQuestionService questionService) {
        this.choiceRepository = choiceRepository;
        this.questionService = questionService;
    }

    @Override
    public Choice create(CreateChoiceRequest request) {
        Choice choice = new Choice();
        Question question = questionService.getById(request.getQuestionId());
        if (question.getType() != Question.Type.MULTIPLE_CHOICE) {
            throw new UnsuitableQuestionException("Can't add choice into non multiple-choice question");
        }

        choice.setQuestion(question);
        choice.setContent(request.getContent());
        choice.setTextFormat(request.getTextFormat());
        choice.setCorrect(request.getCorrect());

        return choiceRepository.save(choice);
    }

    @Override
    public Choice update(Long id, Choice choice) {
        Choice existingChoice = getById(id);

        existingChoice.setContent(choice.getContent());
        existingChoice.setTextFormat(choice.getTextFormat());
        existingChoice.setCorrect(choice.getCorrect());

        return choiceRepository.save(existingChoice);
    }

    @Override
    public Long delete(Choice item) {
        Choice existingChoice = getById(item.getId());
        choiceRepository.delete(item);

        return existingChoice.getId();
    }

    @Override
    public Long deleteById(Long id) {
        Choice existingChoice = getById(id);
        choiceRepository.deleteById(existingChoice.getId());

        return existingChoice.getId();
    }

    @Override
    public Choice getById(Long id) {
        Optional<Choice> choice = choiceRepository.findById(id);

        if (choice.isEmpty()) {
            throw new NotFoundException("Choice Data Not Found");
        }

        return choice.get();
    }

    @Override
    public Collection<Choice> getAll() {
        return choiceRepository.findAll();
    }

    @Override
    public Page<Choice> getAll(Pageable pageable) {
        return choiceRepository.findAll(pageable);
    }

    @Override
    public Page<Choice> getAll(Specification<Choice> specification, Pageable pageable) {
        return choiceRepository.findAll(specification, pageable);
    }
}

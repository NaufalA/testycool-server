package com.testycool.testycoolserver.features.choice;

import com.testycool.testycoolserver.features.choice.dtos.CreateChoiceRequest;
import com.testycool.testycoolserver.features.choice.entities.Choice;
import com.testycool.testycoolserver.features.choice.interfaces.IChoiceRepository;
import com.testycool.testycoolserver.features.choice.interfaces.IChoiceService;
import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.features.question.interfaces.IQuestionService;
import com.testycool.testycoolserver.shared.constants.TextFormat;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import com.testycool.testycoolserver.shared.exceptions.UnsuitableQuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ChoiceServiceTest {

    @Mock
    private IChoiceRepository mockChoiceRepository;
    @Mock
    private IQuestionService mockQuestionService;
    private IChoiceService choiceService;

    @BeforeEach
    void setup() {
        choiceService = new ChoiceService(mockChoiceRepository, mockQuestionService);
    }

    @Test
    void itShouldSuccess_WithResult_WhenCreateChoice() {
        CreateChoiceRequest request = new CreateChoiceRequest();
        request.setQuestionId(1L);
        request.setContent("Choice A");
        request.setTextFormat(TextFormat.TEXT_FORMAT_PLAIN);
        request.setCorrect(true);

        Question question = new Question();
        question.setId(1L);
        question.setContent("## Question 1");
        question.setTextFormat(TextFormat.TEXT_FORMAT_MARKDOWN);
        question.setType(Question.Type.MULTIPLE_CHOICE);

        Choice choice = new Choice();
        choice.setId(1L);
        choice.setQuestion(question);
        choice.setContent(request.getContent());
        choice.setTextFormat(request.getTextFormat());
        choice.setCorrect(request.getCorrect());

        when(mockQuestionService.getById(anyLong())).thenReturn(question);
        when(mockChoiceRepository.save(any(Choice.class))).thenReturn(choice);

        Choice res = choiceService.create(request);

        assertNotNull(res);
        assertEquals(request.getContent(), res.getContent());
    }

    @Test
    void itShouldSuccess_WithResult_WhenCreateChoiceWithMistypedQuestion() {
        CreateChoiceRequest request = new CreateChoiceRequest();
        request.setQuestionId(1L);
        request.setContent("Choice A");
        request.setTextFormat(TextFormat.TEXT_FORMAT_PLAIN);
        request.setCorrect(true);

        Question question = new Question();
        question.setId(1L);
        question.setContent("## Question 1");
        question.setTextFormat(TextFormat.TEXT_FORMAT_MARKDOWN);
        question.setType(Question.Type.ESSAY);

        when(mockQuestionService.getById(anyLong())).thenReturn(question);

        assertThrows(UnsuitableQuestionException.class, () -> choiceService.create(request));
    }

    @Test
    void itShouldSuccess_WithResult_WhenGetByValidId() {
        Choice choice = new Choice();
        choice.setId(1L);
        choice.setContent("Choice A");
        choice.setTextFormat(TextFormat.TEXT_FORMAT_PLAIN);
        choice.setCorrect(true);

        when(mockChoiceRepository.findById(anyLong())).thenReturn(Optional.of(choice));

        Choice res = choiceService.getById(choice.getId());

        assertNotNull(res);
    }

    @Test
    void itShouldSuccess_WithResult_WhenGetByInvalidId() {
        when(mockChoiceRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> choiceService.getById(1L));
    }

    @Test
    void itShouldSuccess_WithResult_WhenUpdateQuestion() {
        Question question = new Question();
        question.setId(1L);
        question.setContent("## Question 1");
        question.setTextFormat(TextFormat.TEXT_FORMAT_MARKDOWN);
        question.setType(Question.Type.MULTIPLE_CHOICE);

        Choice existingChoice = new Choice();
        existingChoice.setId(1L);
        existingChoice.setContent("Choice A");
        existingChoice.setTextFormat(TextFormat.TEXT_FORMAT_PLAIN);
        existingChoice.setCorrect(true);

        Choice choice = new Choice();
        choice.setId(1L);
        choice.setQuestion(question);
        choice.setContent("### Choice A Updated");
        choice.setTextFormat(TextFormat.TEXT_FORMAT_MARKDOWN);
        choice.setCorrect(false);

        when(mockChoiceRepository.findById(anyLong())).thenReturn(Optional.of(existingChoice));
        when(mockChoiceRepository.save(any(Choice.class))).thenReturn(choice);

        Choice res = choiceService.update(choice.getId(), choice);

        assertNotNull(res);
        assertEquals(choice.getContent(), res.getContent());
    }
}
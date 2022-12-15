package com.testycool.testycoolserver.features.question;

import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.features.exam.interfaces.IExamService;
import com.testycool.testycoolserver.features.question.dtos.CreateQuestionRequest;
import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.features.question.interfaces.IQuestionRepository;
import com.testycool.testycoolserver.features.question.interfaces.IQuestionService;
import com.testycool.testycoolserver.shared.constants.TextFormat;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock
    private IQuestionRepository mockQuestionRepository;

    @Mock
    private IExamService mockExamService;

    private IQuestionService questionService;

    @BeforeEach
    void setup() {
        questionService = new QuestionService(mockQuestionRepository, mockExamService);
    }

    @Test
    void itShouldSuccess_WithResult_WhenCreateQuestion() {
        CreateQuestionRequest request = new CreateQuestionRequest();
        request.setExamId(1L);
        request.setContent("Question 1");
        request.setTextFormat(TextFormat.TEXT_FORMAT_PLAIN);
        request.setType(Question.Type.MULTIPLE_CHOICE);

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        Question question = new Question();
        question.setId(1L);
        question.setExam(exam);
        question.setContent(request.getContent());
        question.setTextFormat(request.getTextFormat());
        question.setType(request.getType());

        when(mockExamService.getById(anyLong())).thenReturn(exam);
        when(mockQuestionRepository.save(any(Question.class))).thenReturn(question);

        Question res = questionService.create(request);

        assertNotNull(res);
        assertEquals(request.getContent(), res.getContent());
    }

    @Test
    void itShouldSuccess_WithResult_WhenGetByValidId() {
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        Question question = new Question();
        question.setId(1L);
        question.setExam(exam);
        question.setContent("## Question 1 Updated");
        question.setTextFormat(TextFormat.TEXT_FORMAT_MARKDOWN);
        question.setType(Question.Type.ESSAY);

        when(mockQuestionRepository.findById(anyLong())).thenReturn(Optional.of(question));

        Question res = questionService.getById(question.getId());

        assertNotNull(res);
        assertEquals(question.getContent(), res.getContent());
    }

    @Test
    void itShouldThrow_NotFoundException_WhenGetByInvalidId() {
        when(mockQuestionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> questionService.getById(1L));
    }

    @Test
    void itShouldSuccess_WithResult_WhenUpdateExam() {
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        Question question = new Question();
        question.setId(1L);
        question.setExam(exam);
        question.setContent("## Question 1 Updated");
        question.setTextFormat(TextFormat.TEXT_FORMAT_MARKDOWN);
        question.setType(Question.Type.ESSAY);

        when(mockQuestionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(mockQuestionRepository.save(any(Question.class))).thenReturn(question);

        Question res = questionService.update(question.getId(), question);

        assertNotNull(res);
        assertEquals(question.getContent(), res.getContent());
    }
}
package com.testycool.testycoolserver.features.exam;

import com.testycool.testycoolserver.features.exam.interfaces.IExamRepository;
import com.testycool.testycoolserver.features.exam.interfaces.IExamService;
import com.testycool.testycoolserver.features.exam.dtos.CreateExamRequest;
import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.shared.exceptions.DataExistException;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {
    @Mock
    private IExamRepository mockExamRepository;

    private IExamService examService;

    @BeforeEach
    void setup() {
        examService = new ExamService(mockExamRepository);
    }

    @Test
    void itShouldSuccess_WithResult_WhenCreateExam() {
        CreateExamRequest request = new CreateExamRequest();
        request.setTitle("Exam 1");
        request.setPassword("exam1");
        request.setStartAt(new Date(System.currentTimeMillis()));
        request.setTimeLimit(180000);

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle(request.getTitle());
        exam.setPassword(request.getPassword());
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(request.getStartAt());
        exam.setTimeLimit(request.getTimeLimit());

        when(mockExamRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(mockExamRepository.findByPassword(anyString())).thenReturn(Optional.empty());
        when(mockExamRepository.save(any(Exam.class))).thenReturn(exam);

        Exam res = examService.create(request);

        assertNotNull(res);
        assertEquals(request.getTitle(), res.getTitle());
    }

    @Test
    void itShouldThrow_DataExistException_WhenCreateExamWithDuplicateTitle() {
        CreateExamRequest request = new CreateExamRequest();
        request.setTitle("Exam 1");
        request.setPassword("exam1");
        request.setStartAt(new Date(System.currentTimeMillis()));
        request.setTimeLimit(180000);

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle(request.getTitle());
        exam.setPassword(request.getPassword());
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(request.getStartAt());
        exam.setTimeLimit(request.getTimeLimit());

        when(mockExamRepository.findByTitle(anyString())).thenReturn(Optional.of(exam));

        assertThrows(DataExistException.class, () -> examService.create(request));
    }

    @Test
    void itShouldThrow_DataExistException_WhenCreateExamWithDuplicatePassword() {
        CreateExamRequest request = new CreateExamRequest();
        request.setTitle("Exam 1");
        request.setPassword("exam1");
        request.setStartAt(new Date(System.currentTimeMillis()));
        request.setTimeLimit(180000);

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle(request.getTitle());
        exam.setPassword(request.getPassword());
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(request.getStartAt());
        exam.setTimeLimit(request.getTimeLimit());

        when(mockExamRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(mockExamRepository.findByPassword(anyString())).thenReturn(Optional.of(exam));

        assertThrows(DataExistException.class, () -> examService.create(request));
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

        Exam updatedExam = new Exam();
        updatedExam.setId(1L);
        updatedExam.setTitle("Exam 1 Updated");
        updatedExam.setPassword("exam1updated");
        updatedExam.setStatus(Exam.Status.WAITING);
        updatedExam.setStartAt(new Date(System.currentTimeMillis()));
        updatedExam.setTimeLimit(300000);

        when(mockExamRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(mockExamRepository.findByPassword(anyString())).thenReturn(Optional.empty());
        when(mockExamRepository.findById(anyLong())).thenReturn(Optional.of(exam));
        when(mockExamRepository.save(any(Exam.class))).thenReturn(updatedExam);

        Exam res = examService.update(updatedExam.getId(), updatedExam);

        assertNotNull(res);
        assertEquals(updatedExam.getTitle(), res.getTitle());
    }

    @Test
    void itShouldThrow_DataExistException_WhenUpdateExamWithDuplicateTitle() {
        Exam existingExam = new Exam();
        existingExam.setId(1L);
        existingExam.setTitle("Exam 1");
        existingExam.setPassword("exam1");
        existingExam.setStatus(Exam.Status.WAITING);
        existingExam.setStartAt(new Date(System.currentTimeMillis()));
        existingExam.setTimeLimit(180000);

        Exam exam = new Exam();
        exam.setId(2L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        when(mockExamRepository.findByTitle(anyString())).thenReturn(Optional.of(existingExam));

        assertThrows(DataExistException.class, () -> examService.update(exam.getId(), exam));
    }

    @Test
    void itShouldThrow_DataExistException_WhenUpdateExamWithDuplicatePassword() {
        Exam existingExam = new Exam();
        existingExam.setId(1L);
        existingExam.setTitle("Exam 1");
        existingExam.setPassword("exam1");
        existingExam.setStatus(Exam.Status.WAITING);
        existingExam.setStartAt(new Date(System.currentTimeMillis()));
        existingExam.setTimeLimit(180000);

        Exam exam = new Exam();
        exam.setId(2L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        when(mockExamRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(mockExamRepository.findByPassword(anyString())).thenReturn(Optional.of(existingExam));

        assertThrows(DataExistException.class, () -> examService.update(exam.getId(), exam));
    }

    @Test
    void itShouldThrow_NotFoundException_WhenUpdateExamWithInvalidId() {
        Exam exam = new Exam();
        exam.setId(2L);
        exam.setTitle("Exam 1");
        exam.setPassword("exam1");
        exam.setStatus(Exam.Status.WAITING);
        exam.setStartAt(new Date(System.currentTimeMillis()));
        exam.setTimeLimit(180000);

        when(mockExamRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(mockExamRepository.findByPassword(anyString())).thenReturn(Optional.empty());
        when(mockExamRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> examService.update(exam.getId(), exam));
    }
}
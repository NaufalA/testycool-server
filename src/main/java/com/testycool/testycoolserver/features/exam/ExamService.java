package com.testycool.testycoolserver.features.exam;

import com.testycool.testycoolserver.features.exam.interfaces.IExamRepository;
import com.testycool.testycoolserver.features.exam.interfaces.IExamService;
import com.testycool.testycoolserver.features.exam.dtos.CreateExamRequest;
import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.shared.exceptions.DataExistException;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ExamService implements IExamService {
    private final IExamRepository examRepository;

    public ExamService(IExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Exam create(CreateExamRequest request) {
        if (examRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new DataExistException("Exam with the same title is already exist");
        }
        if (examRepository.findByPassword(request.getPassword()).isPresent()) {
            throw new DataExistException("Exam Password must be unique");
        }


        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setPassword(request.getPassword());
        exam.setStatus(Exam.Status.WAITING);
        exam.setTimeLimit(request.getTimeLimit());
        exam.setStartAt(request.getStartAt());

        return examRepository.save(exam);
    }

    @Override
    public Exam update(Long id, Exam exam) {

        Optional<Exam> existingByTitle = examRepository.findByTitle(exam.getTitle());
        if (existingByTitle.isPresent() && !existingByTitle.get().getId().equals(id)) {
            throw new DataExistException("Exam with the same title is already exist");
        }
        Optional<Exam> existingByPassword = examRepository.findByPassword(exam.getPassword());
        if (existingByPassword.isPresent() && !existingByPassword.get().getId().equals(id)) {
            throw new DataExistException("Exam Password must be unique");
        }

        Exam existingExam = getById(id);

        existingExam.setTitle(exam.getTitle());
        existingExam.setPassword(exam.getPassword());
        existingExam.setStatus(exam.getStatus());
        existingExam.setTimeLimit(exam.getTimeLimit());
        existingExam.setStartAt(exam.getStartAt());

        return examRepository.save(existingExam);
    }

    @Override
    public Exam getByPassword(String password) {
        Optional<Exam> exam = examRepository.findByPassword(password);

        if (exam.isEmpty()) {
            throw new NotFoundException("Exam Data Not Found");
        }

        return exam.get();
    }

    @Override
    public Long delete(Exam item) {
        Exam existingExam = getById(item.getId());
        examRepository.delete(item);

        return existingExam.getId();
    }

    @Override
    public Long deleteById(Long id) {
        Exam existingExam = getById(id);
        examRepository.deleteById(existingExam.getId());

        return existingExam.getId();
    }

    @Override
    public Exam getById(Long id) {
        Optional<Exam> exam = examRepository.findById(id);

        if (exam.isEmpty()) {
            throw new NotFoundException("Exam Data Not Found");
        }

        return exam.get();
    }

    @Override
    public Collection<Exam> getAll() {
        return examRepository.findAll();
    }

    @Override
    public Page<Exam> getAll(Pageable pageable) {
        return examRepository.findAll(pageable);
    }

    @Override
    public Page<Exam> getAll(Specification<Exam> specification, Pageable pageable) {
        return examRepository.findAll(specification, pageable);
    }
}

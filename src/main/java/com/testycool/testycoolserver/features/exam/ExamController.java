package com.testycool.testycoolserver.features.exam;

import com.testycool.testycoolserver.features.exam.dtos.CreateExamRequest;
import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.features.exam.interfaces.IExamService;
import com.testycool.testycoolserver.shared.constants.UrlMapping;
import com.testycool.testycoolserver.shared.dtos.CommonResponse;
import com.testycool.testycoolserver.shared.dtos.PagedResponse;
import com.testycool.testycoolserver.shared.dtos.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(UrlMapping.EXAM_URL)
public class ExamController {
private final IExamService examService;

    public ExamController(IExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody CreateExamRequest request) {
        Exam exam = examService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "New Exam Created",
                exam
        ));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "0") Integer size
    ) {
        if (size <= 0) {
            Collection<Exam> exams = examService.getAll();

            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                    HttpStatus.CREATED.value(),
                    "Get All Exam",
                    exams
            ));
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Exam> exams = examService.getAll(pageable);

            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                    HttpStatus.CREATED.value(),
                    "Get All Exam",
                    new PagedResponse<>(exams)
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") Long id) {
        Exam exam = examService.getById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get Exam With ID " + id,
                exam
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(
            @PathVariable(name = "id") Long id,
            @RequestBody Exam updatedExam
    ) {
        Exam exam = examService.update(id, updatedExam);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Update Exam With ID " + id,
                exam
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable(name = "id") Long id) {
        Long deletedId = examService.deleteById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Delete Exam With ID " + id,
                deletedId
        ));
    }
}

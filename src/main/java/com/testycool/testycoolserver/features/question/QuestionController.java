package com.testycool.testycoolserver.features.question;

import com.testycool.testycoolserver.features.question.dtos.CreateQuestionRequest;
import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.features.question.interfaces.IQuestionService;
import com.testycool.testycoolserver.shared.classes.GenericSpecification;
import com.testycool.testycoolserver.shared.classes.SearchCriteria;
import com.testycool.testycoolserver.shared.constants.QueryOperator;
import com.testycool.testycoolserver.shared.constants.SearchOperation;
import com.testycool.testycoolserver.shared.constants.UrlMapping;
import com.testycool.testycoolserver.shared.dtos.CommonResponse;
import com.testycool.testycoolserver.shared.dtos.PagedResponse;
import com.testycool.testycoolserver.shared.dtos.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.QUESTION_URL)
public class QuestionController {
    private final IQuestionService questionService;

    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody CreateQuestionRequest request) {
        Question question = questionService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "New Question Created",
                question
        ));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAll(
            @RequestParam(name = "examId") Long examId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "0") Integer size
    ) {
        Specification<Question> specification = new GenericSpecification<>(
                new SearchCriteria("examId", examId, QueryOperator.EQUALS, SearchOperation.AND),
                (root, criteria) -> root.join("exam").get("id")
        );

        Pageable pageable;
        if (size <= 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<Question> questions = questionService.getAll(specification, pageable);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get All Question",
                new PagedResponse<>(questions)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") Long id) {
        Question question = questionService.getById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get Question With ID " + id,
                question
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") Long id, @RequestBody Question updatedQuestion) {
        Question question = questionService.update(id, updatedQuestion);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Update Question With ID " + id,
                question
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable(name = "id") Long id) {
        Long deletedId = questionService.deleteById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Delete Question With ID " + id,
                deletedId
        ));
    }
}

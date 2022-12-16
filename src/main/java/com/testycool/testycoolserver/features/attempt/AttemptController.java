package com.testycool.testycoolserver.features.attempt;

import com.testycool.testycoolserver.features.attempt.dtos.CreateAttemptRequest;
import com.testycool.testycoolserver.features.attempt.entities.Attempt;
import com.testycool.testycoolserver.features.attempt.interfaces.IAttemptService;
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
@RequestMapping(UrlMapping.ATTEMPT_URL)
public class AttemptController {
    private final IAttemptService attemptService;

    public AttemptController(IAttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody CreateAttemptRequest request) {
        Attempt attempt = attemptService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "New Attempt Created",
                attempt
        ));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAll(
            @RequestParam(name = "examId") Long examId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "0") Integer size
    ) {
        Specification<Attempt> specification = new GenericSpecification<>(
                new SearchCriteria("examId", examId, QueryOperator.EQUALS, SearchOperation.AND),
                (root, criteria) -> root.join("participantRegistration").join("exam").get("id")
        );

        Pageable pageable;
        if (size <= 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<Attempt> attempts = attemptService.getAll(specification, pageable);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get All Attempt",
                new PagedResponse<>(attempts)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") Long id) {
        Attempt attempt = attemptService.getById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get Attempt With ID " + id,
                attempt
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") Long id, @RequestBody Attempt updatedAttempt) {
        Attempt attempt = attemptService.update(id, updatedAttempt);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Update Attempt With ID " + id,
                attempt
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable(name = "id") Long id) {
        Long deletedId = attemptService.deleteById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Delete Attempt With ID " + id,
                deletedId
        ));
    }
}

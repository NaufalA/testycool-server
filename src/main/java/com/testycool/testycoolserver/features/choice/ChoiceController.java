package com.testycool.testycoolserver.features.choice;

import com.testycool.testycoolserver.features.choice.dtos.CreateChoiceRequest;
import com.testycool.testycoolserver.features.choice.entities.Choice;
import com.testycool.testycoolserver.features.choice.interfaces.IChoiceService;
import com.testycool.testycoolserver.shared.classes.GenericSpecification;
import com.testycool.testycoolserver.shared.classes.SearchCriteria;
import com.testycool.testycoolserver.shared.constants.QueryOperator;
import com.testycool.testycoolserver.shared.constants.SearchOperation;
import com.testycool.testycoolserver.shared.constants.UrlMapping;
import com.testycool.testycoolserver.shared.dtos.CommonResponse;
import com.testycool.testycoolserver.shared.dtos.PagedResponse;
import com.testycool.testycoolserver.shared.dtos.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.CHOICE_URL)
public class ChoiceController {
    private final IChoiceService choiceService;

    public ChoiceController(IChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody CreateChoiceRequest request) {
        Choice choice = choiceService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "New Choice Created",
                choice
        ));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getByQuestionId(
            @RequestParam(name = "questionId") Long questionId
    ) {
        Specification<Choice> specification = new GenericSpecification<>(
                new SearchCriteria("questionId", questionId, QueryOperator.EQUALS, SearchOperation.AND),
                (root, criteria) -> root.join("question").get("id")
        );

        Pageable pageable = Pageable.unpaged();

        Page<Choice> choices = choiceService.getAll(specification, pageable);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get All Choice",
                new PagedResponse<>(choices)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") Long id) {
        Choice choice = choiceService.getById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get Choice With ID " + id,
                choice
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(
            @PathVariable(name = "id") Long id,
            @RequestBody Choice updatedChoice
    ) {
        Choice choice = choiceService.update(id, updatedChoice);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Update Choice With ID " + id,
                choice
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable(name = "id") Long id) {
        Long deletedId = choiceService.deleteById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Delete Choice With ID " + id,
                deletedId
        ));
    }
}

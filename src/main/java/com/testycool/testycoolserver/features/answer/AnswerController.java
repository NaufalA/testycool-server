package com.testycool.testycoolserver.features.answer;

import com.testycool.testycoolserver.features.answer.dtos.AnswerSummary;
import com.testycool.testycoolserver.features.answer.dtos.CreateAnswerRequest;
import com.testycool.testycoolserver.features.answer.entities.Answer;
import com.testycool.testycoolserver.features.answer.interfaces.IAnswerService;
import com.testycool.testycoolserver.shared.constants.UrlMapping;
import com.testycool.testycoolserver.shared.dtos.CommonResponse;
import com.testycool.testycoolserver.shared.dtos.PagedResponse;
import com.testycool.testycoolserver.shared.dtos.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.ANSWER_URL)
public class AnswerController {
    private final IAnswerService answerService;

    public AnswerController(IAnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody CreateAnswerRequest request) {
        Answer answer = answerService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "New Answer Created",
                answer
        ));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getByQuestionId(
            @RequestParam(name = "questionId") Long questionId
    ) {

        Page<Answer> answers = answerService.getByQuestionId(questionId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get All Answer",
                new PagedResponse<>(answers)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") Long id) {
        Answer answer = answerService.getById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get Answer With ID " + id,
                answer
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable(name = "id") Long id, @RequestBody Answer updatedAnswer) {
        Answer answer = answerService.update(id, updatedAnswer);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Update Answer With ID " + id,
                answer
        ));
    }

    @GetMapping("/summary/{questionId}")
    public ResponseEntity<CommonResponse> getAnswerSummary(
            @PathVariable(name = "questionId") Long questionId
    ) throws Exception {
        AnswerSummary summary = answerService.getAnswerSummary(questionId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get Answer Summary for Question With ID " + questionId,
                summary
        ));
    }
}

package com.testycool.testycoolserver.features.participant;

import com.testycool.testycoolserver.features.participant.dtos.ParticipantDto;
import com.testycool.testycoolserver.features.participant.dtos.RegisterParticipantRequest;
import com.testycool.testycoolserver.features.participant.dtos.UpdateParticipantRequest;
import com.testycool.testycoolserver.features.participant.entities.Participant;
import com.testycool.testycoolserver.features.participant.entities.ParticipantRegistration;
import com.testycool.testycoolserver.features.participant.interfaces.IParticipantService;
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
import java.util.List;

@RestController
@RequestMapping(UrlMapping.PARTICIPANT_URL)
public class ParticipantController {

    private final IParticipantService participantService;

    public ParticipantController(IParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody RegisterParticipantRequest request) {
        ParticipantDto participant = participantService.registerParticipant(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "New Participant Registered",
                participant
        ));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAll(
            @RequestParam(name = "examId") Long examId
    ) {
        List<ParticipantDto> participants = participantService.getAllExamParticipant(examId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get All Exam Participant",
                participants
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable(name = "id") Long id) {
        ParticipantRegistration participant = participantService.getById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Get Participant With ID " + id,
                participant
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable(name = "id") Long id) {
        Long deletedId = participantService.unregisterParticipant(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.value(),
                "Delete Participant With ID " + id,
                deletedId
        ));
    }
}

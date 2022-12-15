package com.testycool.testycoolserver.features.participant.interfaces;

import com.testycool.testycoolserver.features.participant.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IParticipantRepository extends JpaRepository<Participant, Long>, JpaSpecificationExecutor<Participant> {
    Optional<Participant> findByName(String name);
}

package com.testycool.testycoolserver.features.participant.interfaces;

import com.testycool.testycoolserver.features.participant.entities.ParticipantRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRegistrationRepository extends JpaRepository<ParticipantRegistration, Long>, JpaSpecificationExecutor<ParticipantRegistration> {
}

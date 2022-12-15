package com.testycool.testycoolserver.features.choice.interfaces;

import com.testycool.testycoolserver.features.choice.entities.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IChoiceRepository extends JpaRepository<Choice, Long>, JpaSpecificationExecutor<Choice> {
}

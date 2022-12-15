package com.testycool.testycoolserver.features.choice.interfaces;

import com.testycool.testycoolserver.features.choice.dtos.CreateChoiceRequest;
import com.testycool.testycoolserver.features.choice.entities.Choice;
import com.testycool.testycoolserver.shared.interfaces.ServiceDeleteExecutor;
import com.testycool.testycoolserver.shared.interfaces.ServiceGetExecutor;
import com.testycool.testycoolserver.shared.interfaces.ServiceUpdateExecutor;

public interface IChoiceService extends ServiceGetExecutor<Choice, Long>, ServiceUpdateExecutor<Choice, Long>, ServiceDeleteExecutor<Choice, Long> {
    Choice create(CreateChoiceRequest request);
}

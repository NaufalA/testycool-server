package com.testycool.testycoolserver.features.exam.interfaces;

import com.testycool.testycoolserver.features.exam.dtos.CreateExamRequest;
import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.shared.interfaces.ServiceDeleteExecutor;
import com.testycool.testycoolserver.shared.interfaces.ServiceGetExecutor;
import com.testycool.testycoolserver.shared.interfaces.ServiceUpdateExecutor;

public interface IExamService extends ServiceGetExecutor<Exam, Long>, ServiceUpdateExecutor<Exam, Long>, ServiceDeleteExecutor<Exam, Long> {
    Exam create(CreateExamRequest request);
    Exam getByPassword(String password);
}

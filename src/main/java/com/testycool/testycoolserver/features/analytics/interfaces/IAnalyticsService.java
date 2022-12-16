package com.testycool.testycoolserver.features.analytics.interfaces;

import com.testycool.testycoolserver.features.analytics.dtos.CreateAnalyticsRequest;
import com.testycool.testycoolserver.features.analytics.entities.Analytics;

import java.util.List;

public interface IAnalyticsService {
    Analytics create(CreateAnalyticsRequest request);
    List<Analytics> getByExamId(Long examId);
}

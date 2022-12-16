package com.testycool.testycoolserver.features.analytics.interfaces;

import com.testycool.testycoolserver.features.analytics.entities.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IAnalyticsRepository extends JpaRepository<Analytics, Long>, JpaSpecificationExecutor<Analytics> {
}

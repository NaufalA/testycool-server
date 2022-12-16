package com.testycool.testycoolserver.features.analytics;

import com.testycool.testycoolserver.features.analytics.dtos.CreateAnalyticsRequest;
import com.testycool.testycoolserver.features.analytics.entities.Analytics;
import com.testycool.testycoolserver.features.analytics.interfaces.IAnalyticsRepository;
import com.testycool.testycoolserver.features.analytics.interfaces.IAnalyticsService;
import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.features.exam.interfaces.IExamService;
import com.testycool.testycoolserver.features.participant.ParticipantService;
import com.testycool.testycoolserver.features.participant.entities.ParticipantRegistration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService implements IAnalyticsService {
    private final IAnalyticsRepository analyticsRepository;
    private final IExamService examService;
    private final ParticipantService participantService;

    public AnalyticsService(IAnalyticsRepository analyticsRepository, IExamService examService, ParticipantService participantService) {
        this.analyticsRepository = analyticsRepository;
        this.examService = examService;
        this.participantService = participantService;
    }

    @Override
    public Analytics create(CreateAnalyticsRequest request) {
        Exam exam = examService.getById(request.getExamId());
        ParticipantRegistration registration = participantService.getById(request.getParticipantId());

        Analytics analytics = new Analytics();
        analytics.setExam(exam);
        analytics.setParticipantRegistration(registration);
        analytics.setMessage(request.getMessage());
        analytics.setCreatedAt(request.getCreatedAt());

        return analyticsRepository.save(analytics);
    }

    @Override
    public List<Analytics> getByExamId(Long examId) {
        return null;
    }
}

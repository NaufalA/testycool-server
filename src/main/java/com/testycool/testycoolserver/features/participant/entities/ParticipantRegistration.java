package com.testycool.testycoolserver.features.participant.entities;

import com.testycool.testycoolserver.features.analytics.entities.Analytics;
import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.shared.entities.SerialBaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "participant_registrations",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_exam_and_participant", columnNames = {"exam_id", "participant_id"})
        }
)
public class ParticipantRegistration extends SerialBaseEntity {
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Column(name = "code", nullable = false)
    private String code;

    @OneToMany(mappedBy = "participantRegistration", orphanRemoval = true)
    private List<Analytics> analyticses = new ArrayList<>();

    public List<Analytics> getAnalyticses() {
        return analyticses;
    }

    public void setAnalyticses(List<Analytics> analyticses) {
        this.analyticses = analyticses;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
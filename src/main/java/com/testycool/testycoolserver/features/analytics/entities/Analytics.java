package com.testycool.testycoolserver.features.analytics.entities;

import com.testycool.testycoolserver.features.exam.entities.Exam;
import com.testycool.testycoolserver.features.participant.entities.ParticipantRegistration;
import com.testycool.testycoolserver.shared.entities.SerialBaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "analytics")
public class Analytics extends SerialBaseEntity {
    @Column(name = "message", nullable = false)
    private String message;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(optional = false)
    @JoinColumn(name = "participant_registration_id", nullable = false)
    private ParticipantRegistration participantRegistration;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public ParticipantRegistration getParticipantRegistration() {
        return participantRegistration;
    }

    public void setParticipantRegistration(ParticipantRegistration participantRegistration) {
        this.participantRegistration = participantRegistration;
    }
}
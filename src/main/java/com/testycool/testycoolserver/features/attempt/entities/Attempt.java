package com.testycool.testycoolserver.features.attempt.entities;

import com.testycool.testycoolserver.features.answer.entities.Answer;
import com.testycool.testycoolserver.features.participant.entities.ParticipantRegistration;
import com.testycool.testycoolserver.shared.entities.SerialBaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "attempts")
public class Attempt extends SerialBaseEntity {

    @Column(name = "corrects", nullable = false)
    private Integer corrects;

    @Column(name = "wrong", nullable = false)
    private Integer wrongs;

    @Column(name = "unanswered", nullable = false)
    private Integer unanswered;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "participant_registration_id")
    private ParticipantRegistration participantRegistration;

    @OneToMany(mappedBy = "attempt", orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    public Integer getCorrects() {
        return corrects;
    }

    public void setCorrects(Integer corrects) {
        this.corrects = corrects;
    }

    public Integer getWrongs() {
        return wrongs;
    }

    public void setWrongs(Integer wrongs) {
        this.wrongs = wrongs;
    }

    public Integer getUnanswered() {
        return unanswered;
    }

    public void setUnanswered(Integer unanswered) {
        this.unanswered = unanswered;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ParticipantRegistration getParticipantRegistration() {
        return participantRegistration;
    }

    public void setParticipantRegistration(ParticipantRegistration participantRegistration) {
        this.participantRegistration = participantRegistration;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public enum Status {
        UNFINISHED,
        FINISHED,
        FORFEITED
    }
}
package com.testycool.testycoolserver.features.exam.entities;

import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.shared.entities.SerialBaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "exams")
public class Exam extends SerialBaseEntity{
    @Column(name = "title", nullable = false, length = 100, unique = true)
    private String title;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "time_limit", nullable = false)
    private Integer timeLimit;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_at", nullable = false)
    private Date startAt = new Date(System.currentTimeMillis());

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.WAITING;

    @OneToMany(mappedBy = "exam", orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public enum Status {
        UNKNOWN,
        WAITING,
        STARTED,
        DONE
    }
}
package com.dhavalapp.classes.domain;

import com.dhavalapp.classes.domain.enumeration.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MockSchedule.
 */
@Entity
@Table(name = "mock_schedule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MockSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private DayOfWeek day;

    @NotNull
    @Column(name = "timing", nullable = false)
    private Instant timing;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mockSchedules", "course", "center" }, allowSetters = true)
    private Batch batch;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MockSchedule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDay() {
        return this.day;
    }

    public MockSchedule day(DayOfWeek day) {
        this.setDay(day);
        return this;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public Instant getTiming() {
        return this.timing;
    }

    public MockSchedule timing(Instant timing) {
        this.setTiming(timing);
        return this;
    }

    public void setTiming(Instant timing) {
        this.timing = timing;
    }

    public Batch getBatch() {
        return this.batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public MockSchedule batch(Batch batch) {
        this.setBatch(batch);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MockSchedule)) {
            return false;
        }
        return id != null && id.equals(((MockSchedule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MockSchedule{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", timing='" + getTiming() + "'" +
            "}";
    }
}

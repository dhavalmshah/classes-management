package com.dhavalapp.classes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Batch.
 */
@Entity
@Table(name = "batch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Batch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 30)
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Min(value = 1)
    @Column(name = "seats", nullable = false)
    private Integer seats;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "batch" }, allowSetters = true)
    private Set<MockSchedule> mockSchedules = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "enrollments", "school" }, allowSetters = true)
    private Course course;

    @ManyToOne
    private Center center;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Batch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Batch duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getSeats() {
        return this.seats;
    }

    public Batch seats(Integer seats) {
        this.setSeats(seats);
        return this;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Set<MockSchedule> getMockSchedules() {
        return this.mockSchedules;
    }

    public void setMockSchedules(Set<MockSchedule> mockSchedules) {
        if (this.mockSchedules != null) {
            this.mockSchedules.forEach(i -> i.setBatch(null));
        }
        if (mockSchedules != null) {
            mockSchedules.forEach(i -> i.setBatch(this));
        }
        this.mockSchedules = mockSchedules;
    }

    public Batch mockSchedules(Set<MockSchedule> mockSchedules) {
        this.setMockSchedules(mockSchedules);
        return this;
    }

    public Batch addMockSchedule(MockSchedule mockSchedule) {
        this.mockSchedules.add(mockSchedule);
        mockSchedule.setBatch(this);
        return this;
    }

    public Batch removeMockSchedule(MockSchedule mockSchedule) {
        this.mockSchedules.remove(mockSchedule);
        mockSchedule.setBatch(null);
        return this;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Batch course(Course course) {
        this.setCourse(course);
        return this;
    }

    public Center getCenter() {
        return this.center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public Batch center(Center center) {
        this.setCenter(center);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Batch)) {
            return false;
        }
        return id != null && id.equals(((Batch) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Batch{" +
                "id=" + getId() +
                ", duration=" + getDuration() +
                ", seats=" + getSeats() +
                "}";
    }
}

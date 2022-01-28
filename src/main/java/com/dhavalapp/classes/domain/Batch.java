package com.dhavalapp.classes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
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

    @Column(name = "name")
    private String name;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "batch")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "batch" }, allowSetters = true)
    private Set<MockSchedule> mockSchedules = new HashSet<>();

    @OneToMany(mappedBy = "course")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fees", "student", "course" }, allowSetters = true)
    private Set<Enrollment> enrollments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "school", "year" }, allowSetters = true)
    private Course course;

    @ManyToOne
    private Center center;

    @ManyToOne
    private Year year;

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

    public String getName() {
        return this.name;
    }

    public Batch name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return this.notes;
    }

    public Batch notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Set<Enrollment> getEnrollments() {
        return this.enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        if (this.enrollments != null) {
            this.enrollments.forEach(i -> i.setCourse(null));
        }
        if (enrollments != null) {
            enrollments.forEach(i -> i.setCourse(this));
        }
        this.enrollments = enrollments;
    }

    public Batch enrollments(Set<Enrollment> enrollments) {
        this.setEnrollments(enrollments);
        return this;
    }

    public Batch addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setCourse(this);
        return this;
    }

    public Batch removeEnrollment(Enrollment enrollment) {
        this.enrollments.remove(enrollment);
        enrollment.setCourse(null);
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

    public Year getYear() {
        return this.year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Batch year(Year year) {
        this.setYear(year);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Batch{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}

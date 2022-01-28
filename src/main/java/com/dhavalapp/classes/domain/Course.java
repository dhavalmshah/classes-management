package com.dhavalapp.classes.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotNull
    @DecimalMin(value = "500")
    @Column(name = "course_cost", precision = 21, scale = 2, nullable = false)
    private BigDecimal courseCost;

    @NotNull
    @Min(value = 30)
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Min(value = 1)
    @Column(name = "seats", nullable = false)
    private Integer seats;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    private School school;

    @ManyToOne
    private Year year;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Course id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public Course courseName(String courseName) {
        this.setCourseName(courseName);
        return this;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigDecimal getCourseCost() {
        return this.courseCost;
    }

    public Course courseCost(BigDecimal courseCost) {
        this.setCourseCost(courseCost);
        return this;
    }

    public void setCourseCost(BigDecimal courseCost) {
        this.courseCost = courseCost;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Course duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getSeats() {
        return this.seats;
    }

    public Course seats(Integer seats) {
        this.setSeats(seats);
        return this;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getNotes() {
        return this.notes;
    }

    public Course notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public School getSchool() {
        return this.school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Course school(School school) {
        this.setSchool(school);
        return this;
    }

    public Year getYear() {
        return this.year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Course year(Year year) {
        this.setYear(year);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseName='" + getCourseName() + "'" +
            ", courseCost=" + getCourseCost() +
            ", duration=" + getDuration() +
            ", seats=" + getSeats() +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}

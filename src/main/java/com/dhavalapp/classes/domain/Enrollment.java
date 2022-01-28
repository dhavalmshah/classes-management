package com.dhavalapp.classes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Enrollment.
 */
@Entity
@Table(name = "enrollment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enrollment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "notes", nullable = false)
    private String notes;

    @JsonIgnoreProperties(value = { "student", "bank", "year" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private FinanceEntry fees;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enrollments", "school", "year" }, allowSetters = true)
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mockSchedules", "enrollments", "course", "center", "year" }, allowSetters = true)
    private Batch course;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Enrollment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return this.notes;
    }

    public Enrollment notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public FinanceEntry getFees() {
        return this.fees;
    }

    public void setFees(FinanceEntry financeEntry) {
        this.fees = financeEntry;
    }

    public Enrollment fees(FinanceEntry financeEntry) {
        this.setFees(financeEntry);
        return this;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Enrollment student(Student student) {
        this.setStudent(student);
        return this;
    }

    public Batch getCourse() {
        return this.course;
    }

    public void setCourse(Batch batch) {
        this.course = batch;
    }

    public Enrollment course(Batch batch) {
        this.setCourse(batch);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enrollment)) {
            return false;
        }
        return id != null && id.equals(((Enrollment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enrollment{" +
            "id=" + getId() +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}

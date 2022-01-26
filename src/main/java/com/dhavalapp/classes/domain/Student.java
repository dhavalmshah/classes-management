package com.dhavalapp.classes.domain;

import com.dhavalapp.classes.domain.enumeration.Standard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Enumerated(EnumType.STRING)
    @Column(name = "standard")
    private Standard standard;

    @Column(name = "student_phone")
    private String studentPhone;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "parent_phone")
    private String parentPhone;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fees", "student", "course" }, allowSetters = true)
    private Set<Enrollment> enrollments = new HashSet<>();

    @ManyToOne
    private School school;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public Student studentName(String studentName) {
        this.setStudentName(studentName);
        return this;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Standard getStandard() {
        return this.standard;
    }

    public Student standard(Standard standard) {
        this.setStandard(standard);
        return this;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public String getStudentPhone() {
        return this.studentPhone;
    }

    public Student studentPhone(String studentPhone) {
        this.setStudentPhone(studentPhone);
        return this;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getParentName() {
        return this.parentName;
    }

    public Student parentName(String parentName) {
        this.setParentName(parentName);
        return this;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return this.parentPhone;
    }

    public Student parentPhone(String parentPhone) {
        this.setParentPhone(parentPhone);
        return this;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public Set<Enrollment> getEnrollments() {
        return this.enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        if (this.enrollments != null) {
            this.enrollments.forEach(i -> i.setStudent(null));
        }
        if (enrollments != null) {
            enrollments.forEach(i -> i.setStudent(this));
        }
        this.enrollments = enrollments;
    }

    public Student enrollments(Set<Enrollment> enrollments) {
        this.setEnrollments(enrollments);
        return this;
    }

    public Student addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setStudent(this);
        return this;
    }

    public Student removeEnrollment(Enrollment enrollment) {
        this.enrollments.remove(enrollment);
        enrollment.setStudent(null);
        return this;
    }

    public School getSchool() {
        return this.school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Student school(School school) {
        this.setSchool(school);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentName='" + getStudentName() + "'" +
            ", standard='" + getStandard() + "'" +
            ", studentPhone='" + getStudentPhone() + "'" +
            ", parentName='" + getParentName() + "'" +
            ", parentPhone='" + getParentPhone() + "'" +
            "}";
    }
}

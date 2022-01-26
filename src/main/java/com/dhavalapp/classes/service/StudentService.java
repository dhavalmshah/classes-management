package com.dhavalapp.classes.service;

import com.dhavalapp.classes.domain.Student;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Student}.
 */
public interface StudentService {
    /**
     * Save a student.
     *
     * @param student the entity to save.
     * @return the persisted entity.
     */
    Student save(Student student);

    /**
     * Partially updates a student.
     *
     * @param student the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Student> partialUpdate(Student student);

    /**
     * Get all the students.
     *
     * @return the list of entities.
     */
    List<Student> findAll();

    /**
     * Get the "id" student.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Student> findOne(Long id);

    /**
     * Delete the "id" student.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

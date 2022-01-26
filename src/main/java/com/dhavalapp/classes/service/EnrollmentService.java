package com.dhavalapp.classes.service;

import com.dhavalapp.classes.domain.Enrollment;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Enrollment}.
 */
public interface EnrollmentService {
    /**
     * Save a enrollment.
     *
     * @param enrollment the entity to save.
     * @return the persisted entity.
     */
    Enrollment save(Enrollment enrollment);

    /**
     * Partially updates a enrollment.
     *
     * @param enrollment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Enrollment> partialUpdate(Enrollment enrollment);

    /**
     * Get all the enrollments.
     *
     * @return the list of entities.
     */
    List<Enrollment> findAll();

    /**
     * Get the "id" enrollment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Enrollment> findOne(Long id);

    /**
     * Delete the "id" enrollment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

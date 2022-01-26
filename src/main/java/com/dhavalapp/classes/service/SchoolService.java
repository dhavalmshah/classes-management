package com.dhavalapp.classes.service;

import com.dhavalapp.classes.domain.School;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link School}.
 */
public interface SchoolService {
    /**
     * Save a school.
     *
     * @param school the entity to save.
     * @return the persisted entity.
     */
    School save(School school);

    /**
     * Partially updates a school.
     *
     * @param school the entity to update partially.
     * @return the persisted entity.
     */
    Optional<School> partialUpdate(School school);

    /**
     * Get all the schools.
     *
     * @return the list of entities.
     */
    List<School> findAll();

    /**
     * Get the "id" school.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<School> findOne(Long id);

    /**
     * Delete the "id" school.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

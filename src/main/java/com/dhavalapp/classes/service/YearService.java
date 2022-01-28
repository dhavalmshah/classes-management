package com.dhavalapp.classes.service;

import com.dhavalapp.classes.domain.Year;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Year}.
 */
public interface YearService {
    /**
     * Save a year.
     *
     * @param year the entity to save.
     * @return the persisted entity.
     */
    Year save(Year year);

    /**
     * Partially updates a year.
     *
     * @param year the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Year> partialUpdate(Year year);

    /**
     * Get all the years.
     *
     * @return the list of entities.
     */
    List<Year> findAll();

    /**
     * Get the "id" year.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Year> findOne(Long id);

    /**
     * Delete the "id" year.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package com.dhavalapp.classes.service;

import com.dhavalapp.classes.domain.Center;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Center}.
 */
public interface CenterService {
    /**
     * Save a center.
     *
     * @param center the entity to save.
     * @return the persisted entity.
     */
    Center save(Center center);

    /**
     * Partially updates a center.
     *
     * @param center the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Center> partialUpdate(Center center);

    /**
     * Get all the centers.
     *
     * @return the list of entities.
     */
    List<Center> findAll();

    /**
     * Get the "id" center.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Center> findOne(Long id);

    /**
     * Delete the "id" center.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

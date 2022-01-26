package com.dhavalapp.classes.service;

import com.dhavalapp.classes.domain.Batch;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Batch}.
 */
public interface BatchService {
    /**
     * Save a batch.
     *
     * @param batch the entity to save.
     * @return the persisted entity.
     */
    Batch save(Batch batch);

    /**
     * Partially updates a batch.
     *
     * @param batch the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Batch> partialUpdate(Batch batch);

    /**
     * Get all the batches.
     *
     * @return the list of entities.
     */
    List<Batch> findAll();

    /**
     * Get the "id" batch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Batch> findOne(Long id);

    /**
     * Delete the "id" batch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

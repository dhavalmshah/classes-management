package com.dhavalapp.classes.service;

import com.dhavalapp.classes.domain.MockSchedule;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MockSchedule}.
 */
public interface MockScheduleService {
    /**
     * Save a mockSchedule.
     *
     * @param mockSchedule the entity to save.
     * @return the persisted entity.
     */
    MockSchedule save(MockSchedule mockSchedule);

    /**
     * Partially updates a mockSchedule.
     *
     * @param mockSchedule the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MockSchedule> partialUpdate(MockSchedule mockSchedule);

    /**
     * Get all the mockSchedules.
     *
     * @return the list of entities.
     */
    List<MockSchedule> findAll();

    /**
     * Get the "id" mockSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MockSchedule> findOne(Long id);

    /**
     * Delete the "id" mockSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

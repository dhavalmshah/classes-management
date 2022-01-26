package com.dhavalapp.classes.service;

import com.dhavalapp.classes.domain.FinanceEntry;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FinanceEntry}.
 */
public interface FinanceEntryService {
    /**
     * Save a financeEntry.
     *
     * @param financeEntry the entity to save.
     * @return the persisted entity.
     */
    FinanceEntry save(FinanceEntry financeEntry);

    /**
     * Partially updates a financeEntry.
     *
     * @param financeEntry the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FinanceEntry> partialUpdate(FinanceEntry financeEntry);

    /**
     * Get all the financeEntries.
     *
     * @return the list of entities.
     */
    List<FinanceEntry> findAll();

    /**
     * Get the "id" financeEntry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FinanceEntry> findOne(Long id);

    /**
     * Delete the "id" financeEntry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

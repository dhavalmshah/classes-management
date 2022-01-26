package com.dhavalapp.classes.service.impl;

import com.dhavalapp.classes.domain.FinanceEntry;
import com.dhavalapp.classes.repository.FinanceEntryRepository;
import com.dhavalapp.classes.service.FinanceEntryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FinanceEntry}.
 */
@Service
@Transactional
public class FinanceEntryServiceImpl implements FinanceEntryService {

    private final Logger log = LoggerFactory.getLogger(FinanceEntryServiceImpl.class);

    private final FinanceEntryRepository financeEntryRepository;

    public FinanceEntryServiceImpl(FinanceEntryRepository financeEntryRepository) {
        this.financeEntryRepository = financeEntryRepository;
    }

    @Override
    public FinanceEntry save(FinanceEntry financeEntry) {
        log.debug("Request to save FinanceEntry : {}", financeEntry);
        return financeEntryRepository.save(financeEntry);
    }

    @Override
    public Optional<FinanceEntry> partialUpdate(FinanceEntry financeEntry) {
        log.debug("Request to partially update FinanceEntry : {}", financeEntry);

        return financeEntryRepository
            .findById(financeEntry.getId())
            .map(existingFinanceEntry -> {
                if (financeEntry.getTransactionDate() != null) {
                    existingFinanceEntry.setTransactionDate(financeEntry.getTransactionDate());
                }
                if (financeEntry.getAmount() != null) {
                    existingFinanceEntry.setAmount(financeEntry.getAmount());
                }
                if (financeEntry.getNotes() != null) {
                    existingFinanceEntry.setNotes(financeEntry.getNotes());
                }

                return existingFinanceEntry;
            })
            .map(financeEntryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FinanceEntry> findAll() {
        log.debug("Request to get all FinanceEntries");
        return financeEntryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FinanceEntry> findOne(Long id) {
        log.debug("Request to get FinanceEntry : {}", id);
        return financeEntryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinanceEntry : {}", id);
        financeEntryRepository.deleteById(id);
    }
}

package com.dhavalapp.classes.service.impl;

import com.dhavalapp.classes.domain.Batch;
import com.dhavalapp.classes.repository.BatchRepository;
import com.dhavalapp.classes.service.BatchService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Batch}.
 */
@Service
@Transactional
public class BatchServiceImpl implements BatchService {

    private final Logger log = LoggerFactory.getLogger(BatchServiceImpl.class);

    private final BatchRepository batchRepository;

    public BatchServiceImpl(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Override
    public Batch save(Batch batch) {
        log.debug("Request to save Batch : {}", batch);
        return batchRepository.save(batch);
    }

    @Override
    public Optional<Batch> partialUpdate(Batch batch) {
        log.debug("Request to partially update Batch : {}", batch);

        return batchRepository
            .findById(batch.getId())
            .map(existingBatch -> {
                if (batch.getDuration() != null) {
                    existingBatch.setDuration(batch.getDuration());
                }
                if (batch.getSeats() != null) {
                    existingBatch.setSeats(batch.getSeats());
                }

                return existingBatch;
            })
            .map(batchRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Batch> findAll() {
        log.debug("Request to get all Batches");
        return batchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Batch> findOne(Long id) {
        log.debug("Request to get Batch : {}", id);
        return batchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Batch : {}", id);
        batchRepository.deleteById(id);
    }
}

package com.dhavalapp.classes.service.impl;

import com.dhavalapp.classes.domain.Center;
import com.dhavalapp.classes.repository.CenterRepository;
import com.dhavalapp.classes.service.CenterService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Center}.
 */
@Service
@Transactional
public class CenterServiceImpl implements CenterService {

    private final Logger log = LoggerFactory.getLogger(CenterServiceImpl.class);

    private final CenterRepository centerRepository;

    public CenterServiceImpl(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    @Override
    public Center save(Center center) {
        log.debug("Request to save Center : {}", center);
        return centerRepository.save(center);
    }

    @Override
    public Optional<Center> partialUpdate(Center center) {
        log.debug("Request to partially update Center : {}", center);

        return centerRepository
            .findById(center.getId())
            .map(existingCenter -> {
                if (center.getCenterName() != null) {
                    existingCenter.setCenterName(center.getCenterName());
                }

                return existingCenter;
            })
            .map(centerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Center> findAll() {
        log.debug("Request to get all Centers");
        return centerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Center> findOne(Long id) {
        log.debug("Request to get Center : {}", id);
        return centerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Center : {}", id);
        centerRepository.deleteById(id);
    }
}

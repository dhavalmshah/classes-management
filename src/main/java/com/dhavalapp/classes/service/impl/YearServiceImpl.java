package com.dhavalapp.classes.service.impl;

import com.dhavalapp.classes.domain.Year;
import com.dhavalapp.classes.repository.YearRepository;
import com.dhavalapp.classes.service.YearService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Year}.
 */
@Service
@Transactional
public class YearServiceImpl implements YearService {

    private final Logger log = LoggerFactory.getLogger(YearServiceImpl.class);

    private final YearRepository yearRepository;

    public YearServiceImpl(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    @Override
    public Year save(Year year) {
        log.debug("Request to save Year : {}", year);
        return yearRepository.save(year);
    }

    @Override
    public Optional<Year> partialUpdate(Year year) {
        log.debug("Request to partially update Year : {}", year);

        return yearRepository
            .findById(year.getId())
            .map(existingYear -> {
                if (year.getYear() != null) {
                    existingYear.setYear(year.getYear());
                }

                return existingYear;
            })
            .map(yearRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Year> findAll() {
        log.debug("Request to get all Years");
        return yearRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Year> findOne(Long id) {
        log.debug("Request to get Year : {}", id);
        return yearRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Year : {}", id);
        yearRepository.deleteById(id);
    }
}

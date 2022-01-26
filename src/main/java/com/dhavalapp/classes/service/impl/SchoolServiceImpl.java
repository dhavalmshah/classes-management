package com.dhavalapp.classes.service.impl;

import com.dhavalapp.classes.domain.School;
import com.dhavalapp.classes.repository.SchoolRepository;
import com.dhavalapp.classes.service.SchoolService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link School}.
 */
@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    private final Logger log = LoggerFactory.getLogger(SchoolServiceImpl.class);

    private final SchoolRepository schoolRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public School save(School school) {
        log.debug("Request to save School : {}", school);
        return schoolRepository.save(school);
    }

    @Override
    public Optional<School> partialUpdate(School school) {
        log.debug("Request to partially update School : {}", school);

        return schoolRepository
            .findById(school.getId())
            .map(existingSchool -> {
                if (school.getSchoolName() != null) {
                    existingSchool.setSchoolName(school.getSchoolName());
                }

                return existingSchool;
            })
            .map(schoolRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<School> findAll() {
        log.debug("Request to get all Schools");
        return schoolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<School> findOne(Long id) {
        log.debug("Request to get School : {}", id);
        return schoolRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete School : {}", id);
        schoolRepository.deleteById(id);
    }
}

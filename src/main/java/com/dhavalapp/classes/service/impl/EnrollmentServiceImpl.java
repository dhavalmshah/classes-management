package com.dhavalapp.classes.service.impl;

import com.dhavalapp.classes.domain.Enrollment;
import com.dhavalapp.classes.repository.EnrollmentRepository;
import com.dhavalapp.classes.service.EnrollmentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Enrollment}.
 */
@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final Logger log = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        log.debug("Request to save Enrollment : {}", enrollment);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Optional<Enrollment> partialUpdate(Enrollment enrollment) {
        log.debug("Request to partially update Enrollment : {}", enrollment);

        return enrollmentRepository
            .findById(enrollment.getId())
            .map(existingEnrollment -> {
                if (enrollment.getNotes() != null) {
                    existingEnrollment.setNotes(enrollment.getNotes());
                }

                return existingEnrollment;
            })
            .map(enrollmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findAll() {
        log.debug("Request to get all Enrollments");
        return enrollmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enrollment> findOne(Long id) {
        log.debug("Request to get Enrollment : {}", id);
        return enrollmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enrollment : {}", id);
        enrollmentRepository.deleteById(id);
    }
}

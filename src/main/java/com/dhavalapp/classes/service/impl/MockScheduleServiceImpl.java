package com.dhavalapp.classes.service.impl;

import com.dhavalapp.classes.domain.MockSchedule;
import com.dhavalapp.classes.repository.MockScheduleRepository;
import com.dhavalapp.classes.service.MockScheduleService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MockSchedule}.
 */
@Service
@Transactional
public class MockScheduleServiceImpl implements MockScheduleService {

    private final Logger log = LoggerFactory.getLogger(MockScheduleServiceImpl.class);

    private final MockScheduleRepository mockScheduleRepository;

    public MockScheduleServiceImpl(MockScheduleRepository mockScheduleRepository) {
        this.mockScheduleRepository = mockScheduleRepository;
    }

    @Override
    public MockSchedule save(MockSchedule mockSchedule) {
        log.debug("Request to save MockSchedule : {}", mockSchedule);
        return mockScheduleRepository.save(mockSchedule);
    }

    @Override
    public Optional<MockSchedule> partialUpdate(MockSchedule mockSchedule) {
        log.debug("Request to partially update MockSchedule : {}", mockSchedule);

        return mockScheduleRepository
            .findById(mockSchedule.getId())
            .map(existingMockSchedule -> {
                if (mockSchedule.getDay() != null) {
                    existingMockSchedule.setDay(mockSchedule.getDay());
                }
                if (mockSchedule.getTiming() != null) {
                    existingMockSchedule.setTiming(mockSchedule.getTiming());
                }

                return existingMockSchedule;
            })
            .map(mockScheduleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockSchedule> findAll() {
        log.debug("Request to get all MockSchedules");
        return mockScheduleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MockSchedule> findOne(Long id) {
        log.debug("Request to get MockSchedule : {}", id);
        return mockScheduleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MockSchedule : {}", id);
        mockScheduleRepository.deleteById(id);
    }
}

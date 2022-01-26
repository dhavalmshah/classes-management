package com.dhavalapp.classes.repository;

import com.dhavalapp.classes.domain.MockSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MockSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MockScheduleRepository extends JpaRepository<MockSchedule, Long> {}

package com.dhavalapp.classes.repository;

import com.dhavalapp.classes.domain.Year;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Year entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YearRepository extends JpaRepository<Year, Long> {}

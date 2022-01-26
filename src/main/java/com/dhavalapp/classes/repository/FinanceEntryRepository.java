package com.dhavalapp.classes.repository;

import com.dhavalapp.classes.domain.FinanceEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FinanceEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanceEntryRepository extends JpaRepository<FinanceEntry, Long> {}

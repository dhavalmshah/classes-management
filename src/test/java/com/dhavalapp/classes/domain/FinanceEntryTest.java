package com.dhavalapp.classes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dhavalapp.classes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FinanceEntryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanceEntry.class);
        FinanceEntry financeEntry1 = new FinanceEntry();
        financeEntry1.setId(1L);
        FinanceEntry financeEntry2 = new FinanceEntry();
        financeEntry2.setId(financeEntry1.getId());
        assertThat(financeEntry1).isEqualTo(financeEntry2);
        financeEntry2.setId(2L);
        assertThat(financeEntry1).isNotEqualTo(financeEntry2);
        financeEntry1.setId(null);
        assertThat(financeEntry1).isNotEqualTo(financeEntry2);
    }
}

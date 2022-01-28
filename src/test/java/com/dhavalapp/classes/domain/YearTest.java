package com.dhavalapp.classes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dhavalapp.classes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class YearTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Year.class);
        Year year1 = new Year();
        year1.setId(1L);
        Year year2 = new Year();
        year2.setId(year1.getId());
        assertThat(year1).isEqualTo(year2);
        year2.setId(2L);
        assertThat(year1).isNotEqualTo(year2);
        year1.setId(null);
        assertThat(year1).isNotEqualTo(year2);
    }
}

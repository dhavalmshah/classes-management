package com.dhavalapp.classes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dhavalapp.classes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MockScheduleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MockSchedule.class);
        MockSchedule mockSchedule1 = new MockSchedule();
        mockSchedule1.setId(1L);
        MockSchedule mockSchedule2 = new MockSchedule();
        mockSchedule2.setId(mockSchedule1.getId());
        assertThat(mockSchedule1).isEqualTo(mockSchedule2);
        mockSchedule2.setId(2L);
        assertThat(mockSchedule1).isNotEqualTo(mockSchedule2);
        mockSchedule1.setId(null);
        assertThat(mockSchedule1).isNotEqualTo(mockSchedule2);
    }
}

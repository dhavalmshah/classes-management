package com.dhavalapp.classes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dhavalapp.classes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Batch.class);
        Batch batch1 = new Batch();
        batch1.setId(1L);
        Batch batch2 = new Batch();
        batch2.setId(batch1.getId());
        assertThat(batch1).isEqualTo(batch2);
        batch2.setId(2L);
        assertThat(batch1).isNotEqualTo(batch2);
        batch1.setId(null);
        assertThat(batch1).isNotEqualTo(batch2);
    }
}

package com.dhavalapp.classes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dhavalapp.classes.IntegrationTest;
import com.dhavalapp.classes.domain.MockSchedule;
import com.dhavalapp.classes.domain.enumeration.DayOfWeek;
import com.dhavalapp.classes.repository.MockScheduleRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MockScheduleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MockScheduleResourceIT {

    private static final DayOfWeek DEFAULT_DAY = DayOfWeek.Sunday;
    private static final DayOfWeek UPDATED_DAY = DayOfWeek.Monday;

    private static final Instant DEFAULT_TIMING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/mock-schedules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MockScheduleRepository mockScheduleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMockScheduleMockMvc;

    private MockSchedule mockSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MockSchedule createEntity(EntityManager em) {
        MockSchedule mockSchedule = new MockSchedule().day(DEFAULT_DAY).timing(DEFAULT_TIMING);
        return mockSchedule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MockSchedule createUpdatedEntity(EntityManager em) {
        MockSchedule mockSchedule = new MockSchedule().day(UPDATED_DAY).timing(UPDATED_TIMING);
        return mockSchedule;
    }

    @BeforeEach
    public void initTest() {
        mockSchedule = createEntity(em);
    }

    @Test
    @Transactional
    void createMockSchedule() throws Exception {
        int databaseSizeBeforeCreate = mockScheduleRepository.findAll().size();
        // Create the MockSchedule
        restMockScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mockSchedule)))
            .andExpect(status().isCreated());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        MockSchedule testMockSchedule = mockScheduleList.get(mockScheduleList.size() - 1);
        assertThat(testMockSchedule.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testMockSchedule.getTiming()).isEqualTo(DEFAULT_TIMING);
    }

    @Test
    @Transactional
    void createMockScheduleWithExistingId() throws Exception {
        // Create the MockSchedule with an existing ID
        mockSchedule.setId(1L);

        int databaseSizeBeforeCreate = mockScheduleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMockScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mockSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = mockScheduleRepository.findAll().size();
        // set the field null
        mockSchedule.setDay(null);

        // Create the MockSchedule, which fails.

        restMockScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mockSchedule)))
            .andExpect(status().isBadRequest());

        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimingIsRequired() throws Exception {
        int databaseSizeBeforeTest = mockScheduleRepository.findAll().size();
        // set the field null
        mockSchedule.setTiming(null);

        // Create the MockSchedule, which fails.

        restMockScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mockSchedule)))
            .andExpect(status().isBadRequest());

        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMockSchedules() throws Exception {
        // Initialize the database
        mockScheduleRepository.saveAndFlush(mockSchedule);

        // Get all the mockScheduleList
        restMockScheduleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mockSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].timing").value(hasItem(DEFAULT_TIMING.toString())));
    }

    @Test
    @Transactional
    void getMockSchedule() throws Exception {
        // Initialize the database
        mockScheduleRepository.saveAndFlush(mockSchedule);

        // Get the mockSchedule
        restMockScheduleMockMvc
            .perform(get(ENTITY_API_URL_ID, mockSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mockSchedule.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.timing").value(DEFAULT_TIMING.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMockSchedule() throws Exception {
        // Get the mockSchedule
        restMockScheduleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMockSchedule() throws Exception {
        // Initialize the database
        mockScheduleRepository.saveAndFlush(mockSchedule);

        int databaseSizeBeforeUpdate = mockScheduleRepository.findAll().size();

        // Update the mockSchedule
        MockSchedule updatedMockSchedule = mockScheduleRepository.findById(mockSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedMockSchedule are not directly saved in db
        em.detach(updatedMockSchedule);
        updatedMockSchedule.day(UPDATED_DAY).timing(UPDATED_TIMING);

        restMockScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMockSchedule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMockSchedule))
            )
            .andExpect(status().isOk());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeUpdate);
        MockSchedule testMockSchedule = mockScheduleList.get(mockScheduleList.size() - 1);
        assertThat(testMockSchedule.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testMockSchedule.getTiming()).isEqualTo(UPDATED_TIMING);
    }

    @Test
    @Transactional
    void putNonExistingMockSchedule() throws Exception {
        int databaseSizeBeforeUpdate = mockScheduleRepository.findAll().size();
        mockSchedule.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMockScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mockSchedule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mockSchedule))
            )
            .andExpect(status().isBadRequest());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMockSchedule() throws Exception {
        int databaseSizeBeforeUpdate = mockScheduleRepository.findAll().size();
        mockSchedule.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMockScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mockSchedule))
            )
            .andExpect(status().isBadRequest());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMockSchedule() throws Exception {
        int databaseSizeBeforeUpdate = mockScheduleRepository.findAll().size();
        mockSchedule.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMockScheduleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mockSchedule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMockScheduleWithPatch() throws Exception {
        // Initialize the database
        mockScheduleRepository.saveAndFlush(mockSchedule);

        int databaseSizeBeforeUpdate = mockScheduleRepository.findAll().size();

        // Update the mockSchedule using partial update
        MockSchedule partialUpdatedMockSchedule = new MockSchedule();
        partialUpdatedMockSchedule.setId(mockSchedule.getId());

        partialUpdatedMockSchedule.day(UPDATED_DAY).timing(UPDATED_TIMING);

        restMockScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMockSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMockSchedule))
            )
            .andExpect(status().isOk());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeUpdate);
        MockSchedule testMockSchedule = mockScheduleList.get(mockScheduleList.size() - 1);
        assertThat(testMockSchedule.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testMockSchedule.getTiming()).isEqualTo(UPDATED_TIMING);
    }

    @Test
    @Transactional
    void fullUpdateMockScheduleWithPatch() throws Exception {
        // Initialize the database
        mockScheduleRepository.saveAndFlush(mockSchedule);

        int databaseSizeBeforeUpdate = mockScheduleRepository.findAll().size();

        // Update the mockSchedule using partial update
        MockSchedule partialUpdatedMockSchedule = new MockSchedule();
        partialUpdatedMockSchedule.setId(mockSchedule.getId());

        partialUpdatedMockSchedule.day(UPDATED_DAY).timing(UPDATED_TIMING);

        restMockScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMockSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMockSchedule))
            )
            .andExpect(status().isOk());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeUpdate);
        MockSchedule testMockSchedule = mockScheduleList.get(mockScheduleList.size() - 1);
        assertThat(testMockSchedule.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testMockSchedule.getTiming()).isEqualTo(UPDATED_TIMING);
    }

    @Test
    @Transactional
    void patchNonExistingMockSchedule() throws Exception {
        int databaseSizeBeforeUpdate = mockScheduleRepository.findAll().size();
        mockSchedule.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMockScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mockSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mockSchedule))
            )
            .andExpect(status().isBadRequest());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMockSchedule() throws Exception {
        int databaseSizeBeforeUpdate = mockScheduleRepository.findAll().size();
        mockSchedule.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMockScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mockSchedule))
            )
            .andExpect(status().isBadRequest());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMockSchedule() throws Exception {
        int databaseSizeBeforeUpdate = mockScheduleRepository.findAll().size();
        mockSchedule.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMockScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mockSchedule))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MockSchedule in the database
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMockSchedule() throws Exception {
        // Initialize the database
        mockScheduleRepository.saveAndFlush(mockSchedule);

        int databaseSizeBeforeDelete = mockScheduleRepository.findAll().size();

        // Delete the mockSchedule
        restMockScheduleMockMvc
            .perform(delete(ENTITY_API_URL_ID, mockSchedule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MockSchedule> mockScheduleList = mockScheduleRepository.findAll();
        assertThat(mockScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

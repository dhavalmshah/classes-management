package com.dhavalapp.classes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dhavalapp.classes.IntegrationTest;
import com.dhavalapp.classes.domain.Year;
import com.dhavalapp.classes.repository.YearRepository;
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
 * Integration tests for the {@link YearResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class YearResourceIT {

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/years";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restYearMockMvc;

    private Year year;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Year createEntity(EntityManager em) {
        Year year = new Year().year(DEFAULT_YEAR);
        return year;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Year createUpdatedEntity(EntityManager em) {
        Year year = new Year().year(UPDATED_YEAR);
        return year;
    }

    @BeforeEach
    public void initTest() {
        year = createEntity(em);
    }

    @Test
    @Transactional
    void createYear() throws Exception {
        int databaseSizeBeforeCreate = yearRepository.findAll().size();
        // Create the Year
        restYearMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(year)))
            .andExpect(status().isCreated());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeCreate + 1);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    void createYearWithExistingId() throws Exception {
        // Create the Year with an existing ID
        year.setId(1L);

        int databaseSizeBeforeCreate = yearRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restYearMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(year)))
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = yearRepository.findAll().size();
        // set the field null
        year.setYear(null);

        // Create the Year, which fails.

        restYearMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(year)))
            .andExpect(status().isBadRequest());

        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllYears() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        // Get all the yearList
        restYearMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(year.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }

    @Test
    @Transactional
    void getYear() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        // Get the year
        restYearMockMvc
            .perform(get(ENTITY_API_URL_ID, year.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(year.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR));
    }

    @Test
    @Transactional
    void getNonExistingYear() throws Exception {
        // Get the year
        restYearMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewYear() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        int databaseSizeBeforeUpdate = yearRepository.findAll().size();

        // Update the year
        Year updatedYear = yearRepository.findById(year.getId()).get();
        // Disconnect from session so that the updates on updatedYear are not directly saved in db
        em.detach(updatedYear);
        updatedYear.year(UPDATED_YEAR);

        restYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedYear.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedYear))
            )
            .andExpect(status().isOk());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    void putNonExistingYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, year.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(year))
            )
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(year))
            )
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(year)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateYearWithPatch() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        int databaseSizeBeforeUpdate = yearRepository.findAll().size();

        // Update the year using partial update
        Year partialUpdatedYear = new Year();
        partialUpdatedYear.setId(year.getId());

        restYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedYear.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedYear))
            )
            .andExpect(status().isOk());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    void fullUpdateYearWithPatch() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        int databaseSizeBeforeUpdate = yearRepository.findAll().size();

        // Update the year using partial update
        Year partialUpdatedYear = new Year();
        partialUpdatedYear.setId(year.getId());

        partialUpdatedYear.year(UPDATED_YEAR);

        restYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedYear.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedYear))
            )
            .andExpect(status().isOk());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    void patchNonExistingYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, year.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(year))
            )
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(year))
            )
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(year)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteYear() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        int databaseSizeBeforeDelete = yearRepository.findAll().size();

        // Delete the year
        restYearMockMvc
            .perform(delete(ENTITY_API_URL_ID, year.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

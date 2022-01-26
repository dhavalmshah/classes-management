package com.dhavalapp.classes.web.rest;

import static com.dhavalapp.classes.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dhavalapp.classes.IntegrationTest;
import com.dhavalapp.classes.domain.FinanceEntry;
import com.dhavalapp.classes.repository.FinanceEntryRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FinanceEntryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FinanceEntryResourceIT {

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/finance-entries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FinanceEntryRepository financeEntryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFinanceEntryMockMvc;

    private FinanceEntry financeEntry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinanceEntry createEntity(EntityManager em) {
        FinanceEntry financeEntry = new FinanceEntry()
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .amount(DEFAULT_AMOUNT)
            .notes(DEFAULT_NOTES);
        return financeEntry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinanceEntry createUpdatedEntity(EntityManager em) {
        FinanceEntry financeEntry = new FinanceEntry()
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .amount(UPDATED_AMOUNT)
            .notes(UPDATED_NOTES);
        return financeEntry;
    }

    @BeforeEach
    public void initTest() {
        financeEntry = createEntity(em);
    }

    @Test
    @Transactional
    void createFinanceEntry() throws Exception {
        int databaseSizeBeforeCreate = financeEntryRepository.findAll().size();
        // Create the FinanceEntry
        restFinanceEntryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(financeEntry)))
            .andExpect(status().isCreated());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeCreate + 1);
        FinanceEntry testFinanceEntry = financeEntryList.get(financeEntryList.size() - 1);
        assertThat(testFinanceEntry.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testFinanceEntry.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testFinanceEntry.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createFinanceEntryWithExistingId() throws Exception {
        // Create the FinanceEntry with an existing ID
        financeEntry.setId(1L);

        int databaseSizeBeforeCreate = financeEntryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanceEntryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(financeEntry)))
            .andExpect(status().isBadRequest());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = financeEntryRepository.findAll().size();
        // set the field null
        financeEntry.setTransactionDate(null);

        // Create the FinanceEntry, which fails.

        restFinanceEntryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(financeEntry)))
            .andExpect(status().isBadRequest());

        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = financeEntryRepository.findAll().size();
        // set the field null
        financeEntry.setAmount(null);

        // Create the FinanceEntry, which fails.

        restFinanceEntryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(financeEntry)))
            .andExpect(status().isBadRequest());

        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFinanceEntries() throws Exception {
        // Initialize the database
        financeEntryRepository.saveAndFlush(financeEntry);

        // Get all the financeEntryList
        restFinanceEntryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financeEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getFinanceEntry() throws Exception {
        // Initialize the database
        financeEntryRepository.saveAndFlush(financeEntry);

        // Get the financeEntry
        restFinanceEntryMockMvc
            .perform(get(ENTITY_API_URL_ID, financeEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(financeEntry.getId().intValue()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingFinanceEntry() throws Exception {
        // Get the financeEntry
        restFinanceEntryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFinanceEntry() throws Exception {
        // Initialize the database
        financeEntryRepository.saveAndFlush(financeEntry);

        int databaseSizeBeforeUpdate = financeEntryRepository.findAll().size();

        // Update the financeEntry
        FinanceEntry updatedFinanceEntry = financeEntryRepository.findById(financeEntry.getId()).get();
        // Disconnect from session so that the updates on updatedFinanceEntry are not directly saved in db
        em.detach(updatedFinanceEntry);
        updatedFinanceEntry.transactionDate(UPDATED_TRANSACTION_DATE).amount(UPDATED_AMOUNT).notes(UPDATED_NOTES);

        restFinanceEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFinanceEntry.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFinanceEntry))
            )
            .andExpect(status().isOk());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeUpdate);
        FinanceEntry testFinanceEntry = financeEntryList.get(financeEntryList.size() - 1);
        assertThat(testFinanceEntry.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testFinanceEntry.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFinanceEntry.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingFinanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = financeEntryRepository.findAll().size();
        financeEntry.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanceEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, financeEntry.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financeEntry))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFinanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = financeEntryRepository.findAll().size();
        financeEntry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanceEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financeEntry))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFinanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = financeEntryRepository.findAll().size();
        financeEntry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanceEntryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(financeEntry)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFinanceEntryWithPatch() throws Exception {
        // Initialize the database
        financeEntryRepository.saveAndFlush(financeEntry);

        int databaseSizeBeforeUpdate = financeEntryRepository.findAll().size();

        // Update the financeEntry using partial update
        FinanceEntry partialUpdatedFinanceEntry = new FinanceEntry();
        partialUpdatedFinanceEntry.setId(financeEntry.getId());

        partialUpdatedFinanceEntry.transactionDate(UPDATED_TRANSACTION_DATE).amount(UPDATED_AMOUNT).notes(UPDATED_NOTES);

        restFinanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinanceEntry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFinanceEntry))
            )
            .andExpect(status().isOk());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeUpdate);
        FinanceEntry testFinanceEntry = financeEntryList.get(financeEntryList.size() - 1);
        assertThat(testFinanceEntry.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testFinanceEntry.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFinanceEntry.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateFinanceEntryWithPatch() throws Exception {
        // Initialize the database
        financeEntryRepository.saveAndFlush(financeEntry);

        int databaseSizeBeforeUpdate = financeEntryRepository.findAll().size();

        // Update the financeEntry using partial update
        FinanceEntry partialUpdatedFinanceEntry = new FinanceEntry();
        partialUpdatedFinanceEntry.setId(financeEntry.getId());

        partialUpdatedFinanceEntry.transactionDate(UPDATED_TRANSACTION_DATE).amount(UPDATED_AMOUNT).notes(UPDATED_NOTES);

        restFinanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinanceEntry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFinanceEntry))
            )
            .andExpect(status().isOk());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeUpdate);
        FinanceEntry testFinanceEntry = financeEntryList.get(financeEntryList.size() - 1);
        assertThat(testFinanceEntry.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testFinanceEntry.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFinanceEntry.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingFinanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = financeEntryRepository.findAll().size();
        financeEntry.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, financeEntry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(financeEntry))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFinanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = financeEntryRepository.findAll().size();
        financeEntry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(financeEntry))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFinanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = financeEntryRepository.findAll().size();
        financeEntry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(financeEntry))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FinanceEntry in the database
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFinanceEntry() throws Exception {
        // Initialize the database
        financeEntryRepository.saveAndFlush(financeEntry);

        int databaseSizeBeforeDelete = financeEntryRepository.findAll().size();

        // Delete the financeEntry
        restFinanceEntryMockMvc
            .perform(delete(ENTITY_API_URL_ID, financeEntry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FinanceEntry> financeEntryList = financeEntryRepository.findAll();
        assertThat(financeEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

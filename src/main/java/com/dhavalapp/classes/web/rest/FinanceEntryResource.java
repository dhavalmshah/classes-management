package com.dhavalapp.classes.web.rest;

import com.dhavalapp.classes.domain.FinanceEntry;
import com.dhavalapp.classes.repository.FinanceEntryRepository;
import com.dhavalapp.classes.service.FinanceEntryService;
import com.dhavalapp.classes.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dhavalapp.classes.domain.FinanceEntry}.
 */
@RestController
@RequestMapping("/api")
public class FinanceEntryResource {

    private final Logger log = LoggerFactory.getLogger(FinanceEntryResource.class);

    private static final String ENTITY_NAME = "financeEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinanceEntryService financeEntryService;

    private final FinanceEntryRepository financeEntryRepository;

    public FinanceEntryResource(FinanceEntryService financeEntryService, FinanceEntryRepository financeEntryRepository) {
        this.financeEntryService = financeEntryService;
        this.financeEntryRepository = financeEntryRepository;
    }

    /**
     * {@code POST  /finance-entries} : Create a new financeEntry.
     *
     * @param financeEntry the financeEntry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new financeEntry, or with status {@code 400 (Bad Request)} if the financeEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/finance-entries")
    public ResponseEntity<FinanceEntry> createFinanceEntry(@Valid @RequestBody FinanceEntry financeEntry) throws URISyntaxException {
        log.debug("REST request to save FinanceEntry : {}", financeEntry);
        if (financeEntry.getId() != null) {
            throw new BadRequestAlertException("A new financeEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinanceEntry result = financeEntryService.save(financeEntry);
        return ResponseEntity
            .created(new URI("/api/finance-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /finance-entries/:id} : Updates an existing financeEntry.
     *
     * @param id the id of the financeEntry to save.
     * @param financeEntry the financeEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financeEntry,
     * or with status {@code 400 (Bad Request)} if the financeEntry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the financeEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/finance-entries/{id}")
    public ResponseEntity<FinanceEntry> updateFinanceEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FinanceEntry financeEntry
    ) throws URISyntaxException {
        log.debug("REST request to update FinanceEntry : {}, {}", id, financeEntry);
        if (financeEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, financeEntry.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!financeEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FinanceEntry result = financeEntryService.save(financeEntry);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, financeEntry.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /finance-entries/:id} : Partial updates given fields of an existing financeEntry, field will ignore if it is null
     *
     * @param id the id of the financeEntry to save.
     * @param financeEntry the financeEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financeEntry,
     * or with status {@code 400 (Bad Request)} if the financeEntry is not valid,
     * or with status {@code 404 (Not Found)} if the financeEntry is not found,
     * or with status {@code 500 (Internal Server Error)} if the financeEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/finance-entries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FinanceEntry> partialUpdateFinanceEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FinanceEntry financeEntry
    ) throws URISyntaxException {
        log.debug("REST request to partial update FinanceEntry partially : {}, {}", id, financeEntry);
        if (financeEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, financeEntry.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!financeEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FinanceEntry> result = financeEntryService.partialUpdate(financeEntry);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, financeEntry.getId().toString())
        );
    }

    /**
     * {@code GET  /finance-entries} : get all the financeEntries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of financeEntries in body.
     */
    @GetMapping("/finance-entries")
    public List<FinanceEntry> getAllFinanceEntries() {
        log.debug("REST request to get all FinanceEntries");
        return financeEntryService.findAll();
    }

    /**
     * {@code GET  /finance-entries/:id} : get the "id" financeEntry.
     *
     * @param id the id of the financeEntry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the financeEntry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/finance-entries/{id}")
    public ResponseEntity<FinanceEntry> getFinanceEntry(@PathVariable Long id) {
        log.debug("REST request to get FinanceEntry : {}", id);
        Optional<FinanceEntry> financeEntry = financeEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(financeEntry);
    }

    /**
     * {@code DELETE  /finance-entries/:id} : delete the "id" financeEntry.
     *
     * @param id the id of the financeEntry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/finance-entries/{id}")
    public ResponseEntity<Void> deleteFinanceEntry(@PathVariable Long id) {
        log.debug("REST request to delete FinanceEntry : {}", id);
        financeEntryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

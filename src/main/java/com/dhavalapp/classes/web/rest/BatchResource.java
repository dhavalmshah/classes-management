package com.dhavalapp.classes.web.rest;

import com.dhavalapp.classes.domain.Batch;
import com.dhavalapp.classes.repository.BatchRepository;
import com.dhavalapp.classes.service.BatchService;
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
 * REST controller for managing {@link com.dhavalapp.classes.domain.Batch}.
 */
@RestController
@RequestMapping("/api")
public class BatchResource {

    private final Logger log = LoggerFactory.getLogger(BatchResource.class);

    private static final String ENTITY_NAME = "batch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatchService batchService;

    private final BatchRepository batchRepository;

    public BatchResource(BatchService batchService, BatchRepository batchRepository) {
        this.batchService = batchService;
        this.batchRepository = batchRepository;
    }

    /**
     * {@code POST  /batches} : Create a new batch.
     *
     * @param batch the batch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new batch, or with status {@code 400 (Bad Request)} if the
     *         batch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/batches")
    public ResponseEntity<Batch> createBatch(@Valid @RequestBody Batch batch) throws URISyntaxException {
        log.debug("REST request to save Batch : {}", batch);
        if (batch.getId() != null) {
            throw new BadRequestAlertException("A new batch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        batch.getMockSchedules().forEach(s -> s.setBatch(batch));
        Batch result = batchService.save(batch);
        return ResponseEntity
            .created(new URI("/api/batches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /batches/:id} : Updates an existing batch.
     *
     * @param id    the id of the batch to save.
     * @param batch the batch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated batch,
     *         or with status {@code 400 (Bad Request)} if the batch is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the batch
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/batches/{id}")
    public ResponseEntity<Batch> updateBatch(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Batch batch)
        throws URISyntaxException {
        log.debug("REST request to update Batch : {}, {}", id, batch);
        if (batch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        batch.getMockSchedules().forEach(s -> s.setBatch(batch));
        Batch result = batchService.save(batch);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batch.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /batches/:id} : Partial updates given fields of an existing
     * batch, field will ignore if it is null
     *
     * @param id    the id of the batch to save.
     * @param batch the batch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated batch,
     *         or with status {@code 400 (Bad Request)} if the batch is not valid,
     *         or with status {@code 404 (Not Found)} if the batch is not found,
     *         or with status {@code 500 (Internal Server Error)} if the batch
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/batches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Batch> partialUpdateBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Batch batch
    ) throws URISyntaxException {
        log.debug("REST request to partial update Batch partially : {}, {}", id, batch);
        if (batch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Batch> result = batchService.partialUpdate(batch);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batch.getId().toString())
        );
    }

    /**
     * {@code GET  /batches} : get all the batches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of batches in body.
     */
    @GetMapping("/batches")
    public List<Batch> getAllBatches() {
        log.debug("REST request to get all Batches");
        return batchService.findAll();
    }

    /**
     * {@code GET  /batches/:id} : get the "id" batch.
     *
     * @param id the id of the batch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the batch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/batches/{id}")
    public ResponseEntity<Batch> getBatch(@PathVariable Long id) {
        log.debug("REST request to get Batch : {}", id);
        Optional<Batch> batch = batchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(batch);
    }

    /**
     * {@code DELETE  /batches/:id} : delete the "id" batch.
     *
     * @param id the id of the batch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/batches/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        log.debug("REST request to delete Batch : {}", id);
        batchService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

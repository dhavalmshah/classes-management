package com.dhavalapp.classes.web.rest;

import com.dhavalapp.classes.domain.MockSchedule;
import com.dhavalapp.classes.repository.MockScheduleRepository;
import com.dhavalapp.classes.service.MockScheduleService;
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
 * REST controller for managing {@link com.dhavalapp.classes.domain.MockSchedule}.
 */
@RestController
@RequestMapping("/api")
public class MockScheduleResource {

    private final Logger log = LoggerFactory.getLogger(MockScheduleResource.class);

    private static final String ENTITY_NAME = "mockSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MockScheduleService mockScheduleService;

    private final MockScheduleRepository mockScheduleRepository;

    public MockScheduleResource(MockScheduleService mockScheduleService, MockScheduleRepository mockScheduleRepository) {
        this.mockScheduleService = mockScheduleService;
        this.mockScheduleRepository = mockScheduleRepository;
    }

    /**
     * {@code POST  /mock-schedules} : Create a new mockSchedule.
     *
     * @param mockSchedule the mockSchedule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mockSchedule, or with status {@code 400 (Bad Request)} if the mockSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mock-schedules")
    public ResponseEntity<MockSchedule> createMockSchedule(@Valid @RequestBody MockSchedule mockSchedule) throws URISyntaxException {
        log.debug("REST request to save MockSchedule : {}", mockSchedule);
        if (mockSchedule.getId() != null) {
            throw new BadRequestAlertException("A new mockSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MockSchedule result = mockScheduleService.save(mockSchedule);
        return ResponseEntity
            .created(new URI("/api/mock-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mock-schedules/:id} : Updates an existing mockSchedule.
     *
     * @param id the id of the mockSchedule to save.
     * @param mockSchedule the mockSchedule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mockSchedule,
     * or with status {@code 400 (Bad Request)} if the mockSchedule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mockSchedule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mock-schedules/{id}")
    public ResponseEntity<MockSchedule> updateMockSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MockSchedule mockSchedule
    ) throws URISyntaxException {
        log.debug("REST request to update MockSchedule : {}, {}", id, mockSchedule);
        if (mockSchedule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mockSchedule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mockScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MockSchedule result = mockScheduleService.save(mockSchedule);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mockSchedule.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mock-schedules/:id} : Partial updates given fields of an existing mockSchedule, field will ignore if it is null
     *
     * @param id the id of the mockSchedule to save.
     * @param mockSchedule the mockSchedule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mockSchedule,
     * or with status {@code 400 (Bad Request)} if the mockSchedule is not valid,
     * or with status {@code 404 (Not Found)} if the mockSchedule is not found,
     * or with status {@code 500 (Internal Server Error)} if the mockSchedule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mock-schedules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MockSchedule> partialUpdateMockSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MockSchedule mockSchedule
    ) throws URISyntaxException {
        log.debug("REST request to partial update MockSchedule partially : {}, {}", id, mockSchedule);
        if (mockSchedule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mockSchedule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mockScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MockSchedule> result = mockScheduleService.partialUpdate(mockSchedule);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mockSchedule.getId().toString())
        );
    }

    /**
     * {@code GET  /mock-schedules} : get all the mockSchedules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mockSchedules in body.
     */
    @GetMapping("/mock-schedules")
    public List<MockSchedule> getAllMockSchedules() {
        log.debug("REST request to get all MockSchedules");
        return mockScheduleService.findAll();
    }

    /**
     * {@code GET  /mock-schedules/:id} : get the "id" mockSchedule.
     *
     * @param id the id of the mockSchedule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mockSchedule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mock-schedules/{id}")
    public ResponseEntity<MockSchedule> getMockSchedule(@PathVariable Long id) {
        log.debug("REST request to get MockSchedule : {}", id);
        Optional<MockSchedule> mockSchedule = mockScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mockSchedule);
    }

    /**
     * {@code DELETE  /mock-schedules/:id} : delete the "id" mockSchedule.
     *
     * @param id the id of the mockSchedule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mock-schedules/{id}")
    public ResponseEntity<Void> deleteMockSchedule(@PathVariable Long id) {
        log.debug("REST request to delete MockSchedule : {}", id);
        mockScheduleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.dhavalapp.classes.web.rest;

import com.dhavalapp.classes.domain.Center;
import com.dhavalapp.classes.repository.CenterRepository;
import com.dhavalapp.classes.service.CenterService;
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
 * REST controller for managing {@link com.dhavalapp.classes.domain.Center}.
 */
@RestController
@RequestMapping("/api")
public class CenterResource {

    private final Logger log = LoggerFactory.getLogger(CenterResource.class);

    private static final String ENTITY_NAME = "center";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CenterService centerService;

    private final CenterRepository centerRepository;

    public CenterResource(CenterService centerService, CenterRepository centerRepository) {
        this.centerService = centerService;
        this.centerRepository = centerRepository;
    }

    /**
     * {@code POST  /centers} : Create a new center.
     *
     * @param center the center to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new center, or with status {@code 400 (Bad Request)} if the center has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/centers")
    public ResponseEntity<Center> createCenter(@Valid @RequestBody Center center) throws URISyntaxException {
        log.debug("REST request to save Center : {}", center);
        if (center.getId() != null) {
            throw new BadRequestAlertException("A new center cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Center result = centerService.save(center);
        return ResponseEntity
            .created(new URI("/api/centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /centers/:id} : Updates an existing center.
     *
     * @param id the id of the center to save.
     * @param center the center to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated center,
     * or with status {@code 400 (Bad Request)} if the center is not valid,
     * or with status {@code 500 (Internal Server Error)} if the center couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/centers/{id}")
    public ResponseEntity<Center> updateCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Center center
    ) throws URISyntaxException {
        log.debug("REST request to update Center : {}, {}", id, center);
        if (center.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, center.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Center result = centerService.save(center);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, center.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /centers/:id} : Partial updates given fields of an existing center, field will ignore if it is null
     *
     * @param id the id of the center to save.
     * @param center the center to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated center,
     * or with status {@code 400 (Bad Request)} if the center is not valid,
     * or with status {@code 404 (Not Found)} if the center is not found,
     * or with status {@code 500 (Internal Server Error)} if the center couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/centers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Center> partialUpdateCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Center center
    ) throws URISyntaxException {
        log.debug("REST request to partial update Center partially : {}, {}", id, center);
        if (center.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, center.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Center> result = centerService.partialUpdate(center);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, center.getId().toString())
        );
    }

    /**
     * {@code GET  /centers} : get all the centers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centers in body.
     */
    @GetMapping("/centers")
    public List<Center> getAllCenters() {
        log.debug("REST request to get all Centers");
        return centerService.findAll();
    }

    /**
     * {@code GET  /centers/:id} : get the "id" center.
     *
     * @param id the id of the center to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the center, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/centers/{id}")
    public ResponseEntity<Center> getCenter(@PathVariable Long id) {
        log.debug("REST request to get Center : {}", id);
        Optional<Center> center = centerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(center);
    }

    /**
     * {@code DELETE  /centers/:id} : delete the "id" center.
     *
     * @param id the id of the center to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/centers/{id}")
    public ResponseEntity<Void> deleteCenter(@PathVariable Long id) {
        log.debug("REST request to delete Center : {}", id);
        centerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

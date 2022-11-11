package com.polarbears.capstone.hmscustomer.web.rest;

import com.polarbears.capstone.hmscustomer.repository.AllergensRepository;
import com.polarbears.capstone.hmscustomer.service.AllergensQueryService;
import com.polarbears.capstone.hmscustomer.service.AllergensService;
import com.polarbears.capstone.hmscustomer.service.criteria.AllergensCriteria;
import com.polarbears.capstone.hmscustomer.service.dto.AllergensDTO;
import com.polarbears.capstone.hmscustomer.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.polarbears.capstone.hmscustomer.domain.Allergens}.
 */
@RestController
@RequestMapping("/api")
public class AllergensResource {

    private final Logger log = LoggerFactory.getLogger(AllergensResource.class);

    private static final String ENTITY_NAME = "hmscustomerAllergens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllergensService allergensService;

    private final AllergensRepository allergensRepository;

    private final AllergensQueryService allergensQueryService;

    public AllergensResource(
        AllergensService allergensService,
        AllergensRepository allergensRepository,
        AllergensQueryService allergensQueryService
    ) {
        this.allergensService = allergensService;
        this.allergensRepository = allergensRepository;
        this.allergensQueryService = allergensQueryService;
    }

    /**
     * {@code POST  /allergens} : Create a new allergens.
     *
     * @param allergensDTO the allergensDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allergensDTO, or with status {@code 400 (Bad Request)} if the allergens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allergens")
    public ResponseEntity<AllergensDTO> createAllergens(@RequestBody AllergensDTO allergensDTO) throws URISyntaxException {
        log.debug("REST request to save Allergens : {}", allergensDTO);
        if (allergensDTO.getId() != null) {
            throw new BadRequestAlertException("A new allergens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllergensDTO result = allergensService.save(allergensDTO);
        return ResponseEntity
            .created(new URI("/api/allergens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allergens/:id} : Updates an existing allergens.
     *
     * @param id the id of the allergensDTO to save.
     * @param allergensDTO the allergensDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allergensDTO,
     * or with status {@code 400 (Bad Request)} if the allergensDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allergensDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allergens/{id}")
    public ResponseEntity<AllergensDTO> updateAllergens(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AllergensDTO allergensDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Allergens : {}, {}", id, allergensDTO);
        if (allergensDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, allergensDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!allergensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AllergensDTO result = allergensService.update(allergensDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allergensDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /allergens/:id} : Partial updates given fields of an existing allergens, field will ignore if it is null
     *
     * @param id the id of the allergensDTO to save.
     * @param allergensDTO the allergensDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allergensDTO,
     * or with status {@code 400 (Bad Request)} if the allergensDTO is not valid,
     * or with status {@code 404 (Not Found)} if the allergensDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the allergensDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/allergens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AllergensDTO> partialUpdateAllergens(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AllergensDTO allergensDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Allergens partially : {}, {}", id, allergensDTO);
        if (allergensDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, allergensDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!allergensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AllergensDTO> result = allergensService.partialUpdate(allergensDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allergensDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /allergens} : get all the allergens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allergens in body.
     */
    @GetMapping("/allergens")
    public ResponseEntity<List<AllergensDTO>> getAllAllergens(AllergensCriteria criteria) {
        log.debug("REST request to get Allergens by criteria: {}", criteria);
        List<AllergensDTO> entityList = allergensQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /allergens/count} : count all the allergens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/allergens/count")
    public ResponseEntity<Long> countAllergens(AllergensCriteria criteria) {
        log.debug("REST request to count Allergens by criteria: {}", criteria);
        return ResponseEntity.ok().body(allergensQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /allergens/:id} : get the "id" allergens.
     *
     * @param id the id of the allergensDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allergensDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allergens/{id}")
    public ResponseEntity<AllergensDTO> getAllergens(@PathVariable Long id) {
        log.debug("REST request to get Allergens : {}", id);
        Optional<AllergensDTO> allergensDTO = allergensService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allergensDTO);
    }

    /**
     * {@code DELETE  /allergens/:id} : delete the "id" allergens.
     *
     * @param id the id of the allergensDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allergens/{id}")
    public ResponseEntity<Void> deleteAllergens(@PathVariable Long id) {
        log.debug("REST request to delete Allergens : {}", id);
        allergensService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

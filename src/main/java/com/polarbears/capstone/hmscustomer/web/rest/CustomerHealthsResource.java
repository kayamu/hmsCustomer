package com.polarbears.capstone.hmscustomer.web.rest;

import com.polarbears.capstone.hmscustomer.repository.CustomerHealthsRepository;
import com.polarbears.capstone.hmscustomer.service.CustomerHealthsQueryService;
import com.polarbears.capstone.hmscustomer.service.CustomerHealthsService;
import com.polarbears.capstone.hmscustomer.service.criteria.CustomerHealthsCriteria;
import com.polarbears.capstone.hmscustomer.service.dto.CustomerHealthsDTO;
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
 * REST controller for managing {@link com.polarbears.capstone.hmscustomer.domain.CustomerHealths}.
 */
@RestController
@RequestMapping("/api")
public class CustomerHealthsResource {

    private final Logger log = LoggerFactory.getLogger(CustomerHealthsResource.class);

    private static final String ENTITY_NAME = "hmscustomerCustomerHealths";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerHealthsService customerHealthsService;

    private final CustomerHealthsRepository customerHealthsRepository;

    private final CustomerHealthsQueryService customerHealthsQueryService;

    public CustomerHealthsResource(
        CustomerHealthsService customerHealthsService,
        CustomerHealthsRepository customerHealthsRepository,
        CustomerHealthsQueryService customerHealthsQueryService
    ) {
        this.customerHealthsService = customerHealthsService;
        this.customerHealthsRepository = customerHealthsRepository;
        this.customerHealthsQueryService = customerHealthsQueryService;
    }

    /**
     * {@code POST  /customer-healths} : Create a new customerHealths.
     *
     * @param customerHealthsDTO the customerHealthsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerHealthsDTO, or with status {@code 400 (Bad Request)} if the customerHealths has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-healths")
    public ResponseEntity<CustomerHealthsDTO> createCustomerHealths(@RequestBody CustomerHealthsDTO customerHealthsDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomerHealths : {}", customerHealthsDTO);
        if (customerHealthsDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerHealths cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerHealthsDTO result = customerHealthsService.save(customerHealthsDTO);
        return ResponseEntity
            .created(new URI("/api/customer-healths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-healths/:id} : Updates an existing customerHealths.
     *
     * @param id the id of the customerHealthsDTO to save.
     * @param customerHealthsDTO the customerHealthsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerHealthsDTO,
     * or with status {@code 400 (Bad Request)} if the customerHealthsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerHealthsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-healths/{id}")
    public ResponseEntity<CustomerHealthsDTO> updateCustomerHealths(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomerHealthsDTO customerHealthsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerHealths : {}, {}", id, customerHealthsDTO);
        if (customerHealthsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerHealthsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerHealthsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerHealthsDTO result = customerHealthsService.update(customerHealthsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerHealthsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-healths/:id} : Partial updates given fields of an existing customerHealths, field will ignore if it is null
     *
     * @param id the id of the customerHealthsDTO to save.
     * @param customerHealthsDTO the customerHealthsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerHealthsDTO,
     * or with status {@code 400 (Bad Request)} if the customerHealthsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customerHealthsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerHealthsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-healths/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomerHealthsDTO> partialUpdateCustomerHealths(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomerHealthsDTO customerHealthsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerHealths partially : {}, {}", id, customerHealthsDTO);
        if (customerHealthsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerHealthsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerHealthsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerHealthsDTO> result = customerHealthsService.partialUpdate(customerHealthsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerHealthsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-healths} : get all the customerHealths.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerHealths in body.
     */
    @GetMapping("/customer-healths")
    public ResponseEntity<List<CustomerHealthsDTO>> getAllCustomerHealths(CustomerHealthsCriteria criteria) {
        log.debug("REST request to get CustomerHealths by criteria: {}", criteria);
        List<CustomerHealthsDTO> entityList = customerHealthsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /customer-healths/count} : count all the customerHealths.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customer-healths/count")
    public ResponseEntity<Long> countCustomerHealths(CustomerHealthsCriteria criteria) {
        log.debug("REST request to count CustomerHealths by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerHealthsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-healths/:id} : get the "id" customerHealths.
     *
     * @param id the id of the customerHealthsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerHealthsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-healths/{id}")
    public ResponseEntity<CustomerHealthsDTO> getCustomerHealths(@PathVariable Long id) {
        log.debug("REST request to get CustomerHealths : {}", id);
        Optional<CustomerHealthsDTO> customerHealthsDTO = customerHealthsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerHealthsDTO);
    }

    /**
     * {@code DELETE  /customer-healths/:id} : delete the "id" customerHealths.
     *
     * @param id the id of the customerHealthsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-healths/{id}")
    public ResponseEntity<Void> deleteCustomerHealths(@PathVariable Long id) {
        log.debug("REST request to delete CustomerHealths : {}", id);
        customerHealthsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

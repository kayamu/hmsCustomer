package com.polarbears.capstone.hmscustomer.service;

import com.polarbears.capstone.hmscustomer.domain.CustomerHealths;
import com.polarbears.capstone.hmscustomer.repository.CustomerHealthsRepository;
import com.polarbears.capstone.hmscustomer.service.dto.CustomerHealthsDTO;
import com.polarbears.capstone.hmscustomer.service.mapper.CustomerHealthsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomerHealths}.
 */
@Service
@Transactional
public class CustomerHealthsService {

    private final Logger log = LoggerFactory.getLogger(CustomerHealthsService.class);

    private final CustomerHealthsRepository customerHealthsRepository;

    private final CustomerHealthsMapper customerHealthsMapper;

    public CustomerHealthsService(CustomerHealthsRepository customerHealthsRepository, CustomerHealthsMapper customerHealthsMapper) {
        this.customerHealthsRepository = customerHealthsRepository;
        this.customerHealthsMapper = customerHealthsMapper;
    }

    /**
     * Save a customerHealths.
     *
     * @param customerHealthsDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerHealthsDTO save(CustomerHealthsDTO customerHealthsDTO) {
        log.debug("Request to save CustomerHealths : {}", customerHealthsDTO);
        CustomerHealths customerHealths = customerHealthsMapper.toEntity(customerHealthsDTO);
        customerHealths = customerHealthsRepository.save(customerHealths);
        return customerHealthsMapper.toDto(customerHealths);
    }

    /**
     * Update a customerHealths.
     *
     * @param customerHealthsDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerHealthsDTO update(CustomerHealthsDTO customerHealthsDTO) {
        log.debug("Request to update CustomerHealths : {}", customerHealthsDTO);
        CustomerHealths customerHealths = customerHealthsMapper.toEntity(customerHealthsDTO);
        customerHealths = customerHealthsRepository.save(customerHealths);
        return customerHealthsMapper.toDto(customerHealths);
    }

    /**
     * Partially update a customerHealths.
     *
     * @param customerHealthsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomerHealthsDTO> partialUpdate(CustomerHealthsDTO customerHealthsDTO) {
        log.debug("Request to partially update CustomerHealths : {}", customerHealthsDTO);

        return customerHealthsRepository
            .findById(customerHealthsDTO.getId())
            .map(existingCustomerHealths -> {
                customerHealthsMapper.partialUpdate(existingCustomerHealths, customerHealthsDTO);

                return existingCustomerHealths;
            })
            .map(customerHealthsRepository::save)
            .map(customerHealthsMapper::toDto);
    }

    /**
     * Get all the customerHealths.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerHealthsDTO> findAll() {
        log.debug("Request to get all CustomerHealths");
        return customerHealthsRepository
            .findAll()
            .stream()
            .map(customerHealthsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the customerHealths with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CustomerHealthsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return customerHealthsRepository.findAllWithEagerRelationships(pageable).map(customerHealthsMapper::toDto);
    }

    /**
     * Get one customerHealths by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerHealthsDTO> findOne(Long id) {
        log.debug("Request to get CustomerHealths : {}", id);
        return customerHealthsRepository.findOneWithEagerRelationships(id).map(customerHealthsMapper::toDto);
    }

    /**
     * Delete the customerHealths by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerHealths : {}", id);
        customerHealthsRepository.deleteById(id);
    }
}

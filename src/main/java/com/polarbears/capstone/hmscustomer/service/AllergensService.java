package com.polarbears.capstone.hmscustomer.service;

import com.polarbears.capstone.hmscustomer.domain.Allergens;
import com.polarbears.capstone.hmscustomer.repository.AllergensRepository;
import com.polarbears.capstone.hmscustomer.service.dto.AllergensDTO;
import com.polarbears.capstone.hmscustomer.service.mapper.AllergensMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Allergens}.
 */
@Service
@Transactional
public class AllergensService {

    private final Logger log = LoggerFactory.getLogger(AllergensService.class);

    private final AllergensRepository allergensRepository;

    private final AllergensMapper allergensMapper;

    public AllergensService(AllergensRepository allergensRepository, AllergensMapper allergensMapper) {
        this.allergensRepository = allergensRepository;
        this.allergensMapper = allergensMapper;
    }

    /**
     * Save a allergens.
     *
     * @param allergensDTO the entity to save.
     * @return the persisted entity.
     */
    public AllergensDTO save(AllergensDTO allergensDTO) {
        log.debug("Request to save Allergens : {}", allergensDTO);
        Allergens allergens = allergensMapper.toEntity(allergensDTO);
        allergens = allergensRepository.save(allergens);
        return allergensMapper.toDto(allergens);
    }

    /**
     * Update a allergens.
     *
     * @param allergensDTO the entity to save.
     * @return the persisted entity.
     */
    public AllergensDTO update(AllergensDTO allergensDTO) {
        log.debug("Request to update Allergens : {}", allergensDTO);
        Allergens allergens = allergensMapper.toEntity(allergensDTO);
        allergens = allergensRepository.save(allergens);
        return allergensMapper.toDto(allergens);
    }

    /**
     * Partially update a allergens.
     *
     * @param allergensDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AllergensDTO> partialUpdate(AllergensDTO allergensDTO) {
        log.debug("Request to partially update Allergens : {}", allergensDTO);

        return allergensRepository
            .findById(allergensDTO.getId())
            .map(existingAllergens -> {
                allergensMapper.partialUpdate(existingAllergens, allergensDTO);

                return existingAllergens;
            })
            .map(allergensRepository::save)
            .map(allergensMapper::toDto);
    }

    /**
     * Get all the allergens.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AllergensDTO> findAll() {
        log.debug("Request to get all Allergens");
        return allergensRepository.findAll().stream().map(allergensMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one allergens by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AllergensDTO> findOne(Long id) {
        log.debug("Request to get Allergens : {}", id);
        return allergensRepository.findById(id).map(allergensMapper::toDto);
    }

    /**
     * Delete the allergens by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Allergens : {}", id);
        allergensRepository.deleteById(id);
    }
}

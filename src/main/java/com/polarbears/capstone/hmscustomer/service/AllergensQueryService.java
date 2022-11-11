package com.polarbears.capstone.hmscustomer.service;

import com.polarbears.capstone.hmscustomer.domain.*; // for static metamodels
import com.polarbears.capstone.hmscustomer.domain.Allergens;
import com.polarbears.capstone.hmscustomer.repository.AllergensRepository;
import com.polarbears.capstone.hmscustomer.service.criteria.AllergensCriteria;
import com.polarbears.capstone.hmscustomer.service.dto.AllergensDTO;
import com.polarbears.capstone.hmscustomer.service.mapper.AllergensMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Allergens} entities in the database.
 * The main input is a {@link AllergensCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AllergensDTO} or a {@link Page} of {@link AllergensDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AllergensQueryService extends QueryService<Allergens> {

    private final Logger log = LoggerFactory.getLogger(AllergensQueryService.class);

    private final AllergensRepository allergensRepository;

    private final AllergensMapper allergensMapper;

    public AllergensQueryService(AllergensRepository allergensRepository, AllergensMapper allergensMapper) {
        this.allergensRepository = allergensRepository;
        this.allergensMapper = allergensMapper;
    }

    /**
     * Return a {@link List} of {@link AllergensDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AllergensDTO> findByCriteria(AllergensCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Allergens> specification = createSpecification(criteria);
        return allergensMapper.toDto(allergensRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AllergensDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AllergensDTO> findByCriteria(AllergensCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Allergens> specification = createSpecification(criteria);
        return allergensRepository.findAll(specification, page).map(allergensMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AllergensCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Allergens> specification = createSpecification(criteria);
        return allergensRepository.count(specification);
    }

    /**
     * Function to convert {@link AllergensCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Allergens> createSpecification(AllergensCriteria criteria) {
        Specification<Allergens> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Allergens_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Allergens_.name));
            }
            if (criteria.getContactId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactId(), Allergens_.contactId));
            }
            if (criteria.getIngradientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIngradientId(), Allergens_.ingradientId));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Allergens_.active));
            }
            if (criteria.getDetail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetail(), Allergens_.detail));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Allergens_.createdDate));
            }
            if (criteria.getCustomerHealthsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerHealthsId(),
                            root -> root.join(Allergens_.customerHealths, JoinType.LEFT).get(CustomerHealths_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

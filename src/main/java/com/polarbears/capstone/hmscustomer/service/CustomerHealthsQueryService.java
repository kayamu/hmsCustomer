package com.polarbears.capstone.hmscustomer.service;

import com.polarbears.capstone.hmscustomer.domain.*; // for static metamodels
import com.polarbears.capstone.hmscustomer.domain.CustomerHealths;
import com.polarbears.capstone.hmscustomer.repository.CustomerHealthsRepository;
import com.polarbears.capstone.hmscustomer.service.criteria.CustomerHealthsCriteria;
import com.polarbears.capstone.hmscustomer.service.dto.CustomerHealthsDTO;
import com.polarbears.capstone.hmscustomer.service.mapper.CustomerHealthsMapper;
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
 * Service for executing complex queries for {@link CustomerHealths} entities in the database.
 * The main input is a {@link CustomerHealthsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerHealthsDTO} or a {@link Page} of {@link CustomerHealthsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerHealthsQueryService extends QueryService<CustomerHealths> {

    private final Logger log = LoggerFactory.getLogger(CustomerHealthsQueryService.class);

    private final CustomerHealthsRepository customerHealthsRepository;

    private final CustomerHealthsMapper customerHealthsMapper;

    public CustomerHealthsQueryService(CustomerHealthsRepository customerHealthsRepository, CustomerHealthsMapper customerHealthsMapper) {
        this.customerHealthsRepository = customerHealthsRepository;
        this.customerHealthsMapper = customerHealthsMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerHealthsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerHealthsDTO> findByCriteria(CustomerHealthsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerHealths> specification = createSpecification(criteria);
        return customerHealthsMapper.toDto(customerHealthsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerHealthsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerHealthsDTO> findByCriteria(CustomerHealthsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerHealths> specification = createSpecification(criteria);
        return customerHealthsRepository.findAll(specification, page).map(customerHealthsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerHealthsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerHealths> specification = createSpecification(criteria);
        return customerHealthsRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerHealthsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerHealths> createSpecification(CustomerHealthsCriteria criteria) {
        Specification<CustomerHealths> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerHealths_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustomerHealths_.name));
            }
            if (criteria.getContactId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactId(), CustomerHealths_.contactId));
            }
            if (criteria.getCurrentWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentWeight(), CustomerHealths_.currentWeight));
            }
            if (criteria.getCurrentHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentHeight(), CustomerHealths_.currentHeight));
            }
            if (criteria.getMeasureUnit() != null) {
                specification = specification.and(buildSpecification(criteria.getMeasureUnit(), CustomerHealths_.measureUnit));
            }
            if (criteria.getActivityLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityLevel(), CustomerHealths_.activityLevel));
            }
            if (criteria.getTargetWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTargetWeight(), CustomerHealths_.targetWeight));
            }
            if (criteria.getTargerCalorie() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTargerCalorie(), CustomerHealths_.targerCalorie));
            }
            if (criteria.getTargetBodyFat() != null) {
                specification = specification.and(buildSpecification(criteria.getTargetBodyFat(), CustomerHealths_.targetBodyFat));
            }
            if (criteria.getGoal() != null) {
                specification = specification.and(buildSpecification(criteria.getGoal(), CustomerHealths_.goal));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CustomerHealths_.active));
            }
            if (criteria.getDetail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetail(), CustomerHealths_.detail));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustomerHealths_.createdDate));
            }
            if (criteria.getAllergensId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAllergensId(),
                            root -> root.join(CustomerHealths_.allergens, JoinType.LEFT).get(Allergens_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

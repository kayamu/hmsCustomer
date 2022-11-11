package com.polarbears.capstone.hmscustomer.repository;

import com.polarbears.capstone.hmscustomer.domain.CustomerHealths;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomerHealths entity.
 *
 * When extending this class, extend CustomerHealthsRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CustomerHealthsRepository
    extends CustomerHealthsRepositoryWithBagRelationships, JpaRepository<CustomerHealths, Long>, JpaSpecificationExecutor<CustomerHealths> {
    default Optional<CustomerHealths> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<CustomerHealths> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<CustomerHealths> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}

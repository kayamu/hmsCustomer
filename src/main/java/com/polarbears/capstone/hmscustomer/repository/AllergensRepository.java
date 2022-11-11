package com.polarbears.capstone.hmscustomer.repository;

import com.polarbears.capstone.hmscustomer.domain.Allergens;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Allergens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergensRepository extends JpaRepository<Allergens, Long>, JpaSpecificationExecutor<Allergens> {}

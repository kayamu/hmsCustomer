package com.polarbears.capstone.hmscustomer.repository;

import com.polarbears.capstone.hmscustomer.domain.CustomerHealths;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CustomerHealthsRepositoryWithBagRelationships {
    Optional<CustomerHealths> fetchBagRelationships(Optional<CustomerHealths> customerHealths);

    List<CustomerHealths> fetchBagRelationships(List<CustomerHealths> customerHealths);

    Page<CustomerHealths> fetchBagRelationships(Page<CustomerHealths> customerHealths);
}

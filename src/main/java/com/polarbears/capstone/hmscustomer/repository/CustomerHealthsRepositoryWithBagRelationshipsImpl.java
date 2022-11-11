package com.polarbears.capstone.hmscustomer.repository;

import com.polarbears.capstone.hmscustomer.domain.CustomerHealths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CustomerHealthsRepositoryWithBagRelationshipsImpl implements CustomerHealthsRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CustomerHealths> fetchBagRelationships(Optional<CustomerHealths> customerHealths) {
        return customerHealths.map(this::fetchAllergens);
    }

    @Override
    public Page<CustomerHealths> fetchBagRelationships(Page<CustomerHealths> customerHealths) {
        return new PageImpl<>(
            fetchBagRelationships(customerHealths.getContent()),
            customerHealths.getPageable(),
            customerHealths.getTotalElements()
        );
    }

    @Override
    public List<CustomerHealths> fetchBagRelationships(List<CustomerHealths> customerHealths) {
        return Optional.of(customerHealths).map(this::fetchAllergens).orElse(Collections.emptyList());
    }

    CustomerHealths fetchAllergens(CustomerHealths result) {
        return entityManager
            .createQuery(
                "select customerHealths from CustomerHealths customerHealths left join fetch customerHealths.allergens where customerHealths is :customerHealths",
                CustomerHealths.class
            )
            .setParameter("customerHealths", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<CustomerHealths> fetchAllergens(List<CustomerHealths> customerHealths) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, customerHealths.size()).forEach(index -> order.put(customerHealths.get(index).getId(), index));
        List<CustomerHealths> result = entityManager
            .createQuery(
                "select distinct customerHealths from CustomerHealths customerHealths left join fetch customerHealths.allergens where customerHealths in :customerHealths",
                CustomerHealths.class
            )
            .setParameter("customerHealths", customerHealths)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}

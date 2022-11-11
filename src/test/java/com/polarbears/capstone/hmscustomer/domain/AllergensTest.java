package com.polarbears.capstone.hmscustomer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmscustomer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AllergensTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Allergens.class);
        Allergens allergens1 = new Allergens();
        allergens1.setId(1L);
        Allergens allergens2 = new Allergens();
        allergens2.setId(allergens1.getId());
        assertThat(allergens1).isEqualTo(allergens2);
        allergens2.setId(2L);
        assertThat(allergens1).isNotEqualTo(allergens2);
        allergens1.setId(null);
        assertThat(allergens1).isNotEqualTo(allergens2);
    }
}

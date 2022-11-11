package com.polarbears.capstone.hmscustomer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmscustomer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerHealthsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerHealths.class);
        CustomerHealths customerHealths1 = new CustomerHealths();
        customerHealths1.setId(1L);
        CustomerHealths customerHealths2 = new CustomerHealths();
        customerHealths2.setId(customerHealths1.getId());
        assertThat(customerHealths1).isEqualTo(customerHealths2);
        customerHealths2.setId(2L);
        assertThat(customerHealths1).isNotEqualTo(customerHealths2);
        customerHealths1.setId(null);
        assertThat(customerHealths1).isNotEqualTo(customerHealths2);
    }
}

package com.polarbears.capstone.hmscustomer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmscustomer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerHealthsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerHealthsDTO.class);
        CustomerHealthsDTO customerHealthsDTO1 = new CustomerHealthsDTO();
        customerHealthsDTO1.setId(1L);
        CustomerHealthsDTO customerHealthsDTO2 = new CustomerHealthsDTO();
        assertThat(customerHealthsDTO1).isNotEqualTo(customerHealthsDTO2);
        customerHealthsDTO2.setId(customerHealthsDTO1.getId());
        assertThat(customerHealthsDTO1).isEqualTo(customerHealthsDTO2);
        customerHealthsDTO2.setId(2L);
        assertThat(customerHealthsDTO1).isNotEqualTo(customerHealthsDTO2);
        customerHealthsDTO1.setId(null);
        assertThat(customerHealthsDTO1).isNotEqualTo(customerHealthsDTO2);
    }
}

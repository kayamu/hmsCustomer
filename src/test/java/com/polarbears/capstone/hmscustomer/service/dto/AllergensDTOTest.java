package com.polarbears.capstone.hmscustomer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmscustomer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AllergensDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllergensDTO.class);
        AllergensDTO allergensDTO1 = new AllergensDTO();
        allergensDTO1.setId(1L);
        AllergensDTO allergensDTO2 = new AllergensDTO();
        assertThat(allergensDTO1).isNotEqualTo(allergensDTO2);
        allergensDTO2.setId(allergensDTO1.getId());
        assertThat(allergensDTO1).isEqualTo(allergensDTO2);
        allergensDTO2.setId(2L);
        assertThat(allergensDTO1).isNotEqualTo(allergensDTO2);
        allergensDTO1.setId(null);
        assertThat(allergensDTO1).isNotEqualTo(allergensDTO2);
    }
}

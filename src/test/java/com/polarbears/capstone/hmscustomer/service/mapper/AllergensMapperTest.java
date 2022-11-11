package com.polarbears.capstone.hmscustomer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AllergensMapperTest {

    private AllergensMapper allergensMapper;

    @BeforeEach
    public void setUp() {
        allergensMapper = new AllergensMapperImpl();
    }
}

package com.polarbears.capstone.hmscustomer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerHealthsMapperTest {

    private CustomerHealthsMapper customerHealthsMapper;

    @BeforeEach
    public void setUp() {
        customerHealthsMapper = new CustomerHealthsMapperImpl();
    }
}

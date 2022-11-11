package com.polarbears.capstone.hmscustomer.service.mapper;

import com.polarbears.capstone.hmscustomer.domain.Allergens;
import com.polarbears.capstone.hmscustomer.service.dto.AllergensDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Allergens} and its DTO {@link AllergensDTO}.
 */
@Mapper(componentModel = "spring")
public interface AllergensMapper extends EntityMapper<AllergensDTO, Allergens> {}

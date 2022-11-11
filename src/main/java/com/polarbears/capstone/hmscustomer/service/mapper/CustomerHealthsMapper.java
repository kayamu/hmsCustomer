package com.polarbears.capstone.hmscustomer.service.mapper;

import com.polarbears.capstone.hmscustomer.domain.Allergens;
import com.polarbears.capstone.hmscustomer.domain.CustomerHealths;
import com.polarbears.capstone.hmscustomer.service.dto.AllergensDTO;
import com.polarbears.capstone.hmscustomer.service.dto.CustomerHealthsDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerHealths} and its DTO {@link CustomerHealthsDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerHealthsMapper extends EntityMapper<CustomerHealthsDTO, CustomerHealths> {
    @Mapping(target = "allergens", source = "allergens", qualifiedByName = "allergensNameSet")
    CustomerHealthsDTO toDto(CustomerHealths s);

    @Mapping(target = "removeAllergens", ignore = true)
    CustomerHealths toEntity(CustomerHealthsDTO customerHealthsDTO);

    @Named("allergensName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AllergensDTO toDtoAllergensName(Allergens allergens);

    @Named("allergensNameSet")
    default Set<AllergensDTO> toDtoAllergensNameSet(Set<Allergens> allergens) {
        return allergens.stream().map(this::toDtoAllergensName).collect(Collectors.toSet());
    }
}

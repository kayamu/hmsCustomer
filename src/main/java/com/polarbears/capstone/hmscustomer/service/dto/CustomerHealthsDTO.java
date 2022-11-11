package com.polarbears.capstone.hmscustomer.service.dto;

import com.polarbears.capstone.hmscustomer.domain.enumeration.BODYFATS;
import com.polarbears.capstone.hmscustomer.domain.enumeration.GOALS;
import com.polarbears.capstone.hmscustomer.domain.enumeration.UNITS;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.polarbears.capstone.hmscustomer.domain.CustomerHealths} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerHealthsDTO implements Serializable {

    private Long id;

    private String name;

    private Long contactId;

    private Double currentWeight;

    private Double currentHeight;

    private UNITS measureUnit;

    private Double activityLevel;

    private Double targetWeight;

    private Double targerCalorie;

    private BODYFATS targetBodyFat;

    private GOALS goal;

    private Boolean active;

    private String detail;

    private LocalDate createdDate;

    private Set<AllergensDTO> allergens = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Double getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(Double currentHeight) {
        this.currentHeight = currentHeight;
    }

    public UNITS getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(UNITS measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Double getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(Double activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Double getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(Double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public Double getTargerCalorie() {
        return targerCalorie;
    }

    public void setTargerCalorie(Double targerCalorie) {
        this.targerCalorie = targerCalorie;
    }

    public BODYFATS getTargetBodyFat() {
        return targetBodyFat;
    }

    public void setTargetBodyFat(BODYFATS targetBodyFat) {
        this.targetBodyFat = targetBodyFat;
    }

    public GOALS getGoal() {
        return goal;
    }

    public void setGoal(GOALS goal) {
        this.goal = goal;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<AllergensDTO> getAllergens() {
        return allergens;
    }

    public void setAllergens(Set<AllergensDTO> allergens) {
        this.allergens = allergens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerHealthsDTO)) {
            return false;
        }

        CustomerHealthsDTO customerHealthsDTO = (CustomerHealthsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerHealthsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerHealthsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contactId=" + getContactId() +
            ", currentWeight=" + getCurrentWeight() +
            ", currentHeight=" + getCurrentHeight() +
            ", measureUnit='" + getMeasureUnit() + "'" +
            ", activityLevel=" + getActivityLevel() +
            ", targetWeight=" + getTargetWeight() +
            ", targerCalorie=" + getTargerCalorie() +
            ", targetBodyFat='" + getTargetBodyFat() + "'" +
            ", goal='" + getGoal() + "'" +
            ", active='" + getActive() + "'" +
            ", detail='" + getDetail() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", allergens=" + getAllergens() +
            "}";
    }
}

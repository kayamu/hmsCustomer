package com.polarbears.capstone.hmscustomer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmscustomer.domain.enumeration.BODYFATS;
import com.polarbears.capstone.hmscustomer.domain.enumeration.GOALS;
import com.polarbears.capstone.hmscustomer.domain.enumeration.UNITS;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustomerHealths.
 */
@Entity
@Table(name = "customer_healths")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerHealths implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "current_weight")
    private Double currentWeight;

    @Column(name = "current_height")
    private Double currentHeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "measure_unit")
    private UNITS measureUnit;

    @Column(name = "activity_level")
    private Double activityLevel;

    @Column(name = "target_weight")
    private Double targetWeight;

    @Column(name = "targer_calorie")
    private Double targerCalorie;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_body_fat")
    private BODYFATS targetBodyFat;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal")
    private GOALS goal;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "detail")
    private String detail;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToMany
    @JoinTable(
        name = "rel_customer_healths__allergens",
        joinColumns = @JoinColumn(name = "customer_healths_id"),
        inverseJoinColumns = @JoinColumn(name = "allergens_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customerHealths" }, allowSetters = true)
    private Set<Allergens> allergens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomerHealths id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CustomerHealths name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getContactId() {
        return this.contactId;
    }

    public CustomerHealths contactId(Long contactId) {
        this.setContactId(contactId);
        return this;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Double getCurrentWeight() {
        return this.currentWeight;
    }

    public CustomerHealths currentWeight(Double currentWeight) {
        this.setCurrentWeight(currentWeight);
        return this;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Double getCurrentHeight() {
        return this.currentHeight;
    }

    public CustomerHealths currentHeight(Double currentHeight) {
        this.setCurrentHeight(currentHeight);
        return this;
    }

    public void setCurrentHeight(Double currentHeight) {
        this.currentHeight = currentHeight;
    }

    public UNITS getMeasureUnit() {
        return this.measureUnit;
    }

    public CustomerHealths measureUnit(UNITS measureUnit) {
        this.setMeasureUnit(measureUnit);
        return this;
    }

    public void setMeasureUnit(UNITS measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Double getActivityLevel() {
        return this.activityLevel;
    }

    public CustomerHealths activityLevel(Double activityLevel) {
        this.setActivityLevel(activityLevel);
        return this;
    }

    public void setActivityLevel(Double activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Double getTargetWeight() {
        return this.targetWeight;
    }

    public CustomerHealths targetWeight(Double targetWeight) {
        this.setTargetWeight(targetWeight);
        return this;
    }

    public void setTargetWeight(Double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public Double getTargerCalorie() {
        return this.targerCalorie;
    }

    public CustomerHealths targerCalorie(Double targerCalorie) {
        this.setTargerCalorie(targerCalorie);
        return this;
    }

    public void setTargerCalorie(Double targerCalorie) {
        this.targerCalorie = targerCalorie;
    }

    public BODYFATS getTargetBodyFat() {
        return this.targetBodyFat;
    }

    public CustomerHealths targetBodyFat(BODYFATS targetBodyFat) {
        this.setTargetBodyFat(targetBodyFat);
        return this;
    }

    public void setTargetBodyFat(BODYFATS targetBodyFat) {
        this.targetBodyFat = targetBodyFat;
    }

    public GOALS getGoal() {
        return this.goal;
    }

    public CustomerHealths goal(GOALS goal) {
        this.setGoal(goal);
        return this;
    }

    public void setGoal(GOALS goal) {
        this.goal = goal;
    }

    public Boolean getActive() {
        return this.active;
    }

    public CustomerHealths active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDetail() {
        return this.detail;
    }

    public CustomerHealths detail(String detail) {
        this.setDetail(detail);
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public CustomerHealths createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Allergens> getAllergens() {
        return this.allergens;
    }

    public void setAllergens(Set<Allergens> allergens) {
        this.allergens = allergens;
    }

    public CustomerHealths allergens(Set<Allergens> allergens) {
        this.setAllergens(allergens);
        return this;
    }

    public CustomerHealths addAllergens(Allergens allergens) {
        this.allergens.add(allergens);
        allergens.getCustomerHealths().add(this);
        return this;
    }

    public CustomerHealths removeAllergens(Allergens allergens) {
        this.allergens.remove(allergens);
        allergens.getCustomerHealths().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerHealths)) {
            return false;
        }
        return id != null && id.equals(((CustomerHealths) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerHealths{" +
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
            "}";
    }
}

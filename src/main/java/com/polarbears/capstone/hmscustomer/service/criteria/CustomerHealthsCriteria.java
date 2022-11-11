package com.polarbears.capstone.hmscustomer.service.criteria;

import com.polarbears.capstone.hmscustomer.domain.enumeration.BODYFATS;
import com.polarbears.capstone.hmscustomer.domain.enumeration.GOALS;
import com.polarbears.capstone.hmscustomer.domain.enumeration.UNITS;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmscustomer.domain.CustomerHealths} entity. This class is used
 * in {@link com.polarbears.capstone.hmscustomer.web.rest.CustomerHealthsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-healths?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerHealthsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering UNITS
     */
    public static class UNITSFilter extends Filter<UNITS> {

        public UNITSFilter() {}

        public UNITSFilter(UNITSFilter filter) {
            super(filter);
        }

        @Override
        public UNITSFilter copy() {
            return new UNITSFilter(this);
        }
    }

    /**
     * Class for filtering BODYFATS
     */
    public static class BODYFATSFilter extends Filter<BODYFATS> {

        public BODYFATSFilter() {}

        public BODYFATSFilter(BODYFATSFilter filter) {
            super(filter);
        }

        @Override
        public BODYFATSFilter copy() {
            return new BODYFATSFilter(this);
        }
    }

    /**
     * Class for filtering GOALS
     */
    public static class GOALSFilter extends Filter<GOALS> {

        public GOALSFilter() {}

        public GOALSFilter(GOALSFilter filter) {
            super(filter);
        }

        @Override
        public GOALSFilter copy() {
            return new GOALSFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter contactId;

    private DoubleFilter currentWeight;

    private DoubleFilter currentHeight;

    private UNITSFilter measureUnit;

    private DoubleFilter activityLevel;

    private DoubleFilter targetWeight;

    private DoubleFilter targerCalorie;

    private BODYFATSFilter targetBodyFat;

    private GOALSFilter goal;

    private BooleanFilter active;

    private StringFilter detail;

    private LocalDateFilter createdDate;

    private LongFilter allergensId;

    private Boolean distinct;

    public CustomerHealthsCriteria() {}

    public CustomerHealthsCriteria(CustomerHealthsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.contactId = other.contactId == null ? null : other.contactId.copy();
        this.currentWeight = other.currentWeight == null ? null : other.currentWeight.copy();
        this.currentHeight = other.currentHeight == null ? null : other.currentHeight.copy();
        this.measureUnit = other.measureUnit == null ? null : other.measureUnit.copy();
        this.activityLevel = other.activityLevel == null ? null : other.activityLevel.copy();
        this.targetWeight = other.targetWeight == null ? null : other.targetWeight.copy();
        this.targerCalorie = other.targerCalorie == null ? null : other.targerCalorie.copy();
        this.targetBodyFat = other.targetBodyFat == null ? null : other.targetBodyFat.copy();
        this.goal = other.goal == null ? null : other.goal.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.detail = other.detail == null ? null : other.detail.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.allergensId = other.allergensId == null ? null : other.allergensId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CustomerHealthsCriteria copy() {
        return new CustomerHealthsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getContactId() {
        return contactId;
    }

    public LongFilter contactId() {
        if (contactId == null) {
            contactId = new LongFilter();
        }
        return contactId;
    }

    public void setContactId(LongFilter contactId) {
        this.contactId = contactId;
    }

    public DoubleFilter getCurrentWeight() {
        return currentWeight;
    }

    public DoubleFilter currentWeight() {
        if (currentWeight == null) {
            currentWeight = new DoubleFilter();
        }
        return currentWeight;
    }

    public void setCurrentWeight(DoubleFilter currentWeight) {
        this.currentWeight = currentWeight;
    }

    public DoubleFilter getCurrentHeight() {
        return currentHeight;
    }

    public DoubleFilter currentHeight() {
        if (currentHeight == null) {
            currentHeight = new DoubleFilter();
        }
        return currentHeight;
    }

    public void setCurrentHeight(DoubleFilter currentHeight) {
        this.currentHeight = currentHeight;
    }

    public UNITSFilter getMeasureUnit() {
        return measureUnit;
    }

    public UNITSFilter measureUnit() {
        if (measureUnit == null) {
            measureUnit = new UNITSFilter();
        }
        return measureUnit;
    }

    public void setMeasureUnit(UNITSFilter measureUnit) {
        this.measureUnit = measureUnit;
    }

    public DoubleFilter getActivityLevel() {
        return activityLevel;
    }

    public DoubleFilter activityLevel() {
        if (activityLevel == null) {
            activityLevel = new DoubleFilter();
        }
        return activityLevel;
    }

    public void setActivityLevel(DoubleFilter activityLevel) {
        this.activityLevel = activityLevel;
    }

    public DoubleFilter getTargetWeight() {
        return targetWeight;
    }

    public DoubleFilter targetWeight() {
        if (targetWeight == null) {
            targetWeight = new DoubleFilter();
        }
        return targetWeight;
    }

    public void setTargetWeight(DoubleFilter targetWeight) {
        this.targetWeight = targetWeight;
    }

    public DoubleFilter getTargerCalorie() {
        return targerCalorie;
    }

    public DoubleFilter targerCalorie() {
        if (targerCalorie == null) {
            targerCalorie = new DoubleFilter();
        }
        return targerCalorie;
    }

    public void setTargerCalorie(DoubleFilter targerCalorie) {
        this.targerCalorie = targerCalorie;
    }

    public BODYFATSFilter getTargetBodyFat() {
        return targetBodyFat;
    }

    public BODYFATSFilter targetBodyFat() {
        if (targetBodyFat == null) {
            targetBodyFat = new BODYFATSFilter();
        }
        return targetBodyFat;
    }

    public void setTargetBodyFat(BODYFATSFilter targetBodyFat) {
        this.targetBodyFat = targetBodyFat;
    }

    public GOALSFilter getGoal() {
        return goal;
    }

    public GOALSFilter goal() {
        if (goal == null) {
            goal = new GOALSFilter();
        }
        return goal;
    }

    public void setGoal(GOALSFilter goal) {
        this.goal = goal;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public BooleanFilter active() {
        if (active == null) {
            active = new BooleanFilter();
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getDetail() {
        return detail;
    }

    public StringFilter detail() {
        if (detail == null) {
            detail = new StringFilter();
        }
        return detail;
    }

    public void setDetail(StringFilter detail) {
        this.detail = detail;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public LocalDateFilter createdDate() {
        if (createdDate == null) {
            createdDate = new LocalDateFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getAllergensId() {
        return allergensId;
    }

    public LongFilter allergensId() {
        if (allergensId == null) {
            allergensId = new LongFilter();
        }
        return allergensId;
    }

    public void setAllergensId(LongFilter allergensId) {
        this.allergensId = allergensId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerHealthsCriteria that = (CustomerHealthsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(currentWeight, that.currentWeight) &&
            Objects.equals(currentHeight, that.currentHeight) &&
            Objects.equals(measureUnit, that.measureUnit) &&
            Objects.equals(activityLevel, that.activityLevel) &&
            Objects.equals(targetWeight, that.targetWeight) &&
            Objects.equals(targerCalorie, that.targerCalorie) &&
            Objects.equals(targetBodyFat, that.targetBodyFat) &&
            Objects.equals(goal, that.goal) &&
            Objects.equals(active, that.active) &&
            Objects.equals(detail, that.detail) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(allergensId, that.allergensId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            contactId,
            currentWeight,
            currentHeight,
            measureUnit,
            activityLevel,
            targetWeight,
            targerCalorie,
            targetBodyFat,
            goal,
            active,
            detail,
            createdDate,
            allergensId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerHealthsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (contactId != null ? "contactId=" + contactId + ", " : "") +
            (currentWeight != null ? "currentWeight=" + currentWeight + ", " : "") +
            (currentHeight != null ? "currentHeight=" + currentHeight + ", " : "") +
            (measureUnit != null ? "measureUnit=" + measureUnit + ", " : "") +
            (activityLevel != null ? "activityLevel=" + activityLevel + ", " : "") +
            (targetWeight != null ? "targetWeight=" + targetWeight + ", " : "") +
            (targerCalorie != null ? "targerCalorie=" + targerCalorie + ", " : "") +
            (targetBodyFat != null ? "targetBodyFat=" + targetBodyFat + ", " : "") +
            (goal != null ? "goal=" + goal + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (detail != null ? "detail=" + detail + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (allergensId != null ? "allergensId=" + allergensId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

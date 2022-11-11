package com.polarbears.capstone.hmscustomer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmscustomer.domain.Allergens} entity. This class is used
 * in {@link com.polarbears.capstone.hmscustomer.web.rest.AllergensResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /allergens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AllergensCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter contactId;

    private IntegerFilter ingradientId;

    private BooleanFilter active;

    private StringFilter detail;

    private LocalDateFilter createdDate;

    private LongFilter customerHealthsId;

    private Boolean distinct;

    public AllergensCriteria() {}

    public AllergensCriteria(AllergensCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.contactId = other.contactId == null ? null : other.contactId.copy();
        this.ingradientId = other.ingradientId == null ? null : other.ingradientId.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.detail = other.detail == null ? null : other.detail.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.customerHealthsId = other.customerHealthsId == null ? null : other.customerHealthsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AllergensCriteria copy() {
        return new AllergensCriteria(this);
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

    public IntegerFilter getIngradientId() {
        return ingradientId;
    }

    public IntegerFilter ingradientId() {
        if (ingradientId == null) {
            ingradientId = new IntegerFilter();
        }
        return ingradientId;
    }

    public void setIngradientId(IntegerFilter ingradientId) {
        this.ingradientId = ingradientId;
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

    public LongFilter getCustomerHealthsId() {
        return customerHealthsId;
    }

    public LongFilter customerHealthsId() {
        if (customerHealthsId == null) {
            customerHealthsId = new LongFilter();
        }
        return customerHealthsId;
    }

    public void setCustomerHealthsId(LongFilter customerHealthsId) {
        this.customerHealthsId = customerHealthsId;
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
        final AllergensCriteria that = (AllergensCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(ingradientId, that.ingradientId) &&
            Objects.equals(active, that.active) &&
            Objects.equals(detail, that.detail) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(customerHealthsId, that.customerHealthsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, contactId, ingradientId, active, detail, createdDate, customerHealthsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AllergensCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (contactId != null ? "contactId=" + contactId + ", " : "") +
            (ingradientId != null ? "ingradientId=" + ingradientId + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (detail != null ? "detail=" + detail + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (customerHealthsId != null ? "customerHealthsId=" + customerHealthsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

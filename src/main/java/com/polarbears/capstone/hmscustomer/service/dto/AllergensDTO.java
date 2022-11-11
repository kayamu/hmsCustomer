package com.polarbears.capstone.hmscustomer.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.polarbears.capstone.hmscustomer.domain.Allergens} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AllergensDTO implements Serializable {

    private Long id;

    private String name;

    private Long contactId;

    private Integer ingradientId;

    private Boolean active;

    private String detail;

    private LocalDate createdDate;

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

    public Integer getIngradientId() {
        return ingradientId;
    }

    public void setIngradientId(Integer ingradientId) {
        this.ingradientId = ingradientId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AllergensDTO)) {
            return false;
        }

        AllergensDTO allergensDTO = (AllergensDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, allergensDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AllergensDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contactId=" + getContactId() +
            ", ingradientId=" + getIngradientId() +
            ", active='" + getActive() + "'" +
            ", detail='" + getDetail() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}

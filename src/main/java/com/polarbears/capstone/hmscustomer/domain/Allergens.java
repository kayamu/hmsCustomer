package com.polarbears.capstone.hmscustomer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Allergens.
 */
@Entity
@Table(name = "allergens")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Allergens implements Serializable {

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

    @Column(name = "ingradient_id")
    private Integer ingradientId;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "detail")
    private String detail;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToMany(mappedBy = "allergens")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "allergens" }, allowSetters = true)
    private Set<CustomerHealths> customerHealths = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Allergens id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Allergens name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getContactId() {
        return this.contactId;
    }

    public Allergens contactId(Long contactId) {
        this.setContactId(contactId);
        return this;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Integer getIngradientId() {
        return this.ingradientId;
    }

    public Allergens ingradientId(Integer ingradientId) {
        this.setIngradientId(ingradientId);
        return this;
    }

    public void setIngradientId(Integer ingradientId) {
        this.ingradientId = ingradientId;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Allergens active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDetail() {
        return this.detail;
    }

    public Allergens detail(String detail) {
        this.setDetail(detail);
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Allergens createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<CustomerHealths> getCustomerHealths() {
        return this.customerHealths;
    }

    public void setCustomerHealths(Set<CustomerHealths> customerHealths) {
        if (this.customerHealths != null) {
            this.customerHealths.forEach(i -> i.removeAllergens(this));
        }
        if (customerHealths != null) {
            customerHealths.forEach(i -> i.addAllergens(this));
        }
        this.customerHealths = customerHealths;
    }

    public Allergens customerHealths(Set<CustomerHealths> customerHealths) {
        this.setCustomerHealths(customerHealths);
        return this;
    }

    public Allergens addCustomerHealths(CustomerHealths customerHealths) {
        this.customerHealths.add(customerHealths);
        customerHealths.getAllergens().add(this);
        return this;
    }

    public Allergens removeCustomerHealths(CustomerHealths customerHealths) {
        this.customerHealths.remove(customerHealths);
        customerHealths.getAllergens().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Allergens)) {
            return false;
        }
        return id != null && id.equals(((Allergens) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Allergens{" +
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

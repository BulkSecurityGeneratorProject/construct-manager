package fr.mathieugeissler.constructmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Estimate.
 */
@Entity
@Table(name = "estimate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Estimate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JsonIgnoreProperties("estimates")
    private Division division;

    @OneToMany(mappedBy = "estimate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EstimateProduct> products = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Estimate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Estimate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotal() {
        return total;
    }

    public Estimate total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Division getDivision() {
        return division;
    }

    public Estimate division(Division division) {
        this.division = division;
        return this;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Set<EstimateProduct> getProducts() {
        return products;
    }

    public Estimate products(Set<EstimateProduct> estimateProducts) {
        this.products = estimateProducts;
        return this;
    }

    public Estimate addProducts(EstimateProduct estimateProduct) {
        this.products.add(estimateProduct);
        estimateProduct.setEstimate(this);
        return this;
    }

    public Estimate removeProducts(EstimateProduct estimateProduct) {
        this.products.remove(estimateProduct);
        estimateProduct.setEstimate(null);
        return this;
    }

    public void setProducts(Set<EstimateProduct> estimateProducts) {
        this.products = estimateProducts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Estimate estimate = (Estimate) o;
        if (estimate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estimate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Estimate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}

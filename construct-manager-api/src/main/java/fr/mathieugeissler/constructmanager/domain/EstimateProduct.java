package fr.mathieugeissler.constructmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EstimateProduct.
 */
@Entity
@Table(name = "estimate_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstimateProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_number")
    private Integer number;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Estimate estimate;

    @ManyToOne
    @JsonIgnoreProperties("estimateProducts")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("estimateProducts")
    private GenericProduct generic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public EstimateProduct number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    public EstimateProduct price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Estimate getEstimate() {
        return estimate;
    }

    public EstimateProduct estimate(Estimate estimate) {
        this.estimate = estimate;
        return this;
    }

    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
    }

    public Product getProduct() {
        return product;
    }

    public EstimateProduct product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public GenericProduct getGeneric() {
        return generic;
    }

    public EstimateProduct generic(GenericProduct genericProduct) {
        this.generic = genericProduct;
        return this;
    }

    public void setGeneric(GenericProduct genericProduct) {
        this.generic = genericProduct;
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
        EstimateProduct estimateProduct = (EstimateProduct) o;
        if (estimateProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estimateProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstimateProduct{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", price=" + getPrice() +
            "}";
    }
}

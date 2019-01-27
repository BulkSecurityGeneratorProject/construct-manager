package fr.mathieugeissler.constructmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fr.mathieugeissler.constructmanager.domain.enumeration.Unit;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "packaging", nullable = false)
    private Double packaging;

    @Enumerated(EnumType.STRING)
    @Column(name = "packaging_unit")
    private Unit packagingUnit;

    @Column(name = "jhi_link")
    private String link;

    @Column(name = "price")
    private Double price;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Shop shop;

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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPackaging() {
        return packaging;
    }

    public Product packaging(Double packaging) {
        this.packaging = packaging;
        return this;
    }

    public void setPackaging(Double packaging) {
        this.packaging = packaging;
    }

    public Unit getPackagingUnit() {
        return packagingUnit;
    }

    public Product packagingUnit(Unit packagingUnit) {
        this.packagingUnit = packagingUnit;
        return this;
    }

    public void setPackagingUnit(Unit packagingUnit) {
        this.packagingUnit = packagingUnit;
    }

    public String getLink() {
        return link;
    }

    public Product link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getReference() {
        return reference;
    }

    public Product reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Shop getShop() {
        return shop;
    }

    public Product shop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", packaging=" + getPackaging() +
            ", packagingUnit='" + getPackagingUnit() + "'" +
            ", link='" + getLink() + "'" +
            ", price=" + getPrice() +
            ", reference='" + getReference() + "'" +
            "}";
    }
}

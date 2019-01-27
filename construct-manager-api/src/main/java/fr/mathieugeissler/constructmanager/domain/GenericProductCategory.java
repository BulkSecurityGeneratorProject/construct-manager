package fr.mathieugeissler.constructmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GenericProductCategory.
 */
@Entity
@Table(name = "generic_product_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GenericProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "genericProductCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GenericProduct> products = new HashSet<>();
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

    public GenericProductCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public GenericProductCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GenericProduct> getProducts() {
        return products;
    }

    public GenericProductCategory products(Set<GenericProduct> genericProducts) {
        this.products = genericProducts;
        return this;
    }

    public GenericProductCategory addProducts(GenericProduct genericProduct) {
        this.products.add(genericProduct);
        genericProduct.setGenericProductCategory(this);
        return this;
    }

    public GenericProductCategory removeProducts(GenericProduct genericProduct) {
        this.products.remove(genericProduct);
        genericProduct.setGenericProductCategory(null);
        return this;
    }

    public void setProducts(Set<GenericProduct> genericProducts) {
        this.products = genericProducts;
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
        GenericProductCategory genericProductCategory = (GenericProductCategory) o;
        if (genericProductCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), genericProductCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GenericProductCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

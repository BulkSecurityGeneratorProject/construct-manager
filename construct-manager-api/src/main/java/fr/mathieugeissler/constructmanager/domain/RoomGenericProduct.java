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
 * A RoomGenericProduct.
 */
@Entity
@Table(name = "room_generic_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoomGenericProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_unit")
    private Unit quantityUnit;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Room room;

    @ManyToOne
    @JsonIgnoreProperties("roomGenericProducts")
    private GenericProduct product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public RoomGenericProduct quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Unit getQuantityUnit() {
        return quantityUnit;
    }

    public RoomGenericProduct quantityUnit(Unit quantityUnit) {
        this.quantityUnit = quantityUnit;
        return this;
    }

    public void setQuantityUnit(Unit quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public Room getRoom() {
        return room;
    }

    public RoomGenericProduct room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public GenericProduct getProduct() {
        return product;
    }

    public RoomGenericProduct product(GenericProduct genericProduct) {
        this.product = genericProduct;
        return this;
    }

    public void setProduct(GenericProduct genericProduct) {
        this.product = genericProduct;
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
        RoomGenericProduct roomGenericProduct = (RoomGenericProduct) o;
        if (roomGenericProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomGenericProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomGenericProduct{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", quantityUnit='" + getQuantityUnit() + "'" +
            "}";
    }
}

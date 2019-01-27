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
 * A Room.
 */
@Entity
@Table(name = "room")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("rooms")
    private Home home;

    @OneToMany(mappedBy = "room")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RoomGenericProduct> products = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("rooms")
    private RoomType type;

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

    public Room name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Room description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Home getHome() {
        return home;
    }

    public Room home(Home home) {
        this.home = home;
        return this;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Set<RoomGenericProduct> getProducts() {
        return products;
    }

    public Room products(Set<RoomGenericProduct> roomGenericProducts) {
        this.products = roomGenericProducts;
        return this;
    }

    public Room addProducts(RoomGenericProduct roomGenericProduct) {
        this.products.add(roomGenericProduct);
        roomGenericProduct.setRoom(this);
        return this;
    }

    public Room removeProducts(RoomGenericProduct roomGenericProduct) {
        this.products.remove(roomGenericProduct);
        roomGenericProduct.setRoom(null);
        return this;
    }

    public void setProducts(Set<RoomGenericProduct> roomGenericProducts) {
        this.products = roomGenericProducts;
    }

    public RoomType getType() {
        return type;
    }

    public Room type(RoomType roomType) {
        this.type = roomType;
        return this;
    }

    public void setType(RoomType roomType) {
        this.type = roomType;
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
        Room room = (Room) o;
        if (room.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), room.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

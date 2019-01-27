package fr.mathieugeissler.constructmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RoomSize.
 */
@Entity
@Table(name = "room_size")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoomSize implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "floor_size")
    private Double floorSize;

    @Column(name = "wall_size")
    private Double wallSize;

    @ManyToOne
    @JsonIgnoreProperties("roomSizes")
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFloorSize() {
        return floorSize;
    }

    public RoomSize floorSize(Double floorSize) {
        this.floorSize = floorSize;
        return this;
    }

    public void setFloorSize(Double floorSize) {
        this.floorSize = floorSize;
    }

    public Double getWallSize() {
        return wallSize;
    }

    public RoomSize wallSize(Double wallSize) {
        this.wallSize = wallSize;
        return this;
    }

    public void setWallSize(Double wallSize) {
        this.wallSize = wallSize;
    }

    public Room getRoom() {
        return room;
    }

    public RoomSize room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
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
        RoomSize roomSize = (RoomSize) o;
        if (roomSize.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomSize.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomSize{" +
            "id=" + getId() +
            ", floorSize=" + getFloorSize() +
            ", wallSize=" + getWallSize() +
            "}";
    }
}

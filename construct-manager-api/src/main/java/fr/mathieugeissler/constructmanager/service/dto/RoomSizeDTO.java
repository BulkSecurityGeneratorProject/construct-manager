package fr.mathieugeissler.constructmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RoomSize entity.
 */
public class RoomSizeDTO implements Serializable {

    private Long id;

    private Double floorSize;

    private Double wallSize;

    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFloorSize() {
        return floorSize;
    }

    public void setFloorSize(Double floorSize) {
        this.floorSize = floorSize;
    }

    public Double getWallSize() {
        return wallSize;
    }

    public void setWallSize(Double wallSize) {
        this.wallSize = wallSize;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoomSizeDTO roomSizeDTO = (RoomSizeDTO) o;
        if (roomSizeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomSizeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomSizeDTO{" +
            "id=" + getId() +
            ", floorSize=" + getFloorSize() +
            ", wallSize=" + getWallSize() +
            ", room=" + getRoomId() +
            "}";
    }
}

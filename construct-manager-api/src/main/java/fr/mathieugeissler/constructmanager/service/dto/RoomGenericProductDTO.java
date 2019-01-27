package fr.mathieugeissler.constructmanager.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import fr.mathieugeissler.constructmanager.domain.enumeration.Unit;

/**
 * A DTO for the RoomGenericProduct entity.
 */
public class RoomGenericProductDTO implements Serializable {

    private Long id;

    @NotNull
    private Double quantity;

    private Unit quantityUnit;

    private Long roomId;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Unit getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(Unit quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long genericProductId) {
        this.productId = genericProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoomGenericProductDTO roomGenericProductDTO = (RoomGenericProductDTO) o;
        if (roomGenericProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomGenericProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomGenericProductDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", quantityUnit='" + getQuantityUnit() + "'" +
            ", room=" + getRoomId() +
            ", product=" + getProductId() +
            "}";
    }
}

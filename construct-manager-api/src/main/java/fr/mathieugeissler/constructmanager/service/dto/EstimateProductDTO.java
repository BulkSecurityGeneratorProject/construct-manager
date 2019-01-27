package fr.mathieugeissler.constructmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EstimateProduct entity.
 */
public class EstimateProductDTO implements Serializable {

    private Long id;

    private Integer number;

    private Double price;

    private Long estimateId;

    private Long productId;

    private Long genericId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getGenericId() {
        return genericId;
    }

    public void setGenericId(Long genericProductId) {
        this.genericId = genericProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstimateProductDTO estimateProductDTO = (EstimateProductDTO) o;
        if (estimateProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estimateProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstimateProductDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", price=" + getPrice() +
            ", estimate=" + getEstimateId() +
            ", product=" + getProductId() +
            ", generic=" + getGenericId() +
            "}";
    }
}

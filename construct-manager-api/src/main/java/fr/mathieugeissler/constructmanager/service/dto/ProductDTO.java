package fr.mathieugeissler.constructmanager.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import fr.mathieugeissler.constructmanager.domain.enumeration.Unit;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Double packaging;

    private Unit packagingUnit;

    private String link;

    private Double price;

    private String reference;

    private Long shopId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPackaging() {
        return packaging;
    }

    public void setPackaging(Double packaging) {
        this.packaging = packaging;
    }

    public Unit getPackagingUnit() {
        return packagingUnit;
    }

    public void setPackagingUnit(Unit packagingUnit) {
        this.packagingUnit = packagingUnit;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", packaging=" + getPackaging() +
            ", packagingUnit='" + getPackagingUnit() + "'" +
            ", link='" + getLink() + "'" +
            ", price=" + getPrice() +
            ", reference='" + getReference() + "'" +
            ", shop=" + getShopId() +
            "}";
    }
}

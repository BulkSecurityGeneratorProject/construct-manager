package fr.mathieugeissler.constructmanager.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the GenericProduct entity.
 */
public class GenericProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Long genericProductCategoryId;

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

    public Long getGenericProductCategoryId() {
        return genericProductCategoryId;
    }

    public void setGenericProductCategoryId(Long genericProductCategoryId) {
        this.genericProductCategoryId = genericProductCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GenericProductDTO genericProductDTO = (GenericProductDTO) o;
        if (genericProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), genericProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GenericProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", genericProductCategory=" + getGenericProductCategoryId() +
            "}";
    }
}

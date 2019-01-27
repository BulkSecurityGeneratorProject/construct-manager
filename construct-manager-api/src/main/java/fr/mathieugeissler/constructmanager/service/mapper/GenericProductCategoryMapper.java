package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.GenericProductCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GenericProductCategory and its DTO GenericProductCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenericProductCategoryMapper extends EntityMapper<GenericProductCategoryDTO, GenericProductCategory> {


    @Mapping(target = "products", ignore = true)
    GenericProductCategory toEntity(GenericProductCategoryDTO genericProductCategoryDTO);

    default GenericProductCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        GenericProductCategory genericProductCategory = new GenericProductCategory();
        genericProductCategory.setId(id);
        return genericProductCategory;
    }
}

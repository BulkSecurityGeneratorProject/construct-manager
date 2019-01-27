package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.GenericProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GenericProduct and its DTO GenericProductDTO.
 */
@Mapper(componentModel = "spring", uses = {GenericProductCategoryMapper.class})
public interface GenericProductMapper extends EntityMapper<GenericProductDTO, GenericProduct> {

    @Mapping(source = "genericProductCategory.id", target = "genericProductCategoryId")
    GenericProductDTO toDto(GenericProduct genericProduct);

    @Mapping(source = "genericProductCategoryId", target = "genericProductCategory")
    GenericProduct toEntity(GenericProductDTO genericProductDTO);

    default GenericProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        GenericProduct genericProduct = new GenericProduct();
        genericProduct.setId(id);
        return genericProduct;
    }
}

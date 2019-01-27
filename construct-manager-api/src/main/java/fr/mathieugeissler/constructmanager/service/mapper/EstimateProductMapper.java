package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.EstimateProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EstimateProduct and its DTO EstimateProductDTO.
 */
@Mapper(componentModel = "spring", uses = {EstimateMapper.class, ProductMapper.class, GenericProductMapper.class})
public interface EstimateProductMapper extends EntityMapper<EstimateProductDTO, EstimateProduct> {

    @Mapping(source = "estimate.id", target = "estimateId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "generic.id", target = "genericId")
    EstimateProductDTO toDto(EstimateProduct estimateProduct);

    @Mapping(source = "estimateId", target = "estimate")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "genericId", target = "generic")
    EstimateProduct toEntity(EstimateProductDTO estimateProductDTO);

    default EstimateProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstimateProduct estimateProduct = new EstimateProduct();
        estimateProduct.setId(id);
        return estimateProduct;
    }
}

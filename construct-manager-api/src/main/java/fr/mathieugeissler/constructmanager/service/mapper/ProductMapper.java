package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "shop.id", target = "shopId")
    ProductDTO toDto(Product product);

    @Mapping(source = "shopId", target = "shop")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}

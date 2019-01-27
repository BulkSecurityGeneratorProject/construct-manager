package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.RoomGenericProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RoomGenericProduct and its DTO RoomGenericProductDTO.
 */
@Mapper(componentModel = "spring", uses = {RoomMapper.class, GenericProductMapper.class})
public interface RoomGenericProductMapper extends EntityMapper<RoomGenericProductDTO, RoomGenericProduct> {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "product.id", target = "productId")
    RoomGenericProductDTO toDto(RoomGenericProduct roomGenericProduct);

    @Mapping(source = "roomId", target = "room")
    @Mapping(source = "productId", target = "product")
    RoomGenericProduct toEntity(RoomGenericProductDTO roomGenericProductDTO);

    default RoomGenericProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        RoomGenericProduct roomGenericProduct = new RoomGenericProduct();
        roomGenericProduct.setId(id);
        return roomGenericProduct;
    }
}

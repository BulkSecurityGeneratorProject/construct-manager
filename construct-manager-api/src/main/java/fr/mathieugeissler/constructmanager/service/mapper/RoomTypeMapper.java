package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.RoomTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RoomType and its DTO RoomTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoomTypeMapper extends EntityMapper<RoomTypeDTO, RoomType> {



    default RoomType fromId(Long id) {
        if (id == null) {
            return null;
        }
        RoomType roomType = new RoomType();
        roomType.setId(id);
        return roomType;
    }
}

package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.RoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Room and its DTO RoomDTO.
 */
@Mapper(componentModel = "spring", uses = {HomeMapper.class, RoomTypeMapper.class})
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {

    @Mapping(source = "home.id", target = "homeId")
    @Mapping(source = "type.id", target = "typeId")
    RoomDTO toDto(Room room);

    @Mapping(source = "homeId", target = "home")
    @Mapping(target = "products", ignore = true)
    @Mapping(source = "typeId", target = "type")
    Room toEntity(RoomDTO roomDTO);

    default Room fromId(Long id) {
        if (id == null) {
            return null;
        }
        Room room = new Room();
        room.setId(id);
        return room;
    }
}

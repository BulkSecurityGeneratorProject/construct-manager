package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.RoomSizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RoomSize and its DTO RoomSizeDTO.
 */
@Mapper(componentModel = "spring", uses = {RoomMapper.class})
public interface RoomSizeMapper extends EntityMapper<RoomSizeDTO, RoomSize> {

    @Mapping(source = "room.id", target = "roomId")
    RoomSizeDTO toDto(RoomSize roomSize);

    @Mapping(source = "roomId", target = "room")
    RoomSize toEntity(RoomSizeDTO roomSizeDTO);

    default RoomSize fromId(Long id) {
        if (id == null) {
            return null;
        }
        RoomSize roomSize = new RoomSize();
        roomSize.setId(id);
        return roomSize;
    }
}

package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.HomeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Home and its DTO HomeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HomeMapper extends EntityMapper<HomeDTO, Home> {


    @Mapping(target = "rooms", ignore = true)
    Home toEntity(HomeDTO homeDTO);

    default Home fromId(Long id) {
        if (id == null) {
            return null;
        }
        Home home = new Home();
        home.setId(id);
        return home;
    }
}

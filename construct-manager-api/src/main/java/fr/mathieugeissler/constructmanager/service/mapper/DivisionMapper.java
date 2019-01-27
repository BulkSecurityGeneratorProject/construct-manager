package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.DivisionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Division and its DTO DivisionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DivisionMapper extends EntityMapper<DivisionDTO, Division> {


    @Mapping(target = "estimates", ignore = true)
    Division toEntity(DivisionDTO divisionDTO);

    default Division fromId(Long id) {
        if (id == null) {
            return null;
        }
        Division division = new Division();
        division.setId(id);
        return division;
    }
}

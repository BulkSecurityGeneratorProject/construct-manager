package fr.mathieugeissler.constructmanager.service.mapper;

import fr.mathieugeissler.constructmanager.domain.*;
import fr.mathieugeissler.constructmanager.service.dto.EstimateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Estimate and its DTO EstimateDTO.
 */
@Mapper(componentModel = "spring", uses = {DivisionMapper.class})
public interface EstimateMapper extends EntityMapper<EstimateDTO, Estimate> {

    @Mapping(source = "division.id", target = "divisionId")
    EstimateDTO toDto(Estimate estimate);

    @Mapping(source = "divisionId", target = "division")
    @Mapping(target = "products", ignore = true)
    Estimate toEntity(EstimateDTO estimateDTO);

    default Estimate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Estimate estimate = new Estimate();
        estimate.setId(id);
        return estimate;
    }
}

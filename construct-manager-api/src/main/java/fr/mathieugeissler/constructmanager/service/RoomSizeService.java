package fr.mathieugeissler.constructmanager.service;

import fr.mathieugeissler.constructmanager.service.dto.RoomSizeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RoomSize.
 */
public interface RoomSizeService {

    /**
     * Save a roomSize.
     *
     * @param roomSizeDTO the entity to save
     * @return the persisted entity
     */
    RoomSizeDTO save(RoomSizeDTO roomSizeDTO);

    /**
     * Get all the roomSizes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoomSizeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" roomSize.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoomSizeDTO> findOne(Long id);

    /**
     * Delete the "id" roomSize.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

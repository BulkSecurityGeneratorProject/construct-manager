package fr.mathieugeissler.constructmanager.service;

import fr.mathieugeissler.constructmanager.service.dto.RoomTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RoomType.
 */
public interface RoomTypeService {

    /**
     * Save a roomType.
     *
     * @param roomTypeDTO the entity to save
     * @return the persisted entity
     */
    RoomTypeDTO save(RoomTypeDTO roomTypeDTO);

    /**
     * Get all the roomTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoomTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" roomType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoomTypeDTO> findOne(Long id);

    /**
     * Delete the "id" roomType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

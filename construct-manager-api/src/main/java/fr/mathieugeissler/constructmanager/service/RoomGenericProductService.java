package fr.mathieugeissler.constructmanager.service;

import fr.mathieugeissler.constructmanager.service.dto.RoomGenericProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RoomGenericProduct.
 */
public interface RoomGenericProductService {

    /**
     * Save a roomGenericProduct.
     *
     * @param roomGenericProductDTO the entity to save
     * @return the persisted entity
     */
    RoomGenericProductDTO save(RoomGenericProductDTO roomGenericProductDTO);

    /**
     * Get all the roomGenericProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoomGenericProductDTO> findAll(Pageable pageable);


    /**
     * Get the "id" roomGenericProduct.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoomGenericProductDTO> findOne(Long id);

    /**
     * Delete the "id" roomGenericProduct.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

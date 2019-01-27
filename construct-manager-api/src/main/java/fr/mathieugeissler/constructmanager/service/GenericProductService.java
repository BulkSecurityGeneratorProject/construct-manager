package fr.mathieugeissler.constructmanager.service;

import fr.mathieugeissler.constructmanager.service.dto.GenericProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing GenericProduct.
 */
public interface GenericProductService {

    /**
     * Save a genericProduct.
     *
     * @param genericProductDTO the entity to save
     * @return the persisted entity
     */
    GenericProductDTO save(GenericProductDTO genericProductDTO);

    /**
     * Get all the genericProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GenericProductDTO> findAll(Pageable pageable);


    /**
     * Get the "id" genericProduct.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GenericProductDTO> findOne(Long id);

    /**
     * Delete the "id" genericProduct.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

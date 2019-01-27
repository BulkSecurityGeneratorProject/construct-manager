package fr.mathieugeissler.constructmanager.service;

import fr.mathieugeissler.constructmanager.service.dto.GenericProductCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing GenericProductCategory.
 */
public interface GenericProductCategoryService {

    /**
     * Save a genericProductCategory.
     *
     * @param genericProductCategoryDTO the entity to save
     * @return the persisted entity
     */
    GenericProductCategoryDTO save(GenericProductCategoryDTO genericProductCategoryDTO);

    /**
     * Get all the genericProductCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GenericProductCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" genericProductCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GenericProductCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" genericProductCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

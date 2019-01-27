package fr.mathieugeissler.constructmanager.service;

import fr.mathieugeissler.constructmanager.service.dto.EstimateProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EstimateProduct.
 */
public interface EstimateProductService {

    /**
     * Save a estimateProduct.
     *
     * @param estimateProductDTO the entity to save
     * @return the persisted entity
     */
    EstimateProductDTO save(EstimateProductDTO estimateProductDTO);

    /**
     * Get all the estimateProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EstimateProductDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estimateProduct.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EstimateProductDTO> findOne(Long id);

    /**
     * Delete the "id" estimateProduct.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

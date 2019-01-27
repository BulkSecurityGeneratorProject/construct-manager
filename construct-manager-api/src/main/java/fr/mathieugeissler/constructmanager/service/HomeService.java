package fr.mathieugeissler.constructmanager.service;

import fr.mathieugeissler.constructmanager.service.dto.HomeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Home.
 */
public interface HomeService {

    /**
     * Save a home.
     *
     * @param homeDTO the entity to save
     * @return the persisted entity
     */
    HomeDTO save(HomeDTO homeDTO);

    /**
     * Get all the homes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HomeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" home.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HomeDTO> findOne(Long id);

    /**
     * Delete the "id" home.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

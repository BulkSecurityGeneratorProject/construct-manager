package fr.mathieugeissler.constructmanager.service.impl;

import fr.mathieugeissler.constructmanager.service.RoomGenericProductService;
import fr.mathieugeissler.constructmanager.domain.RoomGenericProduct;
import fr.mathieugeissler.constructmanager.repository.RoomGenericProductRepository;
import fr.mathieugeissler.constructmanager.service.dto.RoomGenericProductDTO;
import fr.mathieugeissler.constructmanager.service.mapper.RoomGenericProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RoomGenericProduct.
 */
@Service
@Transactional
public class RoomGenericProductServiceImpl implements RoomGenericProductService {

    private final Logger log = LoggerFactory.getLogger(RoomGenericProductServiceImpl.class);

    private final RoomGenericProductRepository roomGenericProductRepository;

    private final RoomGenericProductMapper roomGenericProductMapper;

    public RoomGenericProductServiceImpl(RoomGenericProductRepository roomGenericProductRepository, RoomGenericProductMapper roomGenericProductMapper) {
        this.roomGenericProductRepository = roomGenericProductRepository;
        this.roomGenericProductMapper = roomGenericProductMapper;
    }

    /**
     * Save a roomGenericProduct.
     *
     * @param roomGenericProductDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoomGenericProductDTO save(RoomGenericProductDTO roomGenericProductDTO) {
        log.debug("Request to save RoomGenericProduct : {}", roomGenericProductDTO);
        RoomGenericProduct roomGenericProduct = roomGenericProductMapper.toEntity(roomGenericProductDTO);
        roomGenericProduct = roomGenericProductRepository.save(roomGenericProduct);
        return roomGenericProductMapper.toDto(roomGenericProduct);
    }

    /**
     * Get all the roomGenericProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoomGenericProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoomGenericProducts");
        return roomGenericProductRepository.findAll(pageable)
            .map(roomGenericProductMapper::toDto);
    }


    /**
     * Get one roomGenericProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoomGenericProductDTO> findOne(Long id) {
        log.debug("Request to get RoomGenericProduct : {}", id);
        return roomGenericProductRepository.findById(id)
            .map(roomGenericProductMapper::toDto);
    }

    /**
     * Delete the roomGenericProduct by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomGenericProduct : {}", id);        roomGenericProductRepository.deleteById(id);
    }
}

package fr.mathieugeissler.constructmanager.service.impl;

import fr.mathieugeissler.constructmanager.service.GenericProductService;
import fr.mathieugeissler.constructmanager.domain.GenericProduct;
import fr.mathieugeissler.constructmanager.repository.GenericProductRepository;
import fr.mathieugeissler.constructmanager.service.dto.GenericProductDTO;
import fr.mathieugeissler.constructmanager.service.mapper.GenericProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing GenericProduct.
 */
@Service
@Transactional
public class GenericProductServiceImpl implements GenericProductService {

    private final Logger log = LoggerFactory.getLogger(GenericProductServiceImpl.class);

    private final GenericProductRepository genericProductRepository;

    private final GenericProductMapper genericProductMapper;

    public GenericProductServiceImpl(GenericProductRepository genericProductRepository, GenericProductMapper genericProductMapper) {
        this.genericProductRepository = genericProductRepository;
        this.genericProductMapper = genericProductMapper;
    }

    /**
     * Save a genericProduct.
     *
     * @param genericProductDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GenericProductDTO save(GenericProductDTO genericProductDTO) {
        log.debug("Request to save GenericProduct : {}", genericProductDTO);
        GenericProduct genericProduct = genericProductMapper.toEntity(genericProductDTO);
        genericProduct = genericProductRepository.save(genericProduct);
        return genericProductMapper.toDto(genericProduct);
    }

    /**
     * Get all the genericProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GenericProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GenericProducts");
        return genericProductRepository.findAll(pageable)
            .map(genericProductMapper::toDto);
    }


    /**
     * Get one genericProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GenericProductDTO> findOne(Long id) {
        log.debug("Request to get GenericProduct : {}", id);
        return genericProductRepository.findById(id)
            .map(genericProductMapper::toDto);
    }

    /**
     * Delete the genericProduct by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GenericProduct : {}", id);        genericProductRepository.deleteById(id);
    }
}

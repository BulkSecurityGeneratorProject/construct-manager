package fr.mathieugeissler.constructmanager.service.impl;

import fr.mathieugeissler.constructmanager.service.EstimateProductService;
import fr.mathieugeissler.constructmanager.domain.EstimateProduct;
import fr.mathieugeissler.constructmanager.repository.EstimateProductRepository;
import fr.mathieugeissler.constructmanager.service.dto.EstimateProductDTO;
import fr.mathieugeissler.constructmanager.service.mapper.EstimateProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing EstimateProduct.
 */
@Service
@Transactional
public class EstimateProductServiceImpl implements EstimateProductService {

    private final Logger log = LoggerFactory.getLogger(EstimateProductServiceImpl.class);

    private final EstimateProductRepository estimateProductRepository;

    private final EstimateProductMapper estimateProductMapper;

    public EstimateProductServiceImpl(EstimateProductRepository estimateProductRepository, EstimateProductMapper estimateProductMapper) {
        this.estimateProductRepository = estimateProductRepository;
        this.estimateProductMapper = estimateProductMapper;
    }

    /**
     * Save a estimateProduct.
     *
     * @param estimateProductDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EstimateProductDTO save(EstimateProductDTO estimateProductDTO) {
        log.debug("Request to save EstimateProduct : {}", estimateProductDTO);
        EstimateProduct estimateProduct = estimateProductMapper.toEntity(estimateProductDTO);
        estimateProduct = estimateProductRepository.save(estimateProduct);
        return estimateProductMapper.toDto(estimateProduct);
    }

    /**
     * Get all the estimateProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstimateProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstimateProducts");
        return estimateProductRepository.findAll(pageable)
            .map(estimateProductMapper::toDto);
    }


    /**
     * Get one estimateProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstimateProductDTO> findOne(Long id) {
        log.debug("Request to get EstimateProduct : {}", id);
        return estimateProductRepository.findById(id)
            .map(estimateProductMapper::toDto);
    }

    /**
     * Delete the estimateProduct by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstimateProduct : {}", id);        estimateProductRepository.deleteById(id);
    }
}

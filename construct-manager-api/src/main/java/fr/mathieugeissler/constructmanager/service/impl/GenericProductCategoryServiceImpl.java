package fr.mathieugeissler.constructmanager.service.impl;

import fr.mathieugeissler.constructmanager.service.GenericProductCategoryService;
import fr.mathieugeissler.constructmanager.domain.GenericProductCategory;
import fr.mathieugeissler.constructmanager.repository.GenericProductCategoryRepository;
import fr.mathieugeissler.constructmanager.service.dto.GenericProductCategoryDTO;
import fr.mathieugeissler.constructmanager.service.mapper.GenericProductCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing GenericProductCategory.
 */
@Service
@Transactional
public class GenericProductCategoryServiceImpl implements GenericProductCategoryService {

    private final Logger log = LoggerFactory.getLogger(GenericProductCategoryServiceImpl.class);

    private final GenericProductCategoryRepository genericProductCategoryRepository;

    private final GenericProductCategoryMapper genericProductCategoryMapper;

    public GenericProductCategoryServiceImpl(GenericProductCategoryRepository genericProductCategoryRepository, GenericProductCategoryMapper genericProductCategoryMapper) {
        this.genericProductCategoryRepository = genericProductCategoryRepository;
        this.genericProductCategoryMapper = genericProductCategoryMapper;
    }

    /**
     * Save a genericProductCategory.
     *
     * @param genericProductCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GenericProductCategoryDTO save(GenericProductCategoryDTO genericProductCategoryDTO) {
        log.debug("Request to save GenericProductCategory : {}", genericProductCategoryDTO);
        GenericProductCategory genericProductCategory = genericProductCategoryMapper.toEntity(genericProductCategoryDTO);
        genericProductCategory = genericProductCategoryRepository.save(genericProductCategory);
        return genericProductCategoryMapper.toDto(genericProductCategory);
    }

    /**
     * Get all the genericProductCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GenericProductCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GenericProductCategories");
        return genericProductCategoryRepository.findAll(pageable)
            .map(genericProductCategoryMapper::toDto);
    }


    /**
     * Get one genericProductCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GenericProductCategoryDTO> findOne(Long id) {
        log.debug("Request to get GenericProductCategory : {}", id);
        return genericProductCategoryRepository.findById(id)
            .map(genericProductCategoryMapper::toDto);
    }

    /**
     * Delete the genericProductCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GenericProductCategory : {}", id);        genericProductCategoryRepository.deleteById(id);
    }
}

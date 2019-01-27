package fr.mathieugeissler.constructmanager.service.impl;

import fr.mathieugeissler.constructmanager.service.RoomTypeService;
import fr.mathieugeissler.constructmanager.domain.RoomType;
import fr.mathieugeissler.constructmanager.repository.RoomTypeRepository;
import fr.mathieugeissler.constructmanager.service.dto.RoomTypeDTO;
import fr.mathieugeissler.constructmanager.service.mapper.RoomTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RoomType.
 */
@Service
@Transactional
public class RoomTypeServiceImpl implements RoomTypeService {

    private final Logger log = LoggerFactory.getLogger(RoomTypeServiceImpl.class);

    private final RoomTypeRepository roomTypeRepository;

    private final RoomTypeMapper roomTypeMapper;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository, RoomTypeMapper roomTypeMapper) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomTypeMapper = roomTypeMapper;
    }

    /**
     * Save a roomType.
     *
     * @param roomTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoomTypeDTO save(RoomTypeDTO roomTypeDTO) {
        log.debug("Request to save RoomType : {}", roomTypeDTO);
        RoomType roomType = roomTypeMapper.toEntity(roomTypeDTO);
        roomType = roomTypeRepository.save(roomType);
        return roomTypeMapper.toDto(roomType);
    }

    /**
     * Get all the roomTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoomTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoomTypes");
        return roomTypeRepository.findAll(pageable)
            .map(roomTypeMapper::toDto);
    }


    /**
     * Get one roomType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoomTypeDTO> findOne(Long id) {
        log.debug("Request to get RoomType : {}", id);
        return roomTypeRepository.findById(id)
            .map(roomTypeMapper::toDto);
    }

    /**
     * Delete the roomType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomType : {}", id);        roomTypeRepository.deleteById(id);
    }
}

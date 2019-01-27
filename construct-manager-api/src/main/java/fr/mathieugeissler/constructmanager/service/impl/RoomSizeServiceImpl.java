package fr.mathieugeissler.constructmanager.service.impl;

import fr.mathieugeissler.constructmanager.service.RoomSizeService;
import fr.mathieugeissler.constructmanager.domain.RoomSize;
import fr.mathieugeissler.constructmanager.repository.RoomSizeRepository;
import fr.mathieugeissler.constructmanager.service.dto.RoomSizeDTO;
import fr.mathieugeissler.constructmanager.service.mapper.RoomSizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RoomSize.
 */
@Service
@Transactional
public class RoomSizeServiceImpl implements RoomSizeService {

    private final Logger log = LoggerFactory.getLogger(RoomSizeServiceImpl.class);

    private final RoomSizeRepository roomSizeRepository;

    private final RoomSizeMapper roomSizeMapper;

    public RoomSizeServiceImpl(RoomSizeRepository roomSizeRepository, RoomSizeMapper roomSizeMapper) {
        this.roomSizeRepository = roomSizeRepository;
        this.roomSizeMapper = roomSizeMapper;
    }

    /**
     * Save a roomSize.
     *
     * @param roomSizeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoomSizeDTO save(RoomSizeDTO roomSizeDTO) {
        log.debug("Request to save RoomSize : {}", roomSizeDTO);
        RoomSize roomSize = roomSizeMapper.toEntity(roomSizeDTO);
        roomSize = roomSizeRepository.save(roomSize);
        return roomSizeMapper.toDto(roomSize);
    }

    /**
     * Get all the roomSizes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoomSizeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoomSizes");
        return roomSizeRepository.findAll(pageable)
            .map(roomSizeMapper::toDto);
    }


    /**
     * Get one roomSize by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoomSizeDTO> findOne(Long id) {
        log.debug("Request to get RoomSize : {}", id);
        return roomSizeRepository.findById(id)
            .map(roomSizeMapper::toDto);
    }

    /**
     * Delete the roomSize by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomSize : {}", id);        roomSizeRepository.deleteById(id);
    }
}

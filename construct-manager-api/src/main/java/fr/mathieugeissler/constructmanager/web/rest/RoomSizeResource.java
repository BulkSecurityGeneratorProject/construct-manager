package fr.mathieugeissler.constructmanager.web.rest;
import fr.mathieugeissler.constructmanager.service.RoomSizeService;
import fr.mathieugeissler.constructmanager.web.rest.errors.BadRequestAlertException;
import fr.mathieugeissler.constructmanager.web.rest.util.HeaderUtil;
import fr.mathieugeissler.constructmanager.web.rest.util.PaginationUtil;
import fr.mathieugeissler.constructmanager.service.dto.RoomSizeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RoomSize.
 */
@RestController
@RequestMapping("/api")
public class RoomSizeResource {

    private final Logger log = LoggerFactory.getLogger(RoomSizeResource.class);

    private static final String ENTITY_NAME = "roomSize";

    private final RoomSizeService roomSizeService;

    public RoomSizeResource(RoomSizeService roomSizeService) {
        this.roomSizeService = roomSizeService;
    }

    /**
     * POST  /room-sizes : Create a new roomSize.
     *
     * @param roomSizeDTO the roomSizeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomSizeDTO, or with status 400 (Bad Request) if the roomSize has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/room-sizes")
    public ResponseEntity<RoomSizeDTO> createRoomSize(@RequestBody RoomSizeDTO roomSizeDTO) throws URISyntaxException {
        log.debug("REST request to save RoomSize : {}", roomSizeDTO);
        if (roomSizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new roomSize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomSizeDTO result = roomSizeService.save(roomSizeDTO);
        return ResponseEntity.created(new URI("/api/room-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /room-sizes : Updates an existing roomSize.
     *
     * @param roomSizeDTO the roomSizeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomSizeDTO,
     * or with status 400 (Bad Request) if the roomSizeDTO is not valid,
     * or with status 500 (Internal Server Error) if the roomSizeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/room-sizes")
    public ResponseEntity<RoomSizeDTO> updateRoomSize(@RequestBody RoomSizeDTO roomSizeDTO) throws URISyntaxException {
        log.debug("REST request to update RoomSize : {}", roomSizeDTO);
        if (roomSizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoomSizeDTO result = roomSizeService.save(roomSizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roomSizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /room-sizes : get all the roomSizes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roomSizes in body
     */
    @GetMapping("/room-sizes")
    public ResponseEntity<List<RoomSizeDTO>> getAllRoomSizes(Pageable pageable) {
        log.debug("REST request to get a page of RoomSizes");
        Page<RoomSizeDTO> page = roomSizeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/room-sizes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /room-sizes/:id : get the "id" roomSize.
     *
     * @param id the id of the roomSizeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomSizeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/room-sizes/{id}")
    public ResponseEntity<RoomSizeDTO> getRoomSize(@PathVariable Long id) {
        log.debug("REST request to get RoomSize : {}", id);
        Optional<RoomSizeDTO> roomSizeDTO = roomSizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomSizeDTO);
    }

    /**
     * DELETE  /room-sizes/:id : delete the "id" roomSize.
     *
     * @param id the id of the roomSizeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/room-sizes/{id}")
    public ResponseEntity<Void> deleteRoomSize(@PathVariable Long id) {
        log.debug("REST request to delete RoomSize : {}", id);
        roomSizeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

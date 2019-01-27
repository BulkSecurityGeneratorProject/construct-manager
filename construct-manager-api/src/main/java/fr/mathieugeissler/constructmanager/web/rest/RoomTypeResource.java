package fr.mathieugeissler.constructmanager.web.rest;
import fr.mathieugeissler.constructmanager.service.RoomTypeService;
import fr.mathieugeissler.constructmanager.web.rest.errors.BadRequestAlertException;
import fr.mathieugeissler.constructmanager.web.rest.util.HeaderUtil;
import fr.mathieugeissler.constructmanager.web.rest.util.PaginationUtil;
import fr.mathieugeissler.constructmanager.service.dto.RoomTypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RoomType.
 */
@RestController
@RequestMapping("/api")
public class RoomTypeResource {

    private final Logger log = LoggerFactory.getLogger(RoomTypeResource.class);

    private static final String ENTITY_NAME = "roomType";

    private final RoomTypeService roomTypeService;

    public RoomTypeResource(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    /**
     * POST  /room-types : Create a new roomType.
     *
     * @param roomTypeDTO the roomTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomTypeDTO, or with status 400 (Bad Request) if the roomType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/room-types")
    public ResponseEntity<RoomTypeDTO> createRoomType(@Valid @RequestBody RoomTypeDTO roomTypeDTO) throws URISyntaxException {
        log.debug("REST request to save RoomType : {}", roomTypeDTO);
        if (roomTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new roomType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomTypeDTO result = roomTypeService.save(roomTypeDTO);
        return ResponseEntity.created(new URI("/api/room-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /room-types : Updates an existing roomType.
     *
     * @param roomTypeDTO the roomTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomTypeDTO,
     * or with status 400 (Bad Request) if the roomTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the roomTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/room-types")
    public ResponseEntity<RoomTypeDTO> updateRoomType(@Valid @RequestBody RoomTypeDTO roomTypeDTO) throws URISyntaxException {
        log.debug("REST request to update RoomType : {}", roomTypeDTO);
        if (roomTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoomTypeDTO result = roomTypeService.save(roomTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roomTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /room-types : get all the roomTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roomTypes in body
     */
    @GetMapping("/room-types")
    public ResponseEntity<List<RoomTypeDTO>> getAllRoomTypes(Pageable pageable) {
        log.debug("REST request to get a page of RoomTypes");
        Page<RoomTypeDTO> page = roomTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/room-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /room-types/:id : get the "id" roomType.
     *
     * @param id the id of the roomTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/room-types/{id}")
    public ResponseEntity<RoomTypeDTO> getRoomType(@PathVariable Long id) {
        log.debug("REST request to get RoomType : {}", id);
        Optional<RoomTypeDTO> roomTypeDTO = roomTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomTypeDTO);
    }

    /**
     * DELETE  /room-types/:id : delete the "id" roomType.
     *
     * @param id the id of the roomTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/room-types/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        log.debug("REST request to delete RoomType : {}", id);
        roomTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

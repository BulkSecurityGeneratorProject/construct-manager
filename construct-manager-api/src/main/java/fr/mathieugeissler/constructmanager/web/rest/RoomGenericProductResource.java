package fr.mathieugeissler.constructmanager.web.rest;
import fr.mathieugeissler.constructmanager.service.RoomGenericProductService;
import fr.mathieugeissler.constructmanager.web.rest.errors.BadRequestAlertException;
import fr.mathieugeissler.constructmanager.web.rest.util.HeaderUtil;
import fr.mathieugeissler.constructmanager.web.rest.util.PaginationUtil;
import fr.mathieugeissler.constructmanager.service.dto.RoomGenericProductDTO;
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
 * REST controller for managing RoomGenericProduct.
 */
@RestController
@RequestMapping("/api")
public class RoomGenericProductResource {

    private final Logger log = LoggerFactory.getLogger(RoomGenericProductResource.class);

    private static final String ENTITY_NAME = "roomGenericProduct";

    private final RoomGenericProductService roomGenericProductService;

    public RoomGenericProductResource(RoomGenericProductService roomGenericProductService) {
        this.roomGenericProductService = roomGenericProductService;
    }

    /**
     * POST  /room-generic-products : Create a new roomGenericProduct.
     *
     * @param roomGenericProductDTO the roomGenericProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomGenericProductDTO, or with status 400 (Bad Request) if the roomGenericProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/room-generic-products")
    public ResponseEntity<RoomGenericProductDTO> createRoomGenericProduct(@Valid @RequestBody RoomGenericProductDTO roomGenericProductDTO) throws URISyntaxException {
        log.debug("REST request to save RoomGenericProduct : {}", roomGenericProductDTO);
        if (roomGenericProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new roomGenericProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomGenericProductDTO result = roomGenericProductService.save(roomGenericProductDTO);
        return ResponseEntity.created(new URI("/api/room-generic-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /room-generic-products : Updates an existing roomGenericProduct.
     *
     * @param roomGenericProductDTO the roomGenericProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomGenericProductDTO,
     * or with status 400 (Bad Request) if the roomGenericProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the roomGenericProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/room-generic-products")
    public ResponseEntity<RoomGenericProductDTO> updateRoomGenericProduct(@Valid @RequestBody RoomGenericProductDTO roomGenericProductDTO) throws URISyntaxException {
        log.debug("REST request to update RoomGenericProduct : {}", roomGenericProductDTO);
        if (roomGenericProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoomGenericProductDTO result = roomGenericProductService.save(roomGenericProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roomGenericProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /room-generic-products : get all the roomGenericProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roomGenericProducts in body
     */
    @GetMapping("/room-generic-products")
    public ResponseEntity<List<RoomGenericProductDTO>> getAllRoomGenericProducts(Pageable pageable) {
        log.debug("REST request to get a page of RoomGenericProducts");
        Page<RoomGenericProductDTO> page = roomGenericProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/room-generic-products");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /room-generic-products/:id : get the "id" roomGenericProduct.
     *
     * @param id the id of the roomGenericProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomGenericProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/room-generic-products/{id}")
    public ResponseEntity<RoomGenericProductDTO> getRoomGenericProduct(@PathVariable Long id) {
        log.debug("REST request to get RoomGenericProduct : {}", id);
        Optional<RoomGenericProductDTO> roomGenericProductDTO = roomGenericProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomGenericProductDTO);
    }

    /**
     * DELETE  /room-generic-products/:id : delete the "id" roomGenericProduct.
     *
     * @param id the id of the roomGenericProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/room-generic-products/{id}")
    public ResponseEntity<Void> deleteRoomGenericProduct(@PathVariable Long id) {
        log.debug("REST request to delete RoomGenericProduct : {}", id);
        roomGenericProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package fr.mathieugeissler.constructmanager.web.rest;
import fr.mathieugeissler.constructmanager.service.HomeService;
import fr.mathieugeissler.constructmanager.web.rest.errors.BadRequestAlertException;
import fr.mathieugeissler.constructmanager.web.rest.util.HeaderUtil;
import fr.mathieugeissler.constructmanager.web.rest.util.PaginationUtil;
import fr.mathieugeissler.constructmanager.service.dto.HomeDTO;
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
 * REST controller for managing Home.
 */
@RestController
@RequestMapping("/api")
public class HomeResource {

    private final Logger log = LoggerFactory.getLogger(HomeResource.class);

    private static final String ENTITY_NAME = "home";

    private final HomeService homeService;

    public HomeResource(HomeService homeService) {
        this.homeService = homeService;
    }

    /**
     * POST  /homes : Create a new home.
     *
     * @param homeDTO the homeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new homeDTO, or with status 400 (Bad Request) if the home has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/homes")
    public ResponseEntity<HomeDTO> createHome(@Valid @RequestBody HomeDTO homeDTO) throws URISyntaxException {
        log.debug("REST request to save Home : {}", homeDTO);
        if (homeDTO.getId() != null) {
            throw new BadRequestAlertException("A new home cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HomeDTO result = homeService.save(homeDTO);
        return ResponseEntity.created(new URI("/api/homes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /homes : Updates an existing home.
     *
     * @param homeDTO the homeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated homeDTO,
     * or with status 400 (Bad Request) if the homeDTO is not valid,
     * or with status 500 (Internal Server Error) if the homeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/homes")
    public ResponseEntity<HomeDTO> updateHome(@Valid @RequestBody HomeDTO homeDTO) throws URISyntaxException {
        log.debug("REST request to update Home : {}", homeDTO);
        if (homeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HomeDTO result = homeService.save(homeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, homeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /homes : get all the homes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of homes in body
     */
    @GetMapping("/homes")
    public ResponseEntity<List<HomeDTO>> getAllHomes(Pageable pageable) {
        log.debug("REST request to get a page of Homes");
        Page<HomeDTO> page = homeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/homes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /homes/:id : get the "id" home.
     *
     * @param id the id of the homeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the homeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/homes/{id}")
    public ResponseEntity<HomeDTO> getHome(@PathVariable Long id) {
        log.debug("REST request to get Home : {}", id);
        Optional<HomeDTO> homeDTO = homeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(homeDTO);
    }

    /**
     * DELETE  /homes/:id : delete the "id" home.
     *
     * @param id the id of the homeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/homes/{id}")
    public ResponseEntity<Void> deleteHome(@PathVariable Long id) {
        log.debug("REST request to delete Home : {}", id);
        homeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

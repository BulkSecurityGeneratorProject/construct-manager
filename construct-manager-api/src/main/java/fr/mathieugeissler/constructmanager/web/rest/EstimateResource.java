package fr.mathieugeissler.constructmanager.web.rest;
import fr.mathieugeissler.constructmanager.service.EstimateService;
import fr.mathieugeissler.constructmanager.web.rest.errors.BadRequestAlertException;
import fr.mathieugeissler.constructmanager.web.rest.util.HeaderUtil;
import fr.mathieugeissler.constructmanager.web.rest.util.PaginationUtil;
import fr.mathieugeissler.constructmanager.service.dto.EstimateDTO;
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
 * REST controller for managing Estimate.
 */
@RestController
@RequestMapping("/api")
public class EstimateResource {

    private final Logger log = LoggerFactory.getLogger(EstimateResource.class);

    private static final String ENTITY_NAME = "estimate";

    private final EstimateService estimateService;

    public EstimateResource(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    /**
     * POST  /estimates : Create a new estimate.
     *
     * @param estimateDTO the estimateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estimateDTO, or with status 400 (Bad Request) if the estimate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estimates")
    public ResponseEntity<EstimateDTO> createEstimate(@Valid @RequestBody EstimateDTO estimateDTO) throws URISyntaxException {
        log.debug("REST request to save Estimate : {}", estimateDTO);
        if (estimateDTO.getId() != null) {
            throw new BadRequestAlertException("A new estimate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstimateDTO result = estimateService.save(estimateDTO);
        return ResponseEntity.created(new URI("/api/estimates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estimates : Updates an existing estimate.
     *
     * @param estimateDTO the estimateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estimateDTO,
     * or with status 400 (Bad Request) if the estimateDTO is not valid,
     * or with status 500 (Internal Server Error) if the estimateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estimates")
    public ResponseEntity<EstimateDTO> updateEstimate(@Valid @RequestBody EstimateDTO estimateDTO) throws URISyntaxException {
        log.debug("REST request to update Estimate : {}", estimateDTO);
        if (estimateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstimateDTO result = estimateService.save(estimateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estimateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estimates : get all the estimates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estimates in body
     */
    @GetMapping("/estimates")
    public ResponseEntity<List<EstimateDTO>> getAllEstimates(Pageable pageable) {
        log.debug("REST request to get a page of Estimates");
        Page<EstimateDTO> page = estimateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estimates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /estimates/:id : get the "id" estimate.
     *
     * @param id the id of the estimateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estimateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/estimates/{id}")
    public ResponseEntity<EstimateDTO> getEstimate(@PathVariable Long id) {
        log.debug("REST request to get Estimate : {}", id);
        Optional<EstimateDTO> estimateDTO = estimateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estimateDTO);
    }

    /**
     * DELETE  /estimates/:id : delete the "id" estimate.
     *
     * @param id the id of the estimateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estimates/{id}")
    public ResponseEntity<Void> deleteEstimate(@PathVariable Long id) {
        log.debug("REST request to delete Estimate : {}", id);
        estimateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package fr.mathieugeissler.constructmanager.web.rest;
import fr.mathieugeissler.constructmanager.service.EstimateProductService;
import fr.mathieugeissler.constructmanager.web.rest.errors.BadRequestAlertException;
import fr.mathieugeissler.constructmanager.web.rest.util.HeaderUtil;
import fr.mathieugeissler.constructmanager.web.rest.util.PaginationUtil;
import fr.mathieugeissler.constructmanager.service.dto.EstimateProductDTO;
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
 * REST controller for managing EstimateProduct.
 */
@RestController
@RequestMapping("/api")
public class EstimateProductResource {

    private final Logger log = LoggerFactory.getLogger(EstimateProductResource.class);

    private static final String ENTITY_NAME = "estimateProduct";

    private final EstimateProductService estimateProductService;

    public EstimateProductResource(EstimateProductService estimateProductService) {
        this.estimateProductService = estimateProductService;
    }

    /**
     * POST  /estimate-products : Create a new estimateProduct.
     *
     * @param estimateProductDTO the estimateProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estimateProductDTO, or with status 400 (Bad Request) if the estimateProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estimate-products")
    public ResponseEntity<EstimateProductDTO> createEstimateProduct(@RequestBody EstimateProductDTO estimateProductDTO) throws URISyntaxException {
        log.debug("REST request to save EstimateProduct : {}", estimateProductDTO);
        if (estimateProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new estimateProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstimateProductDTO result = estimateProductService.save(estimateProductDTO);
        return ResponseEntity.created(new URI("/api/estimate-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estimate-products : Updates an existing estimateProduct.
     *
     * @param estimateProductDTO the estimateProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estimateProductDTO,
     * or with status 400 (Bad Request) if the estimateProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the estimateProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estimate-products")
    public ResponseEntity<EstimateProductDTO> updateEstimateProduct(@RequestBody EstimateProductDTO estimateProductDTO) throws URISyntaxException {
        log.debug("REST request to update EstimateProduct : {}", estimateProductDTO);
        if (estimateProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstimateProductDTO result = estimateProductService.save(estimateProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estimateProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estimate-products : get all the estimateProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estimateProducts in body
     */
    @GetMapping("/estimate-products")
    public ResponseEntity<List<EstimateProductDTO>> getAllEstimateProducts(Pageable pageable) {
        log.debug("REST request to get a page of EstimateProducts");
        Page<EstimateProductDTO> page = estimateProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estimate-products");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /estimate-products/:id : get the "id" estimateProduct.
     *
     * @param id the id of the estimateProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estimateProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/estimate-products/{id}")
    public ResponseEntity<EstimateProductDTO> getEstimateProduct(@PathVariable Long id) {
        log.debug("REST request to get EstimateProduct : {}", id);
        Optional<EstimateProductDTO> estimateProductDTO = estimateProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estimateProductDTO);
    }

    /**
     * DELETE  /estimate-products/:id : delete the "id" estimateProduct.
     *
     * @param id the id of the estimateProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estimate-products/{id}")
    public ResponseEntity<Void> deleteEstimateProduct(@PathVariable Long id) {
        log.debug("REST request to delete EstimateProduct : {}", id);
        estimateProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

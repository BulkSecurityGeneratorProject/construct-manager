package fr.mathieugeissler.constructmanager.web.rest;
import fr.mathieugeissler.constructmanager.service.GenericProductService;
import fr.mathieugeissler.constructmanager.web.rest.errors.BadRequestAlertException;
import fr.mathieugeissler.constructmanager.web.rest.util.HeaderUtil;
import fr.mathieugeissler.constructmanager.web.rest.util.PaginationUtil;
import fr.mathieugeissler.constructmanager.service.dto.GenericProductDTO;
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
 * REST controller for managing GenericProduct.
 */
@RestController
@RequestMapping("/api")
public class GenericProductResource {

    private final Logger log = LoggerFactory.getLogger(GenericProductResource.class);

    private static final String ENTITY_NAME = "genericProduct";

    private final GenericProductService genericProductService;

    public GenericProductResource(GenericProductService genericProductService) {
        this.genericProductService = genericProductService;
    }

    /**
     * POST  /generic-products : Create a new genericProduct.
     *
     * @param genericProductDTO the genericProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genericProductDTO, or with status 400 (Bad Request) if the genericProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/generic-products")
    public ResponseEntity<GenericProductDTO> createGenericProduct(@Valid @RequestBody GenericProductDTO genericProductDTO) throws URISyntaxException {
        log.debug("REST request to save GenericProduct : {}", genericProductDTO);
        if (genericProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new genericProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenericProductDTO result = genericProductService.save(genericProductDTO);
        return ResponseEntity.created(new URI("/api/generic-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /generic-products : Updates an existing genericProduct.
     *
     * @param genericProductDTO the genericProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genericProductDTO,
     * or with status 400 (Bad Request) if the genericProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the genericProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/generic-products")
    public ResponseEntity<GenericProductDTO> updateGenericProduct(@Valid @RequestBody GenericProductDTO genericProductDTO) throws URISyntaxException {
        log.debug("REST request to update GenericProduct : {}", genericProductDTO);
        if (genericProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GenericProductDTO result = genericProductService.save(genericProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, genericProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /generic-products : get all the genericProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of genericProducts in body
     */
    @GetMapping("/generic-products")
    public ResponseEntity<List<GenericProductDTO>> getAllGenericProducts(Pageable pageable) {
        log.debug("REST request to get a page of GenericProducts");
        Page<GenericProductDTO> page = genericProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/generic-products");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /generic-products/:id : get the "id" genericProduct.
     *
     * @param id the id of the genericProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genericProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/generic-products/{id}")
    public ResponseEntity<GenericProductDTO> getGenericProduct(@PathVariable Long id) {
        log.debug("REST request to get GenericProduct : {}", id);
        Optional<GenericProductDTO> genericProductDTO = genericProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(genericProductDTO);
    }

    /**
     * DELETE  /generic-products/:id : delete the "id" genericProduct.
     *
     * @param id the id of the genericProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/generic-products/{id}")
    public ResponseEntity<Void> deleteGenericProduct(@PathVariable Long id) {
        log.debug("REST request to delete GenericProduct : {}", id);
        genericProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

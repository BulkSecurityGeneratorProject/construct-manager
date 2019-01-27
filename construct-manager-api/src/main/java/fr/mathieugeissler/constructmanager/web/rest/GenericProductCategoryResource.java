package fr.mathieugeissler.constructmanager.web.rest;
import fr.mathieugeissler.constructmanager.service.GenericProductCategoryService;
import fr.mathieugeissler.constructmanager.web.rest.errors.BadRequestAlertException;
import fr.mathieugeissler.constructmanager.web.rest.util.HeaderUtil;
import fr.mathieugeissler.constructmanager.web.rest.util.PaginationUtil;
import fr.mathieugeissler.constructmanager.service.dto.GenericProductCategoryDTO;
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
 * REST controller for managing GenericProductCategory.
 */
@RestController
@RequestMapping("/api")
public class GenericProductCategoryResource {

    private final Logger log = LoggerFactory.getLogger(GenericProductCategoryResource.class);

    private static final String ENTITY_NAME = "genericProductCategory";

    private final GenericProductCategoryService genericProductCategoryService;

    public GenericProductCategoryResource(GenericProductCategoryService genericProductCategoryService) {
        this.genericProductCategoryService = genericProductCategoryService;
    }

    /**
     * POST  /generic-product-categories : Create a new genericProductCategory.
     *
     * @param genericProductCategoryDTO the genericProductCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genericProductCategoryDTO, or with status 400 (Bad Request) if the genericProductCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/generic-product-categories")
    public ResponseEntity<GenericProductCategoryDTO> createGenericProductCategory(@Valid @RequestBody GenericProductCategoryDTO genericProductCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save GenericProductCategory : {}", genericProductCategoryDTO);
        if (genericProductCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new genericProductCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenericProductCategoryDTO result = genericProductCategoryService.save(genericProductCategoryDTO);
        return ResponseEntity.created(new URI("/api/generic-product-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /generic-product-categories : Updates an existing genericProductCategory.
     *
     * @param genericProductCategoryDTO the genericProductCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genericProductCategoryDTO,
     * or with status 400 (Bad Request) if the genericProductCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the genericProductCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/generic-product-categories")
    public ResponseEntity<GenericProductCategoryDTO> updateGenericProductCategory(@Valid @RequestBody GenericProductCategoryDTO genericProductCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update GenericProductCategory : {}", genericProductCategoryDTO);
        if (genericProductCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GenericProductCategoryDTO result = genericProductCategoryService.save(genericProductCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, genericProductCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /generic-product-categories : get all the genericProductCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of genericProductCategories in body
     */
    @GetMapping("/generic-product-categories")
    public ResponseEntity<List<GenericProductCategoryDTO>> getAllGenericProductCategories(Pageable pageable) {
        log.debug("REST request to get a page of GenericProductCategories");
        Page<GenericProductCategoryDTO> page = genericProductCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/generic-product-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /generic-product-categories/:id : get the "id" genericProductCategory.
     *
     * @param id the id of the genericProductCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genericProductCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/generic-product-categories/{id}")
    public ResponseEntity<GenericProductCategoryDTO> getGenericProductCategory(@PathVariable Long id) {
        log.debug("REST request to get GenericProductCategory : {}", id);
        Optional<GenericProductCategoryDTO> genericProductCategoryDTO = genericProductCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(genericProductCategoryDTO);
    }

    /**
     * DELETE  /generic-product-categories/:id : delete the "id" genericProductCategory.
     *
     * @param id the id of the genericProductCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/generic-product-categories/{id}")
    public ResponseEntity<Void> deleteGenericProductCategory(@PathVariable Long id) {
        log.debug("REST request to delete GenericProductCategory : {}", id);
        genericProductCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

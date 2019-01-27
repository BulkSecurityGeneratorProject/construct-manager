package fr.mathieugeissler.constructmanager.web.rest;

import fr.mathieugeissler.constructmanager.ConstructManagerApiApp;

import fr.mathieugeissler.constructmanager.domain.EstimateProduct;
import fr.mathieugeissler.constructmanager.repository.EstimateProductRepository;
import fr.mathieugeissler.constructmanager.service.EstimateProductService;
import fr.mathieugeissler.constructmanager.service.dto.EstimateProductDTO;
import fr.mathieugeissler.constructmanager.service.mapper.EstimateProductMapper;
import fr.mathieugeissler.constructmanager.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static fr.mathieugeissler.constructmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EstimateProductResource REST controller.
 *
 * @see EstimateProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConstructManagerApiApp.class)
public class EstimateProductResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private EstimateProductRepository estimateProductRepository;

    @Autowired
    private EstimateProductMapper estimateProductMapper;

    @Autowired
    private EstimateProductService estimateProductService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEstimateProductMockMvc;

    private EstimateProduct estimateProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstimateProductResource estimateProductResource = new EstimateProductResource(estimateProductService);
        this.restEstimateProductMockMvc = MockMvcBuilders.standaloneSetup(estimateProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstimateProduct createEntity(EntityManager em) {
        EstimateProduct estimateProduct = new EstimateProduct()
            .number(DEFAULT_NUMBER)
            .price(DEFAULT_PRICE);
        return estimateProduct;
    }

    @Before
    public void initTest() {
        estimateProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstimateProduct() throws Exception {
        int databaseSizeBeforeCreate = estimateProductRepository.findAll().size();

        // Create the EstimateProduct
        EstimateProductDTO estimateProductDTO = estimateProductMapper.toDto(estimateProduct);
        restEstimateProductMockMvc.perform(post("/api/estimate-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimateProductDTO)))
            .andExpect(status().isCreated());

        // Validate the EstimateProduct in the database
        List<EstimateProduct> estimateProductList = estimateProductRepository.findAll();
        assertThat(estimateProductList).hasSize(databaseSizeBeforeCreate + 1);
        EstimateProduct testEstimateProduct = estimateProductList.get(estimateProductList.size() - 1);
        assertThat(testEstimateProduct.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testEstimateProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createEstimateProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estimateProductRepository.findAll().size();

        // Create the EstimateProduct with an existing ID
        estimateProduct.setId(1L);
        EstimateProductDTO estimateProductDTO = estimateProductMapper.toDto(estimateProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstimateProductMockMvc.perform(post("/api/estimate-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimateProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstimateProduct in the database
        List<EstimateProduct> estimateProductList = estimateProductRepository.findAll();
        assertThat(estimateProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEstimateProducts() throws Exception {
        // Initialize the database
        estimateProductRepository.saveAndFlush(estimateProduct);

        // Get all the estimateProductList
        restEstimateProductMockMvc.perform(get("/api/estimate-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estimateProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getEstimateProduct() throws Exception {
        // Initialize the database
        estimateProductRepository.saveAndFlush(estimateProduct);

        // Get the estimateProduct
        restEstimateProductMockMvc.perform(get("/api/estimate-products/{id}", estimateProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estimateProduct.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEstimateProduct() throws Exception {
        // Get the estimateProduct
        restEstimateProductMockMvc.perform(get("/api/estimate-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstimateProduct() throws Exception {
        // Initialize the database
        estimateProductRepository.saveAndFlush(estimateProduct);

        int databaseSizeBeforeUpdate = estimateProductRepository.findAll().size();

        // Update the estimateProduct
        EstimateProduct updatedEstimateProduct = estimateProductRepository.findById(estimateProduct.getId()).get();
        // Disconnect from session so that the updates on updatedEstimateProduct are not directly saved in db
        em.detach(updatedEstimateProduct);
        updatedEstimateProduct
            .number(UPDATED_NUMBER)
            .price(UPDATED_PRICE);
        EstimateProductDTO estimateProductDTO = estimateProductMapper.toDto(updatedEstimateProduct);

        restEstimateProductMockMvc.perform(put("/api/estimate-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimateProductDTO)))
            .andExpect(status().isOk());

        // Validate the EstimateProduct in the database
        List<EstimateProduct> estimateProductList = estimateProductRepository.findAll();
        assertThat(estimateProductList).hasSize(databaseSizeBeforeUpdate);
        EstimateProduct testEstimateProduct = estimateProductList.get(estimateProductList.size() - 1);
        assertThat(testEstimateProduct.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testEstimateProduct.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingEstimateProduct() throws Exception {
        int databaseSizeBeforeUpdate = estimateProductRepository.findAll().size();

        // Create the EstimateProduct
        EstimateProductDTO estimateProductDTO = estimateProductMapper.toDto(estimateProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstimateProductMockMvc.perform(put("/api/estimate-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimateProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstimateProduct in the database
        List<EstimateProduct> estimateProductList = estimateProductRepository.findAll();
        assertThat(estimateProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstimateProduct() throws Exception {
        // Initialize the database
        estimateProductRepository.saveAndFlush(estimateProduct);

        int databaseSizeBeforeDelete = estimateProductRepository.findAll().size();

        // Delete the estimateProduct
        restEstimateProductMockMvc.perform(delete("/api/estimate-products/{id}", estimateProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EstimateProduct> estimateProductList = estimateProductRepository.findAll();
        assertThat(estimateProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstimateProduct.class);
        EstimateProduct estimateProduct1 = new EstimateProduct();
        estimateProduct1.setId(1L);
        EstimateProduct estimateProduct2 = new EstimateProduct();
        estimateProduct2.setId(estimateProduct1.getId());
        assertThat(estimateProduct1).isEqualTo(estimateProduct2);
        estimateProduct2.setId(2L);
        assertThat(estimateProduct1).isNotEqualTo(estimateProduct2);
        estimateProduct1.setId(null);
        assertThat(estimateProduct1).isNotEqualTo(estimateProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstimateProductDTO.class);
        EstimateProductDTO estimateProductDTO1 = new EstimateProductDTO();
        estimateProductDTO1.setId(1L);
        EstimateProductDTO estimateProductDTO2 = new EstimateProductDTO();
        assertThat(estimateProductDTO1).isNotEqualTo(estimateProductDTO2);
        estimateProductDTO2.setId(estimateProductDTO1.getId());
        assertThat(estimateProductDTO1).isEqualTo(estimateProductDTO2);
        estimateProductDTO2.setId(2L);
        assertThat(estimateProductDTO1).isNotEqualTo(estimateProductDTO2);
        estimateProductDTO1.setId(null);
        assertThat(estimateProductDTO1).isNotEqualTo(estimateProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estimateProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estimateProductMapper.fromId(null)).isNull();
    }
}

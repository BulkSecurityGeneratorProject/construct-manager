package fr.mathieugeissler.constructmanager.web.rest;

import fr.mathieugeissler.constructmanager.ConstructManagerApiApp;

import fr.mathieugeissler.constructmanager.domain.GenericProduct;
import fr.mathieugeissler.constructmanager.repository.GenericProductRepository;
import fr.mathieugeissler.constructmanager.service.GenericProductService;
import fr.mathieugeissler.constructmanager.service.dto.GenericProductDTO;
import fr.mathieugeissler.constructmanager.service.mapper.GenericProductMapper;
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
 * Test class for the GenericProductResource REST controller.
 *
 * @see GenericProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConstructManagerApiApp.class)
public class GenericProductResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GenericProductRepository genericProductRepository;

    @Autowired
    private GenericProductMapper genericProductMapper;

    @Autowired
    private GenericProductService genericProductService;

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

    private MockMvc restGenericProductMockMvc;

    private GenericProduct genericProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GenericProductResource genericProductResource = new GenericProductResource(genericProductService);
        this.restGenericProductMockMvc = MockMvcBuilders.standaloneSetup(genericProductResource)
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
    public static GenericProduct createEntity(EntityManager em) {
        GenericProduct genericProduct = new GenericProduct()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return genericProduct;
    }

    @Before
    public void initTest() {
        genericProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenericProduct() throws Exception {
        int databaseSizeBeforeCreate = genericProductRepository.findAll().size();

        // Create the GenericProduct
        GenericProductDTO genericProductDTO = genericProductMapper.toDto(genericProduct);
        restGenericProductMockMvc.perform(post("/api/generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductDTO)))
            .andExpect(status().isCreated());

        // Validate the GenericProduct in the database
        List<GenericProduct> genericProductList = genericProductRepository.findAll();
        assertThat(genericProductList).hasSize(databaseSizeBeforeCreate + 1);
        GenericProduct testGenericProduct = genericProductList.get(genericProductList.size() - 1);
        assertThat(testGenericProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGenericProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGenericProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genericProductRepository.findAll().size();

        // Create the GenericProduct with an existing ID
        genericProduct.setId(1L);
        GenericProductDTO genericProductDTO = genericProductMapper.toDto(genericProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenericProductMockMvc.perform(post("/api/generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GenericProduct in the database
        List<GenericProduct> genericProductList = genericProductRepository.findAll();
        assertThat(genericProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = genericProductRepository.findAll().size();
        // set the field null
        genericProduct.setName(null);

        // Create the GenericProduct, which fails.
        GenericProductDTO genericProductDTO = genericProductMapper.toDto(genericProduct);

        restGenericProductMockMvc.perform(post("/api/generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductDTO)))
            .andExpect(status().isBadRequest());

        List<GenericProduct> genericProductList = genericProductRepository.findAll();
        assertThat(genericProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenericProducts() throws Exception {
        // Initialize the database
        genericProductRepository.saveAndFlush(genericProduct);

        // Get all the genericProductList
        restGenericProductMockMvc.perform(get("/api/generic-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genericProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getGenericProduct() throws Exception {
        // Initialize the database
        genericProductRepository.saveAndFlush(genericProduct);

        // Get the genericProduct
        restGenericProductMockMvc.perform(get("/api/generic-products/{id}", genericProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(genericProduct.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGenericProduct() throws Exception {
        // Get the genericProduct
        restGenericProductMockMvc.perform(get("/api/generic-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenericProduct() throws Exception {
        // Initialize the database
        genericProductRepository.saveAndFlush(genericProduct);

        int databaseSizeBeforeUpdate = genericProductRepository.findAll().size();

        // Update the genericProduct
        GenericProduct updatedGenericProduct = genericProductRepository.findById(genericProduct.getId()).get();
        // Disconnect from session so that the updates on updatedGenericProduct are not directly saved in db
        em.detach(updatedGenericProduct);
        updatedGenericProduct
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        GenericProductDTO genericProductDTO = genericProductMapper.toDto(updatedGenericProduct);

        restGenericProductMockMvc.perform(put("/api/generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductDTO)))
            .andExpect(status().isOk());

        // Validate the GenericProduct in the database
        List<GenericProduct> genericProductList = genericProductRepository.findAll();
        assertThat(genericProductList).hasSize(databaseSizeBeforeUpdate);
        GenericProduct testGenericProduct = genericProductList.get(genericProductList.size() - 1);
        assertThat(testGenericProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGenericProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGenericProduct() throws Exception {
        int databaseSizeBeforeUpdate = genericProductRepository.findAll().size();

        // Create the GenericProduct
        GenericProductDTO genericProductDTO = genericProductMapper.toDto(genericProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenericProductMockMvc.perform(put("/api/generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GenericProduct in the database
        List<GenericProduct> genericProductList = genericProductRepository.findAll();
        assertThat(genericProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGenericProduct() throws Exception {
        // Initialize the database
        genericProductRepository.saveAndFlush(genericProduct);

        int databaseSizeBeforeDelete = genericProductRepository.findAll().size();

        // Delete the genericProduct
        restGenericProductMockMvc.perform(delete("/api/generic-products/{id}", genericProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GenericProduct> genericProductList = genericProductRepository.findAll();
        assertThat(genericProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenericProduct.class);
        GenericProduct genericProduct1 = new GenericProduct();
        genericProduct1.setId(1L);
        GenericProduct genericProduct2 = new GenericProduct();
        genericProduct2.setId(genericProduct1.getId());
        assertThat(genericProduct1).isEqualTo(genericProduct2);
        genericProduct2.setId(2L);
        assertThat(genericProduct1).isNotEqualTo(genericProduct2);
        genericProduct1.setId(null);
        assertThat(genericProduct1).isNotEqualTo(genericProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenericProductDTO.class);
        GenericProductDTO genericProductDTO1 = new GenericProductDTO();
        genericProductDTO1.setId(1L);
        GenericProductDTO genericProductDTO2 = new GenericProductDTO();
        assertThat(genericProductDTO1).isNotEqualTo(genericProductDTO2);
        genericProductDTO2.setId(genericProductDTO1.getId());
        assertThat(genericProductDTO1).isEqualTo(genericProductDTO2);
        genericProductDTO2.setId(2L);
        assertThat(genericProductDTO1).isNotEqualTo(genericProductDTO2);
        genericProductDTO1.setId(null);
        assertThat(genericProductDTO1).isNotEqualTo(genericProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(genericProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(genericProductMapper.fromId(null)).isNull();
    }
}

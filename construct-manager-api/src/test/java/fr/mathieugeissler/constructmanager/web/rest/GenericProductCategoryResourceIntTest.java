package fr.mathieugeissler.constructmanager.web.rest;

import fr.mathieugeissler.constructmanager.ConstructManagerApiApp;

import fr.mathieugeissler.constructmanager.domain.GenericProductCategory;
import fr.mathieugeissler.constructmanager.repository.GenericProductCategoryRepository;
import fr.mathieugeissler.constructmanager.service.GenericProductCategoryService;
import fr.mathieugeissler.constructmanager.service.dto.GenericProductCategoryDTO;
import fr.mathieugeissler.constructmanager.service.mapper.GenericProductCategoryMapper;
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
 * Test class for the GenericProductCategoryResource REST controller.
 *
 * @see GenericProductCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConstructManagerApiApp.class)
public class GenericProductCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GenericProductCategoryRepository genericProductCategoryRepository;

    @Autowired
    private GenericProductCategoryMapper genericProductCategoryMapper;

    @Autowired
    private GenericProductCategoryService genericProductCategoryService;

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

    private MockMvc restGenericProductCategoryMockMvc;

    private GenericProductCategory genericProductCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GenericProductCategoryResource genericProductCategoryResource = new GenericProductCategoryResource(genericProductCategoryService);
        this.restGenericProductCategoryMockMvc = MockMvcBuilders.standaloneSetup(genericProductCategoryResource)
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
    public static GenericProductCategory createEntity(EntityManager em) {
        GenericProductCategory genericProductCategory = new GenericProductCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return genericProductCategory;
    }

    @Before
    public void initTest() {
        genericProductCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenericProductCategory() throws Exception {
        int databaseSizeBeforeCreate = genericProductCategoryRepository.findAll().size();

        // Create the GenericProductCategory
        GenericProductCategoryDTO genericProductCategoryDTO = genericProductCategoryMapper.toDto(genericProductCategory);
        restGenericProductCategoryMockMvc.perform(post("/api/generic-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the GenericProductCategory in the database
        List<GenericProductCategory> genericProductCategoryList = genericProductCategoryRepository.findAll();
        assertThat(genericProductCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        GenericProductCategory testGenericProductCategory = genericProductCategoryList.get(genericProductCategoryList.size() - 1);
        assertThat(testGenericProductCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGenericProductCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGenericProductCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genericProductCategoryRepository.findAll().size();

        // Create the GenericProductCategory with an existing ID
        genericProductCategory.setId(1L);
        GenericProductCategoryDTO genericProductCategoryDTO = genericProductCategoryMapper.toDto(genericProductCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenericProductCategoryMockMvc.perform(post("/api/generic-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GenericProductCategory in the database
        List<GenericProductCategory> genericProductCategoryList = genericProductCategoryRepository.findAll();
        assertThat(genericProductCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = genericProductCategoryRepository.findAll().size();
        // set the field null
        genericProductCategory.setName(null);

        // Create the GenericProductCategory, which fails.
        GenericProductCategoryDTO genericProductCategoryDTO = genericProductCategoryMapper.toDto(genericProductCategory);

        restGenericProductCategoryMockMvc.perform(post("/api/generic-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<GenericProductCategory> genericProductCategoryList = genericProductCategoryRepository.findAll();
        assertThat(genericProductCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenericProductCategories() throws Exception {
        // Initialize the database
        genericProductCategoryRepository.saveAndFlush(genericProductCategory);

        // Get all the genericProductCategoryList
        restGenericProductCategoryMockMvc.perform(get("/api/generic-product-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genericProductCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getGenericProductCategory() throws Exception {
        // Initialize the database
        genericProductCategoryRepository.saveAndFlush(genericProductCategory);

        // Get the genericProductCategory
        restGenericProductCategoryMockMvc.perform(get("/api/generic-product-categories/{id}", genericProductCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(genericProductCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGenericProductCategory() throws Exception {
        // Get the genericProductCategory
        restGenericProductCategoryMockMvc.perform(get("/api/generic-product-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenericProductCategory() throws Exception {
        // Initialize the database
        genericProductCategoryRepository.saveAndFlush(genericProductCategory);

        int databaseSizeBeforeUpdate = genericProductCategoryRepository.findAll().size();

        // Update the genericProductCategory
        GenericProductCategory updatedGenericProductCategory = genericProductCategoryRepository.findById(genericProductCategory.getId()).get();
        // Disconnect from session so that the updates on updatedGenericProductCategory are not directly saved in db
        em.detach(updatedGenericProductCategory);
        updatedGenericProductCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        GenericProductCategoryDTO genericProductCategoryDTO = genericProductCategoryMapper.toDto(updatedGenericProductCategory);

        restGenericProductCategoryMockMvc.perform(put("/api/generic-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the GenericProductCategory in the database
        List<GenericProductCategory> genericProductCategoryList = genericProductCategoryRepository.findAll();
        assertThat(genericProductCategoryList).hasSize(databaseSizeBeforeUpdate);
        GenericProductCategory testGenericProductCategory = genericProductCategoryList.get(genericProductCategoryList.size() - 1);
        assertThat(testGenericProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGenericProductCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGenericProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = genericProductCategoryRepository.findAll().size();

        // Create the GenericProductCategory
        GenericProductCategoryDTO genericProductCategoryDTO = genericProductCategoryMapper.toDto(genericProductCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenericProductCategoryMockMvc.perform(put("/api/generic-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericProductCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GenericProductCategory in the database
        List<GenericProductCategory> genericProductCategoryList = genericProductCategoryRepository.findAll();
        assertThat(genericProductCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGenericProductCategory() throws Exception {
        // Initialize the database
        genericProductCategoryRepository.saveAndFlush(genericProductCategory);

        int databaseSizeBeforeDelete = genericProductCategoryRepository.findAll().size();

        // Delete the genericProductCategory
        restGenericProductCategoryMockMvc.perform(delete("/api/generic-product-categories/{id}", genericProductCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GenericProductCategory> genericProductCategoryList = genericProductCategoryRepository.findAll();
        assertThat(genericProductCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenericProductCategory.class);
        GenericProductCategory genericProductCategory1 = new GenericProductCategory();
        genericProductCategory1.setId(1L);
        GenericProductCategory genericProductCategory2 = new GenericProductCategory();
        genericProductCategory2.setId(genericProductCategory1.getId());
        assertThat(genericProductCategory1).isEqualTo(genericProductCategory2);
        genericProductCategory2.setId(2L);
        assertThat(genericProductCategory1).isNotEqualTo(genericProductCategory2);
        genericProductCategory1.setId(null);
        assertThat(genericProductCategory1).isNotEqualTo(genericProductCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenericProductCategoryDTO.class);
        GenericProductCategoryDTO genericProductCategoryDTO1 = new GenericProductCategoryDTO();
        genericProductCategoryDTO1.setId(1L);
        GenericProductCategoryDTO genericProductCategoryDTO2 = new GenericProductCategoryDTO();
        assertThat(genericProductCategoryDTO1).isNotEqualTo(genericProductCategoryDTO2);
        genericProductCategoryDTO2.setId(genericProductCategoryDTO1.getId());
        assertThat(genericProductCategoryDTO1).isEqualTo(genericProductCategoryDTO2);
        genericProductCategoryDTO2.setId(2L);
        assertThat(genericProductCategoryDTO1).isNotEqualTo(genericProductCategoryDTO2);
        genericProductCategoryDTO1.setId(null);
        assertThat(genericProductCategoryDTO1).isNotEqualTo(genericProductCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(genericProductCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(genericProductCategoryMapper.fromId(null)).isNull();
    }
}

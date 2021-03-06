package fr.mathieugeissler.constructmanager.web.rest;

import fr.mathieugeissler.constructmanager.ConstructManagerApiApp;

import fr.mathieugeissler.constructmanager.domain.Estimate;
import fr.mathieugeissler.constructmanager.repository.EstimateRepository;
import fr.mathieugeissler.constructmanager.service.EstimateService;
import fr.mathieugeissler.constructmanager.service.dto.EstimateDTO;
import fr.mathieugeissler.constructmanager.service.mapper.EstimateMapper;
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
 * Test class for the EstimateResource REST controller.
 *
 * @see EstimateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConstructManagerApiApp.class)
public class EstimateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private EstimateMapper estimateMapper;

    @Autowired
    private EstimateService estimateService;

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

    private MockMvc restEstimateMockMvc;

    private Estimate estimate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstimateResource estimateResource = new EstimateResource(estimateService);
        this.restEstimateMockMvc = MockMvcBuilders.standaloneSetup(estimateResource)
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
    public static Estimate createEntity(EntityManager em) {
        Estimate estimate = new Estimate()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .total(DEFAULT_TOTAL);
        return estimate;
    }

    @Before
    public void initTest() {
        estimate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstimate() throws Exception {
        int databaseSizeBeforeCreate = estimateRepository.findAll().size();

        // Create the Estimate
        EstimateDTO estimateDTO = estimateMapper.toDto(estimate);
        restEstimateMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimateDTO)))
            .andExpect(status().isCreated());

        // Validate the Estimate in the database
        List<Estimate> estimateList = estimateRepository.findAll();
        assertThat(estimateList).hasSize(databaseSizeBeforeCreate + 1);
        Estimate testEstimate = estimateList.get(estimateList.size() - 1);
        assertThat(testEstimate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEstimate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEstimate.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createEstimateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estimateRepository.findAll().size();

        // Create the Estimate with an existing ID
        estimate.setId(1L);
        EstimateDTO estimateDTO = estimateMapper.toDto(estimate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstimateMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estimate in the database
        List<Estimate> estimateList = estimateRepository.findAll();
        assertThat(estimateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimateRepository.findAll().size();
        // set the field null
        estimate.setName(null);

        // Create the Estimate, which fails.
        EstimateDTO estimateDTO = estimateMapper.toDto(estimate);

        restEstimateMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimateDTO)))
            .andExpect(status().isBadRequest());

        List<Estimate> estimateList = estimateRepository.findAll();
        assertThat(estimateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstimates() throws Exception {
        // Initialize the database
        estimateRepository.saveAndFlush(estimate);

        // Get all the estimateList
        restEstimateMockMvc.perform(get("/api/estimates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estimate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getEstimate() throws Exception {
        // Initialize the database
        estimateRepository.saveAndFlush(estimate);

        // Get the estimate
        restEstimateMockMvc.perform(get("/api/estimates/{id}", estimate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estimate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEstimate() throws Exception {
        // Get the estimate
        restEstimateMockMvc.perform(get("/api/estimates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstimate() throws Exception {
        // Initialize the database
        estimateRepository.saveAndFlush(estimate);

        int databaseSizeBeforeUpdate = estimateRepository.findAll().size();

        // Update the estimate
        Estimate updatedEstimate = estimateRepository.findById(estimate.getId()).get();
        // Disconnect from session so that the updates on updatedEstimate are not directly saved in db
        em.detach(updatedEstimate);
        updatedEstimate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .total(UPDATED_TOTAL);
        EstimateDTO estimateDTO = estimateMapper.toDto(updatedEstimate);

        restEstimateMockMvc.perform(put("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimateDTO)))
            .andExpect(status().isOk());

        // Validate the Estimate in the database
        List<Estimate> estimateList = estimateRepository.findAll();
        assertThat(estimateList).hasSize(databaseSizeBeforeUpdate);
        Estimate testEstimate = estimateList.get(estimateList.size() - 1);
        assertThat(testEstimate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEstimate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEstimate.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingEstimate() throws Exception {
        int databaseSizeBeforeUpdate = estimateRepository.findAll().size();

        // Create the Estimate
        EstimateDTO estimateDTO = estimateMapper.toDto(estimate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstimateMockMvc.perform(put("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estimate in the database
        List<Estimate> estimateList = estimateRepository.findAll();
        assertThat(estimateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstimate() throws Exception {
        // Initialize the database
        estimateRepository.saveAndFlush(estimate);

        int databaseSizeBeforeDelete = estimateRepository.findAll().size();

        // Delete the estimate
        restEstimateMockMvc.perform(delete("/api/estimates/{id}", estimate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Estimate> estimateList = estimateRepository.findAll();
        assertThat(estimateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estimate.class);
        Estimate estimate1 = new Estimate();
        estimate1.setId(1L);
        Estimate estimate2 = new Estimate();
        estimate2.setId(estimate1.getId());
        assertThat(estimate1).isEqualTo(estimate2);
        estimate2.setId(2L);
        assertThat(estimate1).isNotEqualTo(estimate2);
        estimate1.setId(null);
        assertThat(estimate1).isNotEqualTo(estimate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstimateDTO.class);
        EstimateDTO estimateDTO1 = new EstimateDTO();
        estimateDTO1.setId(1L);
        EstimateDTO estimateDTO2 = new EstimateDTO();
        assertThat(estimateDTO1).isNotEqualTo(estimateDTO2);
        estimateDTO2.setId(estimateDTO1.getId());
        assertThat(estimateDTO1).isEqualTo(estimateDTO2);
        estimateDTO2.setId(2L);
        assertThat(estimateDTO1).isNotEqualTo(estimateDTO2);
        estimateDTO1.setId(null);
        assertThat(estimateDTO1).isNotEqualTo(estimateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estimateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estimateMapper.fromId(null)).isNull();
    }
}

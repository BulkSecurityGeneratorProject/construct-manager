package fr.mathieugeissler.constructmanager.web.rest;

import fr.mathieugeissler.constructmanager.ConstructManagerApiApp;

import fr.mathieugeissler.constructmanager.domain.Home;
import fr.mathieugeissler.constructmanager.repository.HomeRepository;
import fr.mathieugeissler.constructmanager.service.HomeService;
import fr.mathieugeissler.constructmanager.service.dto.HomeDTO;
import fr.mathieugeissler.constructmanager.service.mapper.HomeMapper;
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
 * Test class for the HomeResource REST controller.
 *
 * @see HomeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConstructManagerApiApp.class)
public class HomeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private HomeMapper homeMapper;

    @Autowired
    private HomeService homeService;

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

    private MockMvc restHomeMockMvc;

    private Home home;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HomeResource homeResource = new HomeResource(homeService);
        this.restHomeMockMvc = MockMvcBuilders.standaloneSetup(homeResource)
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
    public static Home createEntity(EntityManager em) {
        Home home = new Home()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return home;
    }

    @Before
    public void initTest() {
        home = createEntity(em);
    }

    @Test
    @Transactional
    public void createHome() throws Exception {
        int databaseSizeBeforeCreate = homeRepository.findAll().size();

        // Create the Home
        HomeDTO homeDTO = homeMapper.toDto(home);
        restHomeMockMvc.perform(post("/api/homes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homeDTO)))
            .andExpect(status().isCreated());

        // Validate the Home in the database
        List<Home> homeList = homeRepository.findAll();
        assertThat(homeList).hasSize(databaseSizeBeforeCreate + 1);
        Home testHome = homeList.get(homeList.size() - 1);
        assertThat(testHome.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHome.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createHomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = homeRepository.findAll().size();

        // Create the Home with an existing ID
        home.setId(1L);
        HomeDTO homeDTO = homeMapper.toDto(home);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHomeMockMvc.perform(post("/api/homes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Home in the database
        List<Home> homeList = homeRepository.findAll();
        assertThat(homeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = homeRepository.findAll().size();
        // set the field null
        home.setName(null);

        // Create the Home, which fails.
        HomeDTO homeDTO = homeMapper.toDto(home);

        restHomeMockMvc.perform(post("/api/homes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homeDTO)))
            .andExpect(status().isBadRequest());

        List<Home> homeList = homeRepository.findAll();
        assertThat(homeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHomes() throws Exception {
        // Initialize the database
        homeRepository.saveAndFlush(home);

        // Get all the homeList
        restHomeMockMvc.perform(get("/api/homes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(home.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getHome() throws Exception {
        // Initialize the database
        homeRepository.saveAndFlush(home);

        // Get the home
        restHomeMockMvc.perform(get("/api/homes/{id}", home.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(home.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHome() throws Exception {
        // Get the home
        restHomeMockMvc.perform(get("/api/homes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHome() throws Exception {
        // Initialize the database
        homeRepository.saveAndFlush(home);

        int databaseSizeBeforeUpdate = homeRepository.findAll().size();

        // Update the home
        Home updatedHome = homeRepository.findById(home.getId()).get();
        // Disconnect from session so that the updates on updatedHome are not directly saved in db
        em.detach(updatedHome);
        updatedHome
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        HomeDTO homeDTO = homeMapper.toDto(updatedHome);

        restHomeMockMvc.perform(put("/api/homes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homeDTO)))
            .andExpect(status().isOk());

        // Validate the Home in the database
        List<Home> homeList = homeRepository.findAll();
        assertThat(homeList).hasSize(databaseSizeBeforeUpdate);
        Home testHome = homeList.get(homeList.size() - 1);
        assertThat(testHome.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHome.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingHome() throws Exception {
        int databaseSizeBeforeUpdate = homeRepository.findAll().size();

        // Create the Home
        HomeDTO homeDTO = homeMapper.toDto(home);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHomeMockMvc.perform(put("/api/homes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Home in the database
        List<Home> homeList = homeRepository.findAll();
        assertThat(homeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHome() throws Exception {
        // Initialize the database
        homeRepository.saveAndFlush(home);

        int databaseSizeBeforeDelete = homeRepository.findAll().size();

        // Delete the home
        restHomeMockMvc.perform(delete("/api/homes/{id}", home.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Home> homeList = homeRepository.findAll();
        assertThat(homeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Home.class);
        Home home1 = new Home();
        home1.setId(1L);
        Home home2 = new Home();
        home2.setId(home1.getId());
        assertThat(home1).isEqualTo(home2);
        home2.setId(2L);
        assertThat(home1).isNotEqualTo(home2);
        home1.setId(null);
        assertThat(home1).isNotEqualTo(home2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HomeDTO.class);
        HomeDTO homeDTO1 = new HomeDTO();
        homeDTO1.setId(1L);
        HomeDTO homeDTO2 = new HomeDTO();
        assertThat(homeDTO1).isNotEqualTo(homeDTO2);
        homeDTO2.setId(homeDTO1.getId());
        assertThat(homeDTO1).isEqualTo(homeDTO2);
        homeDTO2.setId(2L);
        assertThat(homeDTO1).isNotEqualTo(homeDTO2);
        homeDTO1.setId(null);
        assertThat(homeDTO1).isNotEqualTo(homeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(homeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(homeMapper.fromId(null)).isNull();
    }
}

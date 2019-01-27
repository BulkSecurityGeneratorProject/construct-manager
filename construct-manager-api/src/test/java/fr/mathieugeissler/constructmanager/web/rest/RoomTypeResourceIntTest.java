package fr.mathieugeissler.constructmanager.web.rest;

import fr.mathieugeissler.constructmanager.ConstructManagerApiApp;

import fr.mathieugeissler.constructmanager.domain.RoomType;
import fr.mathieugeissler.constructmanager.repository.RoomTypeRepository;
import fr.mathieugeissler.constructmanager.service.RoomTypeService;
import fr.mathieugeissler.constructmanager.service.dto.RoomTypeDTO;
import fr.mathieugeissler.constructmanager.service.mapper.RoomTypeMapper;
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
 * Test class for the RoomTypeResource REST controller.
 *
 * @see RoomTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConstructManagerApiApp.class)
public class RoomTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomTypeMapper roomTypeMapper;

    @Autowired
    private RoomTypeService roomTypeService;

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

    private MockMvc restRoomTypeMockMvc;

    private RoomType roomType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoomTypeResource roomTypeResource = new RoomTypeResource(roomTypeService);
        this.restRoomTypeMockMvc = MockMvcBuilders.standaloneSetup(roomTypeResource)
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
    public static RoomType createEntity(EntityManager em) {
        RoomType roomType = new RoomType()
            .name(DEFAULT_NAME);
        return roomType;
    }

    @Before
    public void initTest() {
        roomType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoomType() throws Exception {
        int databaseSizeBeforeCreate = roomTypeRepository.findAll().size();

        // Create the RoomType
        RoomTypeDTO roomTypeDTO = roomTypeMapper.toDto(roomType);
        restRoomTypeMockMvc.perform(post("/api/room-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RoomType testRoomType = roomTypeList.get(roomTypeList.size() - 1);
        assertThat(testRoomType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRoomTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomTypeRepository.findAll().size();

        // Create the RoomType with an existing ID
        roomType.setId(1L);
        RoomTypeDTO roomTypeDTO = roomTypeMapper.toDto(roomType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomTypeMockMvc.perform(post("/api/room-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomTypeRepository.findAll().size();
        // set the field null
        roomType.setName(null);

        // Create the RoomType, which fails.
        RoomTypeDTO roomTypeDTO = roomTypeMapper.toDto(roomType);

        restRoomTypeMockMvc.perform(post("/api/room-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomTypeDTO)))
            .andExpect(status().isBadRequest());

        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoomTypes() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList
        restRoomTypeMockMvc.perform(get("/api/room-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getRoomType() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get the roomType
        restRoomTypeMockMvc.perform(get("/api/room-types/{id}", roomType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roomType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoomType() throws Exception {
        // Get the roomType
        restRoomTypeMockMvc.perform(get("/api/room-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoomType() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();

        // Update the roomType
        RoomType updatedRoomType = roomTypeRepository.findById(roomType.getId()).get();
        // Disconnect from session so that the updates on updatedRoomType are not directly saved in db
        em.detach(updatedRoomType);
        updatedRoomType
            .name(UPDATED_NAME);
        RoomTypeDTO roomTypeDTO = roomTypeMapper.toDto(updatedRoomType);

        restRoomTypeMockMvc.perform(put("/api/room-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomTypeDTO)))
            .andExpect(status().isOk());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
        RoomType testRoomType = roomTypeList.get(roomTypeList.size() - 1);
        assertThat(testRoomType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRoomType() throws Exception {
        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();

        // Create the RoomType
        RoomTypeDTO roomTypeDTO = roomTypeMapper.toDto(roomType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomTypeMockMvc.perform(put("/api/room-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoomType() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        int databaseSizeBeforeDelete = roomTypeRepository.findAll().size();

        // Delete the roomType
        restRoomTypeMockMvc.perform(delete("/api/room-types/{id}", roomType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomType.class);
        RoomType roomType1 = new RoomType();
        roomType1.setId(1L);
        RoomType roomType2 = new RoomType();
        roomType2.setId(roomType1.getId());
        assertThat(roomType1).isEqualTo(roomType2);
        roomType2.setId(2L);
        assertThat(roomType1).isNotEqualTo(roomType2);
        roomType1.setId(null);
        assertThat(roomType1).isNotEqualTo(roomType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomTypeDTO.class);
        RoomTypeDTO roomTypeDTO1 = new RoomTypeDTO();
        roomTypeDTO1.setId(1L);
        RoomTypeDTO roomTypeDTO2 = new RoomTypeDTO();
        assertThat(roomTypeDTO1).isNotEqualTo(roomTypeDTO2);
        roomTypeDTO2.setId(roomTypeDTO1.getId());
        assertThat(roomTypeDTO1).isEqualTo(roomTypeDTO2);
        roomTypeDTO2.setId(2L);
        assertThat(roomTypeDTO1).isNotEqualTo(roomTypeDTO2);
        roomTypeDTO1.setId(null);
        assertThat(roomTypeDTO1).isNotEqualTo(roomTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roomTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roomTypeMapper.fromId(null)).isNull();
    }
}

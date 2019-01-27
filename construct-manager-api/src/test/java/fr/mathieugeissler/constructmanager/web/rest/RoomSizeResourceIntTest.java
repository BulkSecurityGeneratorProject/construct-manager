package fr.mathieugeissler.constructmanager.web.rest;

import fr.mathieugeissler.constructmanager.ConstructManagerApiApp;

import fr.mathieugeissler.constructmanager.domain.RoomSize;
import fr.mathieugeissler.constructmanager.repository.RoomSizeRepository;
import fr.mathieugeissler.constructmanager.service.RoomSizeService;
import fr.mathieugeissler.constructmanager.service.dto.RoomSizeDTO;
import fr.mathieugeissler.constructmanager.service.mapper.RoomSizeMapper;
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
 * Test class for the RoomSizeResource REST controller.
 *
 * @see RoomSizeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConstructManagerApiApp.class)
public class RoomSizeResourceIntTest {

    private static final Double DEFAULT_FLOOR_SIZE = 1D;
    private static final Double UPDATED_FLOOR_SIZE = 2D;

    private static final Double DEFAULT_WALL_SIZE = 1D;
    private static final Double UPDATED_WALL_SIZE = 2D;

    @Autowired
    private RoomSizeRepository roomSizeRepository;

    @Autowired
    private RoomSizeMapper roomSizeMapper;

    @Autowired
    private RoomSizeService roomSizeService;

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

    private MockMvc restRoomSizeMockMvc;

    private RoomSize roomSize;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoomSizeResource roomSizeResource = new RoomSizeResource(roomSizeService);
        this.restRoomSizeMockMvc = MockMvcBuilders.standaloneSetup(roomSizeResource)
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
    public static RoomSize createEntity(EntityManager em) {
        RoomSize roomSize = new RoomSize()
            .floorSize(DEFAULT_FLOOR_SIZE)
            .wallSize(DEFAULT_WALL_SIZE);
        return roomSize;
    }

    @Before
    public void initTest() {
        roomSize = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoomSize() throws Exception {
        int databaseSizeBeforeCreate = roomSizeRepository.findAll().size();

        // Create the RoomSize
        RoomSizeDTO roomSizeDTO = roomSizeMapper.toDto(roomSize);
        restRoomSizeMockMvc.perform(post("/api/room-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the RoomSize in the database
        List<RoomSize> roomSizeList = roomSizeRepository.findAll();
        assertThat(roomSizeList).hasSize(databaseSizeBeforeCreate + 1);
        RoomSize testRoomSize = roomSizeList.get(roomSizeList.size() - 1);
        assertThat(testRoomSize.getFloorSize()).isEqualTo(DEFAULT_FLOOR_SIZE);
        assertThat(testRoomSize.getWallSize()).isEqualTo(DEFAULT_WALL_SIZE);
    }

    @Test
    @Transactional
    public void createRoomSizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomSizeRepository.findAll().size();

        // Create the RoomSize with an existing ID
        roomSize.setId(1L);
        RoomSizeDTO roomSizeDTO = roomSizeMapper.toDto(roomSize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomSizeMockMvc.perform(post("/api/room-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomSizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoomSize in the database
        List<RoomSize> roomSizeList = roomSizeRepository.findAll();
        assertThat(roomSizeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRoomSizes() throws Exception {
        // Initialize the database
        roomSizeRepository.saveAndFlush(roomSize);

        // Get all the roomSizeList
        restRoomSizeMockMvc.perform(get("/api/room-sizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomSize.getId().intValue())))
            .andExpect(jsonPath("$.[*].floorSize").value(hasItem(DEFAULT_FLOOR_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].wallSize").value(hasItem(DEFAULT_WALL_SIZE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getRoomSize() throws Exception {
        // Initialize the database
        roomSizeRepository.saveAndFlush(roomSize);

        // Get the roomSize
        restRoomSizeMockMvc.perform(get("/api/room-sizes/{id}", roomSize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roomSize.getId().intValue()))
            .andExpect(jsonPath("$.floorSize").value(DEFAULT_FLOOR_SIZE.doubleValue()))
            .andExpect(jsonPath("$.wallSize").value(DEFAULT_WALL_SIZE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRoomSize() throws Exception {
        // Get the roomSize
        restRoomSizeMockMvc.perform(get("/api/room-sizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoomSize() throws Exception {
        // Initialize the database
        roomSizeRepository.saveAndFlush(roomSize);

        int databaseSizeBeforeUpdate = roomSizeRepository.findAll().size();

        // Update the roomSize
        RoomSize updatedRoomSize = roomSizeRepository.findById(roomSize.getId()).get();
        // Disconnect from session so that the updates on updatedRoomSize are not directly saved in db
        em.detach(updatedRoomSize);
        updatedRoomSize
            .floorSize(UPDATED_FLOOR_SIZE)
            .wallSize(UPDATED_WALL_SIZE);
        RoomSizeDTO roomSizeDTO = roomSizeMapper.toDto(updatedRoomSize);

        restRoomSizeMockMvc.perform(put("/api/room-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomSizeDTO)))
            .andExpect(status().isOk());

        // Validate the RoomSize in the database
        List<RoomSize> roomSizeList = roomSizeRepository.findAll();
        assertThat(roomSizeList).hasSize(databaseSizeBeforeUpdate);
        RoomSize testRoomSize = roomSizeList.get(roomSizeList.size() - 1);
        assertThat(testRoomSize.getFloorSize()).isEqualTo(UPDATED_FLOOR_SIZE);
        assertThat(testRoomSize.getWallSize()).isEqualTo(UPDATED_WALL_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingRoomSize() throws Exception {
        int databaseSizeBeforeUpdate = roomSizeRepository.findAll().size();

        // Create the RoomSize
        RoomSizeDTO roomSizeDTO = roomSizeMapper.toDto(roomSize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomSizeMockMvc.perform(put("/api/room-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomSizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoomSize in the database
        List<RoomSize> roomSizeList = roomSizeRepository.findAll();
        assertThat(roomSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoomSize() throws Exception {
        // Initialize the database
        roomSizeRepository.saveAndFlush(roomSize);

        int databaseSizeBeforeDelete = roomSizeRepository.findAll().size();

        // Delete the roomSize
        restRoomSizeMockMvc.perform(delete("/api/room-sizes/{id}", roomSize.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoomSize> roomSizeList = roomSizeRepository.findAll();
        assertThat(roomSizeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomSize.class);
        RoomSize roomSize1 = new RoomSize();
        roomSize1.setId(1L);
        RoomSize roomSize2 = new RoomSize();
        roomSize2.setId(roomSize1.getId());
        assertThat(roomSize1).isEqualTo(roomSize2);
        roomSize2.setId(2L);
        assertThat(roomSize1).isNotEqualTo(roomSize2);
        roomSize1.setId(null);
        assertThat(roomSize1).isNotEqualTo(roomSize2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomSizeDTO.class);
        RoomSizeDTO roomSizeDTO1 = new RoomSizeDTO();
        roomSizeDTO1.setId(1L);
        RoomSizeDTO roomSizeDTO2 = new RoomSizeDTO();
        assertThat(roomSizeDTO1).isNotEqualTo(roomSizeDTO2);
        roomSizeDTO2.setId(roomSizeDTO1.getId());
        assertThat(roomSizeDTO1).isEqualTo(roomSizeDTO2);
        roomSizeDTO2.setId(2L);
        assertThat(roomSizeDTO1).isNotEqualTo(roomSizeDTO2);
        roomSizeDTO1.setId(null);
        assertThat(roomSizeDTO1).isNotEqualTo(roomSizeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roomSizeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roomSizeMapper.fromId(null)).isNull();
    }
}

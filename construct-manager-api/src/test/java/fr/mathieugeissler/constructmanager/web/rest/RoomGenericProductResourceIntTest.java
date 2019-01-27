package fr.mathieugeissler.constructmanager.web.rest;

import fr.mathieugeissler.constructmanager.ConstructManagerApiApp;

import fr.mathieugeissler.constructmanager.domain.RoomGenericProduct;
import fr.mathieugeissler.constructmanager.repository.RoomGenericProductRepository;
import fr.mathieugeissler.constructmanager.service.RoomGenericProductService;
import fr.mathieugeissler.constructmanager.service.dto.RoomGenericProductDTO;
import fr.mathieugeissler.constructmanager.service.mapper.RoomGenericProductMapper;
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

import fr.mathieugeissler.constructmanager.domain.enumeration.Unit;
/**
 * Test class for the RoomGenericProductResource REST controller.
 *
 * @see RoomGenericProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConstructManagerApiApp.class)
public class RoomGenericProductResourceIntTest {

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Unit DEFAULT_QUANTITY_UNIT = Unit.ML;
    private static final Unit UPDATED_QUANTITY_UNIT = Unit.KG;

    @Autowired
    private RoomGenericProductRepository roomGenericProductRepository;

    @Autowired
    private RoomGenericProductMapper roomGenericProductMapper;

    @Autowired
    private RoomGenericProductService roomGenericProductService;

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

    private MockMvc restRoomGenericProductMockMvc;

    private RoomGenericProduct roomGenericProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoomGenericProductResource roomGenericProductResource = new RoomGenericProductResource(roomGenericProductService);
        this.restRoomGenericProductMockMvc = MockMvcBuilders.standaloneSetup(roomGenericProductResource)
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
    public static RoomGenericProduct createEntity(EntityManager em) {
        RoomGenericProduct roomGenericProduct = new RoomGenericProduct()
            .quantity(DEFAULT_QUANTITY)
            .quantityUnit(DEFAULT_QUANTITY_UNIT);
        return roomGenericProduct;
    }

    @Before
    public void initTest() {
        roomGenericProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoomGenericProduct() throws Exception {
        int databaseSizeBeforeCreate = roomGenericProductRepository.findAll().size();

        // Create the RoomGenericProduct
        RoomGenericProductDTO roomGenericProductDTO = roomGenericProductMapper.toDto(roomGenericProduct);
        restRoomGenericProductMockMvc.perform(post("/api/room-generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomGenericProductDTO)))
            .andExpect(status().isCreated());

        // Validate the RoomGenericProduct in the database
        List<RoomGenericProduct> roomGenericProductList = roomGenericProductRepository.findAll();
        assertThat(roomGenericProductList).hasSize(databaseSizeBeforeCreate + 1);
        RoomGenericProduct testRoomGenericProduct = roomGenericProductList.get(roomGenericProductList.size() - 1);
        assertThat(testRoomGenericProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testRoomGenericProduct.getQuantityUnit()).isEqualTo(DEFAULT_QUANTITY_UNIT);
    }

    @Test
    @Transactional
    public void createRoomGenericProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomGenericProductRepository.findAll().size();

        // Create the RoomGenericProduct with an existing ID
        roomGenericProduct.setId(1L);
        RoomGenericProductDTO roomGenericProductDTO = roomGenericProductMapper.toDto(roomGenericProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomGenericProductMockMvc.perform(post("/api/room-generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomGenericProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoomGenericProduct in the database
        List<RoomGenericProduct> roomGenericProductList = roomGenericProductRepository.findAll();
        assertThat(roomGenericProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomGenericProductRepository.findAll().size();
        // set the field null
        roomGenericProduct.setQuantity(null);

        // Create the RoomGenericProduct, which fails.
        RoomGenericProductDTO roomGenericProductDTO = roomGenericProductMapper.toDto(roomGenericProduct);

        restRoomGenericProductMockMvc.perform(post("/api/room-generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomGenericProductDTO)))
            .andExpect(status().isBadRequest());

        List<RoomGenericProduct> roomGenericProductList = roomGenericProductRepository.findAll();
        assertThat(roomGenericProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoomGenericProducts() throws Exception {
        // Initialize the database
        roomGenericProductRepository.saveAndFlush(roomGenericProduct);

        // Get all the roomGenericProductList
        restRoomGenericProductMockMvc.perform(get("/api/room-generic-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomGenericProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].quantityUnit").value(hasItem(DEFAULT_QUANTITY_UNIT.toString())));
    }
    
    @Test
    @Transactional
    public void getRoomGenericProduct() throws Exception {
        // Initialize the database
        roomGenericProductRepository.saveAndFlush(roomGenericProduct);

        // Get the roomGenericProduct
        restRoomGenericProductMockMvc.perform(get("/api/room-generic-products/{id}", roomGenericProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roomGenericProduct.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.quantityUnit").value(DEFAULT_QUANTITY_UNIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoomGenericProduct() throws Exception {
        // Get the roomGenericProduct
        restRoomGenericProductMockMvc.perform(get("/api/room-generic-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoomGenericProduct() throws Exception {
        // Initialize the database
        roomGenericProductRepository.saveAndFlush(roomGenericProduct);

        int databaseSizeBeforeUpdate = roomGenericProductRepository.findAll().size();

        // Update the roomGenericProduct
        RoomGenericProduct updatedRoomGenericProduct = roomGenericProductRepository.findById(roomGenericProduct.getId()).get();
        // Disconnect from session so that the updates on updatedRoomGenericProduct are not directly saved in db
        em.detach(updatedRoomGenericProduct);
        updatedRoomGenericProduct
            .quantity(UPDATED_QUANTITY)
            .quantityUnit(UPDATED_QUANTITY_UNIT);
        RoomGenericProductDTO roomGenericProductDTO = roomGenericProductMapper.toDto(updatedRoomGenericProduct);

        restRoomGenericProductMockMvc.perform(put("/api/room-generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomGenericProductDTO)))
            .andExpect(status().isOk());

        // Validate the RoomGenericProduct in the database
        List<RoomGenericProduct> roomGenericProductList = roomGenericProductRepository.findAll();
        assertThat(roomGenericProductList).hasSize(databaseSizeBeforeUpdate);
        RoomGenericProduct testRoomGenericProduct = roomGenericProductList.get(roomGenericProductList.size() - 1);
        assertThat(testRoomGenericProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testRoomGenericProduct.getQuantityUnit()).isEqualTo(UPDATED_QUANTITY_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingRoomGenericProduct() throws Exception {
        int databaseSizeBeforeUpdate = roomGenericProductRepository.findAll().size();

        // Create the RoomGenericProduct
        RoomGenericProductDTO roomGenericProductDTO = roomGenericProductMapper.toDto(roomGenericProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomGenericProductMockMvc.perform(put("/api/room-generic-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomGenericProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoomGenericProduct in the database
        List<RoomGenericProduct> roomGenericProductList = roomGenericProductRepository.findAll();
        assertThat(roomGenericProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoomGenericProduct() throws Exception {
        // Initialize the database
        roomGenericProductRepository.saveAndFlush(roomGenericProduct);

        int databaseSizeBeforeDelete = roomGenericProductRepository.findAll().size();

        // Delete the roomGenericProduct
        restRoomGenericProductMockMvc.perform(delete("/api/room-generic-products/{id}", roomGenericProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoomGenericProduct> roomGenericProductList = roomGenericProductRepository.findAll();
        assertThat(roomGenericProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomGenericProduct.class);
        RoomGenericProduct roomGenericProduct1 = new RoomGenericProduct();
        roomGenericProduct1.setId(1L);
        RoomGenericProduct roomGenericProduct2 = new RoomGenericProduct();
        roomGenericProduct2.setId(roomGenericProduct1.getId());
        assertThat(roomGenericProduct1).isEqualTo(roomGenericProduct2);
        roomGenericProduct2.setId(2L);
        assertThat(roomGenericProduct1).isNotEqualTo(roomGenericProduct2);
        roomGenericProduct1.setId(null);
        assertThat(roomGenericProduct1).isNotEqualTo(roomGenericProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomGenericProductDTO.class);
        RoomGenericProductDTO roomGenericProductDTO1 = new RoomGenericProductDTO();
        roomGenericProductDTO1.setId(1L);
        RoomGenericProductDTO roomGenericProductDTO2 = new RoomGenericProductDTO();
        assertThat(roomGenericProductDTO1).isNotEqualTo(roomGenericProductDTO2);
        roomGenericProductDTO2.setId(roomGenericProductDTO1.getId());
        assertThat(roomGenericProductDTO1).isEqualTo(roomGenericProductDTO2);
        roomGenericProductDTO2.setId(2L);
        assertThat(roomGenericProductDTO1).isNotEqualTo(roomGenericProductDTO2);
        roomGenericProductDTO1.setId(null);
        assertThat(roomGenericProductDTO1).isNotEqualTo(roomGenericProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roomGenericProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roomGenericProductMapper.fromId(null)).isNull();
    }
}

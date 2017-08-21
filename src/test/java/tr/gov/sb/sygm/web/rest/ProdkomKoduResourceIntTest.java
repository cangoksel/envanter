package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.ProdkomKodu;
import tr.gov.sb.sygm.repository.ProdkomKoduRepository;
import tr.gov.sb.sygm.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProdkomKoduResource REST controller.
 *
 * @see ProdkomKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class ProdkomKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private ProdkomKoduRepository prodkomKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProdkomKoduMockMvc;

    private ProdkomKodu prodkomKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProdkomKoduResource prodkomKoduResource = new ProdkomKoduResource(prodkomKoduRepository);
        this.restProdkomKoduMockMvc = MockMvcBuilders.standaloneSetup(prodkomKoduResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdkomKodu createEntity(EntityManager em) {
        ProdkomKodu prodkomKodu = new ProdkomKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .kod(DEFAULT_KOD);
        return prodkomKodu;
    }

    @Before
    public void initTest() {
        prodkomKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createProdkomKodu() throws Exception {
        int databaseSizeBeforeCreate = prodkomKoduRepository.findAll().size();

        // Create the ProdkomKodu
        restProdkomKoduMockMvc.perform(post("/api/prodkom-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prodkomKodu)))
            .andExpect(status().isCreated());

        // Validate the ProdkomKodu in the database
        List<ProdkomKodu> prodkomKoduList = prodkomKoduRepository.findAll();
        assertThat(prodkomKoduList).hasSize(databaseSizeBeforeCreate + 1);
        ProdkomKodu testProdkomKodu = prodkomKoduList.get(prodkomKoduList.size() - 1);
        assertThat(testProdkomKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testProdkomKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProdkomKodu.getKod()).isEqualTo(DEFAULT_KOD);
    }

    @Test
    @Transactional
    public void createProdkomKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prodkomKoduRepository.findAll().size();

        // Create the ProdkomKodu with an existing ID
        prodkomKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdkomKoduMockMvc.perform(post("/api/prodkom-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prodkomKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProdkomKodu> prodkomKoduList = prodkomKoduRepository.findAll();
        assertThat(prodkomKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = prodkomKoduRepository.findAll().size();
        // set the field null
        prodkomKodu.setDeleted(null);

        // Create the ProdkomKodu, which fails.

        restProdkomKoduMockMvc.perform(post("/api/prodkom-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prodkomKodu)))
            .andExpect(status().isBadRequest());

        List<ProdkomKodu> prodkomKoduList = prodkomKoduRepository.findAll();
        assertThat(prodkomKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = prodkomKoduRepository.findAll().size();
        // set the field null
        prodkomKodu.setVersion(null);

        // Create the ProdkomKodu, which fails.

        restProdkomKoduMockMvc.perform(post("/api/prodkom-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prodkomKodu)))
            .andExpect(status().isBadRequest());

        List<ProdkomKodu> prodkomKoduList = prodkomKoduRepository.findAll();
        assertThat(prodkomKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKodIsRequired() throws Exception {
        int databaseSizeBeforeTest = prodkomKoduRepository.findAll().size();
        // set the field null
        prodkomKodu.setKod(null);

        // Create the ProdkomKodu, which fails.

        restProdkomKoduMockMvc.perform(post("/api/prodkom-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prodkomKodu)))
            .andExpect(status().isBadRequest());

        List<ProdkomKodu> prodkomKoduList = prodkomKoduRepository.findAll();
        assertThat(prodkomKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdkomKodus() throws Exception {
        // Initialize the database
        prodkomKoduRepository.saveAndFlush(prodkomKodu);

        // Get all the prodkomKoduList
        restProdkomKoduMockMvc.perform(get("/api/prodkom-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodkomKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getProdkomKodu() throws Exception {
        // Initialize the database
        prodkomKoduRepository.saveAndFlush(prodkomKodu);

        // Get the prodkomKodu
        restProdkomKoduMockMvc.perform(get("/api/prodkom-kodus/{id}", prodkomKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prodkomKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProdkomKodu() throws Exception {
        // Get the prodkomKodu
        restProdkomKoduMockMvc.perform(get("/api/prodkom-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdkomKodu() throws Exception {
        // Initialize the database
        prodkomKoduRepository.saveAndFlush(prodkomKodu);
        int databaseSizeBeforeUpdate = prodkomKoduRepository.findAll().size();

        // Update the prodkomKodu
        ProdkomKodu updatedProdkomKodu = prodkomKoduRepository.findOne(prodkomKodu.getId());
        updatedProdkomKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .kod(UPDATED_KOD);

        restProdkomKoduMockMvc.perform(put("/api/prodkom-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProdkomKodu)))
            .andExpect(status().isOk());

        // Validate the ProdkomKodu in the database
        List<ProdkomKodu> prodkomKoduList = prodkomKoduRepository.findAll();
        assertThat(prodkomKoduList).hasSize(databaseSizeBeforeUpdate);
        ProdkomKodu testProdkomKodu = prodkomKoduList.get(prodkomKoduList.size() - 1);
        assertThat(testProdkomKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testProdkomKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProdkomKodu.getKod()).isEqualTo(UPDATED_KOD);
    }

    @Test
    @Transactional
    public void updateNonExistingProdkomKodu() throws Exception {
        int databaseSizeBeforeUpdate = prodkomKoduRepository.findAll().size();

        // Create the ProdkomKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProdkomKoduMockMvc.perform(put("/api/prodkom-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prodkomKodu)))
            .andExpect(status().isCreated());

        // Validate the ProdkomKodu in the database
        List<ProdkomKodu> prodkomKoduList = prodkomKoduRepository.findAll();
        assertThat(prodkomKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProdkomKodu() throws Exception {
        // Initialize the database
        prodkomKoduRepository.saveAndFlush(prodkomKodu);
        int databaseSizeBeforeDelete = prodkomKoduRepository.findAll().size();

        // Get the prodkomKodu
        restProdkomKoduMockMvc.perform(delete("/api/prodkom-kodus/{id}", prodkomKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProdkomKodu> prodkomKoduList = prodkomKoduRepository.findAll();
        assertThat(prodkomKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdkomKodu.class);
        ProdkomKodu prodkomKodu1 = new ProdkomKodu();
        prodkomKodu1.setId(1L);
        ProdkomKodu prodkomKodu2 = new ProdkomKodu();
        prodkomKodu2.setId(prodkomKodu1.getId());
        assertThat(prodkomKodu1).isEqualTo(prodkomKodu2);
        prodkomKodu2.setId(2L);
        assertThat(prodkomKodu1).isNotEqualTo(prodkomKodu2);
        prodkomKodu1.setId(null);
        assertThat(prodkomKodu1).isNotEqualTo(prodkomKodu2);
    }
}

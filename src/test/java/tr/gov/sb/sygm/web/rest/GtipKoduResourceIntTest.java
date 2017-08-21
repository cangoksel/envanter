package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.GtipKodu;
import tr.gov.sb.sygm.repository.GtipKoduRepository;
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
 * Test class for the GtipKoduResource REST controller.
 *
 * @see GtipKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class GtipKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private GtipKoduRepository gtipKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGtipKoduMockMvc;

    private GtipKodu gtipKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GtipKoduResource gtipKoduResource = new GtipKoduResource(gtipKoduRepository);
        this.restGtipKoduMockMvc = MockMvcBuilders.standaloneSetup(gtipKoduResource)
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
    public static GtipKodu createEntity(EntityManager em) {
        GtipKodu gtipKodu = new GtipKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .kod(DEFAULT_KOD);
        return gtipKodu;
    }

    @Before
    public void initTest() {
        gtipKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createGtipKodu() throws Exception {
        int databaseSizeBeforeCreate = gtipKoduRepository.findAll().size();

        // Create the GtipKodu
        restGtipKoduMockMvc.perform(post("/api/gtip-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gtipKodu)))
            .andExpect(status().isCreated());

        // Validate the GtipKodu in the database
        List<GtipKodu> gtipKoduList = gtipKoduRepository.findAll();
        assertThat(gtipKoduList).hasSize(databaseSizeBeforeCreate + 1);
        GtipKodu testGtipKodu = gtipKoduList.get(gtipKoduList.size() - 1);
        assertThat(testGtipKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testGtipKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testGtipKodu.getKod()).isEqualTo(DEFAULT_KOD);
    }

    @Test
    @Transactional
    public void createGtipKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gtipKoduRepository.findAll().size();

        // Create the GtipKodu with an existing ID
        gtipKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGtipKoduMockMvc.perform(post("/api/gtip-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gtipKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GtipKodu> gtipKoduList = gtipKoduRepository.findAll();
        assertThat(gtipKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = gtipKoduRepository.findAll().size();
        // set the field null
        gtipKodu.setDeleted(null);

        // Create the GtipKodu, which fails.

        restGtipKoduMockMvc.perform(post("/api/gtip-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gtipKodu)))
            .andExpect(status().isBadRequest());

        List<GtipKodu> gtipKoduList = gtipKoduRepository.findAll();
        assertThat(gtipKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = gtipKoduRepository.findAll().size();
        // set the field null
        gtipKodu.setVersion(null);

        // Create the GtipKodu, which fails.

        restGtipKoduMockMvc.perform(post("/api/gtip-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gtipKodu)))
            .andExpect(status().isBadRequest());

        List<GtipKodu> gtipKoduList = gtipKoduRepository.findAll();
        assertThat(gtipKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKodIsRequired() throws Exception {
        int databaseSizeBeforeTest = gtipKoduRepository.findAll().size();
        // set the field null
        gtipKodu.setKod(null);

        // Create the GtipKodu, which fails.

        restGtipKoduMockMvc.perform(post("/api/gtip-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gtipKodu)))
            .andExpect(status().isBadRequest());

        List<GtipKodu> gtipKoduList = gtipKoduRepository.findAll();
        assertThat(gtipKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGtipKodus() throws Exception {
        // Initialize the database
        gtipKoduRepository.saveAndFlush(gtipKodu);

        // Get all the gtipKoduList
        restGtipKoduMockMvc.perform(get("/api/gtip-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gtipKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getGtipKodu() throws Exception {
        // Initialize the database
        gtipKoduRepository.saveAndFlush(gtipKodu);

        // Get the gtipKodu
        restGtipKoduMockMvc.perform(get("/api/gtip-kodus/{id}", gtipKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gtipKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGtipKodu() throws Exception {
        // Get the gtipKodu
        restGtipKoduMockMvc.perform(get("/api/gtip-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGtipKodu() throws Exception {
        // Initialize the database
        gtipKoduRepository.saveAndFlush(gtipKodu);
        int databaseSizeBeforeUpdate = gtipKoduRepository.findAll().size();

        // Update the gtipKodu
        GtipKodu updatedGtipKodu = gtipKoduRepository.findOne(gtipKodu.getId());
        updatedGtipKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .kod(UPDATED_KOD);

        restGtipKoduMockMvc.perform(put("/api/gtip-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGtipKodu)))
            .andExpect(status().isOk());

        // Validate the GtipKodu in the database
        List<GtipKodu> gtipKoduList = gtipKoduRepository.findAll();
        assertThat(gtipKoduList).hasSize(databaseSizeBeforeUpdate);
        GtipKodu testGtipKodu = gtipKoduList.get(gtipKoduList.size() - 1);
        assertThat(testGtipKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testGtipKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGtipKodu.getKod()).isEqualTo(UPDATED_KOD);
    }

    @Test
    @Transactional
    public void updateNonExistingGtipKodu() throws Exception {
        int databaseSizeBeforeUpdate = gtipKoduRepository.findAll().size();

        // Create the GtipKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGtipKoduMockMvc.perform(put("/api/gtip-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gtipKodu)))
            .andExpect(status().isCreated());

        // Validate the GtipKodu in the database
        List<GtipKodu> gtipKoduList = gtipKoduRepository.findAll();
        assertThat(gtipKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGtipKodu() throws Exception {
        // Initialize the database
        gtipKoduRepository.saveAndFlush(gtipKodu);
        int databaseSizeBeforeDelete = gtipKoduRepository.findAll().size();

        // Get the gtipKodu
        restGtipKoduMockMvc.perform(delete("/api/gtip-kodus/{id}", gtipKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GtipKodu> gtipKoduList = gtipKoduRepository.findAll();
        assertThat(gtipKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GtipKodu.class);
        GtipKodu gtipKodu1 = new GtipKodu();
        gtipKodu1.setId(1L);
        GtipKodu gtipKodu2 = new GtipKodu();
        gtipKodu2.setId(gtipKodu1.getId());
        assertThat(gtipKodu1).isEqualTo(gtipKodu2);
        gtipKodu2.setId(2L);
        assertThat(gtipKodu1).isNotEqualTo(gtipKodu2);
        gtipKodu1.setId(null);
        assertThat(gtipKodu1).isNotEqualTo(gtipKodu2);
    }
}

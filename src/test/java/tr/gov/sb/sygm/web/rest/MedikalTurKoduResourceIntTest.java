package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.MedikalTurKodu;
import tr.gov.sb.sygm.repository.MedikalTurKoduRepository;
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
 * Test class for the MedikalTurKoduResource REST controller.
 *
 * @see MedikalTurKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class MedikalTurKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private MedikalTurKoduRepository medikalTurKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedikalTurKoduMockMvc;

    private MedikalTurKodu medikalTurKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MedikalTurKoduResource medikalTurKoduResource = new MedikalTurKoduResource(medikalTurKoduRepository);
        this.restMedikalTurKoduMockMvc = MockMvcBuilders.standaloneSetup(medikalTurKoduResource)
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
    public static MedikalTurKodu createEntity(EntityManager em) {
        MedikalTurKodu medikalTurKodu = new MedikalTurKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .kod(DEFAULT_KOD);
        return medikalTurKodu;
    }

    @Before
    public void initTest() {
        medikalTurKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedikalTurKodu() throws Exception {
        int databaseSizeBeforeCreate = medikalTurKoduRepository.findAll().size();

        // Create the MedikalTurKodu
        restMedikalTurKoduMockMvc.perform(post("/api/medikal-tur-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medikalTurKodu)))
            .andExpect(status().isCreated());

        // Validate the MedikalTurKodu in the database
        List<MedikalTurKodu> medikalTurKoduList = medikalTurKoduRepository.findAll();
        assertThat(medikalTurKoduList).hasSize(databaseSizeBeforeCreate + 1);
        MedikalTurKodu testMedikalTurKodu = medikalTurKoduList.get(medikalTurKoduList.size() - 1);
        assertThat(testMedikalTurKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testMedikalTurKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testMedikalTurKodu.getKod()).isEqualTo(DEFAULT_KOD);
    }

    @Test
    @Transactional
    public void createMedikalTurKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medikalTurKoduRepository.findAll().size();

        // Create the MedikalTurKodu with an existing ID
        medikalTurKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedikalTurKoduMockMvc.perform(post("/api/medikal-tur-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medikalTurKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MedikalTurKodu> medikalTurKoduList = medikalTurKoduRepository.findAll();
        assertThat(medikalTurKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = medikalTurKoduRepository.findAll().size();
        // set the field null
        medikalTurKodu.setDeleted(null);

        // Create the MedikalTurKodu, which fails.

        restMedikalTurKoduMockMvc.perform(post("/api/medikal-tur-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medikalTurKodu)))
            .andExpect(status().isBadRequest());

        List<MedikalTurKodu> medikalTurKoduList = medikalTurKoduRepository.findAll();
        assertThat(medikalTurKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = medikalTurKoduRepository.findAll().size();
        // set the field null
        medikalTurKodu.setVersion(null);

        // Create the MedikalTurKodu, which fails.

        restMedikalTurKoduMockMvc.perform(post("/api/medikal-tur-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medikalTurKodu)))
            .andExpect(status().isBadRequest());

        List<MedikalTurKodu> medikalTurKoduList = medikalTurKoduRepository.findAll();
        assertThat(medikalTurKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKodIsRequired() throws Exception {
        int databaseSizeBeforeTest = medikalTurKoduRepository.findAll().size();
        // set the field null
        medikalTurKodu.setKod(null);

        // Create the MedikalTurKodu, which fails.

        restMedikalTurKoduMockMvc.perform(post("/api/medikal-tur-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medikalTurKodu)))
            .andExpect(status().isBadRequest());

        List<MedikalTurKodu> medikalTurKoduList = medikalTurKoduRepository.findAll();
        assertThat(medikalTurKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedikalTurKodus() throws Exception {
        // Initialize the database
        medikalTurKoduRepository.saveAndFlush(medikalTurKodu);

        // Get all the medikalTurKoduList
        restMedikalTurKoduMockMvc.perform(get("/api/medikal-tur-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medikalTurKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getMedikalTurKodu() throws Exception {
        // Initialize the database
        medikalTurKoduRepository.saveAndFlush(medikalTurKodu);

        // Get the medikalTurKodu
        restMedikalTurKoduMockMvc.perform(get("/api/medikal-tur-kodus/{id}", medikalTurKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medikalTurKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedikalTurKodu() throws Exception {
        // Get the medikalTurKodu
        restMedikalTurKoduMockMvc.perform(get("/api/medikal-tur-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedikalTurKodu() throws Exception {
        // Initialize the database
        medikalTurKoduRepository.saveAndFlush(medikalTurKodu);
        int databaseSizeBeforeUpdate = medikalTurKoduRepository.findAll().size();

        // Update the medikalTurKodu
        MedikalTurKodu updatedMedikalTurKodu = medikalTurKoduRepository.findOne(medikalTurKodu.getId());
        updatedMedikalTurKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .kod(UPDATED_KOD);

        restMedikalTurKoduMockMvc.perform(put("/api/medikal-tur-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedikalTurKodu)))
            .andExpect(status().isOk());

        // Validate the MedikalTurKodu in the database
        List<MedikalTurKodu> medikalTurKoduList = medikalTurKoduRepository.findAll();
        assertThat(medikalTurKoduList).hasSize(databaseSizeBeforeUpdate);
        MedikalTurKodu testMedikalTurKodu = medikalTurKoduList.get(medikalTurKoduList.size() - 1);
        assertThat(testMedikalTurKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testMedikalTurKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testMedikalTurKodu.getKod()).isEqualTo(UPDATED_KOD);
    }

    @Test
    @Transactional
    public void updateNonExistingMedikalTurKodu() throws Exception {
        int databaseSizeBeforeUpdate = medikalTurKoduRepository.findAll().size();

        // Create the MedikalTurKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedikalTurKoduMockMvc.perform(put("/api/medikal-tur-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medikalTurKodu)))
            .andExpect(status().isCreated());

        // Validate the MedikalTurKodu in the database
        List<MedikalTurKodu> medikalTurKoduList = medikalTurKoduRepository.findAll();
        assertThat(medikalTurKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedikalTurKodu() throws Exception {
        // Initialize the database
        medikalTurKoduRepository.saveAndFlush(medikalTurKodu);
        int databaseSizeBeforeDelete = medikalTurKoduRepository.findAll().size();

        // Get the medikalTurKodu
        restMedikalTurKoduMockMvc.perform(delete("/api/medikal-tur-kodus/{id}", medikalTurKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedikalTurKodu> medikalTurKoduList = medikalTurKoduRepository.findAll();
        assertThat(medikalTurKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedikalTurKodu.class);
        MedikalTurKodu medikalTurKodu1 = new MedikalTurKodu();
        medikalTurKodu1.setId(1L);
        MedikalTurKodu medikalTurKodu2 = new MedikalTurKodu();
        medikalTurKodu2.setId(medikalTurKodu1.getId());
        assertThat(medikalTurKodu1).isEqualTo(medikalTurKodu2);
        medikalTurKodu2.setId(2L);
        assertThat(medikalTurKodu1).isNotEqualTo(medikalTurKodu2);
        medikalTurKodu1.setId(null);
        assertThat(medikalTurKodu1).isNotEqualTo(medikalTurKodu2);
    }
}

package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.OrtaklikBilgileri;
import tr.gov.sb.sygm.repository.OrtaklikBilgileriRepository;
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
 * Test class for the OrtaklikBilgileriResource REST controller.
 *
 * @see OrtaklikBilgileriResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class OrtaklikBilgileriResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final Boolean DEFAULT_ISBIRLIGI_SUNMA_ISTEGI_VAR_MI = false;
    private static final Boolean UPDATED_ISBIRLIGI_SUNMA_ISTEGI_VAR_MI = true;

    private static final Boolean DEFAULT_ISBIRLIGI_TALEBI_VAR_MI = false;
    private static final Boolean UPDATED_ISBIRLIGI_TALEBI_VAR_MI = true;

    private static final String DEFAULT_ISBIRLIGI_KONSU = "AAAAAAAAAA";
    private static final String UPDATED_ISBIRLIGI_KONSU = "BBBBBBBBBB";

    @Autowired
    private OrtaklikBilgileriRepository ortaklikBilgileriRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrtaklikBilgileriMockMvc;

    private OrtaklikBilgileri ortaklikBilgileri;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrtaklikBilgileriResource ortaklikBilgileriResource = new OrtaklikBilgileriResource(ortaklikBilgileriRepository);
        this.restOrtaklikBilgileriMockMvc = MockMvcBuilders.standaloneSetup(ortaklikBilgileriResource)
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
    public static OrtaklikBilgileri createEntity(EntityManager em) {
        OrtaklikBilgileri ortaklikBilgileri = new OrtaklikBilgileri()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .isbirligiSunmaIstegiVarMi(DEFAULT_ISBIRLIGI_SUNMA_ISTEGI_VAR_MI)
            .isbirligiTalebiVarMi(DEFAULT_ISBIRLIGI_TALEBI_VAR_MI)
            .isbirligiKonsu(DEFAULT_ISBIRLIGI_KONSU);
        return ortaklikBilgileri;
    }

    @Before
    public void initTest() {
        ortaklikBilgileri = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrtaklikBilgileri() throws Exception {
        int databaseSizeBeforeCreate = ortaklikBilgileriRepository.findAll().size();

        // Create the OrtaklikBilgileri
        restOrtaklikBilgileriMockMvc.perform(post("/api/ortaklik-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ortaklikBilgileri)))
            .andExpect(status().isCreated());

        // Validate the OrtaklikBilgileri in the database
        List<OrtaklikBilgileri> ortaklikBilgileriList = ortaklikBilgileriRepository.findAll();
        assertThat(ortaklikBilgileriList).hasSize(databaseSizeBeforeCreate + 1);
        OrtaklikBilgileri testOrtaklikBilgileri = ortaklikBilgileriList.get(ortaklikBilgileriList.size() - 1);
        assertThat(testOrtaklikBilgileri.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testOrtaklikBilgileri.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testOrtaklikBilgileri.isIsbirligiSunmaIstegiVarMi()).isEqualTo(DEFAULT_ISBIRLIGI_SUNMA_ISTEGI_VAR_MI);
        assertThat(testOrtaklikBilgileri.isIsbirligiTalebiVarMi()).isEqualTo(DEFAULT_ISBIRLIGI_TALEBI_VAR_MI);
        assertThat(testOrtaklikBilgileri.getIsbirligiKonsu()).isEqualTo(DEFAULT_ISBIRLIGI_KONSU);
    }

    @Test
    @Transactional
    public void createOrtaklikBilgileriWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ortaklikBilgileriRepository.findAll().size();

        // Create the OrtaklikBilgileri with an existing ID
        ortaklikBilgileri.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrtaklikBilgileriMockMvc.perform(post("/api/ortaklik-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ortaklikBilgileri)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrtaklikBilgileri> ortaklikBilgileriList = ortaklikBilgileriRepository.findAll();
        assertThat(ortaklikBilgileriList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = ortaklikBilgileriRepository.findAll().size();
        // set the field null
        ortaklikBilgileri.setDeleted(null);

        // Create the OrtaklikBilgileri, which fails.

        restOrtaklikBilgileriMockMvc.perform(post("/api/ortaklik-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ortaklikBilgileri)))
            .andExpect(status().isBadRequest());

        List<OrtaklikBilgileri> ortaklikBilgileriList = ortaklikBilgileriRepository.findAll();
        assertThat(ortaklikBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ortaklikBilgileriRepository.findAll().size();
        // set the field null
        ortaklikBilgileri.setVersion(null);

        // Create the OrtaklikBilgileri, which fails.

        restOrtaklikBilgileriMockMvc.perform(post("/api/ortaklik-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ortaklikBilgileri)))
            .andExpect(status().isBadRequest());

        List<OrtaklikBilgileri> ortaklikBilgileriList = ortaklikBilgileriRepository.findAll();
        assertThat(ortaklikBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsbirligiSunmaIstegiVarMiIsRequired() throws Exception {
        int databaseSizeBeforeTest = ortaklikBilgileriRepository.findAll().size();
        // set the field null
        ortaklikBilgileri.setIsbirligiSunmaIstegiVarMi(null);

        // Create the OrtaklikBilgileri, which fails.

        restOrtaklikBilgileriMockMvc.perform(post("/api/ortaklik-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ortaklikBilgileri)))
            .andExpect(status().isBadRequest());

        List<OrtaklikBilgileri> ortaklikBilgileriList = ortaklikBilgileriRepository.findAll();
        assertThat(ortaklikBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsbirligiTalebiVarMiIsRequired() throws Exception {
        int databaseSizeBeforeTest = ortaklikBilgileriRepository.findAll().size();
        // set the field null
        ortaklikBilgileri.setIsbirligiTalebiVarMi(null);

        // Create the OrtaklikBilgileri, which fails.

        restOrtaklikBilgileriMockMvc.perform(post("/api/ortaklik-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ortaklikBilgileri)))
            .andExpect(status().isBadRequest());

        List<OrtaklikBilgileri> ortaklikBilgileriList = ortaklikBilgileriRepository.findAll();
        assertThat(ortaklikBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrtaklikBilgileris() throws Exception {
        // Initialize the database
        ortaklikBilgileriRepository.saveAndFlush(ortaklikBilgileri);

        // Get all the ortaklikBilgileriList
        restOrtaklikBilgileriMockMvc.perform(get("/api/ortaklik-bilgileris?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ortaklikBilgileri.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].isbirligiSunmaIstegiVarMi").value(hasItem(DEFAULT_ISBIRLIGI_SUNMA_ISTEGI_VAR_MI.booleanValue())))
            .andExpect(jsonPath("$.[*].isbirligiTalebiVarMi").value(hasItem(DEFAULT_ISBIRLIGI_TALEBI_VAR_MI.booleanValue())))
            .andExpect(jsonPath("$.[*].isbirligiKonsu").value(hasItem(DEFAULT_ISBIRLIGI_KONSU.toString())));
    }

    @Test
    @Transactional
    public void getOrtaklikBilgileri() throws Exception {
        // Initialize the database
        ortaklikBilgileriRepository.saveAndFlush(ortaklikBilgileri);

        // Get the ortaklikBilgileri
        restOrtaklikBilgileriMockMvc.perform(get("/api/ortaklik-bilgileris/{id}", ortaklikBilgileri.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ortaklikBilgileri.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.isbirligiSunmaIstegiVarMi").value(DEFAULT_ISBIRLIGI_SUNMA_ISTEGI_VAR_MI.booleanValue()))
            .andExpect(jsonPath("$.isbirligiTalebiVarMi").value(DEFAULT_ISBIRLIGI_TALEBI_VAR_MI.booleanValue()))
            .andExpect(jsonPath("$.isbirligiKonsu").value(DEFAULT_ISBIRLIGI_KONSU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrtaklikBilgileri() throws Exception {
        // Get the ortaklikBilgileri
        restOrtaklikBilgileriMockMvc.perform(get("/api/ortaklik-bilgileris/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrtaklikBilgileri() throws Exception {
        // Initialize the database
        ortaklikBilgileriRepository.saveAndFlush(ortaklikBilgileri);
        int databaseSizeBeforeUpdate = ortaklikBilgileriRepository.findAll().size();

        // Update the ortaklikBilgileri
        OrtaklikBilgileri updatedOrtaklikBilgileri = ortaklikBilgileriRepository.findOne(ortaklikBilgileri.getId());
        updatedOrtaklikBilgileri
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .isbirligiSunmaIstegiVarMi(UPDATED_ISBIRLIGI_SUNMA_ISTEGI_VAR_MI)
            .isbirligiTalebiVarMi(UPDATED_ISBIRLIGI_TALEBI_VAR_MI)
            .isbirligiKonsu(UPDATED_ISBIRLIGI_KONSU);

        restOrtaklikBilgileriMockMvc.perform(put("/api/ortaklik-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrtaklikBilgileri)))
            .andExpect(status().isOk());

        // Validate the OrtaklikBilgileri in the database
        List<OrtaklikBilgileri> ortaklikBilgileriList = ortaklikBilgileriRepository.findAll();
        assertThat(ortaklikBilgileriList).hasSize(databaseSizeBeforeUpdate);
        OrtaklikBilgileri testOrtaklikBilgileri = ortaklikBilgileriList.get(ortaklikBilgileriList.size() - 1);
        assertThat(testOrtaklikBilgileri.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testOrtaklikBilgileri.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testOrtaklikBilgileri.isIsbirligiSunmaIstegiVarMi()).isEqualTo(UPDATED_ISBIRLIGI_SUNMA_ISTEGI_VAR_MI);
        assertThat(testOrtaklikBilgileri.isIsbirligiTalebiVarMi()).isEqualTo(UPDATED_ISBIRLIGI_TALEBI_VAR_MI);
        assertThat(testOrtaklikBilgileri.getIsbirligiKonsu()).isEqualTo(UPDATED_ISBIRLIGI_KONSU);
    }

    @Test
    @Transactional
    public void updateNonExistingOrtaklikBilgileri() throws Exception {
        int databaseSizeBeforeUpdate = ortaklikBilgileriRepository.findAll().size();

        // Create the OrtaklikBilgileri

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrtaklikBilgileriMockMvc.perform(put("/api/ortaklik-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ortaklikBilgileri)))
            .andExpect(status().isCreated());

        // Validate the OrtaklikBilgileri in the database
        List<OrtaklikBilgileri> ortaklikBilgileriList = ortaklikBilgileriRepository.findAll();
        assertThat(ortaklikBilgileriList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrtaklikBilgileri() throws Exception {
        // Initialize the database
        ortaklikBilgileriRepository.saveAndFlush(ortaklikBilgileri);
        int databaseSizeBeforeDelete = ortaklikBilgileriRepository.findAll().size();

        // Get the ortaklikBilgileri
        restOrtaklikBilgileriMockMvc.perform(delete("/api/ortaklik-bilgileris/{id}", ortaklikBilgileri.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrtaklikBilgileri> ortaklikBilgileriList = ortaklikBilgileriRepository.findAll();
        assertThat(ortaklikBilgileriList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrtaklikBilgileri.class);
        OrtaklikBilgileri ortaklikBilgileri1 = new OrtaklikBilgileri();
        ortaklikBilgileri1.setId(1L);
        OrtaklikBilgileri ortaklikBilgileri2 = new OrtaklikBilgileri();
        ortaklikBilgileri2.setId(ortaklikBilgileri1.getId());
        assertThat(ortaklikBilgileri1).isEqualTo(ortaklikBilgileri2);
        ortaklikBilgileri2.setId(2L);
        assertThat(ortaklikBilgileri1).isNotEqualTo(ortaklikBilgileri2);
        ortaklikBilgileri1.setId(null);
        assertThat(ortaklikBilgileri1).isNotEqualTo(ortaklikBilgileri2);
    }
}

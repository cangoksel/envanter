package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.Firma;
import tr.gov.sb.sygm.repository.FirmaRepository;
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
 * Test class for the FirmaResource REST controller.
 *
 * @see FirmaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class FirmaResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final Boolean DEFAULT_FIRMA_YETKINLIK_CALISMASI_YEPILDI_MI = false;
    private static final Boolean UPDATED_FIRMA_YETKINLIK_CALISMASI_YEPILDI_MI = true;

    @Autowired
    private FirmaRepository firmaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFirmaMockMvc;

    private Firma firma;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FirmaResource firmaResource = new FirmaResource(firmaRepository);
        this.restFirmaMockMvc = MockMvcBuilders.standaloneSetup(firmaResource)
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
    public static Firma createEntity(EntityManager em) {
        Firma firma = new Firma()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .firmaYetkinlikCalismasiYepildiMi(DEFAULT_FIRMA_YETKINLIK_CALISMASI_YEPILDI_MI);
        return firma;
    }

    @Before
    public void initTest() {
        firma = createEntity(em);
    }

    @Test
    @Transactional
    public void createFirma() throws Exception {
        int databaseSizeBeforeCreate = firmaRepository.findAll().size();

        // Create the Firma
        restFirmaMockMvc.perform(post("/api/firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(firma)))
            .andExpect(status().isCreated());

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeCreate + 1);
        Firma testFirma = firmaList.get(firmaList.size() - 1);
        assertThat(testFirma.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testFirma.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFirma.isFirmaYetkinlikCalismasiYepildiMi()).isEqualTo(DEFAULT_FIRMA_YETKINLIK_CALISMASI_YEPILDI_MI);
    }

    @Test
    @Transactional
    public void createFirmaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = firmaRepository.findAll().size();

        // Create the Firma with an existing ID
        firma.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFirmaMockMvc.perform(post("/api/firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(firma)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = firmaRepository.findAll().size();
        // set the field null
        firma.setDeleted(null);

        // Create the Firma, which fails.

        restFirmaMockMvc.perform(post("/api/firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(firma)))
            .andExpect(status().isBadRequest());

        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = firmaRepository.findAll().size();
        // set the field null
        firma.setVersion(null);

        // Create the Firma, which fails.

        restFirmaMockMvc.perform(post("/api/firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(firma)))
            .andExpect(status().isBadRequest());

        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirmaYetkinlikCalismasiYepildiMiIsRequired() throws Exception {
        int databaseSizeBeforeTest = firmaRepository.findAll().size();
        // set the field null
        firma.setFirmaYetkinlikCalismasiYepildiMi(null);

        // Create the Firma, which fails.

        restFirmaMockMvc.perform(post("/api/firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(firma)))
            .andExpect(status().isBadRequest());

        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFirmas() throws Exception {
        // Initialize the database
        firmaRepository.saveAndFlush(firma);

        // Get all the firmaList
        restFirmaMockMvc.perform(get("/api/firmas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(firma.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].firmaYetkinlikCalismasiYepildiMi").value(hasItem(DEFAULT_FIRMA_YETKINLIK_CALISMASI_YEPILDI_MI.booleanValue())));
    }

    @Test
    @Transactional
    public void getFirma() throws Exception {
        // Initialize the database
        firmaRepository.saveAndFlush(firma);

        // Get the firma
        restFirmaMockMvc.perform(get("/api/firmas/{id}", firma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(firma.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.firmaYetkinlikCalismasiYepildiMi").value(DEFAULT_FIRMA_YETKINLIK_CALISMASI_YEPILDI_MI.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFirma() throws Exception {
        // Get the firma
        restFirmaMockMvc.perform(get("/api/firmas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFirma() throws Exception {
        // Initialize the database
        firmaRepository.saveAndFlush(firma);
        int databaseSizeBeforeUpdate = firmaRepository.findAll().size();

        // Update the firma
        Firma updatedFirma = firmaRepository.findOne(firma.getId());
        updatedFirma
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .firmaYetkinlikCalismasiYepildiMi(UPDATED_FIRMA_YETKINLIK_CALISMASI_YEPILDI_MI);

        restFirmaMockMvc.perform(put("/api/firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFirma)))
            .andExpect(status().isOk());

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
        Firma testFirma = firmaList.get(firmaList.size() - 1);
        assertThat(testFirma.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testFirma.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFirma.isFirmaYetkinlikCalismasiYepildiMi()).isEqualTo(UPDATED_FIRMA_YETKINLIK_CALISMASI_YEPILDI_MI);
    }

    @Test
    @Transactional
    public void updateNonExistingFirma() throws Exception {
        int databaseSizeBeforeUpdate = firmaRepository.findAll().size();

        // Create the Firma

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFirmaMockMvc.perform(put("/api/firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(firma)))
            .andExpect(status().isCreated());

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFirma() throws Exception {
        // Initialize the database
        firmaRepository.saveAndFlush(firma);
        int databaseSizeBeforeDelete = firmaRepository.findAll().size();

        // Get the firma
        restFirmaMockMvc.perform(delete("/api/firmas/{id}", firma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Firma.class);
        Firma firma1 = new Firma();
        firma1.setId(1L);
        Firma firma2 = new Firma();
        firma2.setId(firma1.getId());
        assertThat(firma1).isEqualTo(firma2);
        firma2.setId(2L);
        assertThat(firma1).isNotEqualTo(firma2);
        firma1.setId(null);
        assertThat(firma1).isNotEqualTo(firma2);
    }
}

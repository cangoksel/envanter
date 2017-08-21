package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.Kisi;
import tr.gov.sb.sygm.repository.KisiRepository;
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
 * Test class for the KisiResource REST controller.
 *
 * @see KisiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class KisiResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_SOYADI = "AAAAAAAAAA";
    private static final String UPDATED_SOYADI = "BBBBBBBBBB";

    @Autowired
    private KisiRepository kisiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKisiMockMvc;

    private Kisi kisi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KisiResource kisiResource = new KisiResource(kisiRepository);
        this.restKisiMockMvc = MockMvcBuilders.standaloneSetup(kisiResource)
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
    public static Kisi createEntity(EntityManager em) {
        Kisi kisi = new Kisi()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .adi(DEFAULT_ADI)
            .soyadi(DEFAULT_SOYADI);
        return kisi;
    }

    @Before
    public void initTest() {
        kisi = createEntity(em);
    }

    @Test
    @Transactional
    public void createKisi() throws Exception {
        int databaseSizeBeforeCreate = kisiRepository.findAll().size();

        // Create the Kisi
        restKisiMockMvc.perform(post("/api/kisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kisi)))
            .andExpect(status().isCreated());

        // Validate the Kisi in the database
        List<Kisi> kisiList = kisiRepository.findAll();
        assertThat(kisiList).hasSize(databaseSizeBeforeCreate + 1);
        Kisi testKisi = kisiList.get(kisiList.size() - 1);
        assertThat(testKisi.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testKisi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testKisi.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testKisi.getSoyadi()).isEqualTo(DEFAULT_SOYADI);
    }

    @Test
    @Transactional
    public void createKisiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kisiRepository.findAll().size();

        // Create the Kisi with an existing ID
        kisi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKisiMockMvc.perform(post("/api/kisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kisi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Kisi> kisiList = kisiRepository.findAll();
        assertThat(kisiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = kisiRepository.findAll().size();
        // set the field null
        kisi.setDeleted(null);

        // Create the Kisi, which fails.

        restKisiMockMvc.perform(post("/api/kisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kisi)))
            .andExpect(status().isBadRequest());

        List<Kisi> kisiList = kisiRepository.findAll();
        assertThat(kisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = kisiRepository.findAll().size();
        // set the field null
        kisi.setVersion(null);

        // Create the Kisi, which fails.

        restKisiMockMvc.perform(post("/api/kisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kisi)))
            .andExpect(status().isBadRequest());

        List<Kisi> kisiList = kisiRepository.findAll();
        assertThat(kisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = kisiRepository.findAll().size();
        // set the field null
        kisi.setAdi(null);

        // Create the Kisi, which fails.

        restKisiMockMvc.perform(post("/api/kisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kisi)))
            .andExpect(status().isBadRequest());

        List<Kisi> kisiList = kisiRepository.findAll();
        assertThat(kisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSoyadiIsRequired() throws Exception {
        int databaseSizeBeforeTest = kisiRepository.findAll().size();
        // set the field null
        kisi.setSoyadi(null);

        // Create the Kisi, which fails.

        restKisiMockMvc.perform(post("/api/kisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kisi)))
            .andExpect(status().isBadRequest());

        List<Kisi> kisiList = kisiRepository.findAll();
        assertThat(kisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKisis() throws Exception {
        // Initialize the database
        kisiRepository.saveAndFlush(kisi);

        // Get all the kisiList
        restKisiMockMvc.perform(get("/api/kisis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kisi.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())))
            .andExpect(jsonPath("$.[*].soyadi").value(hasItem(DEFAULT_SOYADI.toString())));
    }

    @Test
    @Transactional
    public void getKisi() throws Exception {
        // Initialize the database
        kisiRepository.saveAndFlush(kisi);

        // Get the kisi
        restKisiMockMvc.perform(get("/api/kisis/{id}", kisi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kisi.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()))
            .andExpect(jsonPath("$.soyadi").value(DEFAULT_SOYADI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKisi() throws Exception {
        // Get the kisi
        restKisiMockMvc.perform(get("/api/kisis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKisi() throws Exception {
        // Initialize the database
        kisiRepository.saveAndFlush(kisi);
        int databaseSizeBeforeUpdate = kisiRepository.findAll().size();

        // Update the kisi
        Kisi updatedKisi = kisiRepository.findOne(kisi.getId());
        updatedKisi
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .adi(UPDATED_ADI)
            .soyadi(UPDATED_SOYADI);

        restKisiMockMvc.perform(put("/api/kisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKisi)))
            .andExpect(status().isOk());

        // Validate the Kisi in the database
        List<Kisi> kisiList = kisiRepository.findAll();
        assertThat(kisiList).hasSize(databaseSizeBeforeUpdate);
        Kisi testKisi = kisiList.get(kisiList.size() - 1);
        assertThat(testKisi.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testKisi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testKisi.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testKisi.getSoyadi()).isEqualTo(UPDATED_SOYADI);
    }

    @Test
    @Transactional
    public void updateNonExistingKisi() throws Exception {
        int databaseSizeBeforeUpdate = kisiRepository.findAll().size();

        // Create the Kisi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKisiMockMvc.perform(put("/api/kisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kisi)))
            .andExpect(status().isCreated());

        // Validate the Kisi in the database
        List<Kisi> kisiList = kisiRepository.findAll();
        assertThat(kisiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKisi() throws Exception {
        // Initialize the database
        kisiRepository.saveAndFlush(kisi);
        int databaseSizeBeforeDelete = kisiRepository.findAll().size();

        // Get the kisi
        restKisiMockMvc.perform(delete("/api/kisis/{id}", kisi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kisi> kisiList = kisiRepository.findAll();
        assertThat(kisiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kisi.class);
        Kisi kisi1 = new Kisi();
        kisi1.setId(1L);
        Kisi kisi2 = new Kisi();
        kisi2.setId(kisi1.getId());
        assertThat(kisi1).isEqualTo(kisi2);
        kisi2.setId(2L);
        assertThat(kisi1).isNotEqualTo(kisi2);
        kisi1.setId(null);
        assertThat(kisi1).isNotEqualTo(kisi2);
    }
}

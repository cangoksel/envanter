package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.Kurulus;
import tr.gov.sb.sygm.repository.KurulusRepository;
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
 * Test class for the KurulusResource REST controller.
 *
 * @see KurulusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class KurulusResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITESI = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITESI = "BBBBBBBBBB";

    private static final Long DEFAULT_UYE_SAYISI = 1L;
    private static final Long UPDATED_UYE_SAYISI = 2L;

    @Autowired
    private KurulusRepository kurulusRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKurulusMockMvc;

    private Kurulus kurulus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KurulusResource kurulusResource = new KurulusResource(kurulusRepository);
        this.restKurulusMockMvc = MockMvcBuilders.standaloneSetup(kurulusResource)
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
    public static Kurulus createEntity(EntityManager em) {
        Kurulus kurulus = new Kurulus()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .adi(DEFAULT_ADI)
            .telefon(DEFAULT_TELEFON)
            .telefon2(DEFAULT_TELEFON_2)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .webSitesi(DEFAULT_WEB_SITESI)
            .uyeSayisi(DEFAULT_UYE_SAYISI);
        return kurulus;
    }

    @Before
    public void initTest() {
        kurulus = createEntity(em);
    }

    @Test
    @Transactional
    public void createKurulus() throws Exception {
        int databaseSizeBeforeCreate = kurulusRepository.findAll().size();

        // Create the Kurulus
        restKurulusMockMvc.perform(post("/api/kuruluses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulus)))
            .andExpect(status().isCreated());

        // Validate the Kurulus in the database
        List<Kurulus> kurulusList = kurulusRepository.findAll();
        assertThat(kurulusList).hasSize(databaseSizeBeforeCreate + 1);
        Kurulus testKurulus = kurulusList.get(kurulusList.size() - 1);
        assertThat(testKurulus.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testKurulus.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testKurulus.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testKurulus.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testKurulus.getTelefon2()).isEqualTo(DEFAULT_TELEFON_2);
        assertThat(testKurulus.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testKurulus.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testKurulus.getWebSitesi()).isEqualTo(DEFAULT_WEB_SITESI);
        assertThat(testKurulus.getUyeSayisi()).isEqualTo(DEFAULT_UYE_SAYISI);
    }

    @Test
    @Transactional
    public void createKurulusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kurulusRepository.findAll().size();

        // Create the Kurulus with an existing ID
        kurulus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKurulusMockMvc.perform(post("/api/kuruluses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulus)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Kurulus> kurulusList = kurulusRepository.findAll();
        assertThat(kurulusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurulusRepository.findAll().size();
        // set the field null
        kurulus.setDeleted(null);

        // Create the Kurulus, which fails.

        restKurulusMockMvc.perform(post("/api/kuruluses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulus)))
            .andExpect(status().isBadRequest());

        List<Kurulus> kurulusList = kurulusRepository.findAll();
        assertThat(kurulusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurulusRepository.findAll().size();
        // set the field null
        kurulus.setVersion(null);

        // Create the Kurulus, which fails.

        restKurulusMockMvc.perform(post("/api/kuruluses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulus)))
            .andExpect(status().isBadRequest());

        List<Kurulus> kurulusList = kurulusRepository.findAll();
        assertThat(kurulusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurulusRepository.findAll().size();
        // set the field null
        kurulus.setAdi(null);

        // Create the Kurulus, which fails.

        restKurulusMockMvc.perform(post("/api/kuruluses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulus)))
            .andExpect(status().isBadRequest());

        List<Kurulus> kurulusList = kurulusRepository.findAll();
        assertThat(kurulusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKuruluses() throws Exception {
        // Initialize the database
        kurulusRepository.saveAndFlush(kurulus);

        // Get all the kurulusList
        restKurulusMockMvc.perform(get("/api/kuruluses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kurulus.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].telefon2").value(hasItem(DEFAULT_TELEFON_2.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].webSitesi").value(hasItem(DEFAULT_WEB_SITESI.toString())))
            .andExpect(jsonPath("$.[*].uyeSayisi").value(hasItem(DEFAULT_UYE_SAYISI.intValue())));
    }

    @Test
    @Transactional
    public void getKurulus() throws Exception {
        // Initialize the database
        kurulusRepository.saveAndFlush(kurulus);

        // Get the kurulus
        restKurulusMockMvc.perform(get("/api/kuruluses/{id}", kurulus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kurulus.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.telefon2").value(DEFAULT_TELEFON_2.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.webSitesi").value(DEFAULT_WEB_SITESI.toString()))
            .andExpect(jsonPath("$.uyeSayisi").value(DEFAULT_UYE_SAYISI.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingKurulus() throws Exception {
        // Get the kurulus
        restKurulusMockMvc.perform(get("/api/kuruluses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKurulus() throws Exception {
        // Initialize the database
        kurulusRepository.saveAndFlush(kurulus);
        int databaseSizeBeforeUpdate = kurulusRepository.findAll().size();

        // Update the kurulus
        Kurulus updatedKurulus = kurulusRepository.findOne(kurulus.getId());
        updatedKurulus
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .adi(UPDATED_ADI)
            .telefon(UPDATED_TELEFON)
            .telefon2(UPDATED_TELEFON_2)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .webSitesi(UPDATED_WEB_SITESI)
            .uyeSayisi(UPDATED_UYE_SAYISI);

        restKurulusMockMvc.perform(put("/api/kuruluses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKurulus)))
            .andExpect(status().isOk());

        // Validate the Kurulus in the database
        List<Kurulus> kurulusList = kurulusRepository.findAll();
        assertThat(kurulusList).hasSize(databaseSizeBeforeUpdate);
        Kurulus testKurulus = kurulusList.get(kurulusList.size() - 1);
        assertThat(testKurulus.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testKurulus.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testKurulus.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testKurulus.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testKurulus.getTelefon2()).isEqualTo(UPDATED_TELEFON_2);
        assertThat(testKurulus.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testKurulus.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKurulus.getWebSitesi()).isEqualTo(UPDATED_WEB_SITESI);
        assertThat(testKurulus.getUyeSayisi()).isEqualTo(UPDATED_UYE_SAYISI);
    }

    @Test
    @Transactional
    public void updateNonExistingKurulus() throws Exception {
        int databaseSizeBeforeUpdate = kurulusRepository.findAll().size();

        // Create the Kurulus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKurulusMockMvc.perform(put("/api/kuruluses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulus)))
            .andExpect(status().isCreated());

        // Validate the Kurulus in the database
        List<Kurulus> kurulusList = kurulusRepository.findAll();
        assertThat(kurulusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKurulus() throws Exception {
        // Initialize the database
        kurulusRepository.saveAndFlush(kurulus);
        int databaseSizeBeforeDelete = kurulusRepository.findAll().size();

        // Get the kurulus
        restKurulusMockMvc.perform(delete("/api/kuruluses/{id}", kurulus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kurulus> kurulusList = kurulusRepository.findAll();
        assertThat(kurulusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kurulus.class);
        Kurulus kurulus1 = new Kurulus();
        kurulus1.setId(1L);
        Kurulus kurulus2 = new Kurulus();
        kurulus2.setId(kurulus1.getId());
        assertThat(kurulus1).isEqualTo(kurulus2);
        kurulus2.setId(2L);
        assertThat(kurulus1).isNotEqualTo(kurulus2);
        kurulus1.setId(null);
        assertThat(kurulus1).isNotEqualTo(kurulus2);
    }
}

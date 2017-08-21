package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.Urun;
import tr.gov.sb.sygm.repository.UrunRepository;
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
 * Test class for the UrunResource REST controller.
 *
 * @see UrunResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class UrunResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_TANIMI = "AAAAAAAAAA";
    private static final String UPDATED_TANIMI = "BBBBBBBBBB";

    private static final String DEFAULT_KULLANIM_ALANLARI = "AAAAAAAAAA";
    private static final String UPDATED_KULLANIM_ALANLARI = "BBBBBBBBBB";

    private static final String DEFAULT_CESITLERI = "AAAAAAAAAA";
    private static final String UPDATED_CESITLERI = "BBBBBBBBBB";

    private static final String DEFAULT_ENDIKASYONLARI = "AAAAAAAAAA";
    private static final String UPDATED_ENDIKASYONLARI = "BBBBBBBBBB";

    private static final String DEFAULT_FORMLARI = "AAAAAAAAAA";
    private static final String UPDATED_FORMLARI = "BBBBBBBBBB";

    @Autowired
    private UrunRepository urunRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUrunMockMvc;

    private Urun urun;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UrunResource urunResource = new UrunResource(urunRepository);
        this.restUrunMockMvc = MockMvcBuilders.standaloneSetup(urunResource)
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
    public static Urun createEntity(EntityManager em) {
        Urun urun = new Urun()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .tanimi(DEFAULT_TANIMI)
            .kullanimAlanlari(DEFAULT_KULLANIM_ALANLARI)
            .cesitleri(DEFAULT_CESITLERI)
            .endikasyonlari(DEFAULT_ENDIKASYONLARI)
            .formlari(DEFAULT_FORMLARI);
        return urun;
    }

    @Before
    public void initTest() {
        urun = createEntity(em);
    }

    @Test
    @Transactional
    public void createUrun() throws Exception {
        int databaseSizeBeforeCreate = urunRepository.findAll().size();

        // Create the Urun
        restUrunMockMvc.perform(post("/api/uruns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urun)))
            .andExpect(status().isCreated());

        // Validate the Urun in the database
        List<Urun> urunList = urunRepository.findAll();
        assertThat(urunList).hasSize(databaseSizeBeforeCreate + 1);
        Urun testUrun = urunList.get(urunList.size() - 1);
        assertThat(testUrun.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testUrun.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUrun.getTanimi()).isEqualTo(DEFAULT_TANIMI);
        assertThat(testUrun.getKullanimAlanlari()).isEqualTo(DEFAULT_KULLANIM_ALANLARI);
        assertThat(testUrun.getCesitleri()).isEqualTo(DEFAULT_CESITLERI);
        assertThat(testUrun.getEndikasyonlari()).isEqualTo(DEFAULT_ENDIKASYONLARI);
        assertThat(testUrun.getFormlari()).isEqualTo(DEFAULT_FORMLARI);
    }

    @Test
    @Transactional
    public void createUrunWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = urunRepository.findAll().size();

        // Create the Urun with an existing ID
        urun.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrunMockMvc.perform(post("/api/uruns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urun)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Urun> urunList = urunRepository.findAll();
        assertThat(urunList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunRepository.findAll().size();
        // set the field null
        urun.setDeleted(null);

        // Create the Urun, which fails.

        restUrunMockMvc.perform(post("/api/uruns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urun)))
            .andExpect(status().isBadRequest());

        List<Urun> urunList = urunRepository.findAll();
        assertThat(urunList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunRepository.findAll().size();
        // set the field null
        urun.setVersion(null);

        // Create the Urun, which fails.

        restUrunMockMvc.perform(post("/api/uruns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urun)))
            .andExpect(status().isBadRequest());

        List<Urun> urunList = urunRepository.findAll();
        assertThat(urunList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUruns() throws Exception {
        // Initialize the database
        urunRepository.saveAndFlush(urun);

        // Get all the urunList
        restUrunMockMvc.perform(get("/api/uruns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urun.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].tanimi").value(hasItem(DEFAULT_TANIMI.toString())))
            .andExpect(jsonPath("$.[*].kullanimAlanlari").value(hasItem(DEFAULT_KULLANIM_ALANLARI.toString())))
            .andExpect(jsonPath("$.[*].cesitleri").value(hasItem(DEFAULT_CESITLERI.toString())))
            .andExpect(jsonPath("$.[*].endikasyonlari").value(hasItem(DEFAULT_ENDIKASYONLARI.toString())))
            .andExpect(jsonPath("$.[*].formlari").value(hasItem(DEFAULT_FORMLARI.toString())));
    }

    @Test
    @Transactional
    public void getUrun() throws Exception {
        // Initialize the database
        urunRepository.saveAndFlush(urun);

        // Get the urun
        restUrunMockMvc.perform(get("/api/uruns/{id}", urun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(urun.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.tanimi").value(DEFAULT_TANIMI.toString()))
            .andExpect(jsonPath("$.kullanimAlanlari").value(DEFAULT_KULLANIM_ALANLARI.toString()))
            .andExpect(jsonPath("$.cesitleri").value(DEFAULT_CESITLERI.toString()))
            .andExpect(jsonPath("$.endikasyonlari").value(DEFAULT_ENDIKASYONLARI.toString()))
            .andExpect(jsonPath("$.formlari").value(DEFAULT_FORMLARI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUrun() throws Exception {
        // Get the urun
        restUrunMockMvc.perform(get("/api/uruns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUrun() throws Exception {
        // Initialize the database
        urunRepository.saveAndFlush(urun);
        int databaseSizeBeforeUpdate = urunRepository.findAll().size();

        // Update the urun
        Urun updatedUrun = urunRepository.findOne(urun.getId());
        updatedUrun
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .tanimi(UPDATED_TANIMI)
            .kullanimAlanlari(UPDATED_KULLANIM_ALANLARI)
            .cesitleri(UPDATED_CESITLERI)
            .endikasyonlari(UPDATED_ENDIKASYONLARI)
            .formlari(UPDATED_FORMLARI);

        restUrunMockMvc.perform(put("/api/uruns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUrun)))
            .andExpect(status().isOk());

        // Validate the Urun in the database
        List<Urun> urunList = urunRepository.findAll();
        assertThat(urunList).hasSize(databaseSizeBeforeUpdate);
        Urun testUrun = urunList.get(urunList.size() - 1);
        assertThat(testUrun.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testUrun.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUrun.getTanimi()).isEqualTo(UPDATED_TANIMI);
        assertThat(testUrun.getKullanimAlanlari()).isEqualTo(UPDATED_KULLANIM_ALANLARI);
        assertThat(testUrun.getCesitleri()).isEqualTo(UPDATED_CESITLERI);
        assertThat(testUrun.getEndikasyonlari()).isEqualTo(UPDATED_ENDIKASYONLARI);
        assertThat(testUrun.getFormlari()).isEqualTo(UPDATED_FORMLARI);
    }

    @Test
    @Transactional
    public void updateNonExistingUrun() throws Exception {
        int databaseSizeBeforeUpdate = urunRepository.findAll().size();

        // Create the Urun

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUrunMockMvc.perform(put("/api/uruns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urun)))
            .andExpect(status().isCreated());

        // Validate the Urun in the database
        List<Urun> urunList = urunRepository.findAll();
        assertThat(urunList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUrun() throws Exception {
        // Initialize the database
        urunRepository.saveAndFlush(urun);
        int databaseSizeBeforeDelete = urunRepository.findAll().size();

        // Get the urun
        restUrunMockMvc.perform(delete("/api/uruns/{id}", urun.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Urun> urunList = urunRepository.findAll();
        assertThat(urunList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Urun.class);
        Urun urun1 = new Urun();
        urun1.setId(1L);
        Urun urun2 = new Urun();
        urun2.setId(urun1.getId());
        assertThat(urun1).isEqualTo(urun2);
        urun2.setId(2L);
        assertThat(urun1).isNotEqualTo(urun2);
        urun1.setId(null);
        assertThat(urun1).isNotEqualTo(urun2);
    }
}

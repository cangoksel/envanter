package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.UrunAltKodu;
import tr.gov.sb.sygm.repository.UrunAltKoduRepository;
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
 * Test class for the UrunAltKoduResource REST controller.
 *
 * @see UrunAltKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class UrunAltKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_URUN_ALT_KODU_ADI = "AAAAAAAAAA";
    private static final String UPDATED_URUN_ALT_KODU_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_URUN_ALT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_URUN_ALT_KOD = "BBBBBBBBBB";

    @Autowired
    private UrunAltKoduRepository urunAltKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUrunAltKoduMockMvc;

    private UrunAltKodu urunAltKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UrunAltKoduResource urunAltKoduResource = new UrunAltKoduResource(urunAltKoduRepository);
        this.restUrunAltKoduMockMvc = MockMvcBuilders.standaloneSetup(urunAltKoduResource)
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
    public static UrunAltKodu createEntity(EntityManager em) {
        UrunAltKodu urunAltKodu = new UrunAltKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .urunAltKoduAdi(DEFAULT_URUN_ALT_KODU_ADI)
            .urunAltKod(DEFAULT_URUN_ALT_KOD);
        return urunAltKodu;
    }

    @Before
    public void initTest() {
        urunAltKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createUrunAltKodu() throws Exception {
        int databaseSizeBeforeCreate = urunAltKoduRepository.findAll().size();

        // Create the UrunAltKodu
        restUrunAltKoduMockMvc.perform(post("/api/urun-alt-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunAltKodu)))
            .andExpect(status().isCreated());

        // Validate the UrunAltKodu in the database
        List<UrunAltKodu> urunAltKoduList = urunAltKoduRepository.findAll();
        assertThat(urunAltKoduList).hasSize(databaseSizeBeforeCreate + 1);
        UrunAltKodu testUrunAltKodu = urunAltKoduList.get(urunAltKoduList.size() - 1);
        assertThat(testUrunAltKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testUrunAltKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUrunAltKodu.getUrunAltKoduAdi()).isEqualTo(DEFAULT_URUN_ALT_KODU_ADI);
        assertThat(testUrunAltKodu.getUrunAltKod()).isEqualTo(DEFAULT_URUN_ALT_KOD);
    }

    @Test
    @Transactional
    public void createUrunAltKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = urunAltKoduRepository.findAll().size();

        // Create the UrunAltKodu with an existing ID
        urunAltKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrunAltKoduMockMvc.perform(post("/api/urun-alt-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunAltKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UrunAltKodu> urunAltKoduList = urunAltKoduRepository.findAll();
        assertThat(urunAltKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunAltKoduRepository.findAll().size();
        // set the field null
        urunAltKodu.setDeleted(null);

        // Create the UrunAltKodu, which fails.

        restUrunAltKoduMockMvc.perform(post("/api/urun-alt-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunAltKodu)))
            .andExpect(status().isBadRequest());

        List<UrunAltKodu> urunAltKoduList = urunAltKoduRepository.findAll();
        assertThat(urunAltKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunAltKoduRepository.findAll().size();
        // set the field null
        urunAltKodu.setVersion(null);

        // Create the UrunAltKodu, which fails.

        restUrunAltKoduMockMvc.perform(post("/api/urun-alt-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunAltKodu)))
            .andExpect(status().isBadRequest());

        List<UrunAltKodu> urunAltKoduList = urunAltKoduRepository.findAll();
        assertThat(urunAltKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrunAltKoduAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunAltKoduRepository.findAll().size();
        // set the field null
        urunAltKodu.setUrunAltKoduAdi(null);

        // Create the UrunAltKodu, which fails.

        restUrunAltKoduMockMvc.perform(post("/api/urun-alt-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunAltKodu)))
            .andExpect(status().isBadRequest());

        List<UrunAltKodu> urunAltKoduList = urunAltKoduRepository.findAll();
        assertThat(urunAltKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrunAltKodIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunAltKoduRepository.findAll().size();
        // set the field null
        urunAltKodu.setUrunAltKod(null);

        // Create the UrunAltKodu, which fails.

        restUrunAltKoduMockMvc.perform(post("/api/urun-alt-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunAltKodu)))
            .andExpect(status().isBadRequest());

        List<UrunAltKodu> urunAltKoduList = urunAltKoduRepository.findAll();
        assertThat(urunAltKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUrunAltKodus() throws Exception {
        // Initialize the database
        urunAltKoduRepository.saveAndFlush(urunAltKodu);

        // Get all the urunAltKoduList
        restUrunAltKoduMockMvc.perform(get("/api/urun-alt-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urunAltKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].urunAltKoduAdi").value(hasItem(DEFAULT_URUN_ALT_KODU_ADI.toString())))
            .andExpect(jsonPath("$.[*].urunAltKod").value(hasItem(DEFAULT_URUN_ALT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getUrunAltKodu() throws Exception {
        // Initialize the database
        urunAltKoduRepository.saveAndFlush(urunAltKodu);

        // Get the urunAltKodu
        restUrunAltKoduMockMvc.perform(get("/api/urun-alt-kodus/{id}", urunAltKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(urunAltKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.urunAltKoduAdi").value(DEFAULT_URUN_ALT_KODU_ADI.toString()))
            .andExpect(jsonPath("$.urunAltKod").value(DEFAULT_URUN_ALT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUrunAltKodu() throws Exception {
        // Get the urunAltKodu
        restUrunAltKoduMockMvc.perform(get("/api/urun-alt-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUrunAltKodu() throws Exception {
        // Initialize the database
        urunAltKoduRepository.saveAndFlush(urunAltKodu);
        int databaseSizeBeforeUpdate = urunAltKoduRepository.findAll().size();

        // Update the urunAltKodu
        UrunAltKodu updatedUrunAltKodu = urunAltKoduRepository.findOne(urunAltKodu.getId());
        updatedUrunAltKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .urunAltKoduAdi(UPDATED_URUN_ALT_KODU_ADI)
            .urunAltKod(UPDATED_URUN_ALT_KOD);

        restUrunAltKoduMockMvc.perform(put("/api/urun-alt-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUrunAltKodu)))
            .andExpect(status().isOk());

        // Validate the UrunAltKodu in the database
        List<UrunAltKodu> urunAltKoduList = urunAltKoduRepository.findAll();
        assertThat(urunAltKoduList).hasSize(databaseSizeBeforeUpdate);
        UrunAltKodu testUrunAltKodu = urunAltKoduList.get(urunAltKoduList.size() - 1);
        assertThat(testUrunAltKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testUrunAltKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUrunAltKodu.getUrunAltKoduAdi()).isEqualTo(UPDATED_URUN_ALT_KODU_ADI);
        assertThat(testUrunAltKodu.getUrunAltKod()).isEqualTo(UPDATED_URUN_ALT_KOD);
    }

    @Test
    @Transactional
    public void updateNonExistingUrunAltKodu() throws Exception {
        int databaseSizeBeforeUpdate = urunAltKoduRepository.findAll().size();

        // Create the UrunAltKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUrunAltKoduMockMvc.perform(put("/api/urun-alt-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunAltKodu)))
            .andExpect(status().isCreated());

        // Validate the UrunAltKodu in the database
        List<UrunAltKodu> urunAltKoduList = urunAltKoduRepository.findAll();
        assertThat(urunAltKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUrunAltKodu() throws Exception {
        // Initialize the database
        urunAltKoduRepository.saveAndFlush(urunAltKodu);
        int databaseSizeBeforeDelete = urunAltKoduRepository.findAll().size();

        // Get the urunAltKodu
        restUrunAltKoduMockMvc.perform(delete("/api/urun-alt-kodus/{id}", urunAltKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UrunAltKodu> urunAltKoduList = urunAltKoduRepository.findAll();
        assertThat(urunAltKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UrunAltKodu.class);
        UrunAltKodu urunAltKodu1 = new UrunAltKodu();
        urunAltKodu1.setId(1L);
        UrunAltKodu urunAltKodu2 = new UrunAltKodu();
        urunAltKodu2.setId(urunAltKodu1.getId());
        assertThat(urunAltKodu1).isEqualTo(urunAltKodu2);
        urunAltKodu2.setId(2L);
        assertThat(urunAltKodu1).isNotEqualTo(urunAltKodu2);
        urunAltKodu1.setId(null);
        assertThat(urunAltKodu1).isNotEqualTo(urunAltKodu2);
    }
}

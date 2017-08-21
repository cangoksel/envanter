package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.UrunKodu;
import tr.gov.sb.sygm.repository.UrunKoduRepository;
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
 * Test class for the UrunKoduResource REST controller.
 *
 * @see UrunKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class UrunKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_URUN_KODU_ADI = "AAAAAAAAAA";
    private static final String UPDATED_URUN_KODU_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_URUN_KOD = "AAAAAAAAAA";
    private static final String UPDATED_URUN_KOD = "BBBBBBBBBB";

    @Autowired
    private UrunKoduRepository urunKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUrunKoduMockMvc;

    private UrunKodu urunKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UrunKoduResource urunKoduResource = new UrunKoduResource(urunKoduRepository);
        this.restUrunKoduMockMvc = MockMvcBuilders.standaloneSetup(urunKoduResource)
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
    public static UrunKodu createEntity(EntityManager em) {
        UrunKodu urunKodu = new UrunKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .urunKoduAdi(DEFAULT_URUN_KODU_ADI)
            .urunKod(DEFAULT_URUN_KOD);
        return urunKodu;
    }

    @Before
    public void initTest() {
        urunKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createUrunKodu() throws Exception {
        int databaseSizeBeforeCreate = urunKoduRepository.findAll().size();

        // Create the UrunKodu
        restUrunKoduMockMvc.perform(post("/api/urun-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunKodu)))
            .andExpect(status().isCreated());

        // Validate the UrunKodu in the database
        List<UrunKodu> urunKoduList = urunKoduRepository.findAll();
        assertThat(urunKoduList).hasSize(databaseSizeBeforeCreate + 1);
        UrunKodu testUrunKodu = urunKoduList.get(urunKoduList.size() - 1);
        assertThat(testUrunKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testUrunKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUrunKodu.getUrunKoduAdi()).isEqualTo(DEFAULT_URUN_KODU_ADI);
        assertThat(testUrunKodu.getUrunKod()).isEqualTo(DEFAULT_URUN_KOD);
    }

    @Test
    @Transactional
    public void createUrunKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = urunKoduRepository.findAll().size();

        // Create the UrunKodu with an existing ID
        urunKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrunKoduMockMvc.perform(post("/api/urun-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UrunKodu> urunKoduList = urunKoduRepository.findAll();
        assertThat(urunKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunKoduRepository.findAll().size();
        // set the field null
        urunKodu.setDeleted(null);

        // Create the UrunKodu, which fails.

        restUrunKoduMockMvc.perform(post("/api/urun-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunKodu)))
            .andExpect(status().isBadRequest());

        List<UrunKodu> urunKoduList = urunKoduRepository.findAll();
        assertThat(urunKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunKoduRepository.findAll().size();
        // set the field null
        urunKodu.setVersion(null);

        // Create the UrunKodu, which fails.

        restUrunKoduMockMvc.perform(post("/api/urun-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunKodu)))
            .andExpect(status().isBadRequest());

        List<UrunKodu> urunKoduList = urunKoduRepository.findAll();
        assertThat(urunKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrunKoduAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunKoduRepository.findAll().size();
        // set the field null
        urunKodu.setUrunKoduAdi(null);

        // Create the UrunKodu, which fails.

        restUrunKoduMockMvc.perform(post("/api/urun-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunKodu)))
            .andExpect(status().isBadRequest());

        List<UrunKodu> urunKoduList = urunKoduRepository.findAll();
        assertThat(urunKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrunKodIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunKoduRepository.findAll().size();
        // set the field null
        urunKodu.setUrunKod(null);

        // Create the UrunKodu, which fails.

        restUrunKoduMockMvc.perform(post("/api/urun-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunKodu)))
            .andExpect(status().isBadRequest());

        List<UrunKodu> urunKoduList = urunKoduRepository.findAll();
        assertThat(urunKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUrunKodus() throws Exception {
        // Initialize the database
        urunKoduRepository.saveAndFlush(urunKodu);

        // Get all the urunKoduList
        restUrunKoduMockMvc.perform(get("/api/urun-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urunKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].urunKoduAdi").value(hasItem(DEFAULT_URUN_KODU_ADI.toString())))
            .andExpect(jsonPath("$.[*].urunKod").value(hasItem(DEFAULT_URUN_KOD.toString())));
    }

    @Test
    @Transactional
    public void getUrunKodu() throws Exception {
        // Initialize the database
        urunKoduRepository.saveAndFlush(urunKodu);

        // Get the urunKodu
        restUrunKoduMockMvc.perform(get("/api/urun-kodus/{id}", urunKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(urunKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.urunKoduAdi").value(DEFAULT_URUN_KODU_ADI.toString()))
            .andExpect(jsonPath("$.urunKod").value(DEFAULT_URUN_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUrunKodu() throws Exception {
        // Get the urunKodu
        restUrunKoduMockMvc.perform(get("/api/urun-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUrunKodu() throws Exception {
        // Initialize the database
        urunKoduRepository.saveAndFlush(urunKodu);
        int databaseSizeBeforeUpdate = urunKoduRepository.findAll().size();

        // Update the urunKodu
        UrunKodu updatedUrunKodu = urunKoduRepository.findOne(urunKodu.getId());
        updatedUrunKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .urunKoduAdi(UPDATED_URUN_KODU_ADI)
            .urunKod(UPDATED_URUN_KOD);

        restUrunKoduMockMvc.perform(put("/api/urun-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUrunKodu)))
            .andExpect(status().isOk());

        // Validate the UrunKodu in the database
        List<UrunKodu> urunKoduList = urunKoduRepository.findAll();
        assertThat(urunKoduList).hasSize(databaseSizeBeforeUpdate);
        UrunKodu testUrunKodu = urunKoduList.get(urunKoduList.size() - 1);
        assertThat(testUrunKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testUrunKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUrunKodu.getUrunKoduAdi()).isEqualTo(UPDATED_URUN_KODU_ADI);
        assertThat(testUrunKodu.getUrunKod()).isEqualTo(UPDATED_URUN_KOD);
    }

    @Test
    @Transactional
    public void updateNonExistingUrunKodu() throws Exception {
        int databaseSizeBeforeUpdate = urunKoduRepository.findAll().size();

        // Create the UrunKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUrunKoduMockMvc.perform(put("/api/urun-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunKodu)))
            .andExpect(status().isCreated());

        // Validate the UrunKodu in the database
        List<UrunKodu> urunKoduList = urunKoduRepository.findAll();
        assertThat(urunKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUrunKodu() throws Exception {
        // Initialize the database
        urunKoduRepository.saveAndFlush(urunKodu);
        int databaseSizeBeforeDelete = urunKoduRepository.findAll().size();

        // Get the urunKodu
        restUrunKoduMockMvc.perform(delete("/api/urun-kodus/{id}", urunKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UrunKodu> urunKoduList = urunKoduRepository.findAll();
        assertThat(urunKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UrunKodu.class);
        UrunKodu urunKodu1 = new UrunKodu();
        urunKodu1.setId(1L);
        UrunKodu urunKodu2 = new UrunKodu();
        urunKodu2.setId(urunKodu1.getId());
        assertThat(urunKodu1).isEqualTo(urunKodu2);
        urunKodu2.setId(2L);
        assertThat(urunKodu1).isNotEqualTo(urunKodu2);
        urunKodu1.setId(null);
        assertThat(urunKodu1).isNotEqualTo(urunKodu2);
    }
}

package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.UrunGrubuKodu;
import tr.gov.sb.sygm.repository.UrunGrubuKoduRepository;
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
 * Test class for the UrunGrubuKoduResource REST controller.
 *
 * @see UrunGrubuKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class UrunGrubuKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_URUN_GRUBU_KODU_ADI = "AAAAAAAAAA";
    private static final String UPDATED_URUN_GRUBU_KODU_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_URUN_GRUBU_KOD = "AAAAAAAAAA";
    private static final String UPDATED_URUN_GRUBU_KOD = "BBBBBBBBBB";

    @Autowired
    private UrunGrubuKoduRepository urunGrubuKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUrunGrubuKoduMockMvc;

    private UrunGrubuKodu urunGrubuKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UrunGrubuKoduResource urunGrubuKoduResource = new UrunGrubuKoduResource(urunGrubuKoduRepository);
        this.restUrunGrubuKoduMockMvc = MockMvcBuilders.standaloneSetup(urunGrubuKoduResource)
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
    public static UrunGrubuKodu createEntity(EntityManager em) {
        UrunGrubuKodu urunGrubuKodu = new UrunGrubuKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .urunGrubuKoduAdi(DEFAULT_URUN_GRUBU_KODU_ADI)
            .urunGrubuKod(DEFAULT_URUN_GRUBU_KOD);
        return urunGrubuKodu;
    }

    @Before
    public void initTest() {
        urunGrubuKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createUrunGrubuKodu() throws Exception {
        int databaseSizeBeforeCreate = urunGrubuKoduRepository.findAll().size();

        // Create the UrunGrubuKodu
        restUrunGrubuKoduMockMvc.perform(post("/api/urun-grubu-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubuKodu)))
            .andExpect(status().isCreated());

        // Validate the UrunGrubuKodu in the database
        List<UrunGrubuKodu> urunGrubuKoduList = urunGrubuKoduRepository.findAll();
        assertThat(urunGrubuKoduList).hasSize(databaseSizeBeforeCreate + 1);
        UrunGrubuKodu testUrunGrubuKodu = urunGrubuKoduList.get(urunGrubuKoduList.size() - 1);
        assertThat(testUrunGrubuKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testUrunGrubuKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUrunGrubuKodu.getUrunGrubuKoduAdi()).isEqualTo(DEFAULT_URUN_GRUBU_KODU_ADI);
        assertThat(testUrunGrubuKodu.getUrunGrubuKod()).isEqualTo(DEFAULT_URUN_GRUBU_KOD);
    }

    @Test
    @Transactional
    public void createUrunGrubuKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = urunGrubuKoduRepository.findAll().size();

        // Create the UrunGrubuKodu with an existing ID
        urunGrubuKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrunGrubuKoduMockMvc.perform(post("/api/urun-grubu-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubuKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UrunGrubuKodu> urunGrubuKoduList = urunGrubuKoduRepository.findAll();
        assertThat(urunGrubuKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunGrubuKoduRepository.findAll().size();
        // set the field null
        urunGrubuKodu.setDeleted(null);

        // Create the UrunGrubuKodu, which fails.

        restUrunGrubuKoduMockMvc.perform(post("/api/urun-grubu-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubuKodu)))
            .andExpect(status().isBadRequest());

        List<UrunGrubuKodu> urunGrubuKoduList = urunGrubuKoduRepository.findAll();
        assertThat(urunGrubuKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunGrubuKoduRepository.findAll().size();
        // set the field null
        urunGrubuKodu.setVersion(null);

        // Create the UrunGrubuKodu, which fails.

        restUrunGrubuKoduMockMvc.perform(post("/api/urun-grubu-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubuKodu)))
            .andExpect(status().isBadRequest());

        List<UrunGrubuKodu> urunGrubuKoduList = urunGrubuKoduRepository.findAll();
        assertThat(urunGrubuKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrunGrubuKoduAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunGrubuKoduRepository.findAll().size();
        // set the field null
        urunGrubuKodu.setUrunGrubuKoduAdi(null);

        // Create the UrunGrubuKodu, which fails.

        restUrunGrubuKoduMockMvc.perform(post("/api/urun-grubu-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubuKodu)))
            .andExpect(status().isBadRequest());

        List<UrunGrubuKodu> urunGrubuKoduList = urunGrubuKoduRepository.findAll();
        assertThat(urunGrubuKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrunGrubuKodIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunGrubuKoduRepository.findAll().size();
        // set the field null
        urunGrubuKodu.setUrunGrubuKod(null);

        // Create the UrunGrubuKodu, which fails.

        restUrunGrubuKoduMockMvc.perform(post("/api/urun-grubu-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubuKodu)))
            .andExpect(status().isBadRequest());

        List<UrunGrubuKodu> urunGrubuKoduList = urunGrubuKoduRepository.findAll();
        assertThat(urunGrubuKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUrunGrubuKodus() throws Exception {
        // Initialize the database
        urunGrubuKoduRepository.saveAndFlush(urunGrubuKodu);

        // Get all the urunGrubuKoduList
        restUrunGrubuKoduMockMvc.perform(get("/api/urun-grubu-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urunGrubuKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].urunGrubuKoduAdi").value(hasItem(DEFAULT_URUN_GRUBU_KODU_ADI.toString())))
            .andExpect(jsonPath("$.[*].urunGrubuKod").value(hasItem(DEFAULT_URUN_GRUBU_KOD.toString())));
    }

    @Test
    @Transactional
    public void getUrunGrubuKodu() throws Exception {
        // Initialize the database
        urunGrubuKoduRepository.saveAndFlush(urunGrubuKodu);

        // Get the urunGrubuKodu
        restUrunGrubuKoduMockMvc.perform(get("/api/urun-grubu-kodus/{id}", urunGrubuKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(urunGrubuKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.urunGrubuKoduAdi").value(DEFAULT_URUN_GRUBU_KODU_ADI.toString()))
            .andExpect(jsonPath("$.urunGrubuKod").value(DEFAULT_URUN_GRUBU_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUrunGrubuKodu() throws Exception {
        // Get the urunGrubuKodu
        restUrunGrubuKoduMockMvc.perform(get("/api/urun-grubu-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUrunGrubuKodu() throws Exception {
        // Initialize the database
        urunGrubuKoduRepository.saveAndFlush(urunGrubuKodu);
        int databaseSizeBeforeUpdate = urunGrubuKoduRepository.findAll().size();

        // Update the urunGrubuKodu
        UrunGrubuKodu updatedUrunGrubuKodu = urunGrubuKoduRepository.findOne(urunGrubuKodu.getId());
        updatedUrunGrubuKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .urunGrubuKoduAdi(UPDATED_URUN_GRUBU_KODU_ADI)
            .urunGrubuKod(UPDATED_URUN_GRUBU_KOD);

        restUrunGrubuKoduMockMvc.perform(put("/api/urun-grubu-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUrunGrubuKodu)))
            .andExpect(status().isOk());

        // Validate the UrunGrubuKodu in the database
        List<UrunGrubuKodu> urunGrubuKoduList = urunGrubuKoduRepository.findAll();
        assertThat(urunGrubuKoduList).hasSize(databaseSizeBeforeUpdate);
        UrunGrubuKodu testUrunGrubuKodu = urunGrubuKoduList.get(urunGrubuKoduList.size() - 1);
        assertThat(testUrunGrubuKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testUrunGrubuKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUrunGrubuKodu.getUrunGrubuKoduAdi()).isEqualTo(UPDATED_URUN_GRUBU_KODU_ADI);
        assertThat(testUrunGrubuKodu.getUrunGrubuKod()).isEqualTo(UPDATED_URUN_GRUBU_KOD);
    }

    @Test
    @Transactional
    public void updateNonExistingUrunGrubuKodu() throws Exception {
        int databaseSizeBeforeUpdate = urunGrubuKoduRepository.findAll().size();

        // Create the UrunGrubuKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUrunGrubuKoduMockMvc.perform(put("/api/urun-grubu-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubuKodu)))
            .andExpect(status().isCreated());

        // Validate the UrunGrubuKodu in the database
        List<UrunGrubuKodu> urunGrubuKoduList = urunGrubuKoduRepository.findAll();
        assertThat(urunGrubuKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUrunGrubuKodu() throws Exception {
        // Initialize the database
        urunGrubuKoduRepository.saveAndFlush(urunGrubuKodu);
        int databaseSizeBeforeDelete = urunGrubuKoduRepository.findAll().size();

        // Get the urunGrubuKodu
        restUrunGrubuKoduMockMvc.perform(delete("/api/urun-grubu-kodus/{id}", urunGrubuKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UrunGrubuKodu> urunGrubuKoduList = urunGrubuKoduRepository.findAll();
        assertThat(urunGrubuKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UrunGrubuKodu.class);
        UrunGrubuKodu urunGrubuKodu1 = new UrunGrubuKodu();
        urunGrubuKodu1.setId(1L);
        UrunGrubuKodu urunGrubuKodu2 = new UrunGrubuKodu();
        urunGrubuKodu2.setId(urunGrubuKodu1.getId());
        assertThat(urunGrubuKodu1).isEqualTo(urunGrubuKodu2);
        urunGrubuKodu2.setId(2L);
        assertThat(urunGrubuKodu1).isNotEqualTo(urunGrubuKodu2);
        urunGrubuKodu1.setId(null);
        assertThat(urunGrubuKodu1).isNotEqualTo(urunGrubuKodu2);
    }
}

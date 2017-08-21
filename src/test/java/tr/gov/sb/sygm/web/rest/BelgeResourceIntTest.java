package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.Belge;
import tr.gov.sb.sygm.repository.BelgeRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BelgeResource REST controller.
 *
 * @see BelgeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class BelgeResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_BELGE_ADI = "AAAAAAAAAA";
    private static final String UPDATED_BELGE_ADI = "BBBBBBBBBB";

    private static final Long DEFAULT_BELGE_BOYUTU = 1L;
    private static final Long UPDATED_BELGE_BOYUTU = 2L;

    private static final LocalDate DEFAULT_OLUSTURMA_ZAMANI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OLUSTURMA_ZAMANI = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_SILINEBILIR_MI = false;
    private static final Boolean UPDATED_SILINEBILIR_MI = true;

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    @Autowired
    private BelgeRepository belgeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBelgeMockMvc;

    private Belge belge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BelgeResource belgeResource = new BelgeResource(belgeRepository);
        this.restBelgeMockMvc = MockMvcBuilders.standaloneSetup(belgeResource)
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
    public static Belge createEntity(EntityManager em) {
        Belge belge = new Belge()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .belgeAdi(DEFAULT_BELGE_ADI)
            .belgeBoyutu(DEFAULT_BELGE_BOYUTU)
            .olusturmaZamani(DEFAULT_OLUSTURMA_ZAMANI)
            .silinebilirMi(DEFAULT_SILINEBILIR_MI)
            .aciklama(DEFAULT_ACIKLAMA)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE);
        return belge;
    }

    @Before
    public void initTest() {
        belge = createEntity(em);
    }

    @Test
    @Transactional
    public void createBelge() throws Exception {
        int databaseSizeBeforeCreate = belgeRepository.findAll().size();

        // Create the Belge
        restBelgeMockMvc.perform(post("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isCreated());

        // Validate the Belge in the database
        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeCreate + 1);
        Belge testBelge = belgeList.get(belgeList.size() - 1);
        assertThat(testBelge.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBelge.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testBelge.getBelgeAdi()).isEqualTo(DEFAULT_BELGE_ADI);
        assertThat(testBelge.getBelgeBoyutu()).isEqualTo(DEFAULT_BELGE_BOYUTU);
        assertThat(testBelge.getOlusturmaZamani()).isEqualTo(DEFAULT_OLUSTURMA_ZAMANI);
        assertThat(testBelge.isSilinebilirMi()).isEqualTo(DEFAULT_SILINEBILIR_MI);
        assertThat(testBelge.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testBelge.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testBelge.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBelgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = belgeRepository.findAll().size();

        // Create the Belge with an existing ID
        belge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBelgeMockMvc.perform(post("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeRepository.findAll().size();
        // set the field null
        belge.setDeleted(null);

        // Create the Belge, which fails.

        restBelgeMockMvc.perform(post("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isBadRequest());

        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeRepository.findAll().size();
        // set the field null
        belge.setVersion(null);

        // Create the Belge, which fails.

        restBelgeMockMvc.perform(post("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isBadRequest());

        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBelgeAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeRepository.findAll().size();
        // set the field null
        belge.setBelgeAdi(null);

        // Create the Belge, which fails.

        restBelgeMockMvc.perform(post("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isBadRequest());

        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBelgeBoyutuIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeRepository.findAll().size();
        // set the field null
        belge.setBelgeBoyutu(null);

        // Create the Belge, which fails.

        restBelgeMockMvc.perform(post("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isBadRequest());

        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOlusturmaZamaniIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeRepository.findAll().size();
        // set the field null
        belge.setOlusturmaZamani(null);

        // Create the Belge, which fails.

        restBelgeMockMvc.perform(post("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isBadRequest());

        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSilinebilirMiIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeRepository.findAll().size();
        // set the field null
        belge.setSilinebilirMi(null);

        // Create the Belge, which fails.

        restBelgeMockMvc.perform(post("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isBadRequest());

        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeRepository.findAll().size();
        // set the field null
        belge.setContent(null);

        // Create the Belge, which fails.

        restBelgeMockMvc.perform(post("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isBadRequest());

        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBelges() throws Exception {
        // Initialize the database
        belgeRepository.saveAndFlush(belge);

        // Get all the belgeList
        restBelgeMockMvc.perform(get("/api/belges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(belge.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].belgeAdi").value(hasItem(DEFAULT_BELGE_ADI.toString())))
            .andExpect(jsonPath("$.[*].belgeBoyutu").value(hasItem(DEFAULT_BELGE_BOYUTU.intValue())))
            .andExpect(jsonPath("$.[*].olusturmaZamani").value(hasItem(DEFAULT_OLUSTURMA_ZAMANI.toString())))
            .andExpect(jsonPath("$.[*].silinebilirMi").value(hasItem(DEFAULT_SILINEBILIR_MI.booleanValue())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))));
    }

    @Test
    @Transactional
    public void getBelge() throws Exception {
        // Initialize the database
        belgeRepository.saveAndFlush(belge);

        // Get the belge
        restBelgeMockMvc.perform(get("/api/belges/{id}", belge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(belge.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.belgeAdi").value(DEFAULT_BELGE_ADI.toString()))
            .andExpect(jsonPath("$.belgeBoyutu").value(DEFAULT_BELGE_BOYUTU.intValue()))
            .andExpect(jsonPath("$.olusturmaZamani").value(DEFAULT_OLUSTURMA_ZAMANI.toString()))
            .andExpect(jsonPath("$.silinebilirMi").value(DEFAULT_SILINEBILIR_MI.booleanValue()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getNonExistingBelge() throws Exception {
        // Get the belge
        restBelgeMockMvc.perform(get("/api/belges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBelge() throws Exception {
        // Initialize the database
        belgeRepository.saveAndFlush(belge);
        int databaseSizeBeforeUpdate = belgeRepository.findAll().size();

        // Update the belge
        Belge updatedBelge = belgeRepository.findOne(belge.getId());
        updatedBelge
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .belgeAdi(UPDATED_BELGE_ADI)
            .belgeBoyutu(UPDATED_BELGE_BOYUTU)
            .olusturmaZamani(UPDATED_OLUSTURMA_ZAMANI)
            .silinebilirMi(UPDATED_SILINEBILIR_MI)
            .aciklama(UPDATED_ACIKLAMA)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE);

        restBelgeMockMvc.perform(put("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBelge)))
            .andExpect(status().isOk());

        // Validate the Belge in the database
        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeUpdate);
        Belge testBelge = belgeList.get(belgeList.size() - 1);
        assertThat(testBelge.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBelge.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testBelge.getBelgeAdi()).isEqualTo(UPDATED_BELGE_ADI);
        assertThat(testBelge.getBelgeBoyutu()).isEqualTo(UPDATED_BELGE_BOYUTU);
        assertThat(testBelge.getOlusturmaZamani()).isEqualTo(UPDATED_OLUSTURMA_ZAMANI);
        assertThat(testBelge.isSilinebilirMi()).isEqualTo(UPDATED_SILINEBILIR_MI);
        assertThat(testBelge.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testBelge.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testBelge.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBelge() throws Exception {
        int databaseSizeBeforeUpdate = belgeRepository.findAll().size();

        // Create the Belge

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBelgeMockMvc.perform(put("/api/belges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belge)))
            .andExpect(status().isCreated());

        // Validate the Belge in the database
        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBelge() throws Exception {
        // Initialize the database
        belgeRepository.saveAndFlush(belge);
        int databaseSizeBeforeDelete = belgeRepository.findAll().size();

        // Get the belge
        restBelgeMockMvc.perform(delete("/api/belges/{id}", belge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Belge> belgeList = belgeRepository.findAll();
        assertThat(belgeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Belge.class);
        Belge belge1 = new Belge();
        belge1.setId(1L);
        Belge belge2 = new Belge();
        belge2.setId(belge1.getId());
        assertThat(belge1).isEqualTo(belge2);
        belge2.setId(2L);
        assertThat(belge1).isNotEqualTo(belge2);
        belge1.setId(null);
        assertThat(belge1).isNotEqualTo(belge2);
    }
}

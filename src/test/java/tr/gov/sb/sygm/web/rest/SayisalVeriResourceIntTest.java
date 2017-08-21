package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.SayisalVeri;
import tr.gov.sb.sygm.repository.SayisalVeriRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SayisalVeriResource REST controller.
 *
 * @see SayisalVeriResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class SayisalVeriResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final LocalDate DEFAULT_YIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YIL = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ITHALAT_MIKTARI = 1L;
    private static final Long UPDATED_ITHALAT_MIKTARI = 2L;

    private static final Long DEFAULT_IHTIYAC_MIKTARI = 1L;
    private static final Long UPDATED_IHTIYAC_MIKTARI = 2L;

    private static final BigDecimal DEFAULT_BIRIM_FIYAT_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_BIRIM_FIYAT_TUTAR = new BigDecimal(2);

    private static final String DEFAULT_BIRIM_FIYAT_PB = "AAAAAAAAAA";
    private static final String UPDATED_BIRIM_FIYAT_PB = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_DUNYA_PAZARI_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_DUNYA_PAZARI_TUTAR = new BigDecimal(2);

    private static final String DEFAULT_DUNYA_PAZARI_PB = "AAAAAAAAAA";
    private static final String UPDATED_DUNYA_PAZARI_PB = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TURKIYE_PAZARI_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_TURKIYE_PAZARI_TUTAR = new BigDecimal(2);

    private static final String DEFAULT_TURKIYE_PAZARI_PB = "AAAAAAAAAA";
    private static final String UPDATED_TURKIYE_PAZARI_PB = "BBBBBBBBBB";

    @Autowired
    private SayisalVeriRepository sayisalVeriRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSayisalVeriMockMvc;

    private SayisalVeri sayisalVeri;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SayisalVeriResource sayisalVeriResource = new SayisalVeriResource(sayisalVeriRepository);
        this.restSayisalVeriMockMvc = MockMvcBuilders.standaloneSetup(sayisalVeriResource)
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
    public static SayisalVeri createEntity(EntityManager em) {
        SayisalVeri sayisalVeri = new SayisalVeri()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .yil(DEFAULT_YIL)
            .ithalatMiktari(DEFAULT_ITHALAT_MIKTARI)
            .ihtiyacMiktari(DEFAULT_IHTIYAC_MIKTARI)
            .birimFiyatTutar(DEFAULT_BIRIM_FIYAT_TUTAR)
            .birimFiyatPB(DEFAULT_BIRIM_FIYAT_PB)
            .dunyaPazariTutar(DEFAULT_DUNYA_PAZARI_TUTAR)
            .dunyaPazariPB(DEFAULT_DUNYA_PAZARI_PB)
            .turkiyePazariTutar(DEFAULT_TURKIYE_PAZARI_TUTAR)
            .turkiyePazariPB(DEFAULT_TURKIYE_PAZARI_PB);
        return sayisalVeri;
    }

    @Before
    public void initTest() {
        sayisalVeri = createEntity(em);
    }

    @Test
    @Transactional
    public void createSayisalVeri() throws Exception {
        int databaseSizeBeforeCreate = sayisalVeriRepository.findAll().size();

        // Create the SayisalVeri
        restSayisalVeriMockMvc.perform(post("/api/sayisal-veris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sayisalVeri)))
            .andExpect(status().isCreated());

        // Validate the SayisalVeri in the database
        List<SayisalVeri> sayisalVeriList = sayisalVeriRepository.findAll();
        assertThat(sayisalVeriList).hasSize(databaseSizeBeforeCreate + 1);
        SayisalVeri testSayisalVeri = sayisalVeriList.get(sayisalVeriList.size() - 1);
        assertThat(testSayisalVeri.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testSayisalVeri.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSayisalVeri.getYil()).isEqualTo(DEFAULT_YIL);
        assertThat(testSayisalVeri.getIthalatMiktari()).isEqualTo(DEFAULT_ITHALAT_MIKTARI);
        assertThat(testSayisalVeri.getIhtiyacMiktari()).isEqualTo(DEFAULT_IHTIYAC_MIKTARI);
        assertThat(testSayisalVeri.getBirimFiyatTutar()).isEqualTo(DEFAULT_BIRIM_FIYAT_TUTAR);
        assertThat(testSayisalVeri.getBirimFiyatPB()).isEqualTo(DEFAULT_BIRIM_FIYAT_PB);
        assertThat(testSayisalVeri.getDunyaPazariTutar()).isEqualTo(DEFAULT_DUNYA_PAZARI_TUTAR);
        assertThat(testSayisalVeri.getDunyaPazariPB()).isEqualTo(DEFAULT_DUNYA_PAZARI_PB);
        assertThat(testSayisalVeri.getTurkiyePazariTutar()).isEqualTo(DEFAULT_TURKIYE_PAZARI_TUTAR);
        assertThat(testSayisalVeri.getTurkiyePazariPB()).isEqualTo(DEFAULT_TURKIYE_PAZARI_PB);
    }

    @Test
    @Transactional
    public void createSayisalVeriWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sayisalVeriRepository.findAll().size();

        // Create the SayisalVeri with an existing ID
        sayisalVeri.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSayisalVeriMockMvc.perform(post("/api/sayisal-veris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sayisalVeri)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SayisalVeri> sayisalVeriList = sayisalVeriRepository.findAll();
        assertThat(sayisalVeriList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = sayisalVeriRepository.findAll().size();
        // set the field null
        sayisalVeri.setDeleted(null);

        // Create the SayisalVeri, which fails.

        restSayisalVeriMockMvc.perform(post("/api/sayisal-veris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sayisalVeri)))
            .andExpect(status().isBadRequest());

        List<SayisalVeri> sayisalVeriList = sayisalVeriRepository.findAll();
        assertThat(sayisalVeriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sayisalVeriRepository.findAll().size();
        // set the field null
        sayisalVeri.setVersion(null);

        // Create the SayisalVeri, which fails.

        restSayisalVeriMockMvc.perform(post("/api/sayisal-veris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sayisalVeri)))
            .andExpect(status().isBadRequest());

        List<SayisalVeri> sayisalVeriList = sayisalVeriRepository.findAll();
        assertThat(sayisalVeriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYilIsRequired() throws Exception {
        int databaseSizeBeforeTest = sayisalVeriRepository.findAll().size();
        // set the field null
        sayisalVeri.setYil(null);

        // Create the SayisalVeri, which fails.

        restSayisalVeriMockMvc.perform(post("/api/sayisal-veris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sayisalVeri)))
            .andExpect(status().isBadRequest());

        List<SayisalVeri> sayisalVeriList = sayisalVeriRepository.findAll();
        assertThat(sayisalVeriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSayisalVeris() throws Exception {
        // Initialize the database
        sayisalVeriRepository.saveAndFlush(sayisalVeri);

        // Get all the sayisalVeriList
        restSayisalVeriMockMvc.perform(get("/api/sayisal-veris?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sayisalVeri.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].yil").value(hasItem(DEFAULT_YIL.toString())))
            .andExpect(jsonPath("$.[*].ithalatMiktari").value(hasItem(DEFAULT_ITHALAT_MIKTARI.intValue())))
            .andExpect(jsonPath("$.[*].ihtiyacMiktari").value(hasItem(DEFAULT_IHTIYAC_MIKTARI.intValue())))
            .andExpect(jsonPath("$.[*].birimFiyatTutar").value(hasItem(DEFAULT_BIRIM_FIYAT_TUTAR.intValue())))
            .andExpect(jsonPath("$.[*].birimFiyatPB").value(hasItem(DEFAULT_BIRIM_FIYAT_PB.toString())))
            .andExpect(jsonPath("$.[*].dunyaPazariTutar").value(hasItem(DEFAULT_DUNYA_PAZARI_TUTAR.intValue())))
            .andExpect(jsonPath("$.[*].dunyaPazariPB").value(hasItem(DEFAULT_DUNYA_PAZARI_PB.toString())))
            .andExpect(jsonPath("$.[*].turkiyePazariTutar").value(hasItem(DEFAULT_TURKIYE_PAZARI_TUTAR.intValue())))
            .andExpect(jsonPath("$.[*].turkiyePazariPB").value(hasItem(DEFAULT_TURKIYE_PAZARI_PB.toString())));
    }

    @Test
    @Transactional
    public void getSayisalVeri() throws Exception {
        // Initialize the database
        sayisalVeriRepository.saveAndFlush(sayisalVeri);

        // Get the sayisalVeri
        restSayisalVeriMockMvc.perform(get("/api/sayisal-veris/{id}", sayisalVeri.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sayisalVeri.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.yil").value(DEFAULT_YIL.toString()))
            .andExpect(jsonPath("$.ithalatMiktari").value(DEFAULT_ITHALAT_MIKTARI.intValue()))
            .andExpect(jsonPath("$.ihtiyacMiktari").value(DEFAULT_IHTIYAC_MIKTARI.intValue()))
            .andExpect(jsonPath("$.birimFiyatTutar").value(DEFAULT_BIRIM_FIYAT_TUTAR.intValue()))
            .andExpect(jsonPath("$.birimFiyatPB").value(DEFAULT_BIRIM_FIYAT_PB.toString()))
            .andExpect(jsonPath("$.dunyaPazariTutar").value(DEFAULT_DUNYA_PAZARI_TUTAR.intValue()))
            .andExpect(jsonPath("$.dunyaPazariPB").value(DEFAULT_DUNYA_PAZARI_PB.toString()))
            .andExpect(jsonPath("$.turkiyePazariTutar").value(DEFAULT_TURKIYE_PAZARI_TUTAR.intValue()))
            .andExpect(jsonPath("$.turkiyePazariPB").value(DEFAULT_TURKIYE_PAZARI_PB.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSayisalVeri() throws Exception {
        // Get the sayisalVeri
        restSayisalVeriMockMvc.perform(get("/api/sayisal-veris/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSayisalVeri() throws Exception {
        // Initialize the database
        sayisalVeriRepository.saveAndFlush(sayisalVeri);
        int databaseSizeBeforeUpdate = sayisalVeriRepository.findAll().size();

        // Update the sayisalVeri
        SayisalVeri updatedSayisalVeri = sayisalVeriRepository.findOne(sayisalVeri.getId());
        updatedSayisalVeri
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .yil(UPDATED_YIL)
            .ithalatMiktari(UPDATED_ITHALAT_MIKTARI)
            .ihtiyacMiktari(UPDATED_IHTIYAC_MIKTARI)
            .birimFiyatTutar(UPDATED_BIRIM_FIYAT_TUTAR)
            .birimFiyatPB(UPDATED_BIRIM_FIYAT_PB)
            .dunyaPazariTutar(UPDATED_DUNYA_PAZARI_TUTAR)
            .dunyaPazariPB(UPDATED_DUNYA_PAZARI_PB)
            .turkiyePazariTutar(UPDATED_TURKIYE_PAZARI_TUTAR)
            .turkiyePazariPB(UPDATED_TURKIYE_PAZARI_PB);

        restSayisalVeriMockMvc.perform(put("/api/sayisal-veris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSayisalVeri)))
            .andExpect(status().isOk());

        // Validate the SayisalVeri in the database
        List<SayisalVeri> sayisalVeriList = sayisalVeriRepository.findAll();
        assertThat(sayisalVeriList).hasSize(databaseSizeBeforeUpdate);
        SayisalVeri testSayisalVeri = sayisalVeriList.get(sayisalVeriList.size() - 1);
        assertThat(testSayisalVeri.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testSayisalVeri.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSayisalVeri.getYil()).isEqualTo(UPDATED_YIL);
        assertThat(testSayisalVeri.getIthalatMiktari()).isEqualTo(UPDATED_ITHALAT_MIKTARI);
        assertThat(testSayisalVeri.getIhtiyacMiktari()).isEqualTo(UPDATED_IHTIYAC_MIKTARI);
        assertThat(testSayisalVeri.getBirimFiyatTutar()).isEqualTo(UPDATED_BIRIM_FIYAT_TUTAR);
        assertThat(testSayisalVeri.getBirimFiyatPB()).isEqualTo(UPDATED_BIRIM_FIYAT_PB);
        assertThat(testSayisalVeri.getDunyaPazariTutar()).isEqualTo(UPDATED_DUNYA_PAZARI_TUTAR);
        assertThat(testSayisalVeri.getDunyaPazariPB()).isEqualTo(UPDATED_DUNYA_PAZARI_PB);
        assertThat(testSayisalVeri.getTurkiyePazariTutar()).isEqualTo(UPDATED_TURKIYE_PAZARI_TUTAR);
        assertThat(testSayisalVeri.getTurkiyePazariPB()).isEqualTo(UPDATED_TURKIYE_PAZARI_PB);
    }

    @Test
    @Transactional
    public void updateNonExistingSayisalVeri() throws Exception {
        int databaseSizeBeforeUpdate = sayisalVeriRepository.findAll().size();

        // Create the SayisalVeri

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSayisalVeriMockMvc.perform(put("/api/sayisal-veris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sayisalVeri)))
            .andExpect(status().isCreated());

        // Validate the SayisalVeri in the database
        List<SayisalVeri> sayisalVeriList = sayisalVeriRepository.findAll();
        assertThat(sayisalVeriList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSayisalVeri() throws Exception {
        // Initialize the database
        sayisalVeriRepository.saveAndFlush(sayisalVeri);
        int databaseSizeBeforeDelete = sayisalVeriRepository.findAll().size();

        // Get the sayisalVeri
        restSayisalVeriMockMvc.perform(delete("/api/sayisal-veris/{id}", sayisalVeri.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SayisalVeri> sayisalVeriList = sayisalVeriRepository.findAll();
        assertThat(sayisalVeriList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SayisalVeri.class);
        SayisalVeri sayisalVeri1 = new SayisalVeri();
        sayisalVeri1.setId(1L);
        SayisalVeri sayisalVeri2 = new SayisalVeri();
        sayisalVeri2.setId(sayisalVeri1.getId());
        assertThat(sayisalVeri1).isEqualTo(sayisalVeri2);
        sayisalVeri2.setId(2L);
        assertThat(sayisalVeri1).isNotEqualTo(sayisalVeri2);
        sayisalVeri1.setId(null);
        assertThat(sayisalVeri1).isNotEqualTo(sayisalVeri2);
    }
}

package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.GenelFirmaBilgileri;
import tr.gov.sb.sygm.repository.GenelFirmaBilgileriRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GenelFirmaBilgileriResource REST controller.
 *
 * @see GenelFirmaBilgileriResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class GenelFirmaBilgileriResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_FIRMA_UNVAN = "AAAAAAAAAA";
    private static final String UPDATED_FIRMA_UNVAN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_KURULUS_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_KURULUS_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_SAGLIK_SEKTORUNDE_MI = false;
    private static final Boolean UPDATED_SAGLIK_SEKTORUNDE_MI = true;

    private static final String DEFAULT_SEKTOR_BILGISI = "AAAAAAAAAA";
    private static final String UPDATED_SEKTOR_BILGISI = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ARGE_BIRIMI_VAR_MI = false;
    private static final Boolean UPDATED_ARGE_BIRIMI_VAR_MI = true;

    private static final String DEFAULT_TICARET_SICIL_NO = "AAAAAAAAAA";
    private static final String UPDATED_TICARET_SICIL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_VERGI_NO = "AAAAAAAAAA";
    private static final String UPDATED_VERGI_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ACIK_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ACIK_ADRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_ADRESI = "AAAAAAAAAA";
    private static final String UPDATED_WEB_ADRESI = "BBBBBBBBBB";

    @Autowired
    private GenelFirmaBilgileriRepository genelFirmaBilgileriRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGenelFirmaBilgileriMockMvc;

    private GenelFirmaBilgileri genelFirmaBilgileri;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GenelFirmaBilgileriResource genelFirmaBilgileriResource = new GenelFirmaBilgileriResource(genelFirmaBilgileriRepository);
        this.restGenelFirmaBilgileriMockMvc = MockMvcBuilders.standaloneSetup(genelFirmaBilgileriResource)
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
    public static GenelFirmaBilgileri createEntity(EntityManager em) {
        GenelFirmaBilgileri genelFirmaBilgileri = new GenelFirmaBilgileri()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .firmaUnvan(DEFAULT_FIRMA_UNVAN)
            .kurulusTarihi(DEFAULT_KURULUS_TARIHI)
            .saglikSektorundeMi(DEFAULT_SAGLIK_SEKTORUNDE_MI)
            .sektorBilgisi(DEFAULT_SEKTOR_BILGISI)
            .argeBirimiVarMi(DEFAULT_ARGE_BIRIMI_VAR_MI)
            .ticaretSicilNo(DEFAULT_TICARET_SICIL_NO)
            .vergiNo(DEFAULT_VERGI_NO)
            .acikAdress(DEFAULT_ACIK_ADRESS)
            .telefon(DEFAULT_TELEFON)
            .telefon2(DEFAULT_TELEFON_2)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .webAdresi(DEFAULT_WEB_ADRESI);
        return genelFirmaBilgileri;
    }

    @Before
    public void initTest() {
        genelFirmaBilgileri = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenelFirmaBilgileri() throws Exception {
        int databaseSizeBeforeCreate = genelFirmaBilgileriRepository.findAll().size();

        // Create the GenelFirmaBilgileri
        restGenelFirmaBilgileriMockMvc.perform(post("/api/genel-firma-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genelFirmaBilgileri)))
            .andExpect(status().isCreated());

        // Validate the GenelFirmaBilgileri in the database
        List<GenelFirmaBilgileri> genelFirmaBilgileriList = genelFirmaBilgileriRepository.findAll();
        assertThat(genelFirmaBilgileriList).hasSize(databaseSizeBeforeCreate + 1);
        GenelFirmaBilgileri testGenelFirmaBilgileri = genelFirmaBilgileriList.get(genelFirmaBilgileriList.size() - 1);
        assertThat(testGenelFirmaBilgileri.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testGenelFirmaBilgileri.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testGenelFirmaBilgileri.getFirmaUnvan()).isEqualTo(DEFAULT_FIRMA_UNVAN);
        assertThat(testGenelFirmaBilgileri.getKurulusTarihi()).isEqualTo(DEFAULT_KURULUS_TARIHI);
        assertThat(testGenelFirmaBilgileri.isSaglikSektorundeMi()).isEqualTo(DEFAULT_SAGLIK_SEKTORUNDE_MI);
        assertThat(testGenelFirmaBilgileri.getSektorBilgisi()).isEqualTo(DEFAULT_SEKTOR_BILGISI);
        assertThat(testGenelFirmaBilgileri.isArgeBirimiVarMi()).isEqualTo(DEFAULT_ARGE_BIRIMI_VAR_MI);
        assertThat(testGenelFirmaBilgileri.getTicaretSicilNo()).isEqualTo(DEFAULT_TICARET_SICIL_NO);
        assertThat(testGenelFirmaBilgileri.getVergiNo()).isEqualTo(DEFAULT_VERGI_NO);
        assertThat(testGenelFirmaBilgileri.getAcikAdress()).isEqualTo(DEFAULT_ACIK_ADRESS);
        assertThat(testGenelFirmaBilgileri.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testGenelFirmaBilgileri.getTelefon2()).isEqualTo(DEFAULT_TELEFON_2);
        assertThat(testGenelFirmaBilgileri.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testGenelFirmaBilgileri.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGenelFirmaBilgileri.getWebAdresi()).isEqualTo(DEFAULT_WEB_ADRESI);
    }

    @Test
    @Transactional
    public void createGenelFirmaBilgileriWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genelFirmaBilgileriRepository.findAll().size();

        // Create the GenelFirmaBilgileri with an existing ID
        genelFirmaBilgileri.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenelFirmaBilgileriMockMvc.perform(post("/api/genel-firma-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genelFirmaBilgileri)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GenelFirmaBilgileri> genelFirmaBilgileriList = genelFirmaBilgileriRepository.findAll();
        assertThat(genelFirmaBilgileriList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = genelFirmaBilgileriRepository.findAll().size();
        // set the field null
        genelFirmaBilgileri.setDeleted(null);

        // Create the GenelFirmaBilgileri, which fails.

        restGenelFirmaBilgileriMockMvc.perform(post("/api/genel-firma-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genelFirmaBilgileri)))
            .andExpect(status().isBadRequest());

        List<GenelFirmaBilgileri> genelFirmaBilgileriList = genelFirmaBilgileriRepository.findAll();
        assertThat(genelFirmaBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = genelFirmaBilgileriRepository.findAll().size();
        // set the field null
        genelFirmaBilgileri.setVersion(null);

        // Create the GenelFirmaBilgileri, which fails.

        restGenelFirmaBilgileriMockMvc.perform(post("/api/genel-firma-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genelFirmaBilgileri)))
            .andExpect(status().isBadRequest());

        List<GenelFirmaBilgileri> genelFirmaBilgileriList = genelFirmaBilgileriRepository.findAll();
        assertThat(genelFirmaBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirmaUnvanIsRequired() throws Exception {
        int databaseSizeBeforeTest = genelFirmaBilgileriRepository.findAll().size();
        // set the field null
        genelFirmaBilgileri.setFirmaUnvan(null);

        // Create the GenelFirmaBilgileri, which fails.

        restGenelFirmaBilgileriMockMvc.perform(post("/api/genel-firma-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genelFirmaBilgileri)))
            .andExpect(status().isBadRequest());

        List<GenelFirmaBilgileri> genelFirmaBilgileriList = genelFirmaBilgileriRepository.findAll();
        assertThat(genelFirmaBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaglikSektorundeMiIsRequired() throws Exception {
        int databaseSizeBeforeTest = genelFirmaBilgileriRepository.findAll().size();
        // set the field null
        genelFirmaBilgileri.setSaglikSektorundeMi(null);

        // Create the GenelFirmaBilgileri, which fails.

        restGenelFirmaBilgileriMockMvc.perform(post("/api/genel-firma-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genelFirmaBilgileri)))
            .andExpect(status().isBadRequest());

        List<GenelFirmaBilgileri> genelFirmaBilgileriList = genelFirmaBilgileriRepository.findAll();
        assertThat(genelFirmaBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenelFirmaBilgileris() throws Exception {
        // Initialize the database
        genelFirmaBilgileriRepository.saveAndFlush(genelFirmaBilgileri);

        // Get all the genelFirmaBilgileriList
        restGenelFirmaBilgileriMockMvc.perform(get("/api/genel-firma-bilgileris?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genelFirmaBilgileri.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].firmaUnvan").value(hasItem(DEFAULT_FIRMA_UNVAN.toString())))
            .andExpect(jsonPath("$.[*].kurulusTarihi").value(hasItem(DEFAULT_KURULUS_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].saglikSektorundeMi").value(hasItem(DEFAULT_SAGLIK_SEKTORUNDE_MI.booleanValue())))
            .andExpect(jsonPath("$.[*].sektorBilgisi").value(hasItem(DEFAULT_SEKTOR_BILGISI.toString())))
            .andExpect(jsonPath("$.[*].argeBirimiVarMi").value(hasItem(DEFAULT_ARGE_BIRIMI_VAR_MI.booleanValue())))
            .andExpect(jsonPath("$.[*].ticaretSicilNo").value(hasItem(DEFAULT_TICARET_SICIL_NO.toString())))
            .andExpect(jsonPath("$.[*].vergiNo").value(hasItem(DEFAULT_VERGI_NO.toString())))
            .andExpect(jsonPath("$.[*].acikAdress").value(hasItem(DEFAULT_ACIK_ADRESS.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].telefon2").value(hasItem(DEFAULT_TELEFON_2.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].webAdresi").value(hasItem(DEFAULT_WEB_ADRESI.toString())));
    }

    @Test
    @Transactional
    public void getGenelFirmaBilgileri() throws Exception {
        // Initialize the database
        genelFirmaBilgileriRepository.saveAndFlush(genelFirmaBilgileri);

        // Get the genelFirmaBilgileri
        restGenelFirmaBilgileriMockMvc.perform(get("/api/genel-firma-bilgileris/{id}", genelFirmaBilgileri.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(genelFirmaBilgileri.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.firmaUnvan").value(DEFAULT_FIRMA_UNVAN.toString()))
            .andExpect(jsonPath("$.kurulusTarihi").value(DEFAULT_KURULUS_TARIHI.toString()))
            .andExpect(jsonPath("$.saglikSektorundeMi").value(DEFAULT_SAGLIK_SEKTORUNDE_MI.booleanValue()))
            .andExpect(jsonPath("$.sektorBilgisi").value(DEFAULT_SEKTOR_BILGISI.toString()))
            .andExpect(jsonPath("$.argeBirimiVarMi").value(DEFAULT_ARGE_BIRIMI_VAR_MI.booleanValue()))
            .andExpect(jsonPath("$.ticaretSicilNo").value(DEFAULT_TICARET_SICIL_NO.toString()))
            .andExpect(jsonPath("$.vergiNo").value(DEFAULT_VERGI_NO.toString()))
            .andExpect(jsonPath("$.acikAdress").value(DEFAULT_ACIK_ADRESS.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.telefon2").value(DEFAULT_TELEFON_2.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.webAdresi").value(DEFAULT_WEB_ADRESI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGenelFirmaBilgileri() throws Exception {
        // Get the genelFirmaBilgileri
        restGenelFirmaBilgileriMockMvc.perform(get("/api/genel-firma-bilgileris/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenelFirmaBilgileri() throws Exception {
        // Initialize the database
        genelFirmaBilgileriRepository.saveAndFlush(genelFirmaBilgileri);
        int databaseSizeBeforeUpdate = genelFirmaBilgileriRepository.findAll().size();

        // Update the genelFirmaBilgileri
        GenelFirmaBilgileri updatedGenelFirmaBilgileri = genelFirmaBilgileriRepository.findOne(genelFirmaBilgileri.getId());
        updatedGenelFirmaBilgileri
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .firmaUnvan(UPDATED_FIRMA_UNVAN)
            .kurulusTarihi(UPDATED_KURULUS_TARIHI)
            .saglikSektorundeMi(UPDATED_SAGLIK_SEKTORUNDE_MI)
            .sektorBilgisi(UPDATED_SEKTOR_BILGISI)
            .argeBirimiVarMi(UPDATED_ARGE_BIRIMI_VAR_MI)
            .ticaretSicilNo(UPDATED_TICARET_SICIL_NO)
            .vergiNo(UPDATED_VERGI_NO)
            .acikAdress(UPDATED_ACIK_ADRESS)
            .telefon(UPDATED_TELEFON)
            .telefon2(UPDATED_TELEFON_2)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .webAdresi(UPDATED_WEB_ADRESI);

        restGenelFirmaBilgileriMockMvc.perform(put("/api/genel-firma-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGenelFirmaBilgileri)))
            .andExpect(status().isOk());

        // Validate the GenelFirmaBilgileri in the database
        List<GenelFirmaBilgileri> genelFirmaBilgileriList = genelFirmaBilgileriRepository.findAll();
        assertThat(genelFirmaBilgileriList).hasSize(databaseSizeBeforeUpdate);
        GenelFirmaBilgileri testGenelFirmaBilgileri = genelFirmaBilgileriList.get(genelFirmaBilgileriList.size() - 1);
        assertThat(testGenelFirmaBilgileri.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testGenelFirmaBilgileri.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGenelFirmaBilgileri.getFirmaUnvan()).isEqualTo(UPDATED_FIRMA_UNVAN);
        assertThat(testGenelFirmaBilgileri.getKurulusTarihi()).isEqualTo(UPDATED_KURULUS_TARIHI);
        assertThat(testGenelFirmaBilgileri.isSaglikSektorundeMi()).isEqualTo(UPDATED_SAGLIK_SEKTORUNDE_MI);
        assertThat(testGenelFirmaBilgileri.getSektorBilgisi()).isEqualTo(UPDATED_SEKTOR_BILGISI);
        assertThat(testGenelFirmaBilgileri.isArgeBirimiVarMi()).isEqualTo(UPDATED_ARGE_BIRIMI_VAR_MI);
        assertThat(testGenelFirmaBilgileri.getTicaretSicilNo()).isEqualTo(UPDATED_TICARET_SICIL_NO);
        assertThat(testGenelFirmaBilgileri.getVergiNo()).isEqualTo(UPDATED_VERGI_NO);
        assertThat(testGenelFirmaBilgileri.getAcikAdress()).isEqualTo(UPDATED_ACIK_ADRESS);
        assertThat(testGenelFirmaBilgileri.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testGenelFirmaBilgileri.getTelefon2()).isEqualTo(UPDATED_TELEFON_2);
        assertThat(testGenelFirmaBilgileri.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testGenelFirmaBilgileri.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGenelFirmaBilgileri.getWebAdresi()).isEqualTo(UPDATED_WEB_ADRESI);
    }

    @Test
    @Transactional
    public void updateNonExistingGenelFirmaBilgileri() throws Exception {
        int databaseSizeBeforeUpdate = genelFirmaBilgileriRepository.findAll().size();

        // Create the GenelFirmaBilgileri

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGenelFirmaBilgileriMockMvc.perform(put("/api/genel-firma-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genelFirmaBilgileri)))
            .andExpect(status().isCreated());

        // Validate the GenelFirmaBilgileri in the database
        List<GenelFirmaBilgileri> genelFirmaBilgileriList = genelFirmaBilgileriRepository.findAll();
        assertThat(genelFirmaBilgileriList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGenelFirmaBilgileri() throws Exception {
        // Initialize the database
        genelFirmaBilgileriRepository.saveAndFlush(genelFirmaBilgileri);
        int databaseSizeBeforeDelete = genelFirmaBilgileriRepository.findAll().size();

        // Get the genelFirmaBilgileri
        restGenelFirmaBilgileriMockMvc.perform(delete("/api/genel-firma-bilgileris/{id}", genelFirmaBilgileri.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GenelFirmaBilgileri> genelFirmaBilgileriList = genelFirmaBilgileriRepository.findAll();
        assertThat(genelFirmaBilgileriList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenelFirmaBilgileri.class);
        GenelFirmaBilgileri genelFirmaBilgileri1 = new GenelFirmaBilgileri();
        genelFirmaBilgileri1.setId(1L);
        GenelFirmaBilgileri genelFirmaBilgileri2 = new GenelFirmaBilgileri();
        genelFirmaBilgileri2.setId(genelFirmaBilgileri1.getId());
        assertThat(genelFirmaBilgileri1).isEqualTo(genelFirmaBilgileri2);
        genelFirmaBilgileri2.setId(2L);
        assertThat(genelFirmaBilgileri1).isNotEqualTo(genelFirmaBilgileri2);
        genelFirmaBilgileri1.setId(null);
        assertThat(genelFirmaBilgileri1).isNotEqualTo(genelFirmaBilgileri2);
    }
}

package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.Kurum;
import tr.gov.sb.sygm.repository.KurumRepository;
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
 * Test class for the KurumResource REST controller.
 *
 * @see KurumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class KurumResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final LocalDate DEFAULT_YIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YIL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    @Autowired
    private KurumRepository kurumRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKurumMockMvc;

    private Kurum kurum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KurumResource kurumResource = new KurumResource(kurumRepository);
        this.restKurumMockMvc = MockMvcBuilders.standaloneSetup(kurumResource)
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
    public static Kurum createEntity(EntityManager em) {
        Kurum kurum = new Kurum()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .yil(DEFAULT_YIL)
            .adi(DEFAULT_ADI);
        return kurum;
    }

    @Before
    public void initTest() {
        kurum = createEntity(em);
    }

    @Test
    @Transactional
    public void createKurum() throws Exception {
        int databaseSizeBeforeCreate = kurumRepository.findAll().size();

        // Create the Kurum
        restKurumMockMvc.perform(post("/api/kurums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurum)))
            .andExpect(status().isCreated());

        // Validate the Kurum in the database
        List<Kurum> kurumList = kurumRepository.findAll();
        assertThat(kurumList).hasSize(databaseSizeBeforeCreate + 1);
        Kurum testKurum = kurumList.get(kurumList.size() - 1);
        assertThat(testKurum.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testKurum.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testKurum.getYil()).isEqualTo(DEFAULT_YIL);
        assertThat(testKurum.getAdi()).isEqualTo(DEFAULT_ADI);
    }

    @Test
    @Transactional
    public void createKurumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kurumRepository.findAll().size();

        // Create the Kurum with an existing ID
        kurum.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKurumMockMvc.perform(post("/api/kurums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurum)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Kurum> kurumList = kurumRepository.findAll();
        assertThat(kurumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurumRepository.findAll().size();
        // set the field null
        kurum.setDeleted(null);

        // Create the Kurum, which fails.

        restKurumMockMvc.perform(post("/api/kurums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurum)))
            .andExpect(status().isBadRequest());

        List<Kurum> kurumList = kurumRepository.findAll();
        assertThat(kurumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurumRepository.findAll().size();
        // set the field null
        kurum.setVersion(null);

        // Create the Kurum, which fails.

        restKurumMockMvc.perform(post("/api/kurums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurum)))
            .andExpect(status().isBadRequest());

        List<Kurum> kurumList = kurumRepository.findAll();
        assertThat(kurumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYilIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurumRepository.findAll().size();
        // set the field null
        kurum.setYil(null);

        // Create the Kurum, which fails.

        restKurumMockMvc.perform(post("/api/kurums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurum)))
            .andExpect(status().isBadRequest());

        List<Kurum> kurumList = kurumRepository.findAll();
        assertThat(kurumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurumRepository.findAll().size();
        // set the field null
        kurum.setAdi(null);

        // Create the Kurum, which fails.

        restKurumMockMvc.perform(post("/api/kurums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurum)))
            .andExpect(status().isBadRequest());

        List<Kurum> kurumList = kurumRepository.findAll();
        assertThat(kurumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKurums() throws Exception {
        // Initialize the database
        kurumRepository.saveAndFlush(kurum);

        // Get all the kurumList
        restKurumMockMvc.perform(get("/api/kurums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kurum.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].yil").value(hasItem(DEFAULT_YIL.toString())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())));
    }

    @Test
    @Transactional
    public void getKurum() throws Exception {
        // Initialize the database
        kurumRepository.saveAndFlush(kurum);

        // Get the kurum
        restKurumMockMvc.perform(get("/api/kurums/{id}", kurum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kurum.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.yil").value(DEFAULT_YIL.toString()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKurum() throws Exception {
        // Get the kurum
        restKurumMockMvc.perform(get("/api/kurums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKurum() throws Exception {
        // Initialize the database
        kurumRepository.saveAndFlush(kurum);
        int databaseSizeBeforeUpdate = kurumRepository.findAll().size();

        // Update the kurum
        Kurum updatedKurum = kurumRepository.findOne(kurum.getId());
        updatedKurum
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .yil(UPDATED_YIL)
            .adi(UPDATED_ADI);

        restKurumMockMvc.perform(put("/api/kurums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKurum)))
            .andExpect(status().isOk());

        // Validate the Kurum in the database
        List<Kurum> kurumList = kurumRepository.findAll();
        assertThat(kurumList).hasSize(databaseSizeBeforeUpdate);
        Kurum testKurum = kurumList.get(kurumList.size() - 1);
        assertThat(testKurum.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testKurum.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testKurum.getYil()).isEqualTo(UPDATED_YIL);
        assertThat(testKurum.getAdi()).isEqualTo(UPDATED_ADI);
    }

    @Test
    @Transactional
    public void updateNonExistingKurum() throws Exception {
        int databaseSizeBeforeUpdate = kurumRepository.findAll().size();

        // Create the Kurum

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKurumMockMvc.perform(put("/api/kurums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurum)))
            .andExpect(status().isCreated());

        // Validate the Kurum in the database
        List<Kurum> kurumList = kurumRepository.findAll();
        assertThat(kurumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKurum() throws Exception {
        // Initialize the database
        kurumRepository.saveAndFlush(kurum);
        int databaseSizeBeforeDelete = kurumRepository.findAll().size();

        // Get the kurum
        restKurumMockMvc.perform(delete("/api/kurums/{id}", kurum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kurum> kurumList = kurumRepository.findAll();
        assertThat(kurumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kurum.class);
        Kurum kurum1 = new Kurum();
        kurum1.setId(1L);
        Kurum kurum2 = new Kurum();
        kurum2.setId(kurum1.getId());
        assertThat(kurum1).isEqualTo(kurum2);
        kurum2.setId(2L);
        assertThat(kurum1).isNotEqualTo(kurum2);
        kurum1.setId(null);
        assertThat(kurum1).isNotEqualTo(kurum2);
    }
}

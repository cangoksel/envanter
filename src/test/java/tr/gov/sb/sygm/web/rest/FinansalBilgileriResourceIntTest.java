package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.FinansalBilgileri;
import tr.gov.sb.sygm.repository.FinansalBilgileriRepository;
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
 * Test class for the FinansalBilgileriResource REST controller.
 *
 * @see FinansalBilgileriResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class FinansalBilgileriResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final LocalDate DEFAULT_YIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YIL = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IHRACAT_VAR_MI = false;
    private static final Boolean UPDATED_IHRACAT_VAR_MI = true;

    @Autowired
    private FinansalBilgileriRepository finansalBilgileriRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFinansalBilgileriMockMvc;

    private FinansalBilgileri finansalBilgileri;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FinansalBilgileriResource finansalBilgileriResource = new FinansalBilgileriResource(finansalBilgileriRepository);
        this.restFinansalBilgileriMockMvc = MockMvcBuilders.standaloneSetup(finansalBilgileriResource)
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
    public static FinansalBilgileri createEntity(EntityManager em) {
        FinansalBilgileri finansalBilgileri = new FinansalBilgileri()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .yil(DEFAULT_YIL)
            .ihracatVarMi(DEFAULT_IHRACAT_VAR_MI);
        return finansalBilgileri;
    }

    @Before
    public void initTest() {
        finansalBilgileri = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinansalBilgileri() throws Exception {
        int databaseSizeBeforeCreate = finansalBilgileriRepository.findAll().size();

        // Create the FinansalBilgileri
        restFinansalBilgileriMockMvc.perform(post("/api/finansal-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalBilgileri)))
            .andExpect(status().isCreated());

        // Validate the FinansalBilgileri in the database
        List<FinansalBilgileri> finansalBilgileriList = finansalBilgileriRepository.findAll();
        assertThat(finansalBilgileriList).hasSize(databaseSizeBeforeCreate + 1);
        FinansalBilgileri testFinansalBilgileri = finansalBilgileriList.get(finansalBilgileriList.size() - 1);
        assertThat(testFinansalBilgileri.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testFinansalBilgileri.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFinansalBilgileri.getYil()).isEqualTo(DEFAULT_YIL);
        assertThat(testFinansalBilgileri.isIhracatVarMi()).isEqualTo(DEFAULT_IHRACAT_VAR_MI);
    }

    @Test
    @Transactional
    public void createFinansalBilgileriWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = finansalBilgileriRepository.findAll().size();

        // Create the FinansalBilgileri with an existing ID
        finansalBilgileri.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinansalBilgileriMockMvc.perform(post("/api/finansal-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalBilgileri)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FinansalBilgileri> finansalBilgileriList = finansalBilgileriRepository.findAll();
        assertThat(finansalBilgileriList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = finansalBilgileriRepository.findAll().size();
        // set the field null
        finansalBilgileri.setDeleted(null);

        // Create the FinansalBilgileri, which fails.

        restFinansalBilgileriMockMvc.perform(post("/api/finansal-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalBilgileri)))
            .andExpect(status().isBadRequest());

        List<FinansalBilgileri> finansalBilgileriList = finansalBilgileriRepository.findAll();
        assertThat(finansalBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = finansalBilgileriRepository.findAll().size();
        // set the field null
        finansalBilgileri.setVersion(null);

        // Create the FinansalBilgileri, which fails.

        restFinansalBilgileriMockMvc.perform(post("/api/finansal-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalBilgileri)))
            .andExpect(status().isBadRequest());

        List<FinansalBilgileri> finansalBilgileriList = finansalBilgileriRepository.findAll();
        assertThat(finansalBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYilIsRequired() throws Exception {
        int databaseSizeBeforeTest = finansalBilgileriRepository.findAll().size();
        // set the field null
        finansalBilgileri.setYil(null);

        // Create the FinansalBilgileri, which fails.

        restFinansalBilgileriMockMvc.perform(post("/api/finansal-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalBilgileri)))
            .andExpect(status().isBadRequest());

        List<FinansalBilgileri> finansalBilgileriList = finansalBilgileriRepository.findAll();
        assertThat(finansalBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIhracatVarMiIsRequired() throws Exception {
        int databaseSizeBeforeTest = finansalBilgileriRepository.findAll().size();
        // set the field null
        finansalBilgileri.setIhracatVarMi(null);

        // Create the FinansalBilgileri, which fails.

        restFinansalBilgileriMockMvc.perform(post("/api/finansal-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalBilgileri)))
            .andExpect(status().isBadRequest());

        List<FinansalBilgileri> finansalBilgileriList = finansalBilgileriRepository.findAll();
        assertThat(finansalBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFinansalBilgileris() throws Exception {
        // Initialize the database
        finansalBilgileriRepository.saveAndFlush(finansalBilgileri);

        // Get all the finansalBilgileriList
        restFinansalBilgileriMockMvc.perform(get("/api/finansal-bilgileris?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finansalBilgileri.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].yil").value(hasItem(DEFAULT_YIL.toString())))
            .andExpect(jsonPath("$.[*].ihracatVarMi").value(hasItem(DEFAULT_IHRACAT_VAR_MI.booleanValue())));
    }

    @Test
    @Transactional
    public void getFinansalBilgileri() throws Exception {
        // Initialize the database
        finansalBilgileriRepository.saveAndFlush(finansalBilgileri);

        // Get the finansalBilgileri
        restFinansalBilgileriMockMvc.perform(get("/api/finansal-bilgileris/{id}", finansalBilgileri.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(finansalBilgileri.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.yil").value(DEFAULT_YIL.toString()))
            .andExpect(jsonPath("$.ihracatVarMi").value(DEFAULT_IHRACAT_VAR_MI.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFinansalBilgileri() throws Exception {
        // Get the finansalBilgileri
        restFinansalBilgileriMockMvc.perform(get("/api/finansal-bilgileris/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinansalBilgileri() throws Exception {
        // Initialize the database
        finansalBilgileriRepository.saveAndFlush(finansalBilgileri);
        int databaseSizeBeforeUpdate = finansalBilgileriRepository.findAll().size();

        // Update the finansalBilgileri
        FinansalBilgileri updatedFinansalBilgileri = finansalBilgileriRepository.findOne(finansalBilgileri.getId());
        updatedFinansalBilgileri
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .yil(UPDATED_YIL)
            .ihracatVarMi(UPDATED_IHRACAT_VAR_MI);

        restFinansalBilgileriMockMvc.perform(put("/api/finansal-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinansalBilgileri)))
            .andExpect(status().isOk());

        // Validate the FinansalBilgileri in the database
        List<FinansalBilgileri> finansalBilgileriList = finansalBilgileriRepository.findAll();
        assertThat(finansalBilgileriList).hasSize(databaseSizeBeforeUpdate);
        FinansalBilgileri testFinansalBilgileri = finansalBilgileriList.get(finansalBilgileriList.size() - 1);
        assertThat(testFinansalBilgileri.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testFinansalBilgileri.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFinansalBilgileri.getYil()).isEqualTo(UPDATED_YIL);
        assertThat(testFinansalBilgileri.isIhracatVarMi()).isEqualTo(UPDATED_IHRACAT_VAR_MI);
    }

    @Test
    @Transactional
    public void updateNonExistingFinansalBilgileri() throws Exception {
        int databaseSizeBeforeUpdate = finansalBilgileriRepository.findAll().size();

        // Create the FinansalBilgileri

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFinansalBilgileriMockMvc.perform(put("/api/finansal-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalBilgileri)))
            .andExpect(status().isCreated());

        // Validate the FinansalBilgileri in the database
        List<FinansalBilgileri> finansalBilgileriList = finansalBilgileriRepository.findAll();
        assertThat(finansalBilgileriList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFinansalBilgileri() throws Exception {
        // Initialize the database
        finansalBilgileriRepository.saveAndFlush(finansalBilgileri);
        int databaseSizeBeforeDelete = finansalBilgileriRepository.findAll().size();

        // Get the finansalBilgileri
        restFinansalBilgileriMockMvc.perform(delete("/api/finansal-bilgileris/{id}", finansalBilgileri.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FinansalBilgileri> finansalBilgileriList = finansalBilgileriRepository.findAll();
        assertThat(finansalBilgileriList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinansalBilgileri.class);
        FinansalBilgileri finansalBilgileri1 = new FinansalBilgileri();
        finansalBilgileri1.setId(1L);
        FinansalBilgileri finansalBilgileri2 = new FinansalBilgileri();
        finansalBilgileri2.setId(finansalBilgileri1.getId());
        assertThat(finansalBilgileri1).isEqualTo(finansalBilgileri2);
        finansalBilgileri2.setId(2L);
        assertThat(finansalBilgileri1).isNotEqualTo(finansalBilgileri2);
        finansalBilgileri1.setId(null);
        assertThat(finansalBilgileri1).isNotEqualTo(finansalBilgileri2);
    }
}

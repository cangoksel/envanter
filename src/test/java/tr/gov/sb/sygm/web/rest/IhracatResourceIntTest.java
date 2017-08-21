package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.Ihracat;
import tr.gov.sb.sygm.repository.IhracatRepository;
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
 * Test class for the IhracatResource REST controller.
 *
 * @see IhracatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class IhracatResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final BigDecimal DEFAULT_ITHALAT_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_ITHALAT_TUTAR = new BigDecimal(2);

    private static final String DEFAULT_ITHALAT_PB = "AAAAAAAAAA";
    private static final String UPDATED_ITHALAT_PB = "BBBBBBBBBB";

    private static final Long DEFAULT_MIKTAR = 1L;
    private static final Long UPDATED_MIKTAR = 2L;

    private static final LocalDate DEFAULT_YIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YIL = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private IhracatRepository ihracatRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIhracatMockMvc;

    private Ihracat ihracat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IhracatResource ihracatResource = new IhracatResource(ihracatRepository);
        this.restIhracatMockMvc = MockMvcBuilders.standaloneSetup(ihracatResource)
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
    public static Ihracat createEntity(EntityManager em) {
        Ihracat ihracat = new Ihracat()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .ithalatTutar(DEFAULT_ITHALAT_TUTAR)
            .ithalatPB(DEFAULT_ITHALAT_PB)
            .miktar(DEFAULT_MIKTAR)
            .yil(DEFAULT_YIL);
        return ihracat;
    }

    @Before
    public void initTest() {
        ihracat = createEntity(em);
    }

    @Test
    @Transactional
    public void createIhracat() throws Exception {
        int databaseSizeBeforeCreate = ihracatRepository.findAll().size();

        // Create the Ihracat
        restIhracatMockMvc.perform(post("/api/ihracats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ihracat)))
            .andExpect(status().isCreated());

        // Validate the Ihracat in the database
        List<Ihracat> ihracatList = ihracatRepository.findAll();
        assertThat(ihracatList).hasSize(databaseSizeBeforeCreate + 1);
        Ihracat testIhracat = ihracatList.get(ihracatList.size() - 1);
        assertThat(testIhracat.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testIhracat.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testIhracat.getIthalatTutar()).isEqualTo(DEFAULT_ITHALAT_TUTAR);
        assertThat(testIhracat.getIthalatPB()).isEqualTo(DEFAULT_ITHALAT_PB);
        assertThat(testIhracat.getMiktar()).isEqualTo(DEFAULT_MIKTAR);
        assertThat(testIhracat.getYil()).isEqualTo(DEFAULT_YIL);
    }

    @Test
    @Transactional
    public void createIhracatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ihracatRepository.findAll().size();

        // Create the Ihracat with an existing ID
        ihracat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIhracatMockMvc.perform(post("/api/ihracats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ihracat)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ihracat> ihracatList = ihracatRepository.findAll();
        assertThat(ihracatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = ihracatRepository.findAll().size();
        // set the field null
        ihracat.setDeleted(null);

        // Create the Ihracat, which fails.

        restIhracatMockMvc.perform(post("/api/ihracats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ihracat)))
            .andExpect(status().isBadRequest());

        List<Ihracat> ihracatList = ihracatRepository.findAll();
        assertThat(ihracatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ihracatRepository.findAll().size();
        // set the field null
        ihracat.setVersion(null);

        // Create the Ihracat, which fails.

        restIhracatMockMvc.perform(post("/api/ihracats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ihracat)))
            .andExpect(status().isBadRequest());

        List<Ihracat> ihracatList = ihracatRepository.findAll();
        assertThat(ihracatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYilIsRequired() throws Exception {
        int databaseSizeBeforeTest = ihracatRepository.findAll().size();
        // set the field null
        ihracat.setYil(null);

        // Create the Ihracat, which fails.

        restIhracatMockMvc.perform(post("/api/ihracats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ihracat)))
            .andExpect(status().isBadRequest());

        List<Ihracat> ihracatList = ihracatRepository.findAll();
        assertThat(ihracatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIhracats() throws Exception {
        // Initialize the database
        ihracatRepository.saveAndFlush(ihracat);

        // Get all the ihracatList
        restIhracatMockMvc.perform(get("/api/ihracats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ihracat.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].ithalatTutar").value(hasItem(DEFAULT_ITHALAT_TUTAR.intValue())))
            .andExpect(jsonPath("$.[*].ithalatPB").value(hasItem(DEFAULT_ITHALAT_PB.toString())))
            .andExpect(jsonPath("$.[*].miktar").value(hasItem(DEFAULT_MIKTAR.intValue())))
            .andExpect(jsonPath("$.[*].yil").value(hasItem(DEFAULT_YIL.toString())));
    }

    @Test
    @Transactional
    public void getIhracat() throws Exception {
        // Initialize the database
        ihracatRepository.saveAndFlush(ihracat);

        // Get the ihracat
        restIhracatMockMvc.perform(get("/api/ihracats/{id}", ihracat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ihracat.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.ithalatTutar").value(DEFAULT_ITHALAT_TUTAR.intValue()))
            .andExpect(jsonPath("$.ithalatPB").value(DEFAULT_ITHALAT_PB.toString()))
            .andExpect(jsonPath("$.miktar").value(DEFAULT_MIKTAR.intValue()))
            .andExpect(jsonPath("$.yil").value(DEFAULT_YIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIhracat() throws Exception {
        // Get the ihracat
        restIhracatMockMvc.perform(get("/api/ihracats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIhracat() throws Exception {
        // Initialize the database
        ihracatRepository.saveAndFlush(ihracat);
        int databaseSizeBeforeUpdate = ihracatRepository.findAll().size();

        // Update the ihracat
        Ihracat updatedIhracat = ihracatRepository.findOne(ihracat.getId());
        updatedIhracat
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .ithalatTutar(UPDATED_ITHALAT_TUTAR)
            .ithalatPB(UPDATED_ITHALAT_PB)
            .miktar(UPDATED_MIKTAR)
            .yil(UPDATED_YIL);

        restIhracatMockMvc.perform(put("/api/ihracats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIhracat)))
            .andExpect(status().isOk());

        // Validate the Ihracat in the database
        List<Ihracat> ihracatList = ihracatRepository.findAll();
        assertThat(ihracatList).hasSize(databaseSizeBeforeUpdate);
        Ihracat testIhracat = ihracatList.get(ihracatList.size() - 1);
        assertThat(testIhracat.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testIhracat.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testIhracat.getIthalatTutar()).isEqualTo(UPDATED_ITHALAT_TUTAR);
        assertThat(testIhracat.getIthalatPB()).isEqualTo(UPDATED_ITHALAT_PB);
        assertThat(testIhracat.getMiktar()).isEqualTo(UPDATED_MIKTAR);
        assertThat(testIhracat.getYil()).isEqualTo(UPDATED_YIL);
    }

    @Test
    @Transactional
    public void updateNonExistingIhracat() throws Exception {
        int databaseSizeBeforeUpdate = ihracatRepository.findAll().size();

        // Create the Ihracat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIhracatMockMvc.perform(put("/api/ihracats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ihracat)))
            .andExpect(status().isCreated());

        // Validate the Ihracat in the database
        List<Ihracat> ihracatList = ihracatRepository.findAll();
        assertThat(ihracatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIhracat() throws Exception {
        // Initialize the database
        ihracatRepository.saveAndFlush(ihracat);
        int databaseSizeBeforeDelete = ihracatRepository.findAll().size();

        // Get the ihracat
        restIhracatMockMvc.perform(delete("/api/ihracats/{id}", ihracat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ihracat> ihracatList = ihracatRepository.findAll();
        assertThat(ihracatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ihracat.class);
        Ihracat ihracat1 = new Ihracat();
        ihracat1.setId(1L);
        Ihracat ihracat2 = new Ihracat();
        ihracat2.setId(ihracat1.getId());
        assertThat(ihracat1).isEqualTo(ihracat2);
        ihracat2.setId(2L);
        assertThat(ihracat1).isNotEqualTo(ihracat2);
        ihracat1.setId(null);
        assertThat(ihracat1).isNotEqualTo(ihracat2);
    }
}

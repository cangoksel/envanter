package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.Ulke;
import tr.gov.sb.sygm.repository.UlkeRepository;
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
 * Test class for the UlkeResource REST controller.
 *
 * @see UlkeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class UlkeResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_HARF_KODU = "AAAAAAAAAA";
    private static final String UPDATED_HARF_KODU = "BBBBBBBBBB";

    private static final String DEFAULT_KODU = "AAAAAAAAAA";
    private static final String UPDATED_KODU = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    @Autowired
    private UlkeRepository ulkeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUlkeMockMvc;

    private Ulke ulke;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UlkeResource ulkeResource = new UlkeResource(ulkeRepository);
        this.restUlkeMockMvc = MockMvcBuilders.standaloneSetup(ulkeResource)
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
    public static Ulke createEntity(EntityManager em) {
        Ulke ulke = new Ulke()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .label(DEFAULT_LABEL)
            .harfKodu(DEFAULT_HARF_KODU)
            .kodu(DEFAULT_KODU)
            .englishName(DEFAULT_ENGLISH_NAME);
        return ulke;
    }

    @Before
    public void initTest() {
        ulke = createEntity(em);
    }

    @Test
    @Transactional
    public void createUlke() throws Exception {
        int databaseSizeBeforeCreate = ulkeRepository.findAll().size();

        // Create the Ulke
        restUlkeMockMvc.perform(post("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ulke)))
            .andExpect(status().isCreated());

        // Validate the Ulke in the database
        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeCreate + 1);
        Ulke testUlke = ulkeList.get(ulkeList.size() - 1);
        assertThat(testUlke.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testUlke.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUlke.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testUlke.getHarfKodu()).isEqualTo(DEFAULT_HARF_KODU);
        assertThat(testUlke.getKodu()).isEqualTo(DEFAULT_KODU);
        assertThat(testUlke.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void createUlkeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ulkeRepository.findAll().size();

        // Create the Ulke with an existing ID
        ulke.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUlkeMockMvc.perform(post("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ulke)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = ulkeRepository.findAll().size();
        // set the field null
        ulke.setDeleted(null);

        // Create the Ulke, which fails.

        restUlkeMockMvc.perform(post("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ulke)))
            .andExpect(status().isBadRequest());

        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ulkeRepository.findAll().size();
        // set the field null
        ulke.setVersion(null);

        // Create the Ulke, which fails.

        restUlkeMockMvc.perform(post("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ulke)))
            .andExpect(status().isBadRequest());

        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = ulkeRepository.findAll().size();
        // set the field null
        ulke.setLabel(null);

        // Create the Ulke, which fails.

        restUlkeMockMvc.perform(post("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ulke)))
            .andExpect(status().isBadRequest());

        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHarfKoduIsRequired() throws Exception {
        int databaseSizeBeforeTest = ulkeRepository.findAll().size();
        // set the field null
        ulke.setHarfKodu(null);

        // Create the Ulke, which fails.

        restUlkeMockMvc.perform(post("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ulke)))
            .andExpect(status().isBadRequest());

        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKoduIsRequired() throws Exception {
        int databaseSizeBeforeTest = ulkeRepository.findAll().size();
        // set the field null
        ulke.setKodu(null);

        // Create the Ulke, which fails.

        restUlkeMockMvc.perform(post("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ulke)))
            .andExpect(status().isBadRequest());

        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnglishNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ulkeRepository.findAll().size();
        // set the field null
        ulke.setEnglishName(null);

        // Create the Ulke, which fails.

        restUlkeMockMvc.perform(post("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ulke)))
            .andExpect(status().isBadRequest());

        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUlkes() throws Exception {
        // Initialize the database
        ulkeRepository.saveAndFlush(ulke);

        // Get all the ulkeList
        restUlkeMockMvc.perform(get("/api/ulkes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ulke.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].harfKodu").value(hasItem(DEFAULT_HARF_KODU.toString())))
            .andExpect(jsonPath("$.[*].kodu").value(hasItem(DEFAULT_KODU.toString())))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())));
    }

    @Test
    @Transactional
    public void getUlke() throws Exception {
        // Initialize the database
        ulkeRepository.saveAndFlush(ulke);

        // Get the ulke
        restUlkeMockMvc.perform(get("/api/ulkes/{id}", ulke.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ulke.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.harfKodu").value(DEFAULT_HARF_KODU.toString()))
            .andExpect(jsonPath("$.kodu").value(DEFAULT_KODU.toString()))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUlke() throws Exception {
        // Get the ulke
        restUlkeMockMvc.perform(get("/api/ulkes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUlke() throws Exception {
        // Initialize the database
        ulkeRepository.saveAndFlush(ulke);
        int databaseSizeBeforeUpdate = ulkeRepository.findAll().size();

        // Update the ulke
        Ulke updatedUlke = ulkeRepository.findOne(ulke.getId());
        updatedUlke
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .label(UPDATED_LABEL)
            .harfKodu(UPDATED_HARF_KODU)
            .kodu(UPDATED_KODU)
            .englishName(UPDATED_ENGLISH_NAME);

        restUlkeMockMvc.perform(put("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUlke)))
            .andExpect(status().isOk());

        // Validate the Ulke in the database
        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeUpdate);
        Ulke testUlke = ulkeList.get(ulkeList.size() - 1);
        assertThat(testUlke.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testUlke.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUlke.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testUlke.getHarfKodu()).isEqualTo(UPDATED_HARF_KODU);
        assertThat(testUlke.getKodu()).isEqualTo(UPDATED_KODU);
        assertThat(testUlke.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUlke() throws Exception {
        int databaseSizeBeforeUpdate = ulkeRepository.findAll().size();

        // Create the Ulke

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUlkeMockMvc.perform(put("/api/ulkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ulke)))
            .andExpect(status().isCreated());

        // Validate the Ulke in the database
        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUlke() throws Exception {
        // Initialize the database
        ulkeRepository.saveAndFlush(ulke);
        int databaseSizeBeforeDelete = ulkeRepository.findAll().size();

        // Get the ulke
        restUlkeMockMvc.perform(delete("/api/ulkes/{id}", ulke.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ulke> ulkeList = ulkeRepository.findAll();
        assertThat(ulkeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ulke.class);
        Ulke ulke1 = new Ulke();
        ulke1.setId(1L);
        Ulke ulke2 = new Ulke();
        ulke2.setId(ulke1.getId());
        assertThat(ulke1).isEqualTo(ulke2);
        ulke2.setId(2L);
        assertThat(ulke1).isNotEqualTo(ulke2);
        ulke1.setId(null);
        assertThat(ulke1).isNotEqualTo(ulke2);
    }
}

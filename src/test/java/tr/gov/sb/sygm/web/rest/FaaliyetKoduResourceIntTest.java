package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.FaaliyetKodu;
import tr.gov.sb.sygm.repository.FaaliyetKoduRepository;
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
 * Test class for the FaaliyetKoduResource REST controller.
 *
 * @see FaaliyetKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class FaaliyetKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_FAALIYET_KODU = "AAAAAAAAAA";
    private static final String UPDATED_FAALIYET_KODU = "BBBBBBBBBB";

    @Autowired
    private FaaliyetKoduRepository faaliyetKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFaaliyetKoduMockMvc;

    private FaaliyetKodu faaliyetKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FaaliyetKoduResource faaliyetKoduResource = new FaaliyetKoduResource(faaliyetKoduRepository);
        this.restFaaliyetKoduMockMvc = MockMvcBuilders.standaloneSetup(faaliyetKoduResource)
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
    public static FaaliyetKodu createEntity(EntityManager em) {
        FaaliyetKodu faaliyetKodu = new FaaliyetKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .faaliyetKodu(DEFAULT_FAALIYET_KODU);
        return faaliyetKodu;
    }

    @Before
    public void initTest() {
        faaliyetKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaaliyetKodu() throws Exception {
        int databaseSizeBeforeCreate = faaliyetKoduRepository.findAll().size();

        // Create the FaaliyetKodu
        restFaaliyetKoduMockMvc.perform(post("/api/faaliyet-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetKodu)))
            .andExpect(status().isCreated());

        // Validate the FaaliyetKodu in the database
        List<FaaliyetKodu> faaliyetKoduList = faaliyetKoduRepository.findAll();
        assertThat(faaliyetKoduList).hasSize(databaseSizeBeforeCreate + 1);
        FaaliyetKodu testFaaliyetKodu = faaliyetKoduList.get(faaliyetKoduList.size() - 1);
        assertThat(testFaaliyetKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testFaaliyetKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFaaliyetKodu.getFaaliyetKodu()).isEqualTo(DEFAULT_FAALIYET_KODU);
    }

    @Test
    @Transactional
    public void createFaaliyetKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faaliyetKoduRepository.findAll().size();

        // Create the FaaliyetKodu with an existing ID
        faaliyetKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaaliyetKoduMockMvc.perform(post("/api/faaliyet-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FaaliyetKodu> faaliyetKoduList = faaliyetKoduRepository.findAll();
        assertThat(faaliyetKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = faaliyetKoduRepository.findAll().size();
        // set the field null
        faaliyetKodu.setDeleted(null);

        // Create the FaaliyetKodu, which fails.

        restFaaliyetKoduMockMvc.perform(post("/api/faaliyet-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetKodu)))
            .andExpect(status().isBadRequest());

        List<FaaliyetKodu> faaliyetKoduList = faaliyetKoduRepository.findAll();
        assertThat(faaliyetKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = faaliyetKoduRepository.findAll().size();
        // set the field null
        faaliyetKodu.setVersion(null);

        // Create the FaaliyetKodu, which fails.

        restFaaliyetKoduMockMvc.perform(post("/api/faaliyet-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetKodu)))
            .andExpect(status().isBadRequest());

        List<FaaliyetKodu> faaliyetKoduList = faaliyetKoduRepository.findAll();
        assertThat(faaliyetKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFaaliyetKoduIsRequired() throws Exception {
        int databaseSizeBeforeTest = faaliyetKoduRepository.findAll().size();
        // set the field null
        faaliyetKodu.setFaaliyetKodu(null);

        // Create the FaaliyetKodu, which fails.

        restFaaliyetKoduMockMvc.perform(post("/api/faaliyet-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetKodu)))
            .andExpect(status().isBadRequest());

        List<FaaliyetKodu> faaliyetKoduList = faaliyetKoduRepository.findAll();
        assertThat(faaliyetKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaaliyetKodus() throws Exception {
        // Initialize the database
        faaliyetKoduRepository.saveAndFlush(faaliyetKodu);

        // Get all the faaliyetKoduList
        restFaaliyetKoduMockMvc.perform(get("/api/faaliyet-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faaliyetKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].faaliyetKodu").value(hasItem(DEFAULT_FAALIYET_KODU.toString())));
    }

    @Test
    @Transactional
    public void getFaaliyetKodu() throws Exception {
        // Initialize the database
        faaliyetKoduRepository.saveAndFlush(faaliyetKodu);

        // Get the faaliyetKodu
        restFaaliyetKoduMockMvc.perform(get("/api/faaliyet-kodus/{id}", faaliyetKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faaliyetKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.faaliyetKodu").value(DEFAULT_FAALIYET_KODU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFaaliyetKodu() throws Exception {
        // Get the faaliyetKodu
        restFaaliyetKoduMockMvc.perform(get("/api/faaliyet-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaaliyetKodu() throws Exception {
        // Initialize the database
        faaliyetKoduRepository.saveAndFlush(faaliyetKodu);
        int databaseSizeBeforeUpdate = faaliyetKoduRepository.findAll().size();

        // Update the faaliyetKodu
        FaaliyetKodu updatedFaaliyetKodu = faaliyetKoduRepository.findOne(faaliyetKodu.getId());
        updatedFaaliyetKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .faaliyetKodu(UPDATED_FAALIYET_KODU);

        restFaaliyetKoduMockMvc.perform(put("/api/faaliyet-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaaliyetKodu)))
            .andExpect(status().isOk());

        // Validate the FaaliyetKodu in the database
        List<FaaliyetKodu> faaliyetKoduList = faaliyetKoduRepository.findAll();
        assertThat(faaliyetKoduList).hasSize(databaseSizeBeforeUpdate);
        FaaliyetKodu testFaaliyetKodu = faaliyetKoduList.get(faaliyetKoduList.size() - 1);
        assertThat(testFaaliyetKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testFaaliyetKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFaaliyetKodu.getFaaliyetKodu()).isEqualTo(UPDATED_FAALIYET_KODU);
    }

    @Test
    @Transactional
    public void updateNonExistingFaaliyetKodu() throws Exception {
        int databaseSizeBeforeUpdate = faaliyetKoduRepository.findAll().size();

        // Create the FaaliyetKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFaaliyetKoduMockMvc.perform(put("/api/faaliyet-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetKodu)))
            .andExpect(status().isCreated());

        // Validate the FaaliyetKodu in the database
        List<FaaliyetKodu> faaliyetKoduList = faaliyetKoduRepository.findAll();
        assertThat(faaliyetKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFaaliyetKodu() throws Exception {
        // Initialize the database
        faaliyetKoduRepository.saveAndFlush(faaliyetKodu);
        int databaseSizeBeforeDelete = faaliyetKoduRepository.findAll().size();

        // Get the faaliyetKodu
        restFaaliyetKoduMockMvc.perform(delete("/api/faaliyet-kodus/{id}", faaliyetKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FaaliyetKodu> faaliyetKoduList = faaliyetKoduRepository.findAll();
        assertThat(faaliyetKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaaliyetKodu.class);
        FaaliyetKodu faaliyetKodu1 = new FaaliyetKodu();
        faaliyetKodu1.setId(1L);
        FaaliyetKodu faaliyetKodu2 = new FaaliyetKodu();
        faaliyetKodu2.setId(faaliyetKodu1.getId());
        assertThat(faaliyetKodu1).isEqualTo(faaliyetKodu2);
        faaliyetKodu2.setId(2L);
        assertThat(faaliyetKodu1).isNotEqualTo(faaliyetKodu2);
        faaliyetKodu1.setId(null);
        assertThat(faaliyetKodu1).isNotEqualTo(faaliyetKodu2);
    }
}

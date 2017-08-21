package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.ActKodu;
import tr.gov.sb.sygm.repository.ActKoduRepository;
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
 * Test class for the ActKoduResource REST controller.
 *
 * @see ActKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class ActKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private ActKoduRepository actKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActKoduMockMvc;

    private ActKodu actKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActKoduResource actKoduResource = new ActKoduResource(actKoduRepository);
        this.restActKoduMockMvc = MockMvcBuilders.standaloneSetup(actKoduResource)
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
    public static ActKodu createEntity(EntityManager em) {
        ActKodu actKodu = new ActKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .kod(DEFAULT_KOD);
        return actKodu;
    }

    @Before
    public void initTest() {
        actKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createActKodu() throws Exception {
        int databaseSizeBeforeCreate = actKoduRepository.findAll().size();

        // Create the ActKodu
        restActKoduMockMvc.perform(post("/api/act-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actKodu)))
            .andExpect(status().isCreated());

        // Validate the ActKodu in the database
        List<ActKodu> actKoduList = actKoduRepository.findAll();
        assertThat(actKoduList).hasSize(databaseSizeBeforeCreate + 1);
        ActKodu testActKodu = actKoduList.get(actKoduList.size() - 1);
        assertThat(testActKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testActKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testActKodu.getKod()).isEqualTo(DEFAULT_KOD);
    }

    @Test
    @Transactional
    public void createActKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actKoduRepository.findAll().size();

        // Create the ActKodu with an existing ID
        actKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActKoduMockMvc.perform(post("/api/act-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ActKodu> actKoduList = actKoduRepository.findAll();
        assertThat(actKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = actKoduRepository.findAll().size();
        // set the field null
        actKodu.setDeleted(null);

        // Create the ActKodu, which fails.

        restActKoduMockMvc.perform(post("/api/act-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actKodu)))
            .andExpect(status().isBadRequest());

        List<ActKodu> actKoduList = actKoduRepository.findAll();
        assertThat(actKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = actKoduRepository.findAll().size();
        // set the field null
        actKodu.setVersion(null);

        // Create the ActKodu, which fails.

        restActKoduMockMvc.perform(post("/api/act-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actKodu)))
            .andExpect(status().isBadRequest());

        List<ActKodu> actKoduList = actKoduRepository.findAll();
        assertThat(actKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKodIsRequired() throws Exception {
        int databaseSizeBeforeTest = actKoduRepository.findAll().size();
        // set the field null
        actKodu.setKod(null);

        // Create the ActKodu, which fails.

        restActKoduMockMvc.perform(post("/api/act-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actKodu)))
            .andExpect(status().isBadRequest());

        List<ActKodu> actKoduList = actKoduRepository.findAll();
        assertThat(actKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActKodus() throws Exception {
        // Initialize the database
        actKoduRepository.saveAndFlush(actKodu);

        // Get all the actKoduList
        restActKoduMockMvc.perform(get("/api/act-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getActKodu() throws Exception {
        // Initialize the database
        actKoduRepository.saveAndFlush(actKodu);

        // Get the actKodu
        restActKoduMockMvc.perform(get("/api/act-kodus/{id}", actKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActKodu() throws Exception {
        // Get the actKodu
        restActKoduMockMvc.perform(get("/api/act-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActKodu() throws Exception {
        // Initialize the database
        actKoduRepository.saveAndFlush(actKodu);
        int databaseSizeBeforeUpdate = actKoduRepository.findAll().size();

        // Update the actKodu
        ActKodu updatedActKodu = actKoduRepository.findOne(actKodu.getId());
        updatedActKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .kod(UPDATED_KOD);

        restActKoduMockMvc.perform(put("/api/act-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActKodu)))
            .andExpect(status().isOk());

        // Validate the ActKodu in the database
        List<ActKodu> actKoduList = actKoduRepository.findAll();
        assertThat(actKoduList).hasSize(databaseSizeBeforeUpdate);
        ActKodu testActKodu = actKoduList.get(actKoduList.size() - 1);
        assertThat(testActKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testActKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testActKodu.getKod()).isEqualTo(UPDATED_KOD);
    }

    @Test
    @Transactional
    public void updateNonExistingActKodu() throws Exception {
        int databaseSizeBeforeUpdate = actKoduRepository.findAll().size();

        // Create the ActKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActKoduMockMvc.perform(put("/api/act-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actKodu)))
            .andExpect(status().isCreated());

        // Validate the ActKodu in the database
        List<ActKodu> actKoduList = actKoduRepository.findAll();
        assertThat(actKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActKodu() throws Exception {
        // Initialize the database
        actKoduRepository.saveAndFlush(actKodu);
        int databaseSizeBeforeDelete = actKoduRepository.findAll().size();

        // Get the actKodu
        restActKoduMockMvc.perform(delete("/api/act-kodus/{id}", actKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActKodu> actKoduList = actKoduRepository.findAll();
        assertThat(actKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActKodu.class);
        ActKodu actKodu1 = new ActKodu();
        actKodu1.setId(1L);
        ActKodu actKodu2 = new ActKodu();
        actKodu2.setId(actKodu1.getId());
        assertThat(actKodu1).isEqualTo(actKodu2);
        actKodu2.setId(2L);
        assertThat(actKodu1).isNotEqualTo(actKodu2);
        actKodu1.setId(null);
        assertThat(actKodu1).isNotEqualTo(actKodu2);
    }
}

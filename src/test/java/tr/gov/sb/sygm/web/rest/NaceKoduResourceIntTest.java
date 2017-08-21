package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.NaceKodu;
import tr.gov.sb.sygm.repository.NaceKoduRepository;
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
 * Test class for the NaceKoduResource REST controller.
 *
 * @see NaceKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class NaceKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private NaceKoduRepository naceKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNaceKoduMockMvc;

    private NaceKodu naceKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NaceKoduResource naceKoduResource = new NaceKoduResource(naceKoduRepository);
        this.restNaceKoduMockMvc = MockMvcBuilders.standaloneSetup(naceKoduResource)
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
    public static NaceKodu createEntity(EntityManager em) {
        NaceKodu naceKodu = new NaceKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .kod(DEFAULT_KOD);
        return naceKodu;
    }

    @Before
    public void initTest() {
        naceKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createNaceKodu() throws Exception {
        int databaseSizeBeforeCreate = naceKoduRepository.findAll().size();

        // Create the NaceKodu
        restNaceKoduMockMvc.perform(post("/api/nace-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naceKodu)))
            .andExpect(status().isCreated());

        // Validate the NaceKodu in the database
        List<NaceKodu> naceKoduList = naceKoduRepository.findAll();
        assertThat(naceKoduList).hasSize(databaseSizeBeforeCreate + 1);
        NaceKodu testNaceKodu = naceKoduList.get(naceKoduList.size() - 1);
        assertThat(testNaceKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testNaceKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testNaceKodu.getKod()).isEqualTo(DEFAULT_KOD);
    }

    @Test
    @Transactional
    public void createNaceKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = naceKoduRepository.findAll().size();

        // Create the NaceKodu with an existing ID
        naceKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaceKoduMockMvc.perform(post("/api/nace-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naceKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NaceKodu> naceKoduList = naceKoduRepository.findAll();
        assertThat(naceKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = naceKoduRepository.findAll().size();
        // set the field null
        naceKodu.setDeleted(null);

        // Create the NaceKodu, which fails.

        restNaceKoduMockMvc.perform(post("/api/nace-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naceKodu)))
            .andExpect(status().isBadRequest());

        List<NaceKodu> naceKoduList = naceKoduRepository.findAll();
        assertThat(naceKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = naceKoduRepository.findAll().size();
        // set the field null
        naceKodu.setVersion(null);

        // Create the NaceKodu, which fails.

        restNaceKoduMockMvc.perform(post("/api/nace-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naceKodu)))
            .andExpect(status().isBadRequest());

        List<NaceKodu> naceKoduList = naceKoduRepository.findAll();
        assertThat(naceKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKodIsRequired() throws Exception {
        int databaseSizeBeforeTest = naceKoduRepository.findAll().size();
        // set the field null
        naceKodu.setKod(null);

        // Create the NaceKodu, which fails.

        restNaceKoduMockMvc.perform(post("/api/nace-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naceKodu)))
            .andExpect(status().isBadRequest());

        List<NaceKodu> naceKoduList = naceKoduRepository.findAll();
        assertThat(naceKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNaceKodus() throws Exception {
        // Initialize the database
        naceKoduRepository.saveAndFlush(naceKodu);

        // Get all the naceKoduList
        restNaceKoduMockMvc.perform(get("/api/nace-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naceKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getNaceKodu() throws Exception {
        // Initialize the database
        naceKoduRepository.saveAndFlush(naceKodu);

        // Get the naceKodu
        restNaceKoduMockMvc.perform(get("/api/nace-kodus/{id}", naceKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(naceKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNaceKodu() throws Exception {
        // Get the naceKodu
        restNaceKoduMockMvc.perform(get("/api/nace-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNaceKodu() throws Exception {
        // Initialize the database
        naceKoduRepository.saveAndFlush(naceKodu);
        int databaseSizeBeforeUpdate = naceKoduRepository.findAll().size();

        // Update the naceKodu
        NaceKodu updatedNaceKodu = naceKoduRepository.findOne(naceKodu.getId());
        updatedNaceKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .kod(UPDATED_KOD);

        restNaceKoduMockMvc.perform(put("/api/nace-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNaceKodu)))
            .andExpect(status().isOk());

        // Validate the NaceKodu in the database
        List<NaceKodu> naceKoduList = naceKoduRepository.findAll();
        assertThat(naceKoduList).hasSize(databaseSizeBeforeUpdate);
        NaceKodu testNaceKodu = naceKoduList.get(naceKoduList.size() - 1);
        assertThat(testNaceKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testNaceKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testNaceKodu.getKod()).isEqualTo(UPDATED_KOD);
    }

    @Test
    @Transactional
    public void updateNonExistingNaceKodu() throws Exception {
        int databaseSizeBeforeUpdate = naceKoduRepository.findAll().size();

        // Create the NaceKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNaceKoduMockMvc.perform(put("/api/nace-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naceKodu)))
            .andExpect(status().isCreated());

        // Validate the NaceKodu in the database
        List<NaceKodu> naceKoduList = naceKoduRepository.findAll();
        assertThat(naceKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNaceKodu() throws Exception {
        // Initialize the database
        naceKoduRepository.saveAndFlush(naceKodu);
        int databaseSizeBeforeDelete = naceKoduRepository.findAll().size();

        // Get the naceKodu
        restNaceKoduMockMvc.perform(delete("/api/nace-kodus/{id}", naceKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NaceKodu> naceKoduList = naceKoduRepository.findAll();
        assertThat(naceKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NaceKodu.class);
        NaceKodu naceKodu1 = new NaceKodu();
        naceKodu1.setId(1L);
        NaceKodu naceKodu2 = new NaceKodu();
        naceKodu2.setId(naceKodu1.getId());
        assertThat(naceKodu1).isEqualTo(naceKodu2);
        naceKodu2.setId(2L);
        assertThat(naceKodu1).isNotEqualTo(naceKodu2);
        naceKodu1.setId(null);
        assertThat(naceKodu1).isNotEqualTo(naceKodu2);
    }
}

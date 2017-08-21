package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.MkysKodu;
import tr.gov.sb.sygm.repository.MkysKoduRepository;
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
 * Test class for the MkysKoduResource REST controller.
 *
 * @see MkysKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class MkysKoduResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private MkysKoduRepository mkysKoduRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMkysKoduMockMvc;

    private MkysKodu mkysKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MkysKoduResource mkysKoduResource = new MkysKoduResource(mkysKoduRepository);
        this.restMkysKoduMockMvc = MockMvcBuilders.standaloneSetup(mkysKoduResource)
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
    public static MkysKodu createEntity(EntityManager em) {
        MkysKodu mkysKodu = new MkysKodu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .kod(DEFAULT_KOD);
        return mkysKodu;
    }

    @Before
    public void initTest() {
        mkysKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createMkysKodu() throws Exception {
        int databaseSizeBeforeCreate = mkysKoduRepository.findAll().size();

        // Create the MkysKodu
        restMkysKoduMockMvc.perform(post("/api/mkys-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mkysKodu)))
            .andExpect(status().isCreated());

        // Validate the MkysKodu in the database
        List<MkysKodu> mkysKoduList = mkysKoduRepository.findAll();
        assertThat(mkysKoduList).hasSize(databaseSizeBeforeCreate + 1);
        MkysKodu testMkysKodu = mkysKoduList.get(mkysKoduList.size() - 1);
        assertThat(testMkysKodu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testMkysKodu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testMkysKodu.getKod()).isEqualTo(DEFAULT_KOD);
    }

    @Test
    @Transactional
    public void createMkysKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mkysKoduRepository.findAll().size();

        // Create the MkysKodu with an existing ID
        mkysKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMkysKoduMockMvc.perform(post("/api/mkys-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mkysKodu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MkysKodu> mkysKoduList = mkysKoduRepository.findAll();
        assertThat(mkysKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = mkysKoduRepository.findAll().size();
        // set the field null
        mkysKodu.setDeleted(null);

        // Create the MkysKodu, which fails.

        restMkysKoduMockMvc.perform(post("/api/mkys-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mkysKodu)))
            .andExpect(status().isBadRequest());

        List<MkysKodu> mkysKoduList = mkysKoduRepository.findAll();
        assertThat(mkysKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mkysKoduRepository.findAll().size();
        // set the field null
        mkysKodu.setVersion(null);

        // Create the MkysKodu, which fails.

        restMkysKoduMockMvc.perform(post("/api/mkys-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mkysKodu)))
            .andExpect(status().isBadRequest());

        List<MkysKodu> mkysKoduList = mkysKoduRepository.findAll();
        assertThat(mkysKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKodIsRequired() throws Exception {
        int databaseSizeBeforeTest = mkysKoduRepository.findAll().size();
        // set the field null
        mkysKodu.setKod(null);

        // Create the MkysKodu, which fails.

        restMkysKoduMockMvc.perform(post("/api/mkys-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mkysKodu)))
            .andExpect(status().isBadRequest());

        List<MkysKodu> mkysKoduList = mkysKoduRepository.findAll();
        assertThat(mkysKoduList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMkysKodus() throws Exception {
        // Initialize the database
        mkysKoduRepository.saveAndFlush(mkysKodu);

        // Get all the mkysKoduList
        restMkysKoduMockMvc.perform(get("/api/mkys-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mkysKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getMkysKodu() throws Exception {
        // Initialize the database
        mkysKoduRepository.saveAndFlush(mkysKodu);

        // Get the mkysKodu
        restMkysKoduMockMvc.perform(get("/api/mkys-kodus/{id}", mkysKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mkysKodu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMkysKodu() throws Exception {
        // Get the mkysKodu
        restMkysKoduMockMvc.perform(get("/api/mkys-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMkysKodu() throws Exception {
        // Initialize the database
        mkysKoduRepository.saveAndFlush(mkysKodu);
        int databaseSizeBeforeUpdate = mkysKoduRepository.findAll().size();

        // Update the mkysKodu
        MkysKodu updatedMkysKodu = mkysKoduRepository.findOne(mkysKodu.getId());
        updatedMkysKodu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .kod(UPDATED_KOD);

        restMkysKoduMockMvc.perform(put("/api/mkys-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMkysKodu)))
            .andExpect(status().isOk());

        // Validate the MkysKodu in the database
        List<MkysKodu> mkysKoduList = mkysKoduRepository.findAll();
        assertThat(mkysKoduList).hasSize(databaseSizeBeforeUpdate);
        MkysKodu testMkysKodu = mkysKoduList.get(mkysKoduList.size() - 1);
        assertThat(testMkysKodu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testMkysKodu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testMkysKodu.getKod()).isEqualTo(UPDATED_KOD);
    }

    @Test
    @Transactional
    public void updateNonExistingMkysKodu() throws Exception {
        int databaseSizeBeforeUpdate = mkysKoduRepository.findAll().size();

        // Create the MkysKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMkysKoduMockMvc.perform(put("/api/mkys-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mkysKodu)))
            .andExpect(status().isCreated());

        // Validate the MkysKodu in the database
        List<MkysKodu> mkysKoduList = mkysKoduRepository.findAll();
        assertThat(mkysKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMkysKodu() throws Exception {
        // Initialize the database
        mkysKoduRepository.saveAndFlush(mkysKodu);
        int databaseSizeBeforeDelete = mkysKoduRepository.findAll().size();

        // Get the mkysKodu
        restMkysKoduMockMvc.perform(delete("/api/mkys-kodus/{id}", mkysKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MkysKodu> mkysKoduList = mkysKoduRepository.findAll();
        assertThat(mkysKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MkysKodu.class);
        MkysKodu mkysKodu1 = new MkysKodu();
        mkysKodu1.setId(1L);
        MkysKodu mkysKodu2 = new MkysKodu();
        mkysKodu2.setId(mkysKodu1.getId());
        assertThat(mkysKodu1).isEqualTo(mkysKodu2);
        mkysKodu2.setId(2L);
        assertThat(mkysKodu1).isNotEqualTo(mkysKodu2);
        mkysKodu1.setId(null);
        assertThat(mkysKodu1).isNotEqualTo(mkysKodu2);
    }
}

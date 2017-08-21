package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.TesisBilgisi;
import tr.gov.sb.sygm.repository.TesisBilgisiRepository;
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
 * Test class for the TesisBilgisiResource REST controller.
 *
 * @see TesisBilgisiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class TesisBilgisiResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_URETIM_ALANI = "AAAAAAAAAA";
    private static final String UPDATED_URETIM_ALANI = "BBBBBBBBBB";

    @Autowired
    private TesisBilgisiRepository tesisBilgisiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTesisBilgisiMockMvc;

    private TesisBilgisi tesisBilgisi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TesisBilgisiResource tesisBilgisiResource = new TesisBilgisiResource(tesisBilgisiRepository);
        this.restTesisBilgisiMockMvc = MockMvcBuilders.standaloneSetup(tesisBilgisiResource)
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
    public static TesisBilgisi createEntity(EntityManager em) {
        TesisBilgisi tesisBilgisi = new TesisBilgisi()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .uretimAlani(DEFAULT_URETIM_ALANI);
        return tesisBilgisi;
    }

    @Before
    public void initTest() {
        tesisBilgisi = createEntity(em);
    }

    @Test
    @Transactional
    public void createTesisBilgisi() throws Exception {
        int databaseSizeBeforeCreate = tesisBilgisiRepository.findAll().size();

        // Create the TesisBilgisi
        restTesisBilgisiMockMvc.perform(post("/api/tesis-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesisBilgisi)))
            .andExpect(status().isCreated());

        // Validate the TesisBilgisi in the database
        List<TesisBilgisi> tesisBilgisiList = tesisBilgisiRepository.findAll();
        assertThat(tesisBilgisiList).hasSize(databaseSizeBeforeCreate + 1);
        TesisBilgisi testTesisBilgisi = tesisBilgisiList.get(tesisBilgisiList.size() - 1);
        assertThat(testTesisBilgisi.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testTesisBilgisi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testTesisBilgisi.getUretimAlani()).isEqualTo(DEFAULT_URETIM_ALANI);
    }

    @Test
    @Transactional
    public void createTesisBilgisiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tesisBilgisiRepository.findAll().size();

        // Create the TesisBilgisi with an existing ID
        tesisBilgisi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTesisBilgisiMockMvc.perform(post("/api/tesis-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesisBilgisi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TesisBilgisi> tesisBilgisiList = tesisBilgisiRepository.findAll();
        assertThat(tesisBilgisiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tesisBilgisiRepository.findAll().size();
        // set the field null
        tesisBilgisi.setDeleted(null);

        // Create the TesisBilgisi, which fails.

        restTesisBilgisiMockMvc.perform(post("/api/tesis-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesisBilgisi)))
            .andExpect(status().isBadRequest());

        List<TesisBilgisi> tesisBilgisiList = tesisBilgisiRepository.findAll();
        assertThat(tesisBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tesisBilgisiRepository.findAll().size();
        // set the field null
        tesisBilgisi.setVersion(null);

        // Create the TesisBilgisi, which fails.

        restTesisBilgisiMockMvc.perform(post("/api/tesis-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesisBilgisi)))
            .andExpect(status().isBadRequest());

        List<TesisBilgisi> tesisBilgisiList = tesisBilgisiRepository.findAll();
        assertThat(tesisBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUretimAlaniIsRequired() throws Exception {
        int databaseSizeBeforeTest = tesisBilgisiRepository.findAll().size();
        // set the field null
        tesisBilgisi.setUretimAlani(null);

        // Create the TesisBilgisi, which fails.

        restTesisBilgisiMockMvc.perform(post("/api/tesis-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesisBilgisi)))
            .andExpect(status().isBadRequest());

        List<TesisBilgisi> tesisBilgisiList = tesisBilgisiRepository.findAll();
        assertThat(tesisBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTesisBilgisis() throws Exception {
        // Initialize the database
        tesisBilgisiRepository.saveAndFlush(tesisBilgisi);

        // Get all the tesisBilgisiList
        restTesisBilgisiMockMvc.perform(get("/api/tesis-bilgisis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tesisBilgisi.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].uretimAlani").value(hasItem(DEFAULT_URETIM_ALANI.toString())));
    }

    @Test
    @Transactional
    public void getTesisBilgisi() throws Exception {
        // Initialize the database
        tesisBilgisiRepository.saveAndFlush(tesisBilgisi);

        // Get the tesisBilgisi
        restTesisBilgisiMockMvc.perform(get("/api/tesis-bilgisis/{id}", tesisBilgisi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tesisBilgisi.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.uretimAlani").value(DEFAULT_URETIM_ALANI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTesisBilgisi() throws Exception {
        // Get the tesisBilgisi
        restTesisBilgisiMockMvc.perform(get("/api/tesis-bilgisis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTesisBilgisi() throws Exception {
        // Initialize the database
        tesisBilgisiRepository.saveAndFlush(tesisBilgisi);
        int databaseSizeBeforeUpdate = tesisBilgisiRepository.findAll().size();

        // Update the tesisBilgisi
        TesisBilgisi updatedTesisBilgisi = tesisBilgisiRepository.findOne(tesisBilgisi.getId());
        updatedTesisBilgisi
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .uretimAlani(UPDATED_URETIM_ALANI);

        restTesisBilgisiMockMvc.perform(put("/api/tesis-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTesisBilgisi)))
            .andExpect(status().isOk());

        // Validate the TesisBilgisi in the database
        List<TesisBilgisi> tesisBilgisiList = tesisBilgisiRepository.findAll();
        assertThat(tesisBilgisiList).hasSize(databaseSizeBeforeUpdate);
        TesisBilgisi testTesisBilgisi = tesisBilgisiList.get(tesisBilgisiList.size() - 1);
        assertThat(testTesisBilgisi.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testTesisBilgisi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testTesisBilgisi.getUretimAlani()).isEqualTo(UPDATED_URETIM_ALANI);
    }

    @Test
    @Transactional
    public void updateNonExistingTesisBilgisi() throws Exception {
        int databaseSizeBeforeUpdate = tesisBilgisiRepository.findAll().size();

        // Create the TesisBilgisi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTesisBilgisiMockMvc.perform(put("/api/tesis-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesisBilgisi)))
            .andExpect(status().isCreated());

        // Validate the TesisBilgisi in the database
        List<TesisBilgisi> tesisBilgisiList = tesisBilgisiRepository.findAll();
        assertThat(tesisBilgisiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTesisBilgisi() throws Exception {
        // Initialize the database
        tesisBilgisiRepository.saveAndFlush(tesisBilgisi);
        int databaseSizeBeforeDelete = tesisBilgisiRepository.findAll().size();

        // Get the tesisBilgisi
        restTesisBilgisiMockMvc.perform(delete("/api/tesis-bilgisis/{id}", tesisBilgisi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TesisBilgisi> tesisBilgisiList = tesisBilgisiRepository.findAll();
        assertThat(tesisBilgisiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TesisBilgisi.class);
        TesisBilgisi tesisBilgisi1 = new TesisBilgisi();
        tesisBilgisi1.setId(1L);
        TesisBilgisi tesisBilgisi2 = new TesisBilgisi();
        tesisBilgisi2.setId(tesisBilgisi1.getId());
        assertThat(tesisBilgisi1).isEqualTo(tesisBilgisi2);
        tesisBilgisi2.setId(2L);
        assertThat(tesisBilgisi1).isNotEqualTo(tesisBilgisi2);
        tesisBilgisi1.setId(null);
        assertThat(tesisBilgisi1).isNotEqualTo(tesisBilgisi2);
    }
}

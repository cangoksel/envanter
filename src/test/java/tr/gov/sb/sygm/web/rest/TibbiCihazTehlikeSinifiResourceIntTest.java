package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.TibbiCihazTehlikeSinifi;
import tr.gov.sb.sygm.repository.TibbiCihazTehlikeSinifiRepository;
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
 * Test class for the TibbiCihazTehlikeSinifiResource REST controller.
 *
 * @see TibbiCihazTehlikeSinifiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class TibbiCihazTehlikeSinifiResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_SINIF = "AAAAAAAAAA";
    private static final String UPDATED_SINIF = "BBBBBBBBBB";

    @Autowired
    private TibbiCihazTehlikeSinifiRepository tibbiCihazTehlikeSinifiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTibbiCihazTehlikeSinifiMockMvc;

    private TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TibbiCihazTehlikeSinifiResource tibbiCihazTehlikeSinifiResource = new TibbiCihazTehlikeSinifiResource(tibbiCihazTehlikeSinifiRepository);
        this.restTibbiCihazTehlikeSinifiMockMvc = MockMvcBuilders.standaloneSetup(tibbiCihazTehlikeSinifiResource)
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
    public static TibbiCihazTehlikeSinifi createEntity(EntityManager em) {
        TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi = new TibbiCihazTehlikeSinifi()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .sinif(DEFAULT_SINIF);
        return tibbiCihazTehlikeSinifi;
    }

    @Before
    public void initTest() {
        tibbiCihazTehlikeSinifi = createEntity(em);
    }

    @Test
    @Transactional
    public void createTibbiCihazTehlikeSinifi() throws Exception {
        int databaseSizeBeforeCreate = tibbiCihazTehlikeSinifiRepository.findAll().size();

        // Create the TibbiCihazTehlikeSinifi
        restTibbiCihazTehlikeSinifiMockMvc.perform(post("/api/tibbi-cihaz-tehlike-sinifis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tibbiCihazTehlikeSinifi)))
            .andExpect(status().isCreated());

        // Validate the TibbiCihazTehlikeSinifi in the database
        List<TibbiCihazTehlikeSinifi> tibbiCihazTehlikeSinifiList = tibbiCihazTehlikeSinifiRepository.findAll();
        assertThat(tibbiCihazTehlikeSinifiList).hasSize(databaseSizeBeforeCreate + 1);
        TibbiCihazTehlikeSinifi testTibbiCihazTehlikeSinifi = tibbiCihazTehlikeSinifiList.get(tibbiCihazTehlikeSinifiList.size() - 1);
        assertThat(testTibbiCihazTehlikeSinifi.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testTibbiCihazTehlikeSinifi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testTibbiCihazTehlikeSinifi.getSinif()).isEqualTo(DEFAULT_SINIF);
    }

    @Test
    @Transactional
    public void createTibbiCihazTehlikeSinifiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tibbiCihazTehlikeSinifiRepository.findAll().size();

        // Create the TibbiCihazTehlikeSinifi with an existing ID
        tibbiCihazTehlikeSinifi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTibbiCihazTehlikeSinifiMockMvc.perform(post("/api/tibbi-cihaz-tehlike-sinifis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tibbiCihazTehlikeSinifi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TibbiCihazTehlikeSinifi> tibbiCihazTehlikeSinifiList = tibbiCihazTehlikeSinifiRepository.findAll();
        assertThat(tibbiCihazTehlikeSinifiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tibbiCihazTehlikeSinifiRepository.findAll().size();
        // set the field null
        tibbiCihazTehlikeSinifi.setDeleted(null);

        // Create the TibbiCihazTehlikeSinifi, which fails.

        restTibbiCihazTehlikeSinifiMockMvc.perform(post("/api/tibbi-cihaz-tehlike-sinifis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tibbiCihazTehlikeSinifi)))
            .andExpect(status().isBadRequest());

        List<TibbiCihazTehlikeSinifi> tibbiCihazTehlikeSinifiList = tibbiCihazTehlikeSinifiRepository.findAll();
        assertThat(tibbiCihazTehlikeSinifiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tibbiCihazTehlikeSinifiRepository.findAll().size();
        // set the field null
        tibbiCihazTehlikeSinifi.setVersion(null);

        // Create the TibbiCihazTehlikeSinifi, which fails.

        restTibbiCihazTehlikeSinifiMockMvc.perform(post("/api/tibbi-cihaz-tehlike-sinifis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tibbiCihazTehlikeSinifi)))
            .andExpect(status().isBadRequest());

        List<TibbiCihazTehlikeSinifi> tibbiCihazTehlikeSinifiList = tibbiCihazTehlikeSinifiRepository.findAll();
        assertThat(tibbiCihazTehlikeSinifiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSinifIsRequired() throws Exception {
        int databaseSizeBeforeTest = tibbiCihazTehlikeSinifiRepository.findAll().size();
        // set the field null
        tibbiCihazTehlikeSinifi.setSinif(null);

        // Create the TibbiCihazTehlikeSinifi, which fails.

        restTibbiCihazTehlikeSinifiMockMvc.perform(post("/api/tibbi-cihaz-tehlike-sinifis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tibbiCihazTehlikeSinifi)))
            .andExpect(status().isBadRequest());

        List<TibbiCihazTehlikeSinifi> tibbiCihazTehlikeSinifiList = tibbiCihazTehlikeSinifiRepository.findAll();
        assertThat(tibbiCihazTehlikeSinifiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTibbiCihazTehlikeSinifis() throws Exception {
        // Initialize the database
        tibbiCihazTehlikeSinifiRepository.saveAndFlush(tibbiCihazTehlikeSinifi);

        // Get all the tibbiCihazTehlikeSinifiList
        restTibbiCihazTehlikeSinifiMockMvc.perform(get("/api/tibbi-cihaz-tehlike-sinifis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tibbiCihazTehlikeSinifi.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].sinif").value(hasItem(DEFAULT_SINIF.toString())));
    }

    @Test
    @Transactional
    public void getTibbiCihazTehlikeSinifi() throws Exception {
        // Initialize the database
        tibbiCihazTehlikeSinifiRepository.saveAndFlush(tibbiCihazTehlikeSinifi);

        // Get the tibbiCihazTehlikeSinifi
        restTibbiCihazTehlikeSinifiMockMvc.perform(get("/api/tibbi-cihaz-tehlike-sinifis/{id}", tibbiCihazTehlikeSinifi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tibbiCihazTehlikeSinifi.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.sinif").value(DEFAULT_SINIF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTibbiCihazTehlikeSinifi() throws Exception {
        // Get the tibbiCihazTehlikeSinifi
        restTibbiCihazTehlikeSinifiMockMvc.perform(get("/api/tibbi-cihaz-tehlike-sinifis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTibbiCihazTehlikeSinifi() throws Exception {
        // Initialize the database
        tibbiCihazTehlikeSinifiRepository.saveAndFlush(tibbiCihazTehlikeSinifi);
        int databaseSizeBeforeUpdate = tibbiCihazTehlikeSinifiRepository.findAll().size();

        // Update the tibbiCihazTehlikeSinifi
        TibbiCihazTehlikeSinifi updatedTibbiCihazTehlikeSinifi = tibbiCihazTehlikeSinifiRepository.findOne(tibbiCihazTehlikeSinifi.getId());
        updatedTibbiCihazTehlikeSinifi
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .sinif(UPDATED_SINIF);

        restTibbiCihazTehlikeSinifiMockMvc.perform(put("/api/tibbi-cihaz-tehlike-sinifis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTibbiCihazTehlikeSinifi)))
            .andExpect(status().isOk());

        // Validate the TibbiCihazTehlikeSinifi in the database
        List<TibbiCihazTehlikeSinifi> tibbiCihazTehlikeSinifiList = tibbiCihazTehlikeSinifiRepository.findAll();
        assertThat(tibbiCihazTehlikeSinifiList).hasSize(databaseSizeBeforeUpdate);
        TibbiCihazTehlikeSinifi testTibbiCihazTehlikeSinifi = tibbiCihazTehlikeSinifiList.get(tibbiCihazTehlikeSinifiList.size() - 1);
        assertThat(testTibbiCihazTehlikeSinifi.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testTibbiCihazTehlikeSinifi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testTibbiCihazTehlikeSinifi.getSinif()).isEqualTo(UPDATED_SINIF);
    }

    @Test
    @Transactional
    public void updateNonExistingTibbiCihazTehlikeSinifi() throws Exception {
        int databaseSizeBeforeUpdate = tibbiCihazTehlikeSinifiRepository.findAll().size();

        // Create the TibbiCihazTehlikeSinifi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTibbiCihazTehlikeSinifiMockMvc.perform(put("/api/tibbi-cihaz-tehlike-sinifis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tibbiCihazTehlikeSinifi)))
            .andExpect(status().isCreated());

        // Validate the TibbiCihazTehlikeSinifi in the database
        List<TibbiCihazTehlikeSinifi> tibbiCihazTehlikeSinifiList = tibbiCihazTehlikeSinifiRepository.findAll();
        assertThat(tibbiCihazTehlikeSinifiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTibbiCihazTehlikeSinifi() throws Exception {
        // Initialize the database
        tibbiCihazTehlikeSinifiRepository.saveAndFlush(tibbiCihazTehlikeSinifi);
        int databaseSizeBeforeDelete = tibbiCihazTehlikeSinifiRepository.findAll().size();

        // Get the tibbiCihazTehlikeSinifi
        restTibbiCihazTehlikeSinifiMockMvc.perform(delete("/api/tibbi-cihaz-tehlike-sinifis/{id}", tibbiCihazTehlikeSinifi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TibbiCihazTehlikeSinifi> tibbiCihazTehlikeSinifiList = tibbiCihazTehlikeSinifiRepository.findAll();
        assertThat(tibbiCihazTehlikeSinifiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TibbiCihazTehlikeSinifi.class);
        TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi1 = new TibbiCihazTehlikeSinifi();
        tibbiCihazTehlikeSinifi1.setId(1L);
        TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi2 = new TibbiCihazTehlikeSinifi();
        tibbiCihazTehlikeSinifi2.setId(tibbiCihazTehlikeSinifi1.getId());
        assertThat(tibbiCihazTehlikeSinifi1).isEqualTo(tibbiCihazTehlikeSinifi2);
        tibbiCihazTehlikeSinifi2.setId(2L);
        assertThat(tibbiCihazTehlikeSinifi1).isNotEqualTo(tibbiCihazTehlikeSinifi2);
        tibbiCihazTehlikeSinifi1.setId(null);
        assertThat(tibbiCihazTehlikeSinifi1).isNotEqualTo(tibbiCihazTehlikeSinifi2);
    }
}

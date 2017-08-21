package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.BelgeTipi;
import tr.gov.sb.sygm.repository.BelgeTipiRepository;
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
 * Test class for the BelgeTipiResource REST controller.
 *
 * @see BelgeTipiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class BelgeTipiResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private BelgeTipiRepository belgeTipiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBelgeTipiMockMvc;

    private BelgeTipi belgeTipi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BelgeTipiResource belgeTipiResource = new BelgeTipiResource(belgeTipiRepository);
        this.restBelgeTipiMockMvc = MockMvcBuilders.standaloneSetup(belgeTipiResource)
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
    public static BelgeTipi createEntity(EntityManager em) {
        BelgeTipi belgeTipi = new BelgeTipi()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .label(DEFAULT_LABEL);
        return belgeTipi;
    }

    @Before
    public void initTest() {
        belgeTipi = createEntity(em);
    }

    @Test
    @Transactional
    public void createBelgeTipi() throws Exception {
        int databaseSizeBeforeCreate = belgeTipiRepository.findAll().size();

        // Create the BelgeTipi
        restBelgeTipiMockMvc.perform(post("/api/belge-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belgeTipi)))
            .andExpect(status().isCreated());

        // Validate the BelgeTipi in the database
        List<BelgeTipi> belgeTipiList = belgeTipiRepository.findAll();
        assertThat(belgeTipiList).hasSize(databaseSizeBeforeCreate + 1);
        BelgeTipi testBelgeTipi = belgeTipiList.get(belgeTipiList.size() - 1);
        assertThat(testBelgeTipi.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBelgeTipi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testBelgeTipi.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createBelgeTipiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = belgeTipiRepository.findAll().size();

        // Create the BelgeTipi with an existing ID
        belgeTipi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBelgeTipiMockMvc.perform(post("/api/belge-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belgeTipi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BelgeTipi> belgeTipiList = belgeTipiRepository.findAll();
        assertThat(belgeTipiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeTipiRepository.findAll().size();
        // set the field null
        belgeTipi.setDeleted(null);

        // Create the BelgeTipi, which fails.

        restBelgeTipiMockMvc.perform(post("/api/belge-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belgeTipi)))
            .andExpect(status().isBadRequest());

        List<BelgeTipi> belgeTipiList = belgeTipiRepository.findAll();
        assertThat(belgeTipiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeTipiRepository.findAll().size();
        // set the field null
        belgeTipi.setVersion(null);

        // Create the BelgeTipi, which fails.

        restBelgeTipiMockMvc.perform(post("/api/belge-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belgeTipi)))
            .andExpect(status().isBadRequest());

        List<BelgeTipi> belgeTipiList = belgeTipiRepository.findAll();
        assertThat(belgeTipiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = belgeTipiRepository.findAll().size();
        // set the field null
        belgeTipi.setLabel(null);

        // Create the BelgeTipi, which fails.

        restBelgeTipiMockMvc.perform(post("/api/belge-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belgeTipi)))
            .andExpect(status().isBadRequest());

        List<BelgeTipi> belgeTipiList = belgeTipiRepository.findAll();
        assertThat(belgeTipiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBelgeTipis() throws Exception {
        // Initialize the database
        belgeTipiRepository.saveAndFlush(belgeTipi);

        // Get all the belgeTipiList
        restBelgeTipiMockMvc.perform(get("/api/belge-tipis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(belgeTipi.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getBelgeTipi() throws Exception {
        // Initialize the database
        belgeTipiRepository.saveAndFlush(belgeTipi);

        // Get the belgeTipi
        restBelgeTipiMockMvc.perform(get("/api/belge-tipis/{id}", belgeTipi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(belgeTipi.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBelgeTipi() throws Exception {
        // Get the belgeTipi
        restBelgeTipiMockMvc.perform(get("/api/belge-tipis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBelgeTipi() throws Exception {
        // Initialize the database
        belgeTipiRepository.saveAndFlush(belgeTipi);
        int databaseSizeBeforeUpdate = belgeTipiRepository.findAll().size();

        // Update the belgeTipi
        BelgeTipi updatedBelgeTipi = belgeTipiRepository.findOne(belgeTipi.getId());
        updatedBelgeTipi
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .label(UPDATED_LABEL);

        restBelgeTipiMockMvc.perform(put("/api/belge-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBelgeTipi)))
            .andExpect(status().isOk());

        // Validate the BelgeTipi in the database
        List<BelgeTipi> belgeTipiList = belgeTipiRepository.findAll();
        assertThat(belgeTipiList).hasSize(databaseSizeBeforeUpdate);
        BelgeTipi testBelgeTipi = belgeTipiList.get(belgeTipiList.size() - 1);
        assertThat(testBelgeTipi.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBelgeTipi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testBelgeTipi.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingBelgeTipi() throws Exception {
        int databaseSizeBeforeUpdate = belgeTipiRepository.findAll().size();

        // Create the BelgeTipi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBelgeTipiMockMvc.perform(put("/api/belge-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belgeTipi)))
            .andExpect(status().isCreated());

        // Validate the BelgeTipi in the database
        List<BelgeTipi> belgeTipiList = belgeTipiRepository.findAll();
        assertThat(belgeTipiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBelgeTipi() throws Exception {
        // Initialize the database
        belgeTipiRepository.saveAndFlush(belgeTipi);
        int databaseSizeBeforeDelete = belgeTipiRepository.findAll().size();

        // Get the belgeTipi
        restBelgeTipiMockMvc.perform(delete("/api/belge-tipis/{id}", belgeTipi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BelgeTipi> belgeTipiList = belgeTipiRepository.findAll();
        assertThat(belgeTipiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BelgeTipi.class);
        BelgeTipi belgeTipi1 = new BelgeTipi();
        belgeTipi1.setId(1L);
        BelgeTipi belgeTipi2 = new BelgeTipi();
        belgeTipi2.setId(belgeTipi1.getId());
        assertThat(belgeTipi1).isEqualTo(belgeTipi2);
        belgeTipi2.setId(2L);
        assertThat(belgeTipi1).isNotEqualTo(belgeTipi2);
        belgeTipi1.setId(null);
        assertThat(belgeTipi1).isNotEqualTo(belgeTipi2);
    }
}

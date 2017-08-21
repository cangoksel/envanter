package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.KurulusTipi;
import tr.gov.sb.sygm.repository.KurulusTipiRepository;
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
 * Test class for the KurulusTipiResource REST controller.
 *
 * @see KurulusTipiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class KurulusTipiResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private KurulusTipiRepository kurulusTipiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKurulusTipiMockMvc;

    private KurulusTipi kurulusTipi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KurulusTipiResource kurulusTipiResource = new KurulusTipiResource(kurulusTipiRepository);
        this.restKurulusTipiMockMvc = MockMvcBuilders.standaloneSetup(kurulusTipiResource)
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
    public static KurulusTipi createEntity(EntityManager em) {
        KurulusTipi kurulusTipi = new KurulusTipi()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .label(DEFAULT_LABEL);
        return kurulusTipi;
    }

    @Before
    public void initTest() {
        kurulusTipi = createEntity(em);
    }

    @Test
    @Transactional
    public void createKurulusTipi() throws Exception {
        int databaseSizeBeforeCreate = kurulusTipiRepository.findAll().size();

        // Create the KurulusTipi
        restKurulusTipiMockMvc.perform(post("/api/kurulus-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulusTipi)))
            .andExpect(status().isCreated());

        // Validate the KurulusTipi in the database
        List<KurulusTipi> kurulusTipiList = kurulusTipiRepository.findAll();
        assertThat(kurulusTipiList).hasSize(databaseSizeBeforeCreate + 1);
        KurulusTipi testKurulusTipi = kurulusTipiList.get(kurulusTipiList.size() - 1);
        assertThat(testKurulusTipi.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testKurulusTipi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testKurulusTipi.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createKurulusTipiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kurulusTipiRepository.findAll().size();

        // Create the KurulusTipi with an existing ID
        kurulusTipi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKurulusTipiMockMvc.perform(post("/api/kurulus-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulusTipi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<KurulusTipi> kurulusTipiList = kurulusTipiRepository.findAll();
        assertThat(kurulusTipiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurulusTipiRepository.findAll().size();
        // set the field null
        kurulusTipi.setDeleted(null);

        // Create the KurulusTipi, which fails.

        restKurulusTipiMockMvc.perform(post("/api/kurulus-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulusTipi)))
            .andExpect(status().isBadRequest());

        List<KurulusTipi> kurulusTipiList = kurulusTipiRepository.findAll();
        assertThat(kurulusTipiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurulusTipiRepository.findAll().size();
        // set the field null
        kurulusTipi.setVersion(null);

        // Create the KurulusTipi, which fails.

        restKurulusTipiMockMvc.perform(post("/api/kurulus-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulusTipi)))
            .andExpect(status().isBadRequest());

        List<KurulusTipi> kurulusTipiList = kurulusTipiRepository.findAll();
        assertThat(kurulusTipiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = kurulusTipiRepository.findAll().size();
        // set the field null
        kurulusTipi.setLabel(null);

        // Create the KurulusTipi, which fails.

        restKurulusTipiMockMvc.perform(post("/api/kurulus-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulusTipi)))
            .andExpect(status().isBadRequest());

        List<KurulusTipi> kurulusTipiList = kurulusTipiRepository.findAll();
        assertThat(kurulusTipiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKurulusTipis() throws Exception {
        // Initialize the database
        kurulusTipiRepository.saveAndFlush(kurulusTipi);

        // Get all the kurulusTipiList
        restKurulusTipiMockMvc.perform(get("/api/kurulus-tipis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kurulusTipi.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getKurulusTipi() throws Exception {
        // Initialize the database
        kurulusTipiRepository.saveAndFlush(kurulusTipi);

        // Get the kurulusTipi
        restKurulusTipiMockMvc.perform(get("/api/kurulus-tipis/{id}", kurulusTipi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kurulusTipi.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKurulusTipi() throws Exception {
        // Get the kurulusTipi
        restKurulusTipiMockMvc.perform(get("/api/kurulus-tipis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKurulusTipi() throws Exception {
        // Initialize the database
        kurulusTipiRepository.saveAndFlush(kurulusTipi);
        int databaseSizeBeforeUpdate = kurulusTipiRepository.findAll().size();

        // Update the kurulusTipi
        KurulusTipi updatedKurulusTipi = kurulusTipiRepository.findOne(kurulusTipi.getId());
        updatedKurulusTipi
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .label(UPDATED_LABEL);

        restKurulusTipiMockMvc.perform(put("/api/kurulus-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKurulusTipi)))
            .andExpect(status().isOk());

        // Validate the KurulusTipi in the database
        List<KurulusTipi> kurulusTipiList = kurulusTipiRepository.findAll();
        assertThat(kurulusTipiList).hasSize(databaseSizeBeforeUpdate);
        KurulusTipi testKurulusTipi = kurulusTipiList.get(kurulusTipiList.size() - 1);
        assertThat(testKurulusTipi.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testKurulusTipi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testKurulusTipi.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingKurulusTipi() throws Exception {
        int databaseSizeBeforeUpdate = kurulusTipiRepository.findAll().size();

        // Create the KurulusTipi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKurulusTipiMockMvc.perform(put("/api/kurulus-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kurulusTipi)))
            .andExpect(status().isCreated());

        // Validate the KurulusTipi in the database
        List<KurulusTipi> kurulusTipiList = kurulusTipiRepository.findAll();
        assertThat(kurulusTipiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKurulusTipi() throws Exception {
        // Initialize the database
        kurulusTipiRepository.saveAndFlush(kurulusTipi);
        int databaseSizeBeforeDelete = kurulusTipiRepository.findAll().size();

        // Get the kurulusTipi
        restKurulusTipiMockMvc.perform(delete("/api/kurulus-tipis/{id}", kurulusTipi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KurulusTipi> kurulusTipiList = kurulusTipiRepository.findAll();
        assertThat(kurulusTipiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KurulusTipi.class);
        KurulusTipi kurulusTipi1 = new KurulusTipi();
        kurulusTipi1.setId(1L);
        KurulusTipi kurulusTipi2 = new KurulusTipi();
        kurulusTipi2.setId(kurulusTipi1.getId());
        assertThat(kurulusTipi1).isEqualTo(kurulusTipi2);
        kurulusTipi2.setId(2L);
        assertThat(kurulusTipi1).isNotEqualTo(kurulusTipi2);
        kurulusTipi1.setId(null);
        assertThat(kurulusTipi1).isNotEqualTo(kurulusTipi2);
    }
}

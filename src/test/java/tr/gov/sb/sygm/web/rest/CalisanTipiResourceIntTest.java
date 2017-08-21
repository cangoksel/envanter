package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.CalisanTipi;
import tr.gov.sb.sygm.repository.CalisanTipiRepository;
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
 * Test class for the CalisanTipiResource REST controller.
 *
 * @see CalisanTipiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class CalisanTipiResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private CalisanTipiRepository calisanTipiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCalisanTipiMockMvc;

    private CalisanTipi calisanTipi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalisanTipiResource calisanTipiResource = new CalisanTipiResource(calisanTipiRepository);
        this.restCalisanTipiMockMvc = MockMvcBuilders.standaloneSetup(calisanTipiResource)
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
    public static CalisanTipi createEntity(EntityManager em) {
        CalisanTipi calisanTipi = new CalisanTipi()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .label(DEFAULT_LABEL);
        return calisanTipi;
    }

    @Before
    public void initTest() {
        calisanTipi = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalisanTipi() throws Exception {
        int databaseSizeBeforeCreate = calisanTipiRepository.findAll().size();

        // Create the CalisanTipi
        restCalisanTipiMockMvc.perform(post("/api/calisan-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanTipi)))
            .andExpect(status().isCreated());

        // Validate the CalisanTipi in the database
        List<CalisanTipi> calisanTipiList = calisanTipiRepository.findAll();
        assertThat(calisanTipiList).hasSize(databaseSizeBeforeCreate + 1);
        CalisanTipi testCalisanTipi = calisanTipiList.get(calisanTipiList.size() - 1);
        assertThat(testCalisanTipi.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testCalisanTipi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCalisanTipi.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createCalisanTipiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calisanTipiRepository.findAll().size();

        // Create the CalisanTipi with an existing ID
        calisanTipi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalisanTipiMockMvc.perform(post("/api/calisan-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanTipi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CalisanTipi> calisanTipiList = calisanTipiRepository.findAll();
        assertThat(calisanTipiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = calisanTipiRepository.findAll().size();
        // set the field null
        calisanTipi.setDeleted(null);

        // Create the CalisanTipi, which fails.

        restCalisanTipiMockMvc.perform(post("/api/calisan-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanTipi)))
            .andExpect(status().isBadRequest());

        List<CalisanTipi> calisanTipiList = calisanTipiRepository.findAll();
        assertThat(calisanTipiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = calisanTipiRepository.findAll().size();
        // set the field null
        calisanTipi.setVersion(null);

        // Create the CalisanTipi, which fails.

        restCalisanTipiMockMvc.perform(post("/api/calisan-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanTipi)))
            .andExpect(status().isBadRequest());

        List<CalisanTipi> calisanTipiList = calisanTipiRepository.findAll();
        assertThat(calisanTipiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = calisanTipiRepository.findAll().size();
        // set the field null
        calisanTipi.setLabel(null);

        // Create the CalisanTipi, which fails.

        restCalisanTipiMockMvc.perform(post("/api/calisan-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanTipi)))
            .andExpect(status().isBadRequest());

        List<CalisanTipi> calisanTipiList = calisanTipiRepository.findAll();
        assertThat(calisanTipiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalisanTipis() throws Exception {
        // Initialize the database
        calisanTipiRepository.saveAndFlush(calisanTipi);

        // Get all the calisanTipiList
        restCalisanTipiMockMvc.perform(get("/api/calisan-tipis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calisanTipi.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getCalisanTipi() throws Exception {
        // Initialize the database
        calisanTipiRepository.saveAndFlush(calisanTipi);

        // Get the calisanTipi
        restCalisanTipiMockMvc.perform(get("/api/calisan-tipis/{id}", calisanTipi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calisanTipi.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalisanTipi() throws Exception {
        // Get the calisanTipi
        restCalisanTipiMockMvc.perform(get("/api/calisan-tipis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalisanTipi() throws Exception {
        // Initialize the database
        calisanTipiRepository.saveAndFlush(calisanTipi);
        int databaseSizeBeforeUpdate = calisanTipiRepository.findAll().size();

        // Update the calisanTipi
        CalisanTipi updatedCalisanTipi = calisanTipiRepository.findOne(calisanTipi.getId());
        updatedCalisanTipi
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .label(UPDATED_LABEL);

        restCalisanTipiMockMvc.perform(put("/api/calisan-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalisanTipi)))
            .andExpect(status().isOk());

        // Validate the CalisanTipi in the database
        List<CalisanTipi> calisanTipiList = calisanTipiRepository.findAll();
        assertThat(calisanTipiList).hasSize(databaseSizeBeforeUpdate);
        CalisanTipi testCalisanTipi = calisanTipiList.get(calisanTipiList.size() - 1);
        assertThat(testCalisanTipi.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testCalisanTipi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCalisanTipi.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingCalisanTipi() throws Exception {
        int databaseSizeBeforeUpdate = calisanTipiRepository.findAll().size();

        // Create the CalisanTipi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCalisanTipiMockMvc.perform(put("/api/calisan-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanTipi)))
            .andExpect(status().isCreated());

        // Validate the CalisanTipi in the database
        List<CalisanTipi> calisanTipiList = calisanTipiRepository.findAll();
        assertThat(calisanTipiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCalisanTipi() throws Exception {
        // Initialize the database
        calisanTipiRepository.saveAndFlush(calisanTipi);
        int databaseSizeBeforeDelete = calisanTipiRepository.findAll().size();

        // Get the calisanTipi
        restCalisanTipiMockMvc.perform(delete("/api/calisan-tipis/{id}", calisanTipi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CalisanTipi> calisanTipiList = calisanTipiRepository.findAll();
        assertThat(calisanTipiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalisanTipi.class);
        CalisanTipi calisanTipi1 = new CalisanTipi();
        calisanTipi1.setId(1L);
        CalisanTipi calisanTipi2 = new CalisanTipi();
        calisanTipi2.setId(calisanTipi1.getId());
        assertThat(calisanTipi1).isEqualTo(calisanTipi2);
        calisanTipi2.setId(2L);
        assertThat(calisanTipi1).isNotEqualTo(calisanTipi2);
        calisanTipi1.setId(null);
        assertThat(calisanTipi1).isNotEqualTo(calisanTipi2);
    }
}

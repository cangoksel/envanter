package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.FaaliyetAlani;
import tr.gov.sb.sygm.repository.FaaliyetAlaniRepository;
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
 * Test class for the FaaliyetAlaniResource REST controller.
 *
 * @see FaaliyetAlaniResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class FaaliyetAlaniResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private FaaliyetAlaniRepository faaliyetAlaniRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFaaliyetAlaniMockMvc;

    private FaaliyetAlani faaliyetAlani;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FaaliyetAlaniResource faaliyetAlaniResource = new FaaliyetAlaniResource(faaliyetAlaniRepository);
        this.restFaaliyetAlaniMockMvc = MockMvcBuilders.standaloneSetup(faaliyetAlaniResource)
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
    public static FaaliyetAlani createEntity(EntityManager em) {
        FaaliyetAlani faaliyetAlani = new FaaliyetAlani()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .label(DEFAULT_LABEL);
        return faaliyetAlani;
    }

    @Before
    public void initTest() {
        faaliyetAlani = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaaliyetAlani() throws Exception {
        int databaseSizeBeforeCreate = faaliyetAlaniRepository.findAll().size();

        // Create the FaaliyetAlani
        restFaaliyetAlaniMockMvc.perform(post("/api/faaliyet-alanis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetAlani)))
            .andExpect(status().isCreated());

        // Validate the FaaliyetAlani in the database
        List<FaaliyetAlani> faaliyetAlaniList = faaliyetAlaniRepository.findAll();
        assertThat(faaliyetAlaniList).hasSize(databaseSizeBeforeCreate + 1);
        FaaliyetAlani testFaaliyetAlani = faaliyetAlaniList.get(faaliyetAlaniList.size() - 1);
        assertThat(testFaaliyetAlani.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testFaaliyetAlani.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFaaliyetAlani.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createFaaliyetAlaniWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faaliyetAlaniRepository.findAll().size();

        // Create the FaaliyetAlani with an existing ID
        faaliyetAlani.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaaliyetAlaniMockMvc.perform(post("/api/faaliyet-alanis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetAlani)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FaaliyetAlani> faaliyetAlaniList = faaliyetAlaniRepository.findAll();
        assertThat(faaliyetAlaniList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = faaliyetAlaniRepository.findAll().size();
        // set the field null
        faaliyetAlani.setDeleted(null);

        // Create the FaaliyetAlani, which fails.

        restFaaliyetAlaniMockMvc.perform(post("/api/faaliyet-alanis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetAlani)))
            .andExpect(status().isBadRequest());

        List<FaaliyetAlani> faaliyetAlaniList = faaliyetAlaniRepository.findAll();
        assertThat(faaliyetAlaniList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = faaliyetAlaniRepository.findAll().size();
        // set the field null
        faaliyetAlani.setVersion(null);

        // Create the FaaliyetAlani, which fails.

        restFaaliyetAlaniMockMvc.perform(post("/api/faaliyet-alanis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetAlani)))
            .andExpect(status().isBadRequest());

        List<FaaliyetAlani> faaliyetAlaniList = faaliyetAlaniRepository.findAll();
        assertThat(faaliyetAlaniList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = faaliyetAlaniRepository.findAll().size();
        // set the field null
        faaliyetAlani.setLabel(null);

        // Create the FaaliyetAlani, which fails.

        restFaaliyetAlaniMockMvc.perform(post("/api/faaliyet-alanis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetAlani)))
            .andExpect(status().isBadRequest());

        List<FaaliyetAlani> faaliyetAlaniList = faaliyetAlaniRepository.findAll();
        assertThat(faaliyetAlaniList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaaliyetAlanis() throws Exception {
        // Initialize the database
        faaliyetAlaniRepository.saveAndFlush(faaliyetAlani);

        // Get all the faaliyetAlaniList
        restFaaliyetAlaniMockMvc.perform(get("/api/faaliyet-alanis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faaliyetAlani.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getFaaliyetAlani() throws Exception {
        // Initialize the database
        faaliyetAlaniRepository.saveAndFlush(faaliyetAlani);

        // Get the faaliyetAlani
        restFaaliyetAlaniMockMvc.perform(get("/api/faaliyet-alanis/{id}", faaliyetAlani.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faaliyetAlani.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFaaliyetAlani() throws Exception {
        // Get the faaliyetAlani
        restFaaliyetAlaniMockMvc.perform(get("/api/faaliyet-alanis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaaliyetAlani() throws Exception {
        // Initialize the database
        faaliyetAlaniRepository.saveAndFlush(faaliyetAlani);
        int databaseSizeBeforeUpdate = faaliyetAlaniRepository.findAll().size();

        // Update the faaliyetAlani
        FaaliyetAlani updatedFaaliyetAlani = faaliyetAlaniRepository.findOne(faaliyetAlani.getId());
        updatedFaaliyetAlani
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .label(UPDATED_LABEL);

        restFaaliyetAlaniMockMvc.perform(put("/api/faaliyet-alanis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaaliyetAlani)))
            .andExpect(status().isOk());

        // Validate the FaaliyetAlani in the database
        List<FaaliyetAlani> faaliyetAlaniList = faaliyetAlaniRepository.findAll();
        assertThat(faaliyetAlaniList).hasSize(databaseSizeBeforeUpdate);
        FaaliyetAlani testFaaliyetAlani = faaliyetAlaniList.get(faaliyetAlaniList.size() - 1);
        assertThat(testFaaliyetAlani.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testFaaliyetAlani.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFaaliyetAlani.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingFaaliyetAlani() throws Exception {
        int databaseSizeBeforeUpdate = faaliyetAlaniRepository.findAll().size();

        // Create the FaaliyetAlani

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFaaliyetAlaniMockMvc.perform(put("/api/faaliyet-alanis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faaliyetAlani)))
            .andExpect(status().isCreated());

        // Validate the FaaliyetAlani in the database
        List<FaaliyetAlani> faaliyetAlaniList = faaliyetAlaniRepository.findAll();
        assertThat(faaliyetAlaniList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFaaliyetAlani() throws Exception {
        // Initialize the database
        faaliyetAlaniRepository.saveAndFlush(faaliyetAlani);
        int databaseSizeBeforeDelete = faaliyetAlaniRepository.findAll().size();

        // Get the faaliyetAlani
        restFaaliyetAlaniMockMvc.perform(delete("/api/faaliyet-alanis/{id}", faaliyetAlani.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FaaliyetAlani> faaliyetAlaniList = faaliyetAlaniRepository.findAll();
        assertThat(faaliyetAlaniList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaaliyetAlani.class);
        FaaliyetAlani faaliyetAlani1 = new FaaliyetAlani();
        faaliyetAlani1.setId(1L);
        FaaliyetAlani faaliyetAlani2 = new FaaliyetAlani();
        faaliyetAlani2.setId(faaliyetAlani1.getId());
        assertThat(faaliyetAlani1).isEqualTo(faaliyetAlani2);
        faaliyetAlani2.setId(2L);
        assertThat(faaliyetAlani1).isNotEqualTo(faaliyetAlani2);
        faaliyetAlani1.setId(null);
        assertThat(faaliyetAlani1).isNotEqualTo(faaliyetAlani2);
    }
}

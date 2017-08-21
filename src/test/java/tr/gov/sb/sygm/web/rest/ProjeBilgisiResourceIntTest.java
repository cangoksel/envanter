package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.ProjeBilgisi;
import tr.gov.sb.sygm.repository.ProjeBilgisiRepository;
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
 * Test class for the ProjeBilgisiResource REST controller.
 *
 * @see ProjeBilgisiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class ProjeBilgisiResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_KONUSU = "AAAAAAAAAA";
    private static final String UPDATED_KONUSU = "BBBBBBBBBB";

    @Autowired
    private ProjeBilgisiRepository projeBilgisiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjeBilgisiMockMvc;

    private ProjeBilgisi projeBilgisi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjeBilgisiResource projeBilgisiResource = new ProjeBilgisiResource(projeBilgisiRepository);
        this.restProjeBilgisiMockMvc = MockMvcBuilders.standaloneSetup(projeBilgisiResource)
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
    public static ProjeBilgisi createEntity(EntityManager em) {
        ProjeBilgisi projeBilgisi = new ProjeBilgisi()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .konusu(DEFAULT_KONUSU);
        return projeBilgisi;
    }

    @Before
    public void initTest() {
        projeBilgisi = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjeBilgisi() throws Exception {
        int databaseSizeBeforeCreate = projeBilgisiRepository.findAll().size();

        // Create the ProjeBilgisi
        restProjeBilgisiMockMvc.perform(post("/api/proje-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeBilgisi)))
            .andExpect(status().isCreated());

        // Validate the ProjeBilgisi in the database
        List<ProjeBilgisi> projeBilgisiList = projeBilgisiRepository.findAll();
        assertThat(projeBilgisiList).hasSize(databaseSizeBeforeCreate + 1);
        ProjeBilgisi testProjeBilgisi = projeBilgisiList.get(projeBilgisiList.size() - 1);
        assertThat(testProjeBilgisi.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testProjeBilgisi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProjeBilgisi.getKonusu()).isEqualTo(DEFAULT_KONUSU);
    }

    @Test
    @Transactional
    public void createProjeBilgisiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projeBilgisiRepository.findAll().size();

        // Create the ProjeBilgisi with an existing ID
        projeBilgisi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjeBilgisiMockMvc.perform(post("/api/proje-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeBilgisi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProjeBilgisi> projeBilgisiList = projeBilgisiRepository.findAll();
        assertThat(projeBilgisiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = projeBilgisiRepository.findAll().size();
        // set the field null
        projeBilgisi.setDeleted(null);

        // Create the ProjeBilgisi, which fails.

        restProjeBilgisiMockMvc.perform(post("/api/proje-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeBilgisi)))
            .andExpect(status().isBadRequest());

        List<ProjeBilgisi> projeBilgisiList = projeBilgisiRepository.findAll();
        assertThat(projeBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projeBilgisiRepository.findAll().size();
        // set the field null
        projeBilgisi.setVersion(null);

        // Create the ProjeBilgisi, which fails.

        restProjeBilgisiMockMvc.perform(post("/api/proje-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeBilgisi)))
            .andExpect(status().isBadRequest());

        List<ProjeBilgisi> projeBilgisiList = projeBilgisiRepository.findAll();
        assertThat(projeBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKonusuIsRequired() throws Exception {
        int databaseSizeBeforeTest = projeBilgisiRepository.findAll().size();
        // set the field null
        projeBilgisi.setKonusu(null);

        // Create the ProjeBilgisi, which fails.

        restProjeBilgisiMockMvc.perform(post("/api/proje-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeBilgisi)))
            .andExpect(status().isBadRequest());

        List<ProjeBilgisi> projeBilgisiList = projeBilgisiRepository.findAll();
        assertThat(projeBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjeBilgisis() throws Exception {
        // Initialize the database
        projeBilgisiRepository.saveAndFlush(projeBilgisi);

        // Get all the projeBilgisiList
        restProjeBilgisiMockMvc.perform(get("/api/proje-bilgisis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projeBilgisi.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].konusu").value(hasItem(DEFAULT_KONUSU.toString())));
    }

    @Test
    @Transactional
    public void getProjeBilgisi() throws Exception {
        // Initialize the database
        projeBilgisiRepository.saveAndFlush(projeBilgisi);

        // Get the projeBilgisi
        restProjeBilgisiMockMvc.perform(get("/api/proje-bilgisis/{id}", projeBilgisi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projeBilgisi.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.konusu").value(DEFAULT_KONUSU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjeBilgisi() throws Exception {
        // Get the projeBilgisi
        restProjeBilgisiMockMvc.perform(get("/api/proje-bilgisis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjeBilgisi() throws Exception {
        // Initialize the database
        projeBilgisiRepository.saveAndFlush(projeBilgisi);
        int databaseSizeBeforeUpdate = projeBilgisiRepository.findAll().size();

        // Update the projeBilgisi
        ProjeBilgisi updatedProjeBilgisi = projeBilgisiRepository.findOne(projeBilgisi.getId());
        updatedProjeBilgisi
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .konusu(UPDATED_KONUSU);

        restProjeBilgisiMockMvc.perform(put("/api/proje-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjeBilgisi)))
            .andExpect(status().isOk());

        // Validate the ProjeBilgisi in the database
        List<ProjeBilgisi> projeBilgisiList = projeBilgisiRepository.findAll();
        assertThat(projeBilgisiList).hasSize(databaseSizeBeforeUpdate);
        ProjeBilgisi testProjeBilgisi = projeBilgisiList.get(projeBilgisiList.size() - 1);
        assertThat(testProjeBilgisi.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testProjeBilgisi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProjeBilgisi.getKonusu()).isEqualTo(UPDATED_KONUSU);
    }

    @Test
    @Transactional
    public void updateNonExistingProjeBilgisi() throws Exception {
        int databaseSizeBeforeUpdate = projeBilgisiRepository.findAll().size();

        // Create the ProjeBilgisi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjeBilgisiMockMvc.perform(put("/api/proje-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeBilgisi)))
            .andExpect(status().isCreated());

        // Validate the ProjeBilgisi in the database
        List<ProjeBilgisi> projeBilgisiList = projeBilgisiRepository.findAll();
        assertThat(projeBilgisiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjeBilgisi() throws Exception {
        // Initialize the database
        projeBilgisiRepository.saveAndFlush(projeBilgisi);
        int databaseSizeBeforeDelete = projeBilgisiRepository.findAll().size();

        // Get the projeBilgisi
        restProjeBilgisiMockMvc.perform(delete("/api/proje-bilgisis/{id}", projeBilgisi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjeBilgisi> projeBilgisiList = projeBilgisiRepository.findAll();
        assertThat(projeBilgisiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjeBilgisi.class);
        ProjeBilgisi projeBilgisi1 = new ProjeBilgisi();
        projeBilgisi1.setId(1L);
        ProjeBilgisi projeBilgisi2 = new ProjeBilgisi();
        projeBilgisi2.setId(projeBilgisi1.getId());
        assertThat(projeBilgisi1).isEqualTo(projeBilgisi2);
        projeBilgisi2.setId(2L);
        assertThat(projeBilgisi1).isNotEqualTo(projeBilgisi2);
        projeBilgisi1.setId(null);
        assertThat(projeBilgisi1).isNotEqualTo(projeBilgisi2);
    }
}

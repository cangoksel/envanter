package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.UrunGrubu;
import tr.gov.sb.sygm.repository.UrunGrubuRepository;
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
 * Test class for the UrunGrubuResource REST controller.
 *
 * @see UrunGrubuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class UrunGrubuResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private UrunGrubuRepository urunGrubuRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUrunGrubuMockMvc;

    private UrunGrubu urunGrubu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UrunGrubuResource urunGrubuResource = new UrunGrubuResource(urunGrubuRepository);
        this.restUrunGrubuMockMvc = MockMvcBuilders.standaloneSetup(urunGrubuResource)
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
    public static UrunGrubu createEntity(EntityManager em) {
        UrunGrubu urunGrubu = new UrunGrubu()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .label(DEFAULT_LABEL);
        return urunGrubu;
    }

    @Before
    public void initTest() {
        urunGrubu = createEntity(em);
    }

    @Test
    @Transactional
    public void createUrunGrubu() throws Exception {
        int databaseSizeBeforeCreate = urunGrubuRepository.findAll().size();

        // Create the UrunGrubu
        restUrunGrubuMockMvc.perform(post("/api/urun-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubu)))
            .andExpect(status().isCreated());

        // Validate the UrunGrubu in the database
        List<UrunGrubu> urunGrubuList = urunGrubuRepository.findAll();
        assertThat(urunGrubuList).hasSize(databaseSizeBeforeCreate + 1);
        UrunGrubu testUrunGrubu = urunGrubuList.get(urunGrubuList.size() - 1);
        assertThat(testUrunGrubu.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testUrunGrubu.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUrunGrubu.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createUrunGrubuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = urunGrubuRepository.findAll().size();

        // Create the UrunGrubu with an existing ID
        urunGrubu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrunGrubuMockMvc.perform(post("/api/urun-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UrunGrubu> urunGrubuList = urunGrubuRepository.findAll();
        assertThat(urunGrubuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunGrubuRepository.findAll().size();
        // set the field null
        urunGrubu.setDeleted(null);

        // Create the UrunGrubu, which fails.

        restUrunGrubuMockMvc.perform(post("/api/urun-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubu)))
            .andExpect(status().isBadRequest());

        List<UrunGrubu> urunGrubuList = urunGrubuRepository.findAll();
        assertThat(urunGrubuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunGrubuRepository.findAll().size();
        // set the field null
        urunGrubu.setVersion(null);

        // Create the UrunGrubu, which fails.

        restUrunGrubuMockMvc.perform(post("/api/urun-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubu)))
            .andExpect(status().isBadRequest());

        List<UrunGrubu> urunGrubuList = urunGrubuRepository.findAll();
        assertThat(urunGrubuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = urunGrubuRepository.findAll().size();
        // set the field null
        urunGrubu.setLabel(null);

        // Create the UrunGrubu, which fails.

        restUrunGrubuMockMvc.perform(post("/api/urun-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubu)))
            .andExpect(status().isBadRequest());

        List<UrunGrubu> urunGrubuList = urunGrubuRepository.findAll();
        assertThat(urunGrubuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUrunGrubus() throws Exception {
        // Initialize the database
        urunGrubuRepository.saveAndFlush(urunGrubu);

        // Get all the urunGrubuList
        restUrunGrubuMockMvc.perform(get("/api/urun-grubus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urunGrubu.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getUrunGrubu() throws Exception {
        // Initialize the database
        urunGrubuRepository.saveAndFlush(urunGrubu);

        // Get the urunGrubu
        restUrunGrubuMockMvc.perform(get("/api/urun-grubus/{id}", urunGrubu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(urunGrubu.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUrunGrubu() throws Exception {
        // Get the urunGrubu
        restUrunGrubuMockMvc.perform(get("/api/urun-grubus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUrunGrubu() throws Exception {
        // Initialize the database
        urunGrubuRepository.saveAndFlush(urunGrubu);
        int databaseSizeBeforeUpdate = urunGrubuRepository.findAll().size();

        // Update the urunGrubu
        UrunGrubu updatedUrunGrubu = urunGrubuRepository.findOne(urunGrubu.getId());
        updatedUrunGrubu
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .label(UPDATED_LABEL);

        restUrunGrubuMockMvc.perform(put("/api/urun-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUrunGrubu)))
            .andExpect(status().isOk());

        // Validate the UrunGrubu in the database
        List<UrunGrubu> urunGrubuList = urunGrubuRepository.findAll();
        assertThat(urunGrubuList).hasSize(databaseSizeBeforeUpdate);
        UrunGrubu testUrunGrubu = urunGrubuList.get(urunGrubuList.size() - 1);
        assertThat(testUrunGrubu.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testUrunGrubu.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUrunGrubu.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingUrunGrubu() throws Exception {
        int databaseSizeBeforeUpdate = urunGrubuRepository.findAll().size();

        // Create the UrunGrubu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUrunGrubuMockMvc.perform(put("/api/urun-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urunGrubu)))
            .andExpect(status().isCreated());

        // Validate the UrunGrubu in the database
        List<UrunGrubu> urunGrubuList = urunGrubuRepository.findAll();
        assertThat(urunGrubuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUrunGrubu() throws Exception {
        // Initialize the database
        urunGrubuRepository.saveAndFlush(urunGrubu);
        int databaseSizeBeforeDelete = urunGrubuRepository.findAll().size();

        // Get the urunGrubu
        restUrunGrubuMockMvc.perform(delete("/api/urun-grubus/{id}", urunGrubu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UrunGrubu> urunGrubuList = urunGrubuRepository.findAll();
        assertThat(urunGrubuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UrunGrubu.class);
        UrunGrubu urunGrubu1 = new UrunGrubu();
        urunGrubu1.setId(1L);
        UrunGrubu urunGrubu2 = new UrunGrubu();
        urunGrubu2.setId(urunGrubu1.getId());
        assertThat(urunGrubu1).isEqualTo(urunGrubu2);
        urunGrubu2.setId(2L);
        assertThat(urunGrubu1).isNotEqualTo(urunGrubu2);
        urunGrubu1.setId(null);
        assertThat(urunGrubu1).isNotEqualTo(urunGrubu2);
    }
}

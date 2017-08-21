package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.UretimBilgisi;
import tr.gov.sb.sygm.repository.UretimBilgisiRepository;
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
 * Test class for the UretimBilgisiResource REST controller.
 *
 * @see UretimBilgisiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class UretimBilgisiResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_FIRMA_ACIKLAMASI = "AAAAAAAAAA";
    private static final String UPDATED_FIRMA_ACIKLAMASI = "BBBBBBBBBB";

    @Autowired
    private UretimBilgisiRepository uretimBilgisiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUretimBilgisiMockMvc;

    private UretimBilgisi uretimBilgisi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UretimBilgisiResource uretimBilgisiResource = new UretimBilgisiResource(uretimBilgisiRepository);
        this.restUretimBilgisiMockMvc = MockMvcBuilders.standaloneSetup(uretimBilgisiResource)
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
    public static UretimBilgisi createEntity(EntityManager em) {
        UretimBilgisi uretimBilgisi = new UretimBilgisi()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .firmaAciklamasi(DEFAULT_FIRMA_ACIKLAMASI);
        return uretimBilgisi;
    }

    @Before
    public void initTest() {
        uretimBilgisi = createEntity(em);
    }

    @Test
    @Transactional
    public void createUretimBilgisi() throws Exception {
        int databaseSizeBeforeCreate = uretimBilgisiRepository.findAll().size();

        // Create the UretimBilgisi
        restUretimBilgisiMockMvc.perform(post("/api/uretim-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uretimBilgisi)))
            .andExpect(status().isCreated());

        // Validate the UretimBilgisi in the database
        List<UretimBilgisi> uretimBilgisiList = uretimBilgisiRepository.findAll();
        assertThat(uretimBilgisiList).hasSize(databaseSizeBeforeCreate + 1);
        UretimBilgisi testUretimBilgisi = uretimBilgisiList.get(uretimBilgisiList.size() - 1);
        assertThat(testUretimBilgisi.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testUretimBilgisi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUretimBilgisi.getFirmaAciklamasi()).isEqualTo(DEFAULT_FIRMA_ACIKLAMASI);
    }

    @Test
    @Transactional
    public void createUretimBilgisiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uretimBilgisiRepository.findAll().size();

        // Create the UretimBilgisi with an existing ID
        uretimBilgisi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUretimBilgisiMockMvc.perform(post("/api/uretim-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uretimBilgisi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UretimBilgisi> uretimBilgisiList = uretimBilgisiRepository.findAll();
        assertThat(uretimBilgisiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = uretimBilgisiRepository.findAll().size();
        // set the field null
        uretimBilgisi.setDeleted(null);

        // Create the UretimBilgisi, which fails.

        restUretimBilgisiMockMvc.perform(post("/api/uretim-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uretimBilgisi)))
            .andExpect(status().isBadRequest());

        List<UretimBilgisi> uretimBilgisiList = uretimBilgisiRepository.findAll();
        assertThat(uretimBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = uretimBilgisiRepository.findAll().size();
        // set the field null
        uretimBilgisi.setVersion(null);

        // Create the UretimBilgisi, which fails.

        restUretimBilgisiMockMvc.perform(post("/api/uretim-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uretimBilgisi)))
            .andExpect(status().isBadRequest());

        List<UretimBilgisi> uretimBilgisiList = uretimBilgisiRepository.findAll();
        assertThat(uretimBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUretimBilgisis() throws Exception {
        // Initialize the database
        uretimBilgisiRepository.saveAndFlush(uretimBilgisi);

        // Get all the uretimBilgisiList
        restUretimBilgisiMockMvc.perform(get("/api/uretim-bilgisis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uretimBilgisi.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].firmaAciklamasi").value(hasItem(DEFAULT_FIRMA_ACIKLAMASI.toString())));
    }

    @Test
    @Transactional
    public void getUretimBilgisi() throws Exception {
        // Initialize the database
        uretimBilgisiRepository.saveAndFlush(uretimBilgisi);

        // Get the uretimBilgisi
        restUretimBilgisiMockMvc.perform(get("/api/uretim-bilgisis/{id}", uretimBilgisi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uretimBilgisi.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.firmaAciklamasi").value(DEFAULT_FIRMA_ACIKLAMASI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUretimBilgisi() throws Exception {
        // Get the uretimBilgisi
        restUretimBilgisiMockMvc.perform(get("/api/uretim-bilgisis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUretimBilgisi() throws Exception {
        // Initialize the database
        uretimBilgisiRepository.saveAndFlush(uretimBilgisi);
        int databaseSizeBeforeUpdate = uretimBilgisiRepository.findAll().size();

        // Update the uretimBilgisi
        UretimBilgisi updatedUretimBilgisi = uretimBilgisiRepository.findOne(uretimBilgisi.getId());
        updatedUretimBilgisi
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .firmaAciklamasi(UPDATED_FIRMA_ACIKLAMASI);

        restUretimBilgisiMockMvc.perform(put("/api/uretim-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUretimBilgisi)))
            .andExpect(status().isOk());

        // Validate the UretimBilgisi in the database
        List<UretimBilgisi> uretimBilgisiList = uretimBilgisiRepository.findAll();
        assertThat(uretimBilgisiList).hasSize(databaseSizeBeforeUpdate);
        UretimBilgisi testUretimBilgisi = uretimBilgisiList.get(uretimBilgisiList.size() - 1);
        assertThat(testUretimBilgisi.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testUretimBilgisi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUretimBilgisi.getFirmaAciklamasi()).isEqualTo(UPDATED_FIRMA_ACIKLAMASI);
    }

    @Test
    @Transactional
    public void updateNonExistingUretimBilgisi() throws Exception {
        int databaseSizeBeforeUpdate = uretimBilgisiRepository.findAll().size();

        // Create the UretimBilgisi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUretimBilgisiMockMvc.perform(put("/api/uretim-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uretimBilgisi)))
            .andExpect(status().isCreated());

        // Validate the UretimBilgisi in the database
        List<UretimBilgisi> uretimBilgisiList = uretimBilgisiRepository.findAll();
        assertThat(uretimBilgisiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUretimBilgisi() throws Exception {
        // Initialize the database
        uretimBilgisiRepository.saveAndFlush(uretimBilgisi);
        int databaseSizeBeforeDelete = uretimBilgisiRepository.findAll().size();

        // Get the uretimBilgisi
        restUretimBilgisiMockMvc.perform(delete("/api/uretim-bilgisis/{id}", uretimBilgisi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UretimBilgisi> uretimBilgisiList = uretimBilgisiRepository.findAll();
        assertThat(uretimBilgisiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UretimBilgisi.class);
        UretimBilgisi uretimBilgisi1 = new UretimBilgisi();
        uretimBilgisi1.setId(1L);
        UretimBilgisi uretimBilgisi2 = new UretimBilgisi();
        uretimBilgisi2.setId(uretimBilgisi1.getId());
        assertThat(uretimBilgisi1).isEqualTo(uretimBilgisi2);
        uretimBilgisi2.setId(2L);
        assertThat(uretimBilgisi1).isNotEqualTo(uretimBilgisi2);
        uretimBilgisi1.setId(null);
        assertThat(uretimBilgisi1).isNotEqualTo(uretimBilgisi2);
    }
}

package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.CalisanSayiBilgisi;
import tr.gov.sb.sygm.repository.CalisanSayiBilgisiRepository;
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
 * Test class for the CalisanSayiBilgisiResource REST controller.
 *
 * @see CalisanSayiBilgisiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class CalisanSayiBilgisiResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final Long DEFAULT_KISI_SAYISI = 1L;
    private static final Long UPDATED_KISI_SAYISI = 2L;

    @Autowired
    private CalisanSayiBilgisiRepository calisanSayiBilgisiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCalisanSayiBilgisiMockMvc;

    private CalisanSayiBilgisi calisanSayiBilgisi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalisanSayiBilgisiResource calisanSayiBilgisiResource = new CalisanSayiBilgisiResource(calisanSayiBilgisiRepository);
        this.restCalisanSayiBilgisiMockMvc = MockMvcBuilders.standaloneSetup(calisanSayiBilgisiResource)
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
    public static CalisanSayiBilgisi createEntity(EntityManager em) {
        CalisanSayiBilgisi calisanSayiBilgisi = new CalisanSayiBilgisi()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .kisiSayisi(DEFAULT_KISI_SAYISI);
        return calisanSayiBilgisi;
    }

    @Before
    public void initTest() {
        calisanSayiBilgisi = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalisanSayiBilgisi() throws Exception {
        int databaseSizeBeforeCreate = calisanSayiBilgisiRepository.findAll().size();

        // Create the CalisanSayiBilgisi
        restCalisanSayiBilgisiMockMvc.perform(post("/api/calisan-sayi-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanSayiBilgisi)))
            .andExpect(status().isCreated());

        // Validate the CalisanSayiBilgisi in the database
        List<CalisanSayiBilgisi> calisanSayiBilgisiList = calisanSayiBilgisiRepository.findAll();
        assertThat(calisanSayiBilgisiList).hasSize(databaseSizeBeforeCreate + 1);
        CalisanSayiBilgisi testCalisanSayiBilgisi = calisanSayiBilgisiList.get(calisanSayiBilgisiList.size() - 1);
        assertThat(testCalisanSayiBilgisi.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testCalisanSayiBilgisi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCalisanSayiBilgisi.getKisiSayisi()).isEqualTo(DEFAULT_KISI_SAYISI);
    }

    @Test
    @Transactional
    public void createCalisanSayiBilgisiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calisanSayiBilgisiRepository.findAll().size();

        // Create the CalisanSayiBilgisi with an existing ID
        calisanSayiBilgisi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalisanSayiBilgisiMockMvc.perform(post("/api/calisan-sayi-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanSayiBilgisi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CalisanSayiBilgisi> calisanSayiBilgisiList = calisanSayiBilgisiRepository.findAll();
        assertThat(calisanSayiBilgisiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = calisanSayiBilgisiRepository.findAll().size();
        // set the field null
        calisanSayiBilgisi.setDeleted(null);

        // Create the CalisanSayiBilgisi, which fails.

        restCalisanSayiBilgisiMockMvc.perform(post("/api/calisan-sayi-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanSayiBilgisi)))
            .andExpect(status().isBadRequest());

        List<CalisanSayiBilgisi> calisanSayiBilgisiList = calisanSayiBilgisiRepository.findAll();
        assertThat(calisanSayiBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = calisanSayiBilgisiRepository.findAll().size();
        // set the field null
        calisanSayiBilgisi.setVersion(null);

        // Create the CalisanSayiBilgisi, which fails.

        restCalisanSayiBilgisiMockMvc.perform(post("/api/calisan-sayi-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanSayiBilgisi)))
            .andExpect(status().isBadRequest());

        List<CalisanSayiBilgisi> calisanSayiBilgisiList = calisanSayiBilgisiRepository.findAll();
        assertThat(calisanSayiBilgisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalisanSayiBilgisis() throws Exception {
        // Initialize the database
        calisanSayiBilgisiRepository.saveAndFlush(calisanSayiBilgisi);

        // Get all the calisanSayiBilgisiList
        restCalisanSayiBilgisiMockMvc.perform(get("/api/calisan-sayi-bilgisis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calisanSayiBilgisi.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].kisiSayisi").value(hasItem(DEFAULT_KISI_SAYISI.intValue())));
    }

    @Test
    @Transactional
    public void getCalisanSayiBilgisi() throws Exception {
        // Initialize the database
        calisanSayiBilgisiRepository.saveAndFlush(calisanSayiBilgisi);

        // Get the calisanSayiBilgisi
        restCalisanSayiBilgisiMockMvc.perform(get("/api/calisan-sayi-bilgisis/{id}", calisanSayiBilgisi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calisanSayiBilgisi.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.kisiSayisi").value(DEFAULT_KISI_SAYISI.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCalisanSayiBilgisi() throws Exception {
        // Get the calisanSayiBilgisi
        restCalisanSayiBilgisiMockMvc.perform(get("/api/calisan-sayi-bilgisis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalisanSayiBilgisi() throws Exception {
        // Initialize the database
        calisanSayiBilgisiRepository.saveAndFlush(calisanSayiBilgisi);
        int databaseSizeBeforeUpdate = calisanSayiBilgisiRepository.findAll().size();

        // Update the calisanSayiBilgisi
        CalisanSayiBilgisi updatedCalisanSayiBilgisi = calisanSayiBilgisiRepository.findOne(calisanSayiBilgisi.getId());
        updatedCalisanSayiBilgisi
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .kisiSayisi(UPDATED_KISI_SAYISI);

        restCalisanSayiBilgisiMockMvc.perform(put("/api/calisan-sayi-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalisanSayiBilgisi)))
            .andExpect(status().isOk());

        // Validate the CalisanSayiBilgisi in the database
        List<CalisanSayiBilgisi> calisanSayiBilgisiList = calisanSayiBilgisiRepository.findAll();
        assertThat(calisanSayiBilgisiList).hasSize(databaseSizeBeforeUpdate);
        CalisanSayiBilgisi testCalisanSayiBilgisi = calisanSayiBilgisiList.get(calisanSayiBilgisiList.size() - 1);
        assertThat(testCalisanSayiBilgisi.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testCalisanSayiBilgisi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCalisanSayiBilgisi.getKisiSayisi()).isEqualTo(UPDATED_KISI_SAYISI);
    }

    @Test
    @Transactional
    public void updateNonExistingCalisanSayiBilgisi() throws Exception {
        int databaseSizeBeforeUpdate = calisanSayiBilgisiRepository.findAll().size();

        // Create the CalisanSayiBilgisi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCalisanSayiBilgisiMockMvc.perform(put("/api/calisan-sayi-bilgisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisanSayiBilgisi)))
            .andExpect(status().isCreated());

        // Validate the CalisanSayiBilgisi in the database
        List<CalisanSayiBilgisi> calisanSayiBilgisiList = calisanSayiBilgisiRepository.findAll();
        assertThat(calisanSayiBilgisiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCalisanSayiBilgisi() throws Exception {
        // Initialize the database
        calisanSayiBilgisiRepository.saveAndFlush(calisanSayiBilgisi);
        int databaseSizeBeforeDelete = calisanSayiBilgisiRepository.findAll().size();

        // Get the calisanSayiBilgisi
        restCalisanSayiBilgisiMockMvc.perform(delete("/api/calisan-sayi-bilgisis/{id}", calisanSayiBilgisi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CalisanSayiBilgisi> calisanSayiBilgisiList = calisanSayiBilgisiRepository.findAll();
        assertThat(calisanSayiBilgisiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalisanSayiBilgisi.class);
        CalisanSayiBilgisi calisanSayiBilgisi1 = new CalisanSayiBilgisi();
        calisanSayiBilgisi1.setId(1L);
        CalisanSayiBilgisi calisanSayiBilgisi2 = new CalisanSayiBilgisi();
        calisanSayiBilgisi2.setId(calisanSayiBilgisi1.getId());
        assertThat(calisanSayiBilgisi1).isEqualTo(calisanSayiBilgisi2);
        calisanSayiBilgisi2.setId(2L);
        assertThat(calisanSayiBilgisi1).isNotEqualTo(calisanSayiBilgisi2);
        calisanSayiBilgisi1.setId(null);
        assertThat(calisanSayiBilgisi1).isNotEqualTo(calisanSayiBilgisi2);
    }
}

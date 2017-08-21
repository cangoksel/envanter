package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.IsbirligiFirma;
import tr.gov.sb.sygm.repository.IsbirligiFirmaRepository;
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
 * Test class for the IsbirligiFirmaResource REST controller.
 *
 * @see IsbirligiFirmaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class IsbirligiFirmaResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_FIRMA_ADI = "AAAAAAAAAA";
    private static final String UPDATED_FIRMA_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_ISBIRLIGI_KONSU = "AAAAAAAAAA";
    private static final String UPDATED_ISBIRLIGI_KONSU = "BBBBBBBBBB";

    @Autowired
    private IsbirligiFirmaRepository isbirligiFirmaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIsbirligiFirmaMockMvc;

    private IsbirligiFirma isbirligiFirma;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IsbirligiFirmaResource isbirligiFirmaResource = new IsbirligiFirmaResource(isbirligiFirmaRepository);
        this.restIsbirligiFirmaMockMvc = MockMvcBuilders.standaloneSetup(isbirligiFirmaResource)
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
    public static IsbirligiFirma createEntity(EntityManager em) {
        IsbirligiFirma isbirligiFirma = new IsbirligiFirma()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .firmaAdi(DEFAULT_FIRMA_ADI)
            .isbirligiKonsu(DEFAULT_ISBIRLIGI_KONSU);
        return isbirligiFirma;
    }

    @Before
    public void initTest() {
        isbirligiFirma = createEntity(em);
    }

    @Test
    @Transactional
    public void createIsbirligiFirma() throws Exception {
        int databaseSizeBeforeCreate = isbirligiFirmaRepository.findAll().size();

        // Create the IsbirligiFirma
        restIsbirligiFirmaMockMvc.perform(post("/api/isbirligi-firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isbirligiFirma)))
            .andExpect(status().isCreated());

        // Validate the IsbirligiFirma in the database
        List<IsbirligiFirma> isbirligiFirmaList = isbirligiFirmaRepository.findAll();
        assertThat(isbirligiFirmaList).hasSize(databaseSizeBeforeCreate + 1);
        IsbirligiFirma testIsbirligiFirma = isbirligiFirmaList.get(isbirligiFirmaList.size() - 1);
        assertThat(testIsbirligiFirma.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testIsbirligiFirma.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testIsbirligiFirma.getFirmaAdi()).isEqualTo(DEFAULT_FIRMA_ADI);
        assertThat(testIsbirligiFirma.getIsbirligiKonsu()).isEqualTo(DEFAULT_ISBIRLIGI_KONSU);
    }

    @Test
    @Transactional
    public void createIsbirligiFirmaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = isbirligiFirmaRepository.findAll().size();

        // Create the IsbirligiFirma with an existing ID
        isbirligiFirma.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsbirligiFirmaMockMvc.perform(post("/api/isbirligi-firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isbirligiFirma)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IsbirligiFirma> isbirligiFirmaList = isbirligiFirmaRepository.findAll();
        assertThat(isbirligiFirmaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = isbirligiFirmaRepository.findAll().size();
        // set the field null
        isbirligiFirma.setDeleted(null);

        // Create the IsbirligiFirma, which fails.

        restIsbirligiFirmaMockMvc.perform(post("/api/isbirligi-firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isbirligiFirma)))
            .andExpect(status().isBadRequest());

        List<IsbirligiFirma> isbirligiFirmaList = isbirligiFirmaRepository.findAll();
        assertThat(isbirligiFirmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = isbirligiFirmaRepository.findAll().size();
        // set the field null
        isbirligiFirma.setVersion(null);

        // Create the IsbirligiFirma, which fails.

        restIsbirligiFirmaMockMvc.perform(post("/api/isbirligi-firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isbirligiFirma)))
            .andExpect(status().isBadRequest());

        List<IsbirligiFirma> isbirligiFirmaList = isbirligiFirmaRepository.findAll();
        assertThat(isbirligiFirmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirmaAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = isbirligiFirmaRepository.findAll().size();
        // set the field null
        isbirligiFirma.setFirmaAdi(null);

        // Create the IsbirligiFirma, which fails.

        restIsbirligiFirmaMockMvc.perform(post("/api/isbirligi-firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isbirligiFirma)))
            .andExpect(status().isBadRequest());

        List<IsbirligiFirma> isbirligiFirmaList = isbirligiFirmaRepository.findAll();
        assertThat(isbirligiFirmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIsbirligiFirmas() throws Exception {
        // Initialize the database
        isbirligiFirmaRepository.saveAndFlush(isbirligiFirma);

        // Get all the isbirligiFirmaList
        restIsbirligiFirmaMockMvc.perform(get("/api/isbirligi-firmas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isbirligiFirma.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].firmaAdi").value(hasItem(DEFAULT_FIRMA_ADI.toString())))
            .andExpect(jsonPath("$.[*].isbirligiKonsu").value(hasItem(DEFAULT_ISBIRLIGI_KONSU.toString())));
    }

    @Test
    @Transactional
    public void getIsbirligiFirma() throws Exception {
        // Initialize the database
        isbirligiFirmaRepository.saveAndFlush(isbirligiFirma);

        // Get the isbirligiFirma
        restIsbirligiFirmaMockMvc.perform(get("/api/isbirligi-firmas/{id}", isbirligiFirma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(isbirligiFirma.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.firmaAdi").value(DEFAULT_FIRMA_ADI.toString()))
            .andExpect(jsonPath("$.isbirligiKonsu").value(DEFAULT_ISBIRLIGI_KONSU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIsbirligiFirma() throws Exception {
        // Get the isbirligiFirma
        restIsbirligiFirmaMockMvc.perform(get("/api/isbirligi-firmas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIsbirligiFirma() throws Exception {
        // Initialize the database
        isbirligiFirmaRepository.saveAndFlush(isbirligiFirma);
        int databaseSizeBeforeUpdate = isbirligiFirmaRepository.findAll().size();

        // Update the isbirligiFirma
        IsbirligiFirma updatedIsbirligiFirma = isbirligiFirmaRepository.findOne(isbirligiFirma.getId());
        updatedIsbirligiFirma
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .firmaAdi(UPDATED_FIRMA_ADI)
            .isbirligiKonsu(UPDATED_ISBIRLIGI_KONSU);

        restIsbirligiFirmaMockMvc.perform(put("/api/isbirligi-firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIsbirligiFirma)))
            .andExpect(status().isOk());

        // Validate the IsbirligiFirma in the database
        List<IsbirligiFirma> isbirligiFirmaList = isbirligiFirmaRepository.findAll();
        assertThat(isbirligiFirmaList).hasSize(databaseSizeBeforeUpdate);
        IsbirligiFirma testIsbirligiFirma = isbirligiFirmaList.get(isbirligiFirmaList.size() - 1);
        assertThat(testIsbirligiFirma.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testIsbirligiFirma.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testIsbirligiFirma.getFirmaAdi()).isEqualTo(UPDATED_FIRMA_ADI);
        assertThat(testIsbirligiFirma.getIsbirligiKonsu()).isEqualTo(UPDATED_ISBIRLIGI_KONSU);
    }

    @Test
    @Transactional
    public void updateNonExistingIsbirligiFirma() throws Exception {
        int databaseSizeBeforeUpdate = isbirligiFirmaRepository.findAll().size();

        // Create the IsbirligiFirma

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIsbirligiFirmaMockMvc.perform(put("/api/isbirligi-firmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isbirligiFirma)))
            .andExpect(status().isCreated());

        // Validate the IsbirligiFirma in the database
        List<IsbirligiFirma> isbirligiFirmaList = isbirligiFirmaRepository.findAll();
        assertThat(isbirligiFirmaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIsbirligiFirma() throws Exception {
        // Initialize the database
        isbirligiFirmaRepository.saveAndFlush(isbirligiFirma);
        int databaseSizeBeforeDelete = isbirligiFirmaRepository.findAll().size();

        // Get the isbirligiFirma
        restIsbirligiFirmaMockMvc.perform(delete("/api/isbirligi-firmas/{id}", isbirligiFirma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IsbirligiFirma> isbirligiFirmaList = isbirligiFirmaRepository.findAll();
        assertThat(isbirligiFirmaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsbirligiFirma.class);
        IsbirligiFirma isbirligiFirma1 = new IsbirligiFirma();
        isbirligiFirma1.setId(1L);
        IsbirligiFirma isbirligiFirma2 = new IsbirligiFirma();
        isbirligiFirma2.setId(isbirligiFirma1.getId());
        assertThat(isbirligiFirma1).isEqualTo(isbirligiFirma2);
        isbirligiFirma2.setId(2L);
        assertThat(isbirligiFirma1).isNotEqualTo(isbirligiFirma2);
        isbirligiFirma1.setId(null);
        assertThat(isbirligiFirma1).isNotEqualTo(isbirligiFirma2);
    }
}

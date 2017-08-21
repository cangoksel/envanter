package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.IsyeriBilgileri;
import tr.gov.sb.sygm.repository.IsyeriBilgileriRepository;
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
 * Test class for the IsyeriBilgileriResource REST controller.
 *
 * @see IsyeriBilgileriResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class IsyeriBilgileriResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    @Autowired
    private IsyeriBilgileriRepository isyeriBilgileriRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIsyeriBilgileriMockMvc;

    private IsyeriBilgileri isyeriBilgileri;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IsyeriBilgileriResource isyeriBilgileriResource = new IsyeriBilgileriResource(isyeriBilgileriRepository);
        this.restIsyeriBilgileriMockMvc = MockMvcBuilders.standaloneSetup(isyeriBilgileriResource)
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
    public static IsyeriBilgileri createEntity(EntityManager em) {
        IsyeriBilgileri isyeriBilgileri = new IsyeriBilgileri()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION);
        return isyeriBilgileri;
    }

    @Before
    public void initTest() {
        isyeriBilgileri = createEntity(em);
    }

    @Test
    @Transactional
    public void createIsyeriBilgileri() throws Exception {
        int databaseSizeBeforeCreate = isyeriBilgileriRepository.findAll().size();

        // Create the IsyeriBilgileri
        restIsyeriBilgileriMockMvc.perform(post("/api/isyeri-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriBilgileri)))
            .andExpect(status().isCreated());

        // Validate the IsyeriBilgileri in the database
        List<IsyeriBilgileri> isyeriBilgileriList = isyeriBilgileriRepository.findAll();
        assertThat(isyeriBilgileriList).hasSize(databaseSizeBeforeCreate + 1);
        IsyeriBilgileri testIsyeriBilgileri = isyeriBilgileriList.get(isyeriBilgileriList.size() - 1);
        assertThat(testIsyeriBilgileri.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testIsyeriBilgileri.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createIsyeriBilgileriWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = isyeriBilgileriRepository.findAll().size();

        // Create the IsyeriBilgileri with an existing ID
        isyeriBilgileri.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsyeriBilgileriMockMvc.perform(post("/api/isyeri-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriBilgileri)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IsyeriBilgileri> isyeriBilgileriList = isyeriBilgileriRepository.findAll();
        assertThat(isyeriBilgileriList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = isyeriBilgileriRepository.findAll().size();
        // set the field null
        isyeriBilgileri.setDeleted(null);

        // Create the IsyeriBilgileri, which fails.

        restIsyeriBilgileriMockMvc.perform(post("/api/isyeri-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriBilgileri)))
            .andExpect(status().isBadRequest());

        List<IsyeriBilgileri> isyeriBilgileriList = isyeriBilgileriRepository.findAll();
        assertThat(isyeriBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = isyeriBilgileriRepository.findAll().size();
        // set the field null
        isyeriBilgileri.setVersion(null);

        // Create the IsyeriBilgileri, which fails.

        restIsyeriBilgileriMockMvc.perform(post("/api/isyeri-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriBilgileri)))
            .andExpect(status().isBadRequest());

        List<IsyeriBilgileri> isyeriBilgileriList = isyeriBilgileriRepository.findAll();
        assertThat(isyeriBilgileriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIsyeriBilgileris() throws Exception {
        // Initialize the database
        isyeriBilgileriRepository.saveAndFlush(isyeriBilgileri);

        // Get all the isyeriBilgileriList
        restIsyeriBilgileriMockMvc.perform(get("/api/isyeri-bilgileris?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isyeriBilgileri.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }

    @Test
    @Transactional
    public void getIsyeriBilgileri() throws Exception {
        // Initialize the database
        isyeriBilgileriRepository.saveAndFlush(isyeriBilgileri);

        // Get the isyeriBilgileri
        restIsyeriBilgileriMockMvc.perform(get("/api/isyeri-bilgileris/{id}", isyeriBilgileri.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(isyeriBilgileri.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIsyeriBilgileri() throws Exception {
        // Get the isyeriBilgileri
        restIsyeriBilgileriMockMvc.perform(get("/api/isyeri-bilgileris/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIsyeriBilgileri() throws Exception {
        // Initialize the database
        isyeriBilgileriRepository.saveAndFlush(isyeriBilgileri);
        int databaseSizeBeforeUpdate = isyeriBilgileriRepository.findAll().size();

        // Update the isyeriBilgileri
        IsyeriBilgileri updatedIsyeriBilgileri = isyeriBilgileriRepository.findOne(isyeriBilgileri.getId());
        updatedIsyeriBilgileri
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION);

        restIsyeriBilgileriMockMvc.perform(put("/api/isyeri-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIsyeriBilgileri)))
            .andExpect(status().isOk());

        // Validate the IsyeriBilgileri in the database
        List<IsyeriBilgileri> isyeriBilgileriList = isyeriBilgileriRepository.findAll();
        assertThat(isyeriBilgileriList).hasSize(databaseSizeBeforeUpdate);
        IsyeriBilgileri testIsyeriBilgileri = isyeriBilgileriList.get(isyeriBilgileriList.size() - 1);
        assertThat(testIsyeriBilgileri.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testIsyeriBilgileri.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingIsyeriBilgileri() throws Exception {
        int databaseSizeBeforeUpdate = isyeriBilgileriRepository.findAll().size();

        // Create the IsyeriBilgileri

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIsyeriBilgileriMockMvc.perform(put("/api/isyeri-bilgileris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriBilgileri)))
            .andExpect(status().isCreated());

        // Validate the IsyeriBilgileri in the database
        List<IsyeriBilgileri> isyeriBilgileriList = isyeriBilgileriRepository.findAll();
        assertThat(isyeriBilgileriList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIsyeriBilgileri() throws Exception {
        // Initialize the database
        isyeriBilgileriRepository.saveAndFlush(isyeriBilgileri);
        int databaseSizeBeforeDelete = isyeriBilgileriRepository.findAll().size();

        // Get the isyeriBilgileri
        restIsyeriBilgileriMockMvc.perform(delete("/api/isyeri-bilgileris/{id}", isyeriBilgileri.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IsyeriBilgileri> isyeriBilgileriList = isyeriBilgileriRepository.findAll();
        assertThat(isyeriBilgileriList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsyeriBilgileri.class);
        IsyeriBilgileri isyeriBilgileri1 = new IsyeriBilgileri();
        isyeriBilgileri1.setId(1L);
        IsyeriBilgileri isyeriBilgileri2 = new IsyeriBilgileri();
        isyeriBilgileri2.setId(isyeriBilgileri1.getId());
        assertThat(isyeriBilgileri1).isEqualTo(isyeriBilgileri2);
        isyeriBilgileri2.setId(2L);
        assertThat(isyeriBilgileri1).isNotEqualTo(isyeriBilgileri2);
        isyeriBilgileri1.setId(null);
        assertThat(isyeriBilgileri1).isNotEqualTo(isyeriBilgileri2);
    }
}

package tr.gov.sb.sygm.web.rest;

import tr.gov.sb.sygm.InventoryApp;

import tr.gov.sb.sygm.domain.YillikCiro;
import tr.gov.sb.sygm.repository.YillikCiroRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the YillikCiroResource REST controller.
 *
 * @see YillikCiroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApp.class)
public class YillikCiroResourceIntTest {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final LocalDate DEFAULT_YIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YIL = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_CIRO_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_CIRO_TUTAR = new BigDecimal(2);

    private static final String DEFAULT_CIRO_PB = "AAAAAAAAAA";
    private static final String UPDATED_CIRO_PB = "BBBBBBBBBB";

    @Autowired
    private YillikCiroRepository yillikCiroRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restYillikCiroMockMvc;

    private YillikCiro yillikCiro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YillikCiroResource yillikCiroResource = new YillikCiroResource(yillikCiroRepository);
        this.restYillikCiroMockMvc = MockMvcBuilders.standaloneSetup(yillikCiroResource)
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
    public static YillikCiro createEntity(EntityManager em) {
        YillikCiro yillikCiro = new YillikCiro()
            .deleted(DEFAULT_DELETED)
            .version(DEFAULT_VERSION)
            .yil(DEFAULT_YIL)
            .ciroTutar(DEFAULT_CIRO_TUTAR)
            .ciroPB(DEFAULT_CIRO_PB);
        return yillikCiro;
    }

    @Before
    public void initTest() {
        yillikCiro = createEntity(em);
    }

    @Test
    @Transactional
    public void createYillikCiro() throws Exception {
        int databaseSizeBeforeCreate = yillikCiroRepository.findAll().size();

        // Create the YillikCiro
        restYillikCiroMockMvc.perform(post("/api/yillik-ciros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yillikCiro)))
            .andExpect(status().isCreated());

        // Validate the YillikCiro in the database
        List<YillikCiro> yillikCiroList = yillikCiroRepository.findAll();
        assertThat(yillikCiroList).hasSize(databaseSizeBeforeCreate + 1);
        YillikCiro testYillikCiro = yillikCiroList.get(yillikCiroList.size() - 1);
        assertThat(testYillikCiro.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testYillikCiro.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testYillikCiro.getYil()).isEqualTo(DEFAULT_YIL);
        assertThat(testYillikCiro.getCiroTutar()).isEqualTo(DEFAULT_CIRO_TUTAR);
        assertThat(testYillikCiro.getCiroPB()).isEqualTo(DEFAULT_CIRO_PB);
    }

    @Test
    @Transactional
    public void createYillikCiroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = yillikCiroRepository.findAll().size();

        // Create the YillikCiro with an existing ID
        yillikCiro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restYillikCiroMockMvc.perform(post("/api/yillik-ciros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yillikCiro)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<YillikCiro> yillikCiroList = yillikCiroRepository.findAll();
        assertThat(yillikCiroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = yillikCiroRepository.findAll().size();
        // set the field null
        yillikCiro.setDeleted(null);

        // Create the YillikCiro, which fails.

        restYillikCiroMockMvc.perform(post("/api/yillik-ciros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yillikCiro)))
            .andExpect(status().isBadRequest());

        List<YillikCiro> yillikCiroList = yillikCiroRepository.findAll();
        assertThat(yillikCiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = yillikCiroRepository.findAll().size();
        // set the field null
        yillikCiro.setVersion(null);

        // Create the YillikCiro, which fails.

        restYillikCiroMockMvc.perform(post("/api/yillik-ciros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yillikCiro)))
            .andExpect(status().isBadRequest());

        List<YillikCiro> yillikCiroList = yillikCiroRepository.findAll();
        assertThat(yillikCiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYilIsRequired() throws Exception {
        int databaseSizeBeforeTest = yillikCiroRepository.findAll().size();
        // set the field null
        yillikCiro.setYil(null);

        // Create the YillikCiro, which fails.

        restYillikCiroMockMvc.perform(post("/api/yillik-ciros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yillikCiro)))
            .andExpect(status().isBadRequest());

        List<YillikCiro> yillikCiroList = yillikCiroRepository.findAll();
        assertThat(yillikCiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYillikCiros() throws Exception {
        // Initialize the database
        yillikCiroRepository.saveAndFlush(yillikCiro);

        // Get all the yillikCiroList
        restYillikCiroMockMvc.perform(get("/api/yillik-ciros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(yillikCiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].yil").value(hasItem(DEFAULT_YIL.toString())))
            .andExpect(jsonPath("$.[*].ciroTutar").value(hasItem(DEFAULT_CIRO_TUTAR.intValue())))
            .andExpect(jsonPath("$.[*].ciroPB").value(hasItem(DEFAULT_CIRO_PB.toString())));
    }

    @Test
    @Transactional
    public void getYillikCiro() throws Exception {
        // Initialize the database
        yillikCiroRepository.saveAndFlush(yillikCiro);

        // Get the yillikCiro
        restYillikCiroMockMvc.perform(get("/api/yillik-ciros/{id}", yillikCiro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(yillikCiro.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.yil").value(DEFAULT_YIL.toString()))
            .andExpect(jsonPath("$.ciroTutar").value(DEFAULT_CIRO_TUTAR.intValue()))
            .andExpect(jsonPath("$.ciroPB").value(DEFAULT_CIRO_PB.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingYillikCiro() throws Exception {
        // Get the yillikCiro
        restYillikCiroMockMvc.perform(get("/api/yillik-ciros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYillikCiro() throws Exception {
        // Initialize the database
        yillikCiroRepository.saveAndFlush(yillikCiro);
        int databaseSizeBeforeUpdate = yillikCiroRepository.findAll().size();

        // Update the yillikCiro
        YillikCiro updatedYillikCiro = yillikCiroRepository.findOne(yillikCiro.getId());
        updatedYillikCiro
            .deleted(UPDATED_DELETED)
            .version(UPDATED_VERSION)
            .yil(UPDATED_YIL)
            .ciroTutar(UPDATED_CIRO_TUTAR)
            .ciroPB(UPDATED_CIRO_PB);

        restYillikCiroMockMvc.perform(put("/api/yillik-ciros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedYillikCiro)))
            .andExpect(status().isOk());

        // Validate the YillikCiro in the database
        List<YillikCiro> yillikCiroList = yillikCiroRepository.findAll();
        assertThat(yillikCiroList).hasSize(databaseSizeBeforeUpdate);
        YillikCiro testYillikCiro = yillikCiroList.get(yillikCiroList.size() - 1);
        assertThat(testYillikCiro.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testYillikCiro.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testYillikCiro.getYil()).isEqualTo(UPDATED_YIL);
        assertThat(testYillikCiro.getCiroTutar()).isEqualTo(UPDATED_CIRO_TUTAR);
        assertThat(testYillikCiro.getCiroPB()).isEqualTo(UPDATED_CIRO_PB);
    }

    @Test
    @Transactional
    public void updateNonExistingYillikCiro() throws Exception {
        int databaseSizeBeforeUpdate = yillikCiroRepository.findAll().size();

        // Create the YillikCiro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restYillikCiroMockMvc.perform(put("/api/yillik-ciros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yillikCiro)))
            .andExpect(status().isCreated());

        // Validate the YillikCiro in the database
        List<YillikCiro> yillikCiroList = yillikCiroRepository.findAll();
        assertThat(yillikCiroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteYillikCiro() throws Exception {
        // Initialize the database
        yillikCiroRepository.saveAndFlush(yillikCiro);
        int databaseSizeBeforeDelete = yillikCiroRepository.findAll().size();

        // Get the yillikCiro
        restYillikCiroMockMvc.perform(delete("/api/yillik-ciros/{id}", yillikCiro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<YillikCiro> yillikCiroList = yillikCiroRepository.findAll();
        assertThat(yillikCiroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(YillikCiro.class);
        YillikCiro yillikCiro1 = new YillikCiro();
        yillikCiro1.setId(1L);
        YillikCiro yillikCiro2 = new YillikCiro();
        yillikCiro2.setId(yillikCiro1.getId());
        assertThat(yillikCiro1).isEqualTo(yillikCiro2);
        yillikCiro2.setId(2L);
        assertThat(yillikCiro1).isNotEqualTo(yillikCiro2);
        yillikCiro1.setId(null);
        assertThat(yillikCiro1).isNotEqualTo(yillikCiro2);
    }
}

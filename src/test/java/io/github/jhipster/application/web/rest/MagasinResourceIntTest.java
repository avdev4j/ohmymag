package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.OhmymagApp;

import io.github.jhipster.application.domain.Magasin;
import io.github.jhipster.application.domain.Departement;
import io.github.jhipster.application.repository.MagasinRepository;
import io.github.jhipster.application.service.MagasinService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.MagasinStatus;
/**
 * Test class for the MagasinResource REST controller.
 *
 * @see MagasinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OhmymagApp.class)
public class MagasinResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final MagasinStatus DEFAULT_STATUS = MagasinStatus.OPEN;
    private static final MagasinStatus UPDATED_STATUS = MagasinStatus.CLOSED;

    @Autowired
    private MagasinRepository magasinRepository;

    @Autowired
    private MagasinService magasinService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMagasinMockMvc;

    private Magasin magasin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MagasinResource magasinResource = new MagasinResource(magasinService);
        this.restMagasinMockMvc = MockMvcBuilders.standaloneSetup(magasinResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Magasin createEntity(EntityManager em) {
        Magasin magasin = new Magasin()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS);
        // Add required entity
        Departement departement = DepartementResourceIntTest.createEntity(em);
        em.persist(departement);
        em.flush();
        magasin.setDepartement(departement);
        return magasin;
    }

    @Before
    public void initTest() {
        magasin = createEntity(em);
    }

    @Test
    @Transactional
    public void createMagasin() throws Exception {
        int databaseSizeBeforeCreate = magasinRepository.findAll().size();

        // Create the Magasin
        restMagasinMockMvc.perform(post("/api/magasins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(magasin)))
            .andExpect(status().isCreated());

        // Validate the Magasin in the database
        List<Magasin> magasinList = magasinRepository.findAll();
        assertThat(magasinList).hasSize(databaseSizeBeforeCreate + 1);
        Magasin testMagasin = magasinList.get(magasinList.size() - 1);
        assertThat(testMagasin.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMagasin.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMagasin.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createMagasinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = magasinRepository.findAll().size();

        // Create the Magasin with an existing ID
        magasin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMagasinMockMvc.perform(post("/api/magasins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(magasin)))
            .andExpect(status().isBadRequest());

        // Validate the Magasin in the database
        List<Magasin> magasinList = magasinRepository.findAll();
        assertThat(magasinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = magasinRepository.findAll().size();
        // set the field null
        magasin.setCode(null);

        // Create the Magasin, which fails.

        restMagasinMockMvc.perform(post("/api/magasins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(magasin)))
            .andExpect(status().isBadRequest());

        List<Magasin> magasinList = magasinRepository.findAll();
        assertThat(magasinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = magasinRepository.findAll().size();
        // set the field null
        magasin.setStatus(null);

        // Create the Magasin, which fails.

        restMagasinMockMvc.perform(post("/api/magasins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(magasin)))
            .andExpect(status().isBadRequest());

        List<Magasin> magasinList = magasinRepository.findAll();
        assertThat(magasinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMagasins() throws Exception {
        // Initialize the database
        magasinRepository.saveAndFlush(magasin);

        // Get all the magasinList
        restMagasinMockMvc.perform(get("/api/magasins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(magasin.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getMagasin() throws Exception {
        // Initialize the database
        magasinRepository.saveAndFlush(magasin);

        // Get the magasin
        restMagasinMockMvc.perform(get("/api/magasins/{id}", magasin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(magasin.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMagasin() throws Exception {
        // Get the magasin
        restMagasinMockMvc.perform(get("/api/magasins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMagasin() throws Exception {
        // Initialize the database
        magasinService.save(magasin);

        int databaseSizeBeforeUpdate = magasinRepository.findAll().size();

        // Update the magasin
        Magasin updatedMagasin = magasinRepository.findById(magasin.getId()).get();
        // Disconnect from session so that the updates on updatedMagasin are not directly saved in db
        em.detach(updatedMagasin);
        updatedMagasin
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);

        restMagasinMockMvc.perform(put("/api/magasins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMagasin)))
            .andExpect(status().isOk());

        // Validate the Magasin in the database
        List<Magasin> magasinList = magasinRepository.findAll();
        assertThat(magasinList).hasSize(databaseSizeBeforeUpdate);
        Magasin testMagasin = magasinList.get(magasinList.size() - 1);
        assertThat(testMagasin.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMagasin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMagasin.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingMagasin() throws Exception {
        int databaseSizeBeforeUpdate = magasinRepository.findAll().size();

        // Create the Magasin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMagasinMockMvc.perform(put("/api/magasins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(magasin)))
            .andExpect(status().isBadRequest());

        // Validate the Magasin in the database
        List<Magasin> magasinList = magasinRepository.findAll();
        assertThat(magasinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMagasin() throws Exception {
        // Initialize the database
        magasinService.save(magasin);

        int databaseSizeBeforeDelete = magasinRepository.findAll().size();

        // Delete the magasin
        restMagasinMockMvc.perform(delete("/api/magasins/{id}", magasin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Magasin> magasinList = magasinRepository.findAll();
        assertThat(magasinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Magasin.class);
        Magasin magasin1 = new Magasin();
        magasin1.setId(1L);
        Magasin magasin2 = new Magasin();
        magasin2.setId(magasin1.getId());
        assertThat(magasin1).isEqualTo(magasin2);
        magasin2.setId(2L);
        assertThat(magasin1).isNotEqualTo(magasin2);
        magasin1.setId(null);
        assertThat(magasin1).isNotEqualTo(magasin2);
    }
}

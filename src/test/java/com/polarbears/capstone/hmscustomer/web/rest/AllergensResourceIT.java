package com.polarbears.capstone.hmscustomer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmscustomer.IntegrationTest;
import com.polarbears.capstone.hmscustomer.domain.Allergens;
import com.polarbears.capstone.hmscustomer.domain.CustomerHealths;
import com.polarbears.capstone.hmscustomer.repository.AllergensRepository;
import com.polarbears.capstone.hmscustomer.service.criteria.AllergensCriteria;
import com.polarbears.capstone.hmscustomer.service.dto.AllergensDTO;
import com.polarbears.capstone.hmscustomer.service.mapper.AllergensMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AllergensResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AllergensResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_ID = 1L;
    private static final Long UPDATED_CONTACT_ID = 2L;
    private static final Long SMALLER_CONTACT_ID = 1L - 1L;

    private static final Integer DEFAULT_INGRADIENT_ID = 1;
    private static final Integer UPDATED_INGRADIENT_ID = 2;
    private static final Integer SMALLER_INGRADIENT_ID = 1 - 1;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/allergens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AllergensRepository allergensRepository;

    @Autowired
    private AllergensMapper allergensMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAllergensMockMvc;

    private Allergens allergens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allergens createEntity(EntityManager em) {
        Allergens allergens = new Allergens()
            .name(DEFAULT_NAME)
            .contactId(DEFAULT_CONTACT_ID)
            .ingradientId(DEFAULT_INGRADIENT_ID)
            .active(DEFAULT_ACTIVE)
            .detail(DEFAULT_DETAIL)
            .createdDate(DEFAULT_CREATED_DATE);
        return allergens;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allergens createUpdatedEntity(EntityManager em) {
        Allergens allergens = new Allergens()
            .name(UPDATED_NAME)
            .contactId(UPDATED_CONTACT_ID)
            .ingradientId(UPDATED_INGRADIENT_ID)
            .active(UPDATED_ACTIVE)
            .detail(UPDATED_DETAIL)
            .createdDate(UPDATED_CREATED_DATE);
        return allergens;
    }

    @BeforeEach
    public void initTest() {
        allergens = createEntity(em);
    }

    @Test
    @Transactional
    void createAllergens() throws Exception {
        int databaseSizeBeforeCreate = allergensRepository.findAll().size();
        // Create the Allergens
        AllergensDTO allergensDTO = allergensMapper.toDto(allergens);
        restAllergensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergensDTO)))
            .andExpect(status().isCreated());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeCreate + 1);
        Allergens testAllergens = allergensList.get(allergensList.size() - 1);
        assertThat(testAllergens.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAllergens.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testAllergens.getIngradientId()).isEqualTo(DEFAULT_INGRADIENT_ID);
        assertThat(testAllergens.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAllergens.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testAllergens.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createAllergensWithExistingId() throws Exception {
        // Create the Allergens with an existing ID
        allergens.setId(1L);
        AllergensDTO allergensDTO = allergensMapper.toDto(allergens);

        int databaseSizeBeforeCreate = allergensRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllergensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergensDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAllergens() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList
        restAllergensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergens.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].ingradientId").value(hasItem(DEFAULT_INGRADIENT_ID)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getAllergens() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get the allergens
        restAllergensMockMvc
            .perform(get(ENTITY_API_URL_ID, allergens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(allergens.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.ingradientId").value(DEFAULT_INGRADIENT_ID))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getAllergensByIdFiltering() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        Long id = allergens.getId();

        defaultAllergensShouldBeFound("id.equals=" + id);
        defaultAllergensShouldNotBeFound("id.notEquals=" + id);

        defaultAllergensShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAllergensShouldNotBeFound("id.greaterThan=" + id);

        defaultAllergensShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAllergensShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAllergensByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where name equals to DEFAULT_NAME
        defaultAllergensShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the allergensList where name equals to UPDATED_NAME
        defaultAllergensShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAllergensByNameIsInShouldWork() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAllergensShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the allergensList where name equals to UPDATED_NAME
        defaultAllergensShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAllergensByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where name is not null
        defaultAllergensShouldBeFound("name.specified=true");

        // Get all the allergensList where name is null
        defaultAllergensShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllAllergensByNameContainsSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where name contains DEFAULT_NAME
        defaultAllergensShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the allergensList where name contains UPDATED_NAME
        defaultAllergensShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAllergensByNameNotContainsSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where name does not contain DEFAULT_NAME
        defaultAllergensShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the allergensList where name does not contain UPDATED_NAME
        defaultAllergensShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAllergensByContactIdIsEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where contactId equals to DEFAULT_CONTACT_ID
        defaultAllergensShouldBeFound("contactId.equals=" + DEFAULT_CONTACT_ID);

        // Get all the allergensList where contactId equals to UPDATED_CONTACT_ID
        defaultAllergensShouldNotBeFound("contactId.equals=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByContactIdIsInShouldWork() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where contactId in DEFAULT_CONTACT_ID or UPDATED_CONTACT_ID
        defaultAllergensShouldBeFound("contactId.in=" + DEFAULT_CONTACT_ID + "," + UPDATED_CONTACT_ID);

        // Get all the allergensList where contactId equals to UPDATED_CONTACT_ID
        defaultAllergensShouldNotBeFound("contactId.in=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByContactIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where contactId is not null
        defaultAllergensShouldBeFound("contactId.specified=true");

        // Get all the allergensList where contactId is null
        defaultAllergensShouldNotBeFound("contactId.specified=false");
    }

    @Test
    @Transactional
    void getAllAllergensByContactIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where contactId is greater than or equal to DEFAULT_CONTACT_ID
        defaultAllergensShouldBeFound("contactId.greaterThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the allergensList where contactId is greater than or equal to UPDATED_CONTACT_ID
        defaultAllergensShouldNotBeFound("contactId.greaterThanOrEqual=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByContactIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where contactId is less than or equal to DEFAULT_CONTACT_ID
        defaultAllergensShouldBeFound("contactId.lessThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the allergensList where contactId is less than or equal to SMALLER_CONTACT_ID
        defaultAllergensShouldNotBeFound("contactId.lessThanOrEqual=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByContactIdIsLessThanSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where contactId is less than DEFAULT_CONTACT_ID
        defaultAllergensShouldNotBeFound("contactId.lessThan=" + DEFAULT_CONTACT_ID);

        // Get all the allergensList where contactId is less than UPDATED_CONTACT_ID
        defaultAllergensShouldBeFound("contactId.lessThan=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByContactIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where contactId is greater than DEFAULT_CONTACT_ID
        defaultAllergensShouldNotBeFound("contactId.greaterThan=" + DEFAULT_CONTACT_ID);

        // Get all the allergensList where contactId is greater than SMALLER_CONTACT_ID
        defaultAllergensShouldBeFound("contactId.greaterThan=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByIngradientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where ingradientId equals to DEFAULT_INGRADIENT_ID
        defaultAllergensShouldBeFound("ingradientId.equals=" + DEFAULT_INGRADIENT_ID);

        // Get all the allergensList where ingradientId equals to UPDATED_INGRADIENT_ID
        defaultAllergensShouldNotBeFound("ingradientId.equals=" + UPDATED_INGRADIENT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByIngradientIdIsInShouldWork() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where ingradientId in DEFAULT_INGRADIENT_ID or UPDATED_INGRADIENT_ID
        defaultAllergensShouldBeFound("ingradientId.in=" + DEFAULT_INGRADIENT_ID + "," + UPDATED_INGRADIENT_ID);

        // Get all the allergensList where ingradientId equals to UPDATED_INGRADIENT_ID
        defaultAllergensShouldNotBeFound("ingradientId.in=" + UPDATED_INGRADIENT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByIngradientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where ingradientId is not null
        defaultAllergensShouldBeFound("ingradientId.specified=true");

        // Get all the allergensList where ingradientId is null
        defaultAllergensShouldNotBeFound("ingradientId.specified=false");
    }

    @Test
    @Transactional
    void getAllAllergensByIngradientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where ingradientId is greater than or equal to DEFAULT_INGRADIENT_ID
        defaultAllergensShouldBeFound("ingradientId.greaterThanOrEqual=" + DEFAULT_INGRADIENT_ID);

        // Get all the allergensList where ingradientId is greater than or equal to UPDATED_INGRADIENT_ID
        defaultAllergensShouldNotBeFound("ingradientId.greaterThanOrEqual=" + UPDATED_INGRADIENT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByIngradientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where ingradientId is less than or equal to DEFAULT_INGRADIENT_ID
        defaultAllergensShouldBeFound("ingradientId.lessThanOrEqual=" + DEFAULT_INGRADIENT_ID);

        // Get all the allergensList where ingradientId is less than or equal to SMALLER_INGRADIENT_ID
        defaultAllergensShouldNotBeFound("ingradientId.lessThanOrEqual=" + SMALLER_INGRADIENT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByIngradientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where ingradientId is less than DEFAULT_INGRADIENT_ID
        defaultAllergensShouldNotBeFound("ingradientId.lessThan=" + DEFAULT_INGRADIENT_ID);

        // Get all the allergensList where ingradientId is less than UPDATED_INGRADIENT_ID
        defaultAllergensShouldBeFound("ingradientId.lessThan=" + UPDATED_INGRADIENT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByIngradientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where ingradientId is greater than DEFAULT_INGRADIENT_ID
        defaultAllergensShouldNotBeFound("ingradientId.greaterThan=" + DEFAULT_INGRADIENT_ID);

        // Get all the allergensList where ingradientId is greater than SMALLER_INGRADIENT_ID
        defaultAllergensShouldBeFound("ingradientId.greaterThan=" + SMALLER_INGRADIENT_ID);
    }

    @Test
    @Transactional
    void getAllAllergensByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where active equals to DEFAULT_ACTIVE
        defaultAllergensShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the allergensList where active equals to UPDATED_ACTIVE
        defaultAllergensShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllAllergensByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAllergensShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the allergensList where active equals to UPDATED_ACTIVE
        defaultAllergensShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllAllergensByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where active is not null
        defaultAllergensShouldBeFound("active.specified=true");

        // Get all the allergensList where active is null
        defaultAllergensShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllAllergensByDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where detail equals to DEFAULT_DETAIL
        defaultAllergensShouldBeFound("detail.equals=" + DEFAULT_DETAIL);

        // Get all the allergensList where detail equals to UPDATED_DETAIL
        defaultAllergensShouldNotBeFound("detail.equals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllAllergensByDetailIsInShouldWork() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where detail in DEFAULT_DETAIL or UPDATED_DETAIL
        defaultAllergensShouldBeFound("detail.in=" + DEFAULT_DETAIL + "," + UPDATED_DETAIL);

        // Get all the allergensList where detail equals to UPDATED_DETAIL
        defaultAllergensShouldNotBeFound("detail.in=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllAllergensByDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where detail is not null
        defaultAllergensShouldBeFound("detail.specified=true");

        // Get all the allergensList where detail is null
        defaultAllergensShouldNotBeFound("detail.specified=false");
    }

    @Test
    @Transactional
    void getAllAllergensByDetailContainsSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where detail contains DEFAULT_DETAIL
        defaultAllergensShouldBeFound("detail.contains=" + DEFAULT_DETAIL);

        // Get all the allergensList where detail contains UPDATED_DETAIL
        defaultAllergensShouldNotBeFound("detail.contains=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllAllergensByDetailNotContainsSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where detail does not contain DEFAULT_DETAIL
        defaultAllergensShouldNotBeFound("detail.doesNotContain=" + DEFAULT_DETAIL);

        // Get all the allergensList where detail does not contain UPDATED_DETAIL
        defaultAllergensShouldBeFound("detail.doesNotContain=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllAllergensByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where createdDate equals to DEFAULT_CREATED_DATE
        defaultAllergensShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the allergensList where createdDate equals to UPDATED_CREATED_DATE
        defaultAllergensShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAllergensByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultAllergensShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the allergensList where createdDate equals to UPDATED_CREATED_DATE
        defaultAllergensShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAllergensByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where createdDate is not null
        defaultAllergensShouldBeFound("createdDate.specified=true");

        // Get all the allergensList where createdDate is null
        defaultAllergensShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAllergensByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultAllergensShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the allergensList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultAllergensShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAllergensByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultAllergensShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the allergensList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultAllergensShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAllergensByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where createdDate is less than DEFAULT_CREATED_DATE
        defaultAllergensShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the allergensList where createdDate is less than UPDATED_CREATED_DATE
        defaultAllergensShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAllergensByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        // Get all the allergensList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultAllergensShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the allergensList where createdDate is greater than SMALLER_CREATED_DATE
        defaultAllergensShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAllergensByCustomerHealthsIsEqualToSomething() throws Exception {
        CustomerHealths customerHealths;
        if (TestUtil.findAll(em, CustomerHealths.class).isEmpty()) {
            allergensRepository.saveAndFlush(allergens);
            customerHealths = CustomerHealthsResourceIT.createEntity(em);
        } else {
            customerHealths = TestUtil.findAll(em, CustomerHealths.class).get(0);
        }
        em.persist(customerHealths);
        em.flush();
        allergens.addCustomerHealths(customerHealths);
        allergensRepository.saveAndFlush(allergens);
        Long customerHealthsId = customerHealths.getId();

        // Get all the allergensList where customerHealths equals to customerHealthsId
        defaultAllergensShouldBeFound("customerHealthsId.equals=" + customerHealthsId);

        // Get all the allergensList where customerHealths equals to (customerHealthsId + 1)
        defaultAllergensShouldNotBeFound("customerHealthsId.equals=" + (customerHealthsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAllergensShouldBeFound(String filter) throws Exception {
        restAllergensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergens.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].ingradientId").value(hasItem(DEFAULT_INGRADIENT_ID)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restAllergensMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAllergensShouldNotBeFound(String filter) throws Exception {
        restAllergensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAllergensMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAllergens() throws Exception {
        // Get the allergens
        restAllergensMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAllergens() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        int databaseSizeBeforeUpdate = allergensRepository.findAll().size();

        // Update the allergens
        Allergens updatedAllergens = allergensRepository.findById(allergens.getId()).get();
        // Disconnect from session so that the updates on updatedAllergens are not directly saved in db
        em.detach(updatedAllergens);
        updatedAllergens
            .name(UPDATED_NAME)
            .contactId(UPDATED_CONTACT_ID)
            .ingradientId(UPDATED_INGRADIENT_ID)
            .active(UPDATED_ACTIVE)
            .detail(UPDATED_DETAIL)
            .createdDate(UPDATED_CREATED_DATE);
        AllergensDTO allergensDTO = allergensMapper.toDto(updatedAllergens);

        restAllergensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, allergensDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(allergensDTO))
            )
            .andExpect(status().isOk());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeUpdate);
        Allergens testAllergens = allergensList.get(allergensList.size() - 1);
        assertThat(testAllergens.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAllergens.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testAllergens.getIngradientId()).isEqualTo(UPDATED_INGRADIENT_ID);
        assertThat(testAllergens.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAllergens.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAllergens.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAllergens() throws Exception {
        int databaseSizeBeforeUpdate = allergensRepository.findAll().size();
        allergens.setId(count.incrementAndGet());

        // Create the Allergens
        AllergensDTO allergensDTO = allergensMapper.toDto(allergens);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllergensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, allergensDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(allergensDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAllergens() throws Exception {
        int databaseSizeBeforeUpdate = allergensRepository.findAll().size();
        allergens.setId(count.incrementAndGet());

        // Create the Allergens
        AllergensDTO allergensDTO = allergensMapper.toDto(allergens);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAllergensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(allergensDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAllergens() throws Exception {
        int databaseSizeBeforeUpdate = allergensRepository.findAll().size();
        allergens.setId(count.incrementAndGet());

        // Create the Allergens
        AllergensDTO allergensDTO = allergensMapper.toDto(allergens);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAllergensMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergensDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAllergensWithPatch() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        int databaseSizeBeforeUpdate = allergensRepository.findAll().size();

        // Update the allergens using partial update
        Allergens partialUpdatedAllergens = new Allergens();
        partialUpdatedAllergens.setId(allergens.getId());

        partialUpdatedAllergens
            .name(UPDATED_NAME)
            .contactId(UPDATED_CONTACT_ID)
            .ingradientId(UPDATED_INGRADIENT_ID)
            .detail(UPDATED_DETAIL)
            .createdDate(UPDATED_CREATED_DATE);

        restAllergensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAllergens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAllergens))
            )
            .andExpect(status().isOk());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeUpdate);
        Allergens testAllergens = allergensList.get(allergensList.size() - 1);
        assertThat(testAllergens.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAllergens.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testAllergens.getIngradientId()).isEqualTo(UPDATED_INGRADIENT_ID);
        assertThat(testAllergens.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAllergens.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAllergens.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAllergensWithPatch() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        int databaseSizeBeforeUpdate = allergensRepository.findAll().size();

        // Update the allergens using partial update
        Allergens partialUpdatedAllergens = new Allergens();
        partialUpdatedAllergens.setId(allergens.getId());

        partialUpdatedAllergens
            .name(UPDATED_NAME)
            .contactId(UPDATED_CONTACT_ID)
            .ingradientId(UPDATED_INGRADIENT_ID)
            .active(UPDATED_ACTIVE)
            .detail(UPDATED_DETAIL)
            .createdDate(UPDATED_CREATED_DATE);

        restAllergensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAllergens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAllergens))
            )
            .andExpect(status().isOk());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeUpdate);
        Allergens testAllergens = allergensList.get(allergensList.size() - 1);
        assertThat(testAllergens.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAllergens.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testAllergens.getIngradientId()).isEqualTo(UPDATED_INGRADIENT_ID);
        assertThat(testAllergens.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAllergens.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAllergens.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAllergens() throws Exception {
        int databaseSizeBeforeUpdate = allergensRepository.findAll().size();
        allergens.setId(count.incrementAndGet());

        // Create the Allergens
        AllergensDTO allergensDTO = allergensMapper.toDto(allergens);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllergensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, allergensDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(allergensDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAllergens() throws Exception {
        int databaseSizeBeforeUpdate = allergensRepository.findAll().size();
        allergens.setId(count.incrementAndGet());

        // Create the Allergens
        AllergensDTO allergensDTO = allergensMapper.toDto(allergens);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAllergensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(allergensDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAllergens() throws Exception {
        int databaseSizeBeforeUpdate = allergensRepository.findAll().size();
        allergens.setId(count.incrementAndGet());

        // Create the Allergens
        AllergensDTO allergensDTO = allergensMapper.toDto(allergens);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAllergensMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(allergensDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Allergens in the database
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAllergens() throws Exception {
        // Initialize the database
        allergensRepository.saveAndFlush(allergens);

        int databaseSizeBeforeDelete = allergensRepository.findAll().size();

        // Delete the allergens
        restAllergensMockMvc
            .perform(delete(ENTITY_API_URL_ID, allergens.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Allergens> allergensList = allergensRepository.findAll();
        assertThat(allergensList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.polarbears.capstone.hmscustomer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmscustomer.IntegrationTest;
import com.polarbears.capstone.hmscustomer.domain.Allergens;
import com.polarbears.capstone.hmscustomer.domain.CustomerHealths;
import com.polarbears.capstone.hmscustomer.domain.enumeration.BODYFATS;
import com.polarbears.capstone.hmscustomer.domain.enumeration.GOALS;
import com.polarbears.capstone.hmscustomer.domain.enumeration.UNITS;
import com.polarbears.capstone.hmscustomer.repository.CustomerHealthsRepository;
import com.polarbears.capstone.hmscustomer.service.CustomerHealthsService;
import com.polarbears.capstone.hmscustomer.service.criteria.CustomerHealthsCriteria;
import com.polarbears.capstone.hmscustomer.service.dto.CustomerHealthsDTO;
import com.polarbears.capstone.hmscustomer.service.mapper.CustomerHealthsMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CustomerHealthsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CustomerHealthsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_ID = 1L;
    private static final Long UPDATED_CONTACT_ID = 2L;
    private static final Long SMALLER_CONTACT_ID = 1L - 1L;

    private static final Double DEFAULT_CURRENT_WEIGHT = 1D;
    private static final Double UPDATED_CURRENT_WEIGHT = 2D;
    private static final Double SMALLER_CURRENT_WEIGHT = 1D - 1D;

    private static final Double DEFAULT_CURRENT_HEIGHT = 1D;
    private static final Double UPDATED_CURRENT_HEIGHT = 2D;
    private static final Double SMALLER_CURRENT_HEIGHT = 1D - 1D;

    private static final UNITS DEFAULT_MEASURE_UNIT = UNITS.KG;
    private static final UNITS UPDATED_MEASURE_UNIT = UNITS.LB;

    private static final Double DEFAULT_ACTIVITY_LEVEL = 1D;
    private static final Double UPDATED_ACTIVITY_LEVEL = 2D;
    private static final Double SMALLER_ACTIVITY_LEVEL = 1D - 1D;

    private static final Double DEFAULT_TARGET_WEIGHT = 1D;
    private static final Double UPDATED_TARGET_WEIGHT = 2D;
    private static final Double SMALLER_TARGET_WEIGHT = 1D - 1D;

    private static final Double DEFAULT_TARGER_CALORIE = 1D;
    private static final Double UPDATED_TARGER_CALORIE = 2D;
    private static final Double SMALLER_TARGER_CALORIE = 1D - 1D;

    private static final BODYFATS DEFAULT_TARGET_BODY_FAT = BODYFATS.LOW;
    private static final BODYFATS UPDATED_TARGET_BODY_FAT = BODYFATS.MEDIUM;

    private static final GOALS DEFAULT_GOAL = GOALS.LOSEFAT;
    private static final GOALS UPDATED_GOAL = GOALS.MAINTAIN;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/customer-healths";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerHealthsRepository customerHealthsRepository;

    @Mock
    private CustomerHealthsRepository customerHealthsRepositoryMock;

    @Autowired
    private CustomerHealthsMapper customerHealthsMapper;

    @Mock
    private CustomerHealthsService customerHealthsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerHealthsMockMvc;

    private CustomerHealths customerHealths;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerHealths createEntity(EntityManager em) {
        CustomerHealths customerHealths = new CustomerHealths()
            .name(DEFAULT_NAME)
            .contactId(DEFAULT_CONTACT_ID)
            .currentWeight(DEFAULT_CURRENT_WEIGHT)
            .currentHeight(DEFAULT_CURRENT_HEIGHT)
            .measureUnit(DEFAULT_MEASURE_UNIT)
            .activityLevel(DEFAULT_ACTIVITY_LEVEL)
            .targetWeight(DEFAULT_TARGET_WEIGHT)
            .targerCalorie(DEFAULT_TARGER_CALORIE)
            .targetBodyFat(DEFAULT_TARGET_BODY_FAT)
            .goal(DEFAULT_GOAL)
            .active(DEFAULT_ACTIVE)
            .detail(DEFAULT_DETAIL)
            .createdDate(DEFAULT_CREATED_DATE);
        return customerHealths;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerHealths createUpdatedEntity(EntityManager em) {
        CustomerHealths customerHealths = new CustomerHealths()
            .name(UPDATED_NAME)
            .contactId(UPDATED_CONTACT_ID)
            .currentWeight(UPDATED_CURRENT_WEIGHT)
            .currentHeight(UPDATED_CURRENT_HEIGHT)
            .measureUnit(UPDATED_MEASURE_UNIT)
            .activityLevel(UPDATED_ACTIVITY_LEVEL)
            .targetWeight(UPDATED_TARGET_WEIGHT)
            .targerCalorie(UPDATED_TARGER_CALORIE)
            .targetBodyFat(UPDATED_TARGET_BODY_FAT)
            .goal(UPDATED_GOAL)
            .active(UPDATED_ACTIVE)
            .detail(UPDATED_DETAIL)
            .createdDate(UPDATED_CREATED_DATE);
        return customerHealths;
    }

    @BeforeEach
    public void initTest() {
        customerHealths = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomerHealths() throws Exception {
        int databaseSizeBeforeCreate = customerHealthsRepository.findAll().size();
        // Create the CustomerHealths
        CustomerHealthsDTO customerHealthsDTO = customerHealthsMapper.toDto(customerHealths);
        restCustomerHealthsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerHealthsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerHealths testCustomerHealths = customerHealthsList.get(customerHealthsList.size() - 1);
        assertThat(testCustomerHealths.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerHealths.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testCustomerHealths.getCurrentWeight()).isEqualTo(DEFAULT_CURRENT_WEIGHT);
        assertThat(testCustomerHealths.getCurrentHeight()).isEqualTo(DEFAULT_CURRENT_HEIGHT);
        assertThat(testCustomerHealths.getMeasureUnit()).isEqualTo(DEFAULT_MEASURE_UNIT);
        assertThat(testCustomerHealths.getActivityLevel()).isEqualTo(DEFAULT_ACTIVITY_LEVEL);
        assertThat(testCustomerHealths.getTargetWeight()).isEqualTo(DEFAULT_TARGET_WEIGHT);
        assertThat(testCustomerHealths.getTargerCalorie()).isEqualTo(DEFAULT_TARGER_CALORIE);
        assertThat(testCustomerHealths.getTargetBodyFat()).isEqualTo(DEFAULT_TARGET_BODY_FAT);
        assertThat(testCustomerHealths.getGoal()).isEqualTo(DEFAULT_GOAL);
        assertThat(testCustomerHealths.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCustomerHealths.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testCustomerHealths.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createCustomerHealthsWithExistingId() throws Exception {
        // Create the CustomerHealths with an existing ID
        customerHealths.setId(1L);
        CustomerHealthsDTO customerHealthsDTO = customerHealthsMapper.toDto(customerHealths);

        int databaseSizeBeforeCreate = customerHealthsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerHealthsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerHealthsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomerHealths() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList
        restCustomerHealthsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerHealths.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].currentWeight").value(hasItem(DEFAULT_CURRENT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].currentHeight").value(hasItem(DEFAULT_CURRENT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].measureUnit").value(hasItem(DEFAULT_MEASURE_UNIT.toString())))
            .andExpect(jsonPath("$.[*].activityLevel").value(hasItem(DEFAULT_ACTIVITY_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].targetWeight").value(hasItem(DEFAULT_TARGET_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].targerCalorie").value(hasItem(DEFAULT_TARGER_CALORIE.doubleValue())))
            .andExpect(jsonPath("$.[*].targetBodyFat").value(hasItem(DEFAULT_TARGET_BODY_FAT.toString())))
            .andExpect(jsonPath("$.[*].goal").value(hasItem(DEFAULT_GOAL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCustomerHealthsWithEagerRelationshipsIsEnabled() throws Exception {
        when(customerHealthsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCustomerHealthsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(customerHealthsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCustomerHealthsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(customerHealthsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCustomerHealthsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(customerHealthsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCustomerHealths() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get the customerHealths
        restCustomerHealthsMockMvc
            .perform(get(ENTITY_API_URL_ID, customerHealths.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerHealths.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.currentWeight").value(DEFAULT_CURRENT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.currentHeight").value(DEFAULT_CURRENT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.measureUnit").value(DEFAULT_MEASURE_UNIT.toString()))
            .andExpect(jsonPath("$.activityLevel").value(DEFAULT_ACTIVITY_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.targetWeight").value(DEFAULT_TARGET_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.targerCalorie").value(DEFAULT_TARGER_CALORIE.doubleValue()))
            .andExpect(jsonPath("$.targetBodyFat").value(DEFAULT_TARGET_BODY_FAT.toString()))
            .andExpect(jsonPath("$.goal").value(DEFAULT_GOAL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getCustomerHealthsByIdFiltering() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        Long id = customerHealths.getId();

        defaultCustomerHealthsShouldBeFound("id.equals=" + id);
        defaultCustomerHealthsShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerHealthsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerHealthsShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerHealthsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerHealthsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where name equals to DEFAULT_NAME
        defaultCustomerHealthsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerHealthsList where name equals to UPDATED_NAME
        defaultCustomerHealthsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerHealthsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerHealthsList where name equals to UPDATED_NAME
        defaultCustomerHealthsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where name is not null
        defaultCustomerHealthsShouldBeFound("name.specified=true");

        // Get all the customerHealthsList where name is null
        defaultCustomerHealthsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByNameContainsSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where name contains DEFAULT_NAME
        defaultCustomerHealthsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the customerHealthsList where name contains UPDATED_NAME
        defaultCustomerHealthsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where name does not contain DEFAULT_NAME
        defaultCustomerHealthsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the customerHealthsList where name does not contain UPDATED_NAME
        defaultCustomerHealthsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByContactIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where contactId equals to DEFAULT_CONTACT_ID
        defaultCustomerHealthsShouldBeFound("contactId.equals=" + DEFAULT_CONTACT_ID);

        // Get all the customerHealthsList where contactId equals to UPDATED_CONTACT_ID
        defaultCustomerHealthsShouldNotBeFound("contactId.equals=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByContactIdIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where contactId in DEFAULT_CONTACT_ID or UPDATED_CONTACT_ID
        defaultCustomerHealthsShouldBeFound("contactId.in=" + DEFAULT_CONTACT_ID + "," + UPDATED_CONTACT_ID);

        // Get all the customerHealthsList where contactId equals to UPDATED_CONTACT_ID
        defaultCustomerHealthsShouldNotBeFound("contactId.in=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByContactIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where contactId is not null
        defaultCustomerHealthsShouldBeFound("contactId.specified=true");

        // Get all the customerHealthsList where contactId is null
        defaultCustomerHealthsShouldNotBeFound("contactId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByContactIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where contactId is greater than or equal to DEFAULT_CONTACT_ID
        defaultCustomerHealthsShouldBeFound("contactId.greaterThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the customerHealthsList where contactId is greater than or equal to UPDATED_CONTACT_ID
        defaultCustomerHealthsShouldNotBeFound("contactId.greaterThanOrEqual=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByContactIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where contactId is less than or equal to DEFAULT_CONTACT_ID
        defaultCustomerHealthsShouldBeFound("contactId.lessThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the customerHealthsList where contactId is less than or equal to SMALLER_CONTACT_ID
        defaultCustomerHealthsShouldNotBeFound("contactId.lessThanOrEqual=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByContactIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where contactId is less than DEFAULT_CONTACT_ID
        defaultCustomerHealthsShouldNotBeFound("contactId.lessThan=" + DEFAULT_CONTACT_ID);

        // Get all the customerHealthsList where contactId is less than UPDATED_CONTACT_ID
        defaultCustomerHealthsShouldBeFound("contactId.lessThan=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByContactIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where contactId is greater than DEFAULT_CONTACT_ID
        defaultCustomerHealthsShouldNotBeFound("contactId.greaterThan=" + DEFAULT_CONTACT_ID);

        // Get all the customerHealthsList where contactId is greater than SMALLER_CONTACT_ID
        defaultCustomerHealthsShouldBeFound("contactId.greaterThan=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentWeight equals to DEFAULT_CURRENT_WEIGHT
        defaultCustomerHealthsShouldBeFound("currentWeight.equals=" + DEFAULT_CURRENT_WEIGHT);

        // Get all the customerHealthsList where currentWeight equals to UPDATED_CURRENT_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentWeight.equals=" + UPDATED_CURRENT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentWeightIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentWeight in DEFAULT_CURRENT_WEIGHT or UPDATED_CURRENT_WEIGHT
        defaultCustomerHealthsShouldBeFound("currentWeight.in=" + DEFAULT_CURRENT_WEIGHT + "," + UPDATED_CURRENT_WEIGHT);

        // Get all the customerHealthsList where currentWeight equals to UPDATED_CURRENT_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentWeight.in=" + UPDATED_CURRENT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentWeight is not null
        defaultCustomerHealthsShouldBeFound("currentWeight.specified=true");

        // Get all the customerHealthsList where currentWeight is null
        defaultCustomerHealthsShouldNotBeFound("currentWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentWeight is greater than or equal to DEFAULT_CURRENT_WEIGHT
        defaultCustomerHealthsShouldBeFound("currentWeight.greaterThanOrEqual=" + DEFAULT_CURRENT_WEIGHT);

        // Get all the customerHealthsList where currentWeight is greater than or equal to UPDATED_CURRENT_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentWeight.greaterThanOrEqual=" + UPDATED_CURRENT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentWeight is less than or equal to DEFAULT_CURRENT_WEIGHT
        defaultCustomerHealthsShouldBeFound("currentWeight.lessThanOrEqual=" + DEFAULT_CURRENT_WEIGHT);

        // Get all the customerHealthsList where currentWeight is less than or equal to SMALLER_CURRENT_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentWeight.lessThanOrEqual=" + SMALLER_CURRENT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentWeight is less than DEFAULT_CURRENT_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentWeight.lessThan=" + DEFAULT_CURRENT_WEIGHT);

        // Get all the customerHealthsList where currentWeight is less than UPDATED_CURRENT_WEIGHT
        defaultCustomerHealthsShouldBeFound("currentWeight.lessThan=" + UPDATED_CURRENT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentWeight is greater than DEFAULT_CURRENT_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentWeight.greaterThan=" + DEFAULT_CURRENT_WEIGHT);

        // Get all the customerHealthsList where currentWeight is greater than SMALLER_CURRENT_WEIGHT
        defaultCustomerHealthsShouldBeFound("currentWeight.greaterThan=" + SMALLER_CURRENT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentHeight equals to DEFAULT_CURRENT_HEIGHT
        defaultCustomerHealthsShouldBeFound("currentHeight.equals=" + DEFAULT_CURRENT_HEIGHT);

        // Get all the customerHealthsList where currentHeight equals to UPDATED_CURRENT_HEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentHeight.equals=" + UPDATED_CURRENT_HEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentHeightIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentHeight in DEFAULT_CURRENT_HEIGHT or UPDATED_CURRENT_HEIGHT
        defaultCustomerHealthsShouldBeFound("currentHeight.in=" + DEFAULT_CURRENT_HEIGHT + "," + UPDATED_CURRENT_HEIGHT);

        // Get all the customerHealthsList where currentHeight equals to UPDATED_CURRENT_HEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentHeight.in=" + UPDATED_CURRENT_HEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentHeight is not null
        defaultCustomerHealthsShouldBeFound("currentHeight.specified=true");

        // Get all the customerHealthsList where currentHeight is null
        defaultCustomerHealthsShouldNotBeFound("currentHeight.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentHeight is greater than or equal to DEFAULT_CURRENT_HEIGHT
        defaultCustomerHealthsShouldBeFound("currentHeight.greaterThanOrEqual=" + DEFAULT_CURRENT_HEIGHT);

        // Get all the customerHealthsList where currentHeight is greater than or equal to UPDATED_CURRENT_HEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentHeight.greaterThanOrEqual=" + UPDATED_CURRENT_HEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentHeight is less than or equal to DEFAULT_CURRENT_HEIGHT
        defaultCustomerHealthsShouldBeFound("currentHeight.lessThanOrEqual=" + DEFAULT_CURRENT_HEIGHT);

        // Get all the customerHealthsList where currentHeight is less than or equal to SMALLER_CURRENT_HEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentHeight.lessThanOrEqual=" + SMALLER_CURRENT_HEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentHeight is less than DEFAULT_CURRENT_HEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentHeight.lessThan=" + DEFAULT_CURRENT_HEIGHT);

        // Get all the customerHealthsList where currentHeight is less than UPDATED_CURRENT_HEIGHT
        defaultCustomerHealthsShouldBeFound("currentHeight.lessThan=" + UPDATED_CURRENT_HEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCurrentHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where currentHeight is greater than DEFAULT_CURRENT_HEIGHT
        defaultCustomerHealthsShouldNotBeFound("currentHeight.greaterThan=" + DEFAULT_CURRENT_HEIGHT);

        // Get all the customerHealthsList where currentHeight is greater than SMALLER_CURRENT_HEIGHT
        defaultCustomerHealthsShouldBeFound("currentHeight.greaterThan=" + SMALLER_CURRENT_HEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByMeasureUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where measureUnit equals to DEFAULT_MEASURE_UNIT
        defaultCustomerHealthsShouldBeFound("measureUnit.equals=" + DEFAULT_MEASURE_UNIT);

        // Get all the customerHealthsList where measureUnit equals to UPDATED_MEASURE_UNIT
        defaultCustomerHealthsShouldNotBeFound("measureUnit.equals=" + UPDATED_MEASURE_UNIT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByMeasureUnitIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where measureUnit in DEFAULT_MEASURE_UNIT or UPDATED_MEASURE_UNIT
        defaultCustomerHealthsShouldBeFound("measureUnit.in=" + DEFAULT_MEASURE_UNIT + "," + UPDATED_MEASURE_UNIT);

        // Get all the customerHealthsList where measureUnit equals to UPDATED_MEASURE_UNIT
        defaultCustomerHealthsShouldNotBeFound("measureUnit.in=" + UPDATED_MEASURE_UNIT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByMeasureUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where measureUnit is not null
        defaultCustomerHealthsShouldBeFound("measureUnit.specified=true");

        // Get all the customerHealthsList where measureUnit is null
        defaultCustomerHealthsShouldNotBeFound("measureUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActivityLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where activityLevel equals to DEFAULT_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldBeFound("activityLevel.equals=" + DEFAULT_ACTIVITY_LEVEL);

        // Get all the customerHealthsList where activityLevel equals to UPDATED_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldNotBeFound("activityLevel.equals=" + UPDATED_ACTIVITY_LEVEL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActivityLevelIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where activityLevel in DEFAULT_ACTIVITY_LEVEL or UPDATED_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldBeFound("activityLevel.in=" + DEFAULT_ACTIVITY_LEVEL + "," + UPDATED_ACTIVITY_LEVEL);

        // Get all the customerHealthsList where activityLevel equals to UPDATED_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldNotBeFound("activityLevel.in=" + UPDATED_ACTIVITY_LEVEL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActivityLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where activityLevel is not null
        defaultCustomerHealthsShouldBeFound("activityLevel.specified=true");

        // Get all the customerHealthsList where activityLevel is null
        defaultCustomerHealthsShouldNotBeFound("activityLevel.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActivityLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where activityLevel is greater than or equal to DEFAULT_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldBeFound("activityLevel.greaterThanOrEqual=" + DEFAULT_ACTIVITY_LEVEL);

        // Get all the customerHealthsList where activityLevel is greater than or equal to UPDATED_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldNotBeFound("activityLevel.greaterThanOrEqual=" + UPDATED_ACTIVITY_LEVEL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActivityLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where activityLevel is less than or equal to DEFAULT_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldBeFound("activityLevel.lessThanOrEqual=" + DEFAULT_ACTIVITY_LEVEL);

        // Get all the customerHealthsList where activityLevel is less than or equal to SMALLER_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldNotBeFound("activityLevel.lessThanOrEqual=" + SMALLER_ACTIVITY_LEVEL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActivityLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where activityLevel is less than DEFAULT_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldNotBeFound("activityLevel.lessThan=" + DEFAULT_ACTIVITY_LEVEL);

        // Get all the customerHealthsList where activityLevel is less than UPDATED_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldBeFound("activityLevel.lessThan=" + UPDATED_ACTIVITY_LEVEL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActivityLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where activityLevel is greater than DEFAULT_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldNotBeFound("activityLevel.greaterThan=" + DEFAULT_ACTIVITY_LEVEL);

        // Get all the customerHealthsList where activityLevel is greater than SMALLER_ACTIVITY_LEVEL
        defaultCustomerHealthsShouldBeFound("activityLevel.greaterThan=" + SMALLER_ACTIVITY_LEVEL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetWeight equals to DEFAULT_TARGET_WEIGHT
        defaultCustomerHealthsShouldBeFound("targetWeight.equals=" + DEFAULT_TARGET_WEIGHT);

        // Get all the customerHealthsList where targetWeight equals to UPDATED_TARGET_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("targetWeight.equals=" + UPDATED_TARGET_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetWeightIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetWeight in DEFAULT_TARGET_WEIGHT or UPDATED_TARGET_WEIGHT
        defaultCustomerHealthsShouldBeFound("targetWeight.in=" + DEFAULT_TARGET_WEIGHT + "," + UPDATED_TARGET_WEIGHT);

        // Get all the customerHealthsList where targetWeight equals to UPDATED_TARGET_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("targetWeight.in=" + UPDATED_TARGET_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetWeight is not null
        defaultCustomerHealthsShouldBeFound("targetWeight.specified=true");

        // Get all the customerHealthsList where targetWeight is null
        defaultCustomerHealthsShouldNotBeFound("targetWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetWeight is greater than or equal to DEFAULT_TARGET_WEIGHT
        defaultCustomerHealthsShouldBeFound("targetWeight.greaterThanOrEqual=" + DEFAULT_TARGET_WEIGHT);

        // Get all the customerHealthsList where targetWeight is greater than or equal to UPDATED_TARGET_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("targetWeight.greaterThanOrEqual=" + UPDATED_TARGET_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetWeight is less than or equal to DEFAULT_TARGET_WEIGHT
        defaultCustomerHealthsShouldBeFound("targetWeight.lessThanOrEqual=" + DEFAULT_TARGET_WEIGHT);

        // Get all the customerHealthsList where targetWeight is less than or equal to SMALLER_TARGET_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("targetWeight.lessThanOrEqual=" + SMALLER_TARGET_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetWeight is less than DEFAULT_TARGET_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("targetWeight.lessThan=" + DEFAULT_TARGET_WEIGHT);

        // Get all the customerHealthsList where targetWeight is less than UPDATED_TARGET_WEIGHT
        defaultCustomerHealthsShouldBeFound("targetWeight.lessThan=" + UPDATED_TARGET_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetWeight is greater than DEFAULT_TARGET_WEIGHT
        defaultCustomerHealthsShouldNotBeFound("targetWeight.greaterThan=" + DEFAULT_TARGET_WEIGHT);

        // Get all the customerHealthsList where targetWeight is greater than SMALLER_TARGET_WEIGHT
        defaultCustomerHealthsShouldBeFound("targetWeight.greaterThan=" + SMALLER_TARGET_WEIGHT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargerCalorieIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targerCalorie equals to DEFAULT_TARGER_CALORIE
        defaultCustomerHealthsShouldBeFound("targerCalorie.equals=" + DEFAULT_TARGER_CALORIE);

        // Get all the customerHealthsList where targerCalorie equals to UPDATED_TARGER_CALORIE
        defaultCustomerHealthsShouldNotBeFound("targerCalorie.equals=" + UPDATED_TARGER_CALORIE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargerCalorieIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targerCalorie in DEFAULT_TARGER_CALORIE or UPDATED_TARGER_CALORIE
        defaultCustomerHealthsShouldBeFound("targerCalorie.in=" + DEFAULT_TARGER_CALORIE + "," + UPDATED_TARGER_CALORIE);

        // Get all the customerHealthsList where targerCalorie equals to UPDATED_TARGER_CALORIE
        defaultCustomerHealthsShouldNotBeFound("targerCalorie.in=" + UPDATED_TARGER_CALORIE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargerCalorieIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targerCalorie is not null
        defaultCustomerHealthsShouldBeFound("targerCalorie.specified=true");

        // Get all the customerHealthsList where targerCalorie is null
        defaultCustomerHealthsShouldNotBeFound("targerCalorie.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargerCalorieIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targerCalorie is greater than or equal to DEFAULT_TARGER_CALORIE
        defaultCustomerHealthsShouldBeFound("targerCalorie.greaterThanOrEqual=" + DEFAULT_TARGER_CALORIE);

        // Get all the customerHealthsList where targerCalorie is greater than or equal to UPDATED_TARGER_CALORIE
        defaultCustomerHealthsShouldNotBeFound("targerCalorie.greaterThanOrEqual=" + UPDATED_TARGER_CALORIE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargerCalorieIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targerCalorie is less than or equal to DEFAULT_TARGER_CALORIE
        defaultCustomerHealthsShouldBeFound("targerCalorie.lessThanOrEqual=" + DEFAULT_TARGER_CALORIE);

        // Get all the customerHealthsList where targerCalorie is less than or equal to SMALLER_TARGER_CALORIE
        defaultCustomerHealthsShouldNotBeFound("targerCalorie.lessThanOrEqual=" + SMALLER_TARGER_CALORIE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargerCalorieIsLessThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targerCalorie is less than DEFAULT_TARGER_CALORIE
        defaultCustomerHealthsShouldNotBeFound("targerCalorie.lessThan=" + DEFAULT_TARGER_CALORIE);

        // Get all the customerHealthsList where targerCalorie is less than UPDATED_TARGER_CALORIE
        defaultCustomerHealthsShouldBeFound("targerCalorie.lessThan=" + UPDATED_TARGER_CALORIE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargerCalorieIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targerCalorie is greater than DEFAULT_TARGER_CALORIE
        defaultCustomerHealthsShouldNotBeFound("targerCalorie.greaterThan=" + DEFAULT_TARGER_CALORIE);

        // Get all the customerHealthsList where targerCalorie is greater than SMALLER_TARGER_CALORIE
        defaultCustomerHealthsShouldBeFound("targerCalorie.greaterThan=" + SMALLER_TARGER_CALORIE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetBodyFatIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetBodyFat equals to DEFAULT_TARGET_BODY_FAT
        defaultCustomerHealthsShouldBeFound("targetBodyFat.equals=" + DEFAULT_TARGET_BODY_FAT);

        // Get all the customerHealthsList where targetBodyFat equals to UPDATED_TARGET_BODY_FAT
        defaultCustomerHealthsShouldNotBeFound("targetBodyFat.equals=" + UPDATED_TARGET_BODY_FAT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetBodyFatIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetBodyFat in DEFAULT_TARGET_BODY_FAT or UPDATED_TARGET_BODY_FAT
        defaultCustomerHealthsShouldBeFound("targetBodyFat.in=" + DEFAULT_TARGET_BODY_FAT + "," + UPDATED_TARGET_BODY_FAT);

        // Get all the customerHealthsList where targetBodyFat equals to UPDATED_TARGET_BODY_FAT
        defaultCustomerHealthsShouldNotBeFound("targetBodyFat.in=" + UPDATED_TARGET_BODY_FAT);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByTargetBodyFatIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where targetBodyFat is not null
        defaultCustomerHealthsShouldBeFound("targetBodyFat.specified=true");

        // Get all the customerHealthsList where targetBodyFat is null
        defaultCustomerHealthsShouldNotBeFound("targetBodyFat.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByGoalIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where goal equals to DEFAULT_GOAL
        defaultCustomerHealthsShouldBeFound("goal.equals=" + DEFAULT_GOAL);

        // Get all the customerHealthsList where goal equals to UPDATED_GOAL
        defaultCustomerHealthsShouldNotBeFound("goal.equals=" + UPDATED_GOAL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByGoalIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where goal in DEFAULT_GOAL or UPDATED_GOAL
        defaultCustomerHealthsShouldBeFound("goal.in=" + DEFAULT_GOAL + "," + UPDATED_GOAL);

        // Get all the customerHealthsList where goal equals to UPDATED_GOAL
        defaultCustomerHealthsShouldNotBeFound("goal.in=" + UPDATED_GOAL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByGoalIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where goal is not null
        defaultCustomerHealthsShouldBeFound("goal.specified=true");

        // Get all the customerHealthsList where goal is null
        defaultCustomerHealthsShouldNotBeFound("goal.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where active equals to DEFAULT_ACTIVE
        defaultCustomerHealthsShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the customerHealthsList where active equals to UPDATED_ACTIVE
        defaultCustomerHealthsShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCustomerHealthsShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the customerHealthsList where active equals to UPDATED_ACTIVE
        defaultCustomerHealthsShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where active is not null
        defaultCustomerHealthsShouldBeFound("active.specified=true");

        // Get all the customerHealthsList where active is null
        defaultCustomerHealthsShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where detail equals to DEFAULT_DETAIL
        defaultCustomerHealthsShouldBeFound("detail.equals=" + DEFAULT_DETAIL);

        // Get all the customerHealthsList where detail equals to UPDATED_DETAIL
        defaultCustomerHealthsShouldNotBeFound("detail.equals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByDetailIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where detail in DEFAULT_DETAIL or UPDATED_DETAIL
        defaultCustomerHealthsShouldBeFound("detail.in=" + DEFAULT_DETAIL + "," + UPDATED_DETAIL);

        // Get all the customerHealthsList where detail equals to UPDATED_DETAIL
        defaultCustomerHealthsShouldNotBeFound("detail.in=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where detail is not null
        defaultCustomerHealthsShouldBeFound("detail.specified=true");

        // Get all the customerHealthsList where detail is null
        defaultCustomerHealthsShouldNotBeFound("detail.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByDetailContainsSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where detail contains DEFAULT_DETAIL
        defaultCustomerHealthsShouldBeFound("detail.contains=" + DEFAULT_DETAIL);

        // Get all the customerHealthsList where detail contains UPDATED_DETAIL
        defaultCustomerHealthsShouldNotBeFound("detail.contains=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByDetailNotContainsSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where detail does not contain DEFAULT_DETAIL
        defaultCustomerHealthsShouldNotBeFound("detail.doesNotContain=" + DEFAULT_DETAIL);

        // Get all the customerHealthsList where detail does not contain UPDATED_DETAIL
        defaultCustomerHealthsShouldBeFound("detail.doesNotContain=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultCustomerHealthsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the customerHealthsList where createdDate equals to UPDATED_CREATED_DATE
        defaultCustomerHealthsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultCustomerHealthsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the customerHealthsList where createdDate equals to UPDATED_CREATED_DATE
        defaultCustomerHealthsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where createdDate is not null
        defaultCustomerHealthsShouldBeFound("createdDate.specified=true");

        // Get all the customerHealthsList where createdDate is null
        defaultCustomerHealthsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultCustomerHealthsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the customerHealthsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultCustomerHealthsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultCustomerHealthsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the customerHealthsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultCustomerHealthsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultCustomerHealthsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the customerHealthsList where createdDate is less than UPDATED_CREATED_DATE
        defaultCustomerHealthsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        // Get all the customerHealthsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultCustomerHealthsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the customerHealthsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultCustomerHealthsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerHealthsByAllergensIsEqualToSomething() throws Exception {
        Allergens allergens;
        if (TestUtil.findAll(em, Allergens.class).isEmpty()) {
            customerHealthsRepository.saveAndFlush(customerHealths);
            allergens = AllergensResourceIT.createEntity(em);
        } else {
            allergens = TestUtil.findAll(em, Allergens.class).get(0);
        }
        em.persist(allergens);
        em.flush();
        customerHealths.addAllergens(allergens);
        customerHealthsRepository.saveAndFlush(customerHealths);
        Long allergensId = allergens.getId();

        // Get all the customerHealthsList where allergens equals to allergensId
        defaultCustomerHealthsShouldBeFound("allergensId.equals=" + allergensId);

        // Get all the customerHealthsList where allergens equals to (allergensId + 1)
        defaultCustomerHealthsShouldNotBeFound("allergensId.equals=" + (allergensId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerHealthsShouldBeFound(String filter) throws Exception {
        restCustomerHealthsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerHealths.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].currentWeight").value(hasItem(DEFAULT_CURRENT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].currentHeight").value(hasItem(DEFAULT_CURRENT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].measureUnit").value(hasItem(DEFAULT_MEASURE_UNIT.toString())))
            .andExpect(jsonPath("$.[*].activityLevel").value(hasItem(DEFAULT_ACTIVITY_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].targetWeight").value(hasItem(DEFAULT_TARGET_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].targerCalorie").value(hasItem(DEFAULT_TARGER_CALORIE.doubleValue())))
            .andExpect(jsonPath("$.[*].targetBodyFat").value(hasItem(DEFAULT_TARGET_BODY_FAT.toString())))
            .andExpect(jsonPath("$.[*].goal").value(hasItem(DEFAULT_GOAL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restCustomerHealthsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerHealthsShouldNotBeFound(String filter) throws Exception {
        restCustomerHealthsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerHealthsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomerHealths() throws Exception {
        // Get the customerHealths
        restCustomerHealthsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCustomerHealths() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        int databaseSizeBeforeUpdate = customerHealthsRepository.findAll().size();

        // Update the customerHealths
        CustomerHealths updatedCustomerHealths = customerHealthsRepository.findById(customerHealths.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerHealths are not directly saved in db
        em.detach(updatedCustomerHealths);
        updatedCustomerHealths
            .name(UPDATED_NAME)
            .contactId(UPDATED_CONTACT_ID)
            .currentWeight(UPDATED_CURRENT_WEIGHT)
            .currentHeight(UPDATED_CURRENT_HEIGHT)
            .measureUnit(UPDATED_MEASURE_UNIT)
            .activityLevel(UPDATED_ACTIVITY_LEVEL)
            .targetWeight(UPDATED_TARGET_WEIGHT)
            .targerCalorie(UPDATED_TARGER_CALORIE)
            .targetBodyFat(UPDATED_TARGET_BODY_FAT)
            .goal(UPDATED_GOAL)
            .active(UPDATED_ACTIVE)
            .detail(UPDATED_DETAIL)
            .createdDate(UPDATED_CREATED_DATE);
        CustomerHealthsDTO customerHealthsDTO = customerHealthsMapper.toDto(updatedCustomerHealths);

        restCustomerHealthsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerHealthsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerHealthsDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeUpdate);
        CustomerHealths testCustomerHealths = customerHealthsList.get(customerHealthsList.size() - 1);
        assertThat(testCustomerHealths.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerHealths.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testCustomerHealths.getCurrentWeight()).isEqualTo(UPDATED_CURRENT_WEIGHT);
        assertThat(testCustomerHealths.getCurrentHeight()).isEqualTo(UPDATED_CURRENT_HEIGHT);
        assertThat(testCustomerHealths.getMeasureUnit()).isEqualTo(UPDATED_MEASURE_UNIT);
        assertThat(testCustomerHealths.getActivityLevel()).isEqualTo(UPDATED_ACTIVITY_LEVEL);
        assertThat(testCustomerHealths.getTargetWeight()).isEqualTo(UPDATED_TARGET_WEIGHT);
        assertThat(testCustomerHealths.getTargerCalorie()).isEqualTo(UPDATED_TARGER_CALORIE);
        assertThat(testCustomerHealths.getTargetBodyFat()).isEqualTo(UPDATED_TARGET_BODY_FAT);
        assertThat(testCustomerHealths.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testCustomerHealths.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCustomerHealths.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testCustomerHealths.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCustomerHealths() throws Exception {
        int databaseSizeBeforeUpdate = customerHealthsRepository.findAll().size();
        customerHealths.setId(count.incrementAndGet());

        // Create the CustomerHealths
        CustomerHealthsDTO customerHealthsDTO = customerHealthsMapper.toDto(customerHealths);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerHealthsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerHealthsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerHealthsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerHealths() throws Exception {
        int databaseSizeBeforeUpdate = customerHealthsRepository.findAll().size();
        customerHealths.setId(count.incrementAndGet());

        // Create the CustomerHealths
        CustomerHealthsDTO customerHealthsDTO = customerHealthsMapper.toDto(customerHealths);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerHealthsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerHealthsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerHealths() throws Exception {
        int databaseSizeBeforeUpdate = customerHealthsRepository.findAll().size();
        customerHealths.setId(count.incrementAndGet());

        // Create the CustomerHealths
        CustomerHealthsDTO customerHealthsDTO = customerHealthsMapper.toDto(customerHealths);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerHealthsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerHealthsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerHealthsWithPatch() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        int databaseSizeBeforeUpdate = customerHealthsRepository.findAll().size();

        // Update the customerHealths using partial update
        CustomerHealths partialUpdatedCustomerHealths = new CustomerHealths();
        partialUpdatedCustomerHealths.setId(customerHealths.getId());

        partialUpdatedCustomerHealths
            .currentHeight(UPDATED_CURRENT_HEIGHT)
            .measureUnit(UPDATED_MEASURE_UNIT)
            .activityLevel(UPDATED_ACTIVITY_LEVEL)
            .targerCalorie(UPDATED_TARGER_CALORIE)
            .targetBodyFat(UPDATED_TARGET_BODY_FAT)
            .goal(UPDATED_GOAL)
            .active(UPDATED_ACTIVE)
            .detail(UPDATED_DETAIL)
            .createdDate(UPDATED_CREATED_DATE);

        restCustomerHealthsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerHealths.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerHealths))
            )
            .andExpect(status().isOk());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeUpdate);
        CustomerHealths testCustomerHealths = customerHealthsList.get(customerHealthsList.size() - 1);
        assertThat(testCustomerHealths.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerHealths.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testCustomerHealths.getCurrentWeight()).isEqualTo(DEFAULT_CURRENT_WEIGHT);
        assertThat(testCustomerHealths.getCurrentHeight()).isEqualTo(UPDATED_CURRENT_HEIGHT);
        assertThat(testCustomerHealths.getMeasureUnit()).isEqualTo(UPDATED_MEASURE_UNIT);
        assertThat(testCustomerHealths.getActivityLevel()).isEqualTo(UPDATED_ACTIVITY_LEVEL);
        assertThat(testCustomerHealths.getTargetWeight()).isEqualTo(DEFAULT_TARGET_WEIGHT);
        assertThat(testCustomerHealths.getTargerCalorie()).isEqualTo(UPDATED_TARGER_CALORIE);
        assertThat(testCustomerHealths.getTargetBodyFat()).isEqualTo(UPDATED_TARGET_BODY_FAT);
        assertThat(testCustomerHealths.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testCustomerHealths.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCustomerHealths.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testCustomerHealths.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCustomerHealthsWithPatch() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        int databaseSizeBeforeUpdate = customerHealthsRepository.findAll().size();

        // Update the customerHealths using partial update
        CustomerHealths partialUpdatedCustomerHealths = new CustomerHealths();
        partialUpdatedCustomerHealths.setId(customerHealths.getId());

        partialUpdatedCustomerHealths
            .name(UPDATED_NAME)
            .contactId(UPDATED_CONTACT_ID)
            .currentWeight(UPDATED_CURRENT_WEIGHT)
            .currentHeight(UPDATED_CURRENT_HEIGHT)
            .measureUnit(UPDATED_MEASURE_UNIT)
            .activityLevel(UPDATED_ACTIVITY_LEVEL)
            .targetWeight(UPDATED_TARGET_WEIGHT)
            .targerCalorie(UPDATED_TARGER_CALORIE)
            .targetBodyFat(UPDATED_TARGET_BODY_FAT)
            .goal(UPDATED_GOAL)
            .active(UPDATED_ACTIVE)
            .detail(UPDATED_DETAIL)
            .createdDate(UPDATED_CREATED_DATE);

        restCustomerHealthsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerHealths.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerHealths))
            )
            .andExpect(status().isOk());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeUpdate);
        CustomerHealths testCustomerHealths = customerHealthsList.get(customerHealthsList.size() - 1);
        assertThat(testCustomerHealths.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerHealths.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testCustomerHealths.getCurrentWeight()).isEqualTo(UPDATED_CURRENT_WEIGHT);
        assertThat(testCustomerHealths.getCurrentHeight()).isEqualTo(UPDATED_CURRENT_HEIGHT);
        assertThat(testCustomerHealths.getMeasureUnit()).isEqualTo(UPDATED_MEASURE_UNIT);
        assertThat(testCustomerHealths.getActivityLevel()).isEqualTo(UPDATED_ACTIVITY_LEVEL);
        assertThat(testCustomerHealths.getTargetWeight()).isEqualTo(UPDATED_TARGET_WEIGHT);
        assertThat(testCustomerHealths.getTargerCalorie()).isEqualTo(UPDATED_TARGER_CALORIE);
        assertThat(testCustomerHealths.getTargetBodyFat()).isEqualTo(UPDATED_TARGET_BODY_FAT);
        assertThat(testCustomerHealths.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testCustomerHealths.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCustomerHealths.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testCustomerHealths.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCustomerHealths() throws Exception {
        int databaseSizeBeforeUpdate = customerHealthsRepository.findAll().size();
        customerHealths.setId(count.incrementAndGet());

        // Create the CustomerHealths
        CustomerHealthsDTO customerHealthsDTO = customerHealthsMapper.toDto(customerHealths);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerHealthsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerHealthsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerHealthsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerHealths() throws Exception {
        int databaseSizeBeforeUpdate = customerHealthsRepository.findAll().size();
        customerHealths.setId(count.incrementAndGet());

        // Create the CustomerHealths
        CustomerHealthsDTO customerHealthsDTO = customerHealthsMapper.toDto(customerHealths);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerHealthsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerHealthsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerHealths() throws Exception {
        int databaseSizeBeforeUpdate = customerHealthsRepository.findAll().size();
        customerHealths.setId(count.incrementAndGet());

        // Create the CustomerHealths
        CustomerHealthsDTO customerHealthsDTO = customerHealthsMapper.toDto(customerHealths);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerHealthsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerHealthsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerHealths in the database
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerHealths() throws Exception {
        // Initialize the database
        customerHealthsRepository.saveAndFlush(customerHealths);

        int databaseSizeBeforeDelete = customerHealthsRepository.findAll().size();

        // Delete the customerHealths
        restCustomerHealthsMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerHealths.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerHealths> customerHealthsList = customerHealthsRepository.findAll();
        assertThat(customerHealthsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

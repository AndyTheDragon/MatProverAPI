package dat.controllers;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dto.QuestionDTO;
import dat.entities.Question;
import dat.enums.TestFormat;
import dat.routes.Routes;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionResourceTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    final ObjectMapper objectMapper = new ObjectMapper();
    Question q1, q2;
    static final Logger logger = LoggerFactory.getLogger(QuestionResourceTest.class.getName());

    @BeforeAll
    static void setUpAll() {
        logger.info("Setting up all tests");
        final Map<String, IController> controllers = new HashMap<>();
        controllers.put("question", new QuestionController(emf));
        controllers.put("assignment", new AssignmentController(emf));
        controllers.put("mathTeam", new MathTeamController(emf));
        controllers.put("security", new SecurityController(emf));
        Routes routes = new Routes(controllers);
        ApplicationConfig
                .getInstance()
                .initiateServer()
                .setRoute(routes.getRoutes())
                .handleException()
                .setApiExceptionHandling()
                .checkSecurityRoles()
                .startServer(7078);
        RestAssured.baseURI = "http://localhost:7078/api";
        logger.info("All tests setup complete");
    }

    @BeforeEach
    void setUp() {
        logger.info("Setting up each test");
        try (EntityManager em = emf.createEntityManager()) {
            q1 = new Question(2024,
                    "Johnny",
                    10,
                    1,
                    "What is Doofus?",
                    "http://example.com/image.jpg",
                    "Sample Category",
                    "Sample License",
                    "Sample Level",
                    TestFormat.MED);
            q2 = new Question(2023,
                    "Author Name",
                    10,
                    2,
                    "What is Dingus?",
                    "http://example.com/image.jpg",
                    "Sample Category",
                    "Sample License",
                    "Sample Level",
                    TestFormat.MED);
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Question").executeUpdate();
            em.persist(q1);
            em.persist(q2);
            em.getTransaction().commit();
            logger.info("Test setup complete");
        } catch (Exception e) {
            logger.error("Error setting up test", e);
        }
    }

    @Test
    void getAll() {
        logger.info("Executing getAll test");
        given()
                .when()
                .get("/opgave")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
        logger.info("getAll test executed successfully");
    }

    @Test
    void getById() {
        logger.info("Executing getById test");
        given()
                .when()
                .get("/opgave/" + q2.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(q2.getId()));
        logger.info("getById test executed successfully");
    }

    @Test
    void create() {
        logger.info("Executing create test");
        Question newQuestion = new Question(
                2023,
                "Author Name",
                10,
                3,
                "What is Love?",
                "http://example.com/image.jpg",
                "Sample Category",
                "Sample License",
                "Sample Level",
                TestFormat.MED
        );
        try {
            String json = objectMapper.writeValueAsString(new QuestionDTO(newQuestion));
            given().when()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(json)
                    .post("/opgave")
                    .then()
                    .statusCode(201)
                    .body("questionText", equalTo(newQuestion.getQuestionText()));
            logger.info("create test executed successfully");
        } catch (JsonProcessingException e) {
            logger.error("Error creating question", e);
            fail();
        }
    }

    @Test
    public void update() {
        try {
            // Prepare a valid QuestionDTO JSON payload
            String requestBody = String.format("""
            {
                "id": %d,
                "year": 2024,
                "author": "Updated Author",
                "questionText": "Updated Question Text",
                "points": 10,
                "questionNumber": 1,
                "pictureURL": "http://example.com/updated_image.jpg",
                "category": "Updated Category",
                "license": "Updated License",
                "level": "Updated Level",
                "testFormat": "MED"
            }
        """, q1.getId());

            given()
                    .when()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(requestBody)
                    .patch("/opgave")
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(q1.getId()))
                    .body("author", equalTo("Updated Author"))
                    .body("questionText", equalTo("Updated Question Text"));
            logger.info("Update test executed successfully");
        } catch (Exception e) {
            logger.error("Error during update test", e);
            fail("Exception occurred during update test: " + e.getMessage());
        }
    }

    @Test
    void delete() {
        logger.info("Executing delete test");
        given()
                .when()
                .delete("/opgave/" + q1.getId())
                .then()
                .statusCode(204);
        logger.info("delete test executed successfully");
    }
}
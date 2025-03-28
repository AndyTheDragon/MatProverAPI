package dat.controllers;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dto.AssignmentInputDTO;
import dat.dto.MathTeamSimpleDTO;
import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import dat.routes.Routes;
import dat.utils.Populator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssignmentResourceTest
{

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    final ObjectMapper objectMapper = new ObjectMapper();
    Assignment test_a1, test_a2;
    Question test_q1, test_q2;
    MathTeam test_myMathTeam;
    UserAccount test_myUser;
    private final Logger logger = LoggerFactory.getLogger(AssignmentResourceTest.class.getName());
    private Populator populator;

    @BeforeAll
    static void setUpAll()
    {
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
                .startServer(7080);
        RestAssured.baseURI = "http://localhost:7080/api";
    }

    @BeforeEach
    void setUp()
    {
        populator = new Populator();
        try (EntityManager em = emf.createEntityManager())
        {
            populator.resetAndPersistEntities(em);
            test_q1 = populator.getQ1();
            test_q2 = populator.getQ2();
            test_myUser = populator.getUserA();
            test_myMathTeam = populator.getMathTeamA();
            test_a1 = populator.getAssignmentA();
            test_a2 = populator.getAssignmentB();
        }
    }

    @Test
    void test_getAllAssignments()
    {
        given()
                .when()
                .get("/opgaveset")
                .then()
                .statusCode(200)
                .body("size()", equalTo(4));
    }

    @Test
    void test_getAssignmentById()
    {
        given()
                .when()
                .get("/opgaveset"+"/" + test_a1.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(test_a1.getId()));
    }

    @Test
    void test_CreateAssignment() {
        Assignment assignment = new Assignment("Test Assignment");
        assignment.setMathTeam(test_myMathTeam);
        assignment.addQuestion(test_q1);
        assignment.addQuestion(test_q2);

        try {
            AssignmentInputDTO inputDTO = new AssignmentInputDTO(null,
                                                        assignment.getIntroText(),
                                                        new MathTeamSimpleDTO(assignment.getMathTeam()));
            String json = objectMapper.writeValueAsString(inputDTO);

            given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(json)
                    .post("/opgaveset")
                    .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("mathTeam.id", equalTo(assignment.getMathTeam().getId()));;
        } catch (Exception e) {
            logger.error("Error creating assignment", e);
            //fail();
        }
    }

}
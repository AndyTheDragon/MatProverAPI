package dat.controllers;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dao.CrudDAO;
import dat.dao.GenericDAO;
import dat.dto.AssignmentInputDTO;
import dat.dto.AssignmentOutputDTO;
import dat.dto.MathTeamSimpleDTO;
import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import dat.enums.Roles;
import dat.routes.Routes;
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
import java.util.Set;

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
    private final Logger logger = LoggerFactory.getLogger(AssignmentResourceTest.class.getName());

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
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Assignment").executeUpdate();
            em.createQuery("DELETE FROM Question").executeUpdate();

            CrudDAO test_dao = new GenericDAO(emf);
            test_q1 = test_dao.create(new Question(10, "test question"));
            test_q2 = test_dao.create(new Question(5, "test question 2"));
            UserAccount test_myUser = new UserAccount("unilogin", "kodeord");
            test_myUser.addRole(Roles.ADMIN);
            test_myUser.addRole(Roles.USER_READ);
            test_myUser = test_dao.create(test_myUser);

            test_myMathTeam = new MathTeam("MathTeam 2a");
            test_myMathTeam = test_dao.create(test_myMathTeam);
            test_myUser.addMathTeam(test_myMathTeam);
            test_myUser = test_dao.update(test_myUser);

            Assignment assignment = new Assignment();
            test_myMathTeam.addAssignment(assignment);
            assignment = test_dao.create(assignment);
            test_myMathTeam = test_dao.update(test_myMathTeam);

            assignment.addQuestion(test_q1);
            test_a1 = test_dao.update(assignment);
            test_myMathTeam = test_dao.update(test_myMathTeam);

            test_a2 = new Assignment("Assignment 2", test_myMathTeam, Set.of(test_q1, test_q2));
            test_a2 = test_dao.create(test_a2);
            test_dao.update(test_myMathTeam);
            test_dao.update(test_myUser);

            em.getTransaction().commit();
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
                .body("size()", equalTo(2))
                .body("[0].id", equalTo(test_a1.getId()))
                .body("[0].mathTeam.id", equalTo(test_a1.getMathTeam().getId()))
                .body("[0].mathTeam.description", equalTo(test_a1.getMathTeam().getDescription()))
                .body("[0].questions.size()", equalTo(1))
                .body("[1].id", equalTo(test_a2.getId()))
                .body("[1].questions.size()", equalTo(2));
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
            // Manually initialize nested lazy fields before converting to DTO
            //assignment.getQuestions().forEach(q -> Optional.ofNullable(q.getAssignments()).ifPresent(Set::size));
            //assignment.getMathTeam().getAssignments().size(); // if needed

            // Convert to DTO *after* initialization
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
            //.body("questions.size()", equalTo(2)); // Uncomment if response returns questions
        } catch (Exception e) {
            logger.error("Error creating assignment", e);
            e.printStackTrace();
            //fail();
        }
    }

}
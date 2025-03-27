package dat.controllers;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dao.CrudDAO;
import dat.dao.GenericDAO;
import dat.dto.AssignmentDTO;
import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import dat.enums.Roles;
import dat.routes.Routes;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssignmentResourceTest
{

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    final ObjectMapper objectMapper = new ObjectMapper();
    Assignment test_a1, test_a2;
    Question test_q1, test_q2;
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

            MathTeam test_myMathTeam = new MathTeam("MathTeam 2a");
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

            test_a2 = new Assignment("Assignment 2", test_myMathTeam, test_myUser, Set.of(test_q1, test_q2));
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
                .body("[0].owner", equalTo(test_a1.getOwner().getId())) //Dette skal laves om, brug OwnerDTO i AssignmentDTO
                .body("[0].questions.size()", equalTo(1))
                .body("[1].id", equalTo(test_a2.getId()))
                .body("[1].questions.size()", equalTo(2));
    }

}
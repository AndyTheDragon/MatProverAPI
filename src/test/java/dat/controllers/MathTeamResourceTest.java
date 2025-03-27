package dat.controllers;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dao.CrudDAO;
import dat.dao.GenericDAO;
import dat.dto.MathTeamDTO;
import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import dat.enums.Roles;
import dat.routes.Routes;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.fail;

public class MathTeamResourceTest
{
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    final ObjectMapper objectMapper = new ObjectMapper();
    Assignment test_a1, test_a2;
    Question test_q1, test_q2;
    private final Logger logger = LoggerFactory.getLogger(MathTeamResourceTest.class.getName());

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
                .startServer(7082);
        RestAssured.baseURI = "http://localhost:7082/api";
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
            test_q1 = test_dao.create(new Question(10, "fuck det her test"));
            test_q2 = test_dao.create(new Question(11, "stadig en lortetest"));
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

            test_a2 = new Assignment("Assignment 2", test_myMathTeam, test_myUser, Set.of(test_q1));
            test_a2 = test_dao.create(test_a2);
            test_dao.update(test_myMathTeam);
            test_dao.update(test_myUser);

            em.getTransaction().commit();
        }
    }

    @Test
    void getAll()
    {
        given()
                .when()
                .get("/hold")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("size()", equalTo(1));
    }

    @Test
    void getOne()
    {
        given()
                .when()
                .get("/hold/1")
                .then()
                .statusCode(200)
                .body("description", equalTo("MathTeam 2a"));
    }

    @Test
    void create()
    {
        MathTeam entity = new MathTeam(("Mathteam A"));
        try
        {
            String json = objectMapper.writeValueAsString(new MathTeamDTO(entity));
            given().when()
                .contentType("application/json")
                .accept("application/json")
                .body(json)
                .post("/hold")
                .then()
                .statusCode(204)
                .body("description", equalTo(entity.getDescription()));
        } catch (JsonProcessingException e)
        {
            logger.error("Error creating math team", e);
            fail();
        }
    }

    @Test
    void addAssignment()
    {
        fail();
    }

    @Test
    void removeAssignment()
    {
        fail();
    }

    @Test
    void delete()
    {
        given()
                .when()
                .delete("/hold/1")
                .then()
                .statusCode(204);
    }
}

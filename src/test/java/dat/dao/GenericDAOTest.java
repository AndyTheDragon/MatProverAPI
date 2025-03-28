package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.*;
import dat.utils.Populator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class GenericDAOTest
{
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final GenericDAO genericDAO = new GenericDAO(emf);
    private Question q1, q2, q3;
    private UserAccount userA, userB;
    private MathTeam mathTeamA, mathTeamB, mathTeamC;
    private Assignment assignmentA, assignmentB, assignmentC, assignmentD;
    private Populator populator;

    @BeforeEach
    void setUp()
    {
        populator = new Populator();
        try (EntityManager em = emf.createEntityManager())
        {
            populator.resetAndPersistEntities(em);
            q1 = populator.getQ1();
            q2 = populator.getQ2();
            q3 = populator.getQ3();
            userA = populator.getUserA();
            userB = populator.getUserB();
            mathTeamA = populator.getMathTeamA();
            mathTeamB = populator.getMathTeamB();
            mathTeamC = populator.getMathTeamC();
            assignmentA = populator.getAssignmentA();
            assignmentB = populator.getAssignmentB();
            assignmentC = populator.getAssignmentC();
            assignmentD = populator.getAssignmentD();
        }
        catch (Exception e)
        {
            fail();
        }
    }


    @Test
    void create_noRelations()
    {
        // Arrange
        Question testq = new Question();
        testq.setQuestionText("Test question");
        UserAccount testUser = new UserAccount("uni", "password");
        MathTeam testMathTeam = new MathTeam("TestMathTeam");
        Assignment testAssignment = new Assignment("TestAssignment");

        // Act
        Question resultQuestion = genericDAO.create(testq);
        UserAccount resultUser = genericDAO.create(testUser);
        MathTeam resultMathTeam = genericDAO.create(testMathTeam);
        Assignment resultAssignment = genericDAO.create(testAssignment);

        // Assert
        assertThat(resultQuestion, samePropertyValuesAs(testq));
        assertNotNull(resultQuestion);
        try (EntityManager em = emf.createEntityManager())
        {
            Question foundQuestion = em.find(Question.class, resultQuestion.getId());
            assertThat(foundQuestion, samePropertyValuesAs(testq));
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM Question t", Long.class).getSingleResult();
            assertThat(amountInDb, is(4L));
        }
        assertThat(resultUser, samePropertyValuesAs(testUser));
        assertNotNull(resultUser);
        try (EntityManager em = emf.createEntityManager())
        {
            UserAccount foundUser = em.find(UserAccount.class, resultUser.getId());
            assertThat(foundUser, samePropertyValuesAs(testUser));
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM UserAccount t", Long.class).getSingleResult();
            assertThat(amountInDb, is(3L));
        }
        assertThat(resultMathTeam, samePropertyValuesAs(testMathTeam));
        assertNotNull(resultMathTeam);
        try (EntityManager em = emf.createEntityManager())
        {
            MathTeam foundMathTeam = em.find(MathTeam.class, resultMathTeam.getId());
            assertThat(foundMathTeam, samePropertyValuesAs(testMathTeam));
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM MathTeam t", Long.class).getSingleResult();
            assertThat(amountInDb, is(4L));
        }
        assertThat(resultAssignment, samePropertyValuesAs(testAssignment));
        assertNotNull(resultAssignment);
        try (EntityManager em = emf.createEntityManager())
        {
            Assignment foundAssignment = em.find(Assignment.class, resultAssignment.getId());
            assertThat(foundAssignment, samePropertyValuesAs(testAssignment));
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM Assignment t", Long.class).getSingleResult();
            assertThat(amountInDb, is(5L));
        }

    }

    @Test
    void create_withRelations()
    {
        // Arrange
        Question testq = new Question();
        testq.setQuestionText("Test question");
        testq.setPoints(10);
        UserAccount testUser = new UserAccount("uni", "password");
        MathTeam testMathTeam = new MathTeam("TestMathTeam");
        Assignment testAssignment = new Assignment("TestAssignment");

        // Act
        Question resultQuestion = genericDAO.create(testq);
        UserAccount resultUser = genericDAO.create(testUser);
        MathTeam resultMathTeam = genericDAO.create(testMathTeam);
        resultUser.addMathTeam(resultMathTeam);
        //testUser.addMathTeam(resultMathTeam);
        resultUser = genericDAO.update(resultUser);
        Assignment resultAssignment = genericDAO.create(testAssignment);
        resultMathTeam.addAssignment(resultAssignment);
        testMathTeam.addAssignment(resultAssignment);
        resultMathTeam = genericDAO.update(resultMathTeam);
        resultAssignment.addQuestion(testq);
        testAssignment.addQuestion(resultQuestion);
        resultAssignment = genericDAO.update(resultAssignment);

        // Assert
        assertThat(resultQuestion, samePropertyValuesAs(testq));
        assertNotNull(resultQuestion);
        try (EntityManager em = emf.createEntityManager())
        {
            Question foundQuestion = em.find(Question.class, resultQuestion.getId());
            assertThat(foundQuestion, samePropertyValuesAs(testq));
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM Question t", Long.class).getSingleResult();
            assertThat(amountInDb, is(4L));
        }
        assertThat(resultUser, samePropertyValuesAs(testUser, "assignments", "mathTeams"));
        assertNotNull(resultUser);
        try (EntityManager em = emf.createEntityManager())
        {
            UserAccount foundUser = em.find(UserAccount.class, resultUser.getId());
            assertThat(foundUser, samePropertyValuesAs(testUser, "assignments", "mathTeams"));
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM UserAccount t", Long.class).getSingleResult();
            assertThat(amountInDb, is(3L));
        }
        assertThat(resultMathTeam, samePropertyValuesAs(testMathTeam, "assignments", "owner", "questions"));
        assertThat(resultMathTeam.getOwner(), samePropertyValuesAs(testMathTeam.getOwner(), "assignments", "mathTeams"));
        assertNotNull(resultMathTeam);
        try (EntityManager em = emf.createEntityManager())
        {
            MathTeam foundMathTeam = em.find(MathTeam.class, resultMathTeam.getId());
            assertThat(foundMathTeam, samePropertyValuesAs(testMathTeam, "assignments", "owner", "questions"));
            assertThat(foundMathTeam.getOwner(), samePropertyValuesAs(testMathTeam.getOwner(), "assignments", "mathTeams"));
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM MathTeam t", Long.class).getSingleResult();
            assertThat(amountInDb, is(4L));
        }
        assertThat(resultAssignment, samePropertyValuesAs(testAssignment, "mathTeam", "questions"));
        assertThat(resultAssignment.getMathTeam(), samePropertyValuesAs(testAssignment.getMathTeam(), "assignments", "owner", "questions"));
        assertNotNull(resultAssignment);
        try (EntityManager em = emf.createEntityManager())
        {
            Assignment foundAssignment = em.find(Assignment.class, resultAssignment.getId());
            assertThat(foundAssignment, samePropertyValuesAs(testAssignment, "mathTeam", "questions"));
            assertThat(foundAssignment.getMathTeam(), samePropertyValuesAs(testAssignment.getMathTeam(), "assignments", "owner", "questions"));
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM Assignment t", Long.class).getSingleResult();
            assertThat(amountInDb, is(5L));
        }

    }

    @Test
    void read()
    {
        // Arrange
        Question expectedQuestion = q1;
        UserAccount expectedUser = userA;
        MathTeam expectedMathTeam = mathTeamA;
        Assignment expectedAssignment = assignmentA;

        // Act
        Question resultQuestion = genericDAO.getById(Question.class, q1.getId());
        UserAccount resultUser = genericDAO.getById(UserAccount.class, userA.getId());
        MathTeam resultMathTeam = genericDAO.getById(MathTeam.class, mathTeamA.getId());
        Assignment resultAssignment = genericDAO.getById(Assignment.class, assignmentA.getId());

        // Assert
        assertThat(resultQuestion, samePropertyValuesAs(expectedQuestion, "assignments"));
        assertThat(resultUser, samePropertyValuesAs(expectedUser, "assignments", "mathTeams"));
        assertThat(resultUser.getMathTeams().size(), equalTo(expectedUser.getMathTeams().size()));
        assertThat(resultUser.getAssignments().size(), equalTo(expectedUser.getAssignments().size()));
        assertThat(resultMathTeam, samePropertyValuesAs(expectedMathTeam, "assignments", "owner", "questions"));
        assertThat(resultMathTeam.getOwner(), samePropertyValuesAs(expectedMathTeam.getOwner(), "assignments", "mathTeams"));
        assertThat(resultMathTeam.getAssignments().size(), equalTo(expectedMathTeam.getAssignments().size()));
        assertThat(resultMathTeam.getQuestions().size(), equalTo(expectedMathTeam.getQuestions().size()));
        assertThat(resultAssignment, samePropertyValuesAs(expectedAssignment, "mathTeam", "questions"));
    }
/*
    @Test
    void read_notFound()
    {


        // Act
        DaoException exception = assertThrows(DaoException.class, () -> genericDAO.getById(Hotel.class, 1000L));
        //Hotel result = genericDAO.read(Hotel.class, 1000L);

        // Assert
        assertThat(exception.getMessage(), is("Error reading object from db"));
    }

    @Test
    void findAll()
    {
        // Arrange
        List<Hotel> expected = List.of(h1, h2);

        // Act
        List<Hotel> result = genericDAO.getMany(Hotel.class);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), is(2));
        assertThat(result.get(0), samePropertyValuesAs(expected.get(0), "rooms"));
        assertThat(result.get(1), samePropertyValuesAs(expected.get(1), "rooms"));
    }

    @Test
    void update()
    {
        // Arrange
        h1.setName("UpdatedName");

        // Act
        Hotel result = genericDAO.update(h1);

        // Assert
        assertThat(result, samePropertyValuesAs(h1, "rooms"));
        //assertThat(result.getRooms(), containsInAnyOrder(h1.getRooms()));

    }

    @Test
    void updateMany()
    {
        // Arrange
        h1.setName("UpdatedName");
        h2.setName("UpdatedName");
        List<Hotel> testEntities = List.of(h1, h2);

        // Act
        List<Hotel> result = genericDAO.update(testEntities);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), is(2));
        assertThat(result.get(0), samePropertyValuesAs(h1, "rooms"));
        assertThat(result.get(1), samePropertyValuesAs(h2, "rooms"));
    }

    @Test
    void delete()
    {
        // Act
        genericDAO.delete(h1);

        // Assert
        try (EntityManager em = emf.createEntityManager())
        {
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM Hotel t", Long.class).getSingleResult();
            assertThat(amountInDb, is(1L));
            Hotel found = em.find(Hotel.class, h1.getId());
            assertNull(found);
        }
    }

    @Test
    void delete_byId()
    {
        // Act
        genericDAO.delete(Hotel.class, h2.getId());

        // Assert
        try (EntityManager em = emf.createEntityManager())
        {
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM Hotel t", Long.class).getSingleResult();
            assertThat(amountInDb, is(1L));
            Hotel found = em.find(Hotel.class, h2.getId());
            assertNull(found);
        }
    }*/
}
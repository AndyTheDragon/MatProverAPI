package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.controllers.*;
import dat.dao.CrudDAO;
import dat.dao.GenericDAO;
import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import dat.enums.Roles;
import dat.routes.Routes;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Main
{
    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    public static void main(String[] args)
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
                .startServer(7070);

        Assignment a1, a2;
        Question q1, q2;
        CrudDAO dao = new GenericDAO(emf);
        q1 = dao.create(new Question(10, "test question"));
        q2 = dao.create(new Question(5, "test question 2"));
        UserAccount myUser = new UserAccount("unilogin", "kodeord");
        myUser.addRole(Roles.ADMIN);
        myUser.addRole(Roles.USER_READ);
        myUser = dao.create(myUser);

        MathTeam myMathTeam = new MathTeam("MathTeam 2a");
        myMathTeam = dao.create(myMathTeam);
        myUser.addMathTeam(myMathTeam);
        myUser = dao.update(myUser);

        Assignment assignment = new Assignment();
        myMathTeam.addAssignment(assignment);
        assignment = dao.create(assignment);
        myMathTeam = dao.update(myMathTeam);

        assignment.addQuestion(q1);
        a1 = dao.update(assignment);
        myMathTeam = dao.update(myMathTeam);

        a2 = new Assignment("Assignment 2", myMathTeam, myUser, Set.of(q1, q2));
        a2 = dao.create(a2);
        dao.update(myMathTeam);
        dao.update(myUser);
    }
}
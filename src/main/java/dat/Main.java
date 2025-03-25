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

        CrudDAO dao = new GenericDAO(emf);

        Question question = new Question();
        dao.create(question);

        UserAccount myUser = new UserAccount("unilogin", "kodeord");
        myUser.addRole(Roles.ADMIN);
        myUser.addRole(Roles.USER_READ);
        myUser = dao.create(myUser);
        System.out.println("User created with no teams and no assignments");
        System.out.println(myUser);

        MathTeam myMathTeam = new MathTeam("MathTeam 2a");
        myMathTeam = dao.create(myMathTeam);
        myUser.addMathTeam(myMathTeam);
        myUser = dao.update(myUser);
        System.out.println("Team created and added to user");
        System.out.println(myUser);
        System.out.println(myMathTeam);
        System.out.println(myMathTeam.getAssignments());
        System.out.println(myMathTeam.getQuestions());


        Assignment assignment = new Assignment();
        myMathTeam.addAssignment(assignment);
        assignment = dao.create(assignment);
        myMathTeam = dao.update(myMathTeam);
        System.out.println("Assignment created and added to team");
        System.out.println(myUser);
        System.out.println(myMathTeam);
        System.out.println(myMathTeam.getAssignments());
        System.out.println(myMathTeam.getQuestions());
        System.out.println(assignment);
        System.out.println(assignment.getMathTeam());
        System.out.println(assignment.getQuestions());

        assignment.addQuestion(question);
        assignment = dao.update(assignment);
        myMathTeam = dao.update(myMathTeam);
        System.out.println("Question added to assignment");
        System.out.println(myUser);
        System.out.println(myMathTeam);
        System.out.println(myMathTeam.getAssignments());
        System.out.println(myMathTeam.getQuestions());
        System.out.println(assignment);
        System.out.println(assignment.getMathTeam());
        System.out.println(assignment.getQuestions());


    }
}
package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.controllers.AssignmentController;
import dat.controllers.MathTeamController;
import dat.controllers.QuestionController;
import dat.controllers.SecurityController;
import dat.routes.Routes;
import jakarta.persistence.EntityManagerFactory;


public class Main
{
    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    public static void main(String[] args)
    {
        QuestionController questionController = new QuestionController(emf);
        AssignmentController assignmentController = new AssignmentController(emf);
        MathTeamController mathTeamController = new MathTeamController(emf);
        SecurityController securityController = new SecurityController(emf);
        Routes routes = new Routes(questionController, assignmentController, mathTeamController, securityController);

        ApplicationConfig
                .getInstance()
                .initiateServer()
                .setRoute(routes.getRoutes())
                .handleException()
                .setApiExceptionHandling()
                .checkSecurityRoles()
                .startServer(7070);
    }
}
package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.controllers.*;
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
    }
}
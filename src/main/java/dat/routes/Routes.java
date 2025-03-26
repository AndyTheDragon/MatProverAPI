package dat.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.controllers.*;
import dat.enums.Roles;
import io.javalin.apibuilder.EndpointGroup;

import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes
{
    private final QuestionController questionController;
    private final AssignmentController assignmentController;
    private final MathTeamController mathTeamController;
    private final SecurityController securityController;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public Routes(Map<String, IController> controllers)
    {
        this.questionController = (QuestionController) controllers.get("question");
        this.assignmentController = (AssignmentController) controllers.get("assignment");
        this.mathTeamController = (MathTeamController) controllers.get("mathTeam");
        this.securityController = (SecurityController) controllers.get("security");
    }

    public  EndpointGroup getRoutes()
    {
        return () -> {
            path("opgave", questionRoutes());
            path("opgaveset", assignmentRoutes());
            path("hold", mathTeamRoutes());
            path("user", userRoutes());
            path("auth", authRoutes());
        };
    }

    public EndpointGroup questionRoutes()
    {
        return () -> {
            get(questionController::getAll, Roles.ANYONE);
            get("/{id}", questionController::getById, Roles.ANYONE);
            //get("/{category}", questionController::getCategory, Roles.ANYONE);
            post(questionController::create, Roles.ANYONE);
            patch(questionController::update, Roles.ANYONE);
            delete("/{id}", questionController::delete, Roles.ANYONE);
        };
    }

    public EndpointGroup assignmentRoutes()
    {
        return () -> {
            get(assignmentController::getAll, Roles.ANYONE);
            get("/{id}", assignmentController::getById, Roles.ANYONE);
            post(assignmentController::create, Roles.ANYONE);
            post("/{id}/add", assignmentController::addQuestionToAssignment, Roles.ANYONE);
            delete("/{id}/remove", assignmentController::removeQuestionFromAssignment, Roles.ANYONE);
            delete("/{id}", assignmentController::delete, Roles.ANYONE);
        };
    }

    public EndpointGroup mathTeamRoutes()
    {
        return () -> {
            get(mathTeamController::getAll, Roles.ANYONE);
            get("/{id}", mathTeamController::getById, Roles.ANYONE);
            post(mathTeamController::create, Roles.ANYONE);
            post("/{id}/add", mathTeamController::addAssignmentToTeam, Roles.ANYONE);
            delete("/{id}/remove", mathTeamController::removeAssignmentFromTeam, Roles.ANYONE);
            delete("/{id}", mathTeamController::delete, Roles.ANYONE);
        };
    }

    public EndpointGroup userRoutes()
    {
        return () -> {
            get(securityController::getAll, Roles.ANYONE);
            get("/{id}", securityController::getById, Roles.ANYONE);
            delete("/{id}", securityController::delete, Roles.ANYONE);
        };
    }


    private  EndpointGroup authRoutes()
    {
        return () -> {
            get("/test", ctx->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from Open")), Roles.ANYONE);
            get("/healthcheck", securityController::healthCheck, Roles.ANYONE);
            post("/login", securityController::login, Roles.ANYONE);
            post("/register", securityController::register, Roles.ANYONE);
            get("/verify", securityController::verify , Roles.ANYONE);
            get("/tokenlifespan", securityController::timeToLive , Roles.ANYONE);
        };
    }

}

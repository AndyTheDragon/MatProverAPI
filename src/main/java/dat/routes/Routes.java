package dat.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.controllers.HotelController;
import dat.controllers.SecurityController;
import dat.enums.Roles;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes
{
    private final HotelController hotelController;
    private final SecurityController securityController;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public Routes(HotelController hotelController, SecurityController securityController)
    {
        this.hotelController = hotelController;
        this.securityController = securityController;
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
            get("/{category}", questionController::getCategory, Roles.ANYONE);
            post(questionController::create, Roles.ANYONE);
            patch(questionController::update, Roles.ANYONE);
            delete("/{id}", questionController::delete, Roles.ANYONE);
        };
    }

    public EndpointGroup assignmentRoutes()
    {
        return () -> {
            get(assignmentController::assignment, Roles.ANYONE);
            get("/{id}", assignmentController::getById, Roles.ANYONE);
            post(assignmentController::createAssignment, Roles.ANYONE);
            post("/{id}/add", assignmentController::addQuestionToAssignment, Roles.ANYONE);
            delete("/{id}/remove", assignmentController::removeQuestionFromAssignment, Roles.ANYONE);
        };
    }

    public EndpointGroup mathTeamRoutes()
    {
        return () -> {
            get(mathTeamController::mathTeam, Roles.ANYONE);
            get("/{id}", mathTeamController::getById, Roles.ANYONE);
            post(mathTeamController::createMathTeam, Roles.ANYONE);
        };
    }

    public EndpointGroup userRoutes()
    {
        return () -> {
            get(securityController::getAll, Roles.ANYONE);
            get("/{id}", securityController::getById, Roles.ANYONE);
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

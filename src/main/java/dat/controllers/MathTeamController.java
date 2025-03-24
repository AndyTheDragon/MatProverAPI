package dat.controllers;

import dat.dto.ErrorMessage;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class MathTeamController implements IController
{
    public MathTeamController(EntityManagerFactory emf)
    {
    }

    @Override
    public void create(Context ctx)
    {
        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
        ctx.status(501).json(error);
    }

    @Override
    public void getById(Context ctx)
    {
        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
        ctx.status(501).json(error);
    }

    @Override
    public void getAll(Context ctx)
    {
        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
        ctx.status(501).json(error);
    }

    @Override
    public void update(Context ctx)
    {
        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
        ctx.status(501).json(error);
    }

    @Override
    public void delete(Context ctx)
    {
        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
        ctx.status(501).json(error);
    }
}

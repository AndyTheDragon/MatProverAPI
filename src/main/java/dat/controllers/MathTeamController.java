package dat.controllers;

import dat.dao.CrudDAO;
import dat.dao.GenericDAO;
import dat.dto.ErrorMessage;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;


public class MathTeamController implements IController
{
    private final CrudDAO dao;

    public MathTeamController(EntityManagerFactory emf)
    {
        dao = new GenericDAO(emf);
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

    public void addAssignmentToTeam(Context ctx)
    {
        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
        ctx.status(501).json(error);
    }

    public void removeAssignmentFromTeam(Context ctx)
    {
        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
        ctx.status(501).json(error);
    }
}

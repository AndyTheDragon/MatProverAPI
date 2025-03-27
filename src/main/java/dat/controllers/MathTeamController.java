package dat.controllers;

import dat.config.HibernateConfig;
import dat.dao.CrudDAO;
import dat.dao.GenericDAO;
import dat.dto.ErrorMessage;
import dat.dto.MathTeamDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MathTeamController implements IController
{
    private final CrudDAO dao;
    private final Logger logger = LoggerFactory.getLogger(MathTeamController.class);
    private  final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    public MathTeamController(EntityManagerFactory emf)
    {
        dao = new GenericDAO(emf);
    }

    @Override
    public void create(Context ctx)
    {
        try
        {
            ctx.json(MathTeamDTO.class);
        } catch (Exception e)
        {
            logger.error("unable to create object", e);
            ErrorMessage err = new ErrorMessage("Error persisting to db");
            ctx.json(err);
        }
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

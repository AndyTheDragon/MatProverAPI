package dat.controllers;

import dat.dao.CrudDAO;
import dat.dao.HotelDAO;
import dat.dto.ErrorMessage;
import dat.dto.HotelDTO;
import dat.entities.Hotel;
import dat.entities.Room;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QuestionController implements IController
{
    private final CrudDAO dao;
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);


    public QuestionController(EntityManagerFactory emf)
    {
        dao = new HotelDAO(emf);
    }

    public QuestionController(CrudDAO dao)
    {
        this.dao = dao;
    }



    @Override
    public void getAll(Context ctx)
    {
        try
        {
            ctx.json(dao.getMany(Hotel.class));
        }
        catch (Exception ex)
        {
            logger.error("Error getting entities", ex);
            ErrorMessage error = new ErrorMessage("Error getting entities");
            ctx.status(404).json(error);
        }
    }

    @Override
    public void getById(Context ctx)
    {

        try {
            //long id = Long.parseLong(ctx.pathParam("id"));
            long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i>0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            HotelDTO foundEntity = new HotelDTO(dao.getById(Hotel.class, id));
            ctx.json(foundEntity);

        } catch (Exception ex){
            logger.error("Error getting entity", ex);
            ErrorMessage error = new ErrorMessage("No entity with that id");
            ctx.status(404).json(error);
        }
    }

    @Override
    public void create(Context ctx)
    {
        try
        {
            HotelDTO incomingTest = ctx.bodyAsClass(HotelDTO.class);
            Hotel entity = new Hotel(incomingTest);
            Hotel createdEntity = dao.create(entity);
            for (Room room : entity.getRooms())
            {
                room.setHotel(createdEntity);
                dao.update(room);
            }
            ctx.json(new HotelDTO(createdEntity));
        }
        catch (Exception ex)
        {
            logger.error("Error creating entity", ex);
            ErrorMessage error = new ErrorMessage("Error creating entity");
            ctx.status(400).json(error);
        }
    }

    public void update(Context ctx)
    {
        try
        {
            //int id = Integer.parseInt(ctx.pathParam("id"));
            long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i>0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            HotelDTO incomingEntity = ctx.bodyAsClass(HotelDTO.class);
            Hotel hotelToUpdate = dao.getById(Hotel.class, id);
            if (incomingEntity.getName() != null)
            {
                hotelToUpdate.setName(incomingEntity.getName());
            }
            if (incomingEntity.getAddress() != null)
            {
                hotelToUpdate.setAddress(incomingEntity.getAddress());
            }
            Hotel updatedEntity = dao.update(hotelToUpdate);
            HotelDTO returnedEntity = new HotelDTO(updatedEntity);
            ctx.json(returnedEntity);
        }
        catch (Exception ex)
        {
            logger.error("Error updating entity", ex);
            ErrorMessage error = new ErrorMessage("Error updating entity. " + ex.getMessage());
            ctx.status(400).json(error);
        }
    }

    public void delete(Context ctx)
    {
        try
        {
            //long id = Long.parseLong(ctx.pathParam("id"));
            long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i>0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            dao.delete(Hotel.class, id);
            ctx.status(204);
        }
        catch (Exception ex)
        {
            logger.error("Error deleting entity", ex);
            ErrorMessage error = new ErrorMessage("Error deleting entity");
            ctx.status(400).json(error);
        }
    }

    public void getCategory(Context ctx)
    {
        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
        ctx.status(501).json(error);
    }
}

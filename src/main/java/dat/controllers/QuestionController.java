package dat.controllers;

import dat.dao.CrudDAO;
import dat.dao.HotelDAO;
import dat.dto.ErrorMessage;
import dat.dto.HotelDTO;
import dat.dto.QuestionDTO;
import dat.entities.Hotel;
import dat.entities.Question;
import dat.entities.Room;
import dat.exceptions.ApiException;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
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


    @Override
    public void getAll(Context ctx)
    {
        try
        {
            ctx.json(dao.getAll(Question.class));
        }
        catch (Exception ex)
        {
            logger.error("Error getting entities", ex);
            throw new ApiException(404, "No content found for this request");
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
            QuestionDTO foundEntity = new QuestionDTO(dao.getById(Question.class, id));
            ctx.json(foundEntity);

        } catch (Exception ex){
            logger.error("Error getting entity", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    @Override
    public void create(Context ctx)
    {
        try
        {
            logger.info("Received request to create a new Question");
            QuestionDTO incomingTest = ctx.bodyAsClass(QuestionDTO.class);
            logger.info("Request data: {}", incomingTest);

            Question entity = new Question(incomingTest);
            Question createdEntity = dao.create(entity);

            logger.info("Created new Question with ID: {}", createdEntity.getId());
            ctx.json(new QuestionDTO(createdEntity));
        }
        catch (Exception ex)
        {
            logger.error("Error creating entity", ex);
            throw new ApiException(400, "Field ‘xxx’ is required"); //Jeg ved ikke hvordan jeg gør `xxx´ dynamisk.
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

//    public void getCategory(Context ctx)
//    {
//        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
//        ctx.status(501).json(error);
//    }
}

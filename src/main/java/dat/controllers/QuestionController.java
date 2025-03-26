package dat.controllers;

import dat.dao.CrudDAO;
import dat.dao.HotelDAO;
import dat.dto.QuestionDTO;
import dat.entities.Question;
import dat.exceptions.ApiException;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;


public class QuestionController implements IController, IQuestionController
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
            ctx.status(200).json(dao.getMany(Question.class));
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
        try
        {
            long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            QuestionDTO foundEntity = new QuestionDTO(dao.getById(Question.class, id));
            ctx.status(200).json(foundEntity);

        }
        catch (Exception ex)
        {
            logger.error("Error getting entity", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    @Override
    public void create(Context ctx)
    {
        try
        {
            QuestionDTO incomingTest = ctx.bodyAsClass(QuestionDTO.class);
            Question entity = new Question(incomingTest);
            Question createdEntity = dao.create(entity);
            ctx.status(201).json(new QuestionDTO(createdEntity));
        }
        catch (Exception ex)
        {
            logger.error("Error creating entity", ex);
            throw new ApiException(400, "Field ‘xxx’ is required"); //TODO Lav hjælpe metode til fejlmelding
        }
    }

    public void update(Context ctx)
    {
        try
        {
            QuestionDTO questionDTO = ctx.bodyAsClass(QuestionDTO.class);

            if (questionDTO.getId() == null || questionDTO.getId() <= 0)
            {
                logger.warn("Invalid id: {}", questionDTO.getId());
                throw new BadRequestResponse("Invalid id");
            }

            Question questionToUpdate = dao.getById(Question.class, questionDTO.getId());

            updateFieldIfNotNull(questionToUpdate::setTermDate, questionDTO.getTermDate());
            updateFieldIfNotNull(questionToUpdate::setAuthor, questionDTO.getAuthor());
            updateFieldIfNotNull(questionToUpdate::setPoints, questionDTO.getPoints());
            updateFieldIfNotNull(questionToUpdate::setYear, questionDTO.getYear());
            updateFieldIfNotNull(questionToUpdate::setQuestionText, questionDTO.getQuestionText());
            updateFieldIfNotNull(questionToUpdate::setPictureURL, questionDTO.getPictureURL());
            updateFieldIfNotNull(questionToUpdate::setCategory, questionDTO.getCategory());
            updateFieldIfNotNull(questionToUpdate::setLicense, questionDTO.getLicense());
            updateFieldIfNotNull(questionToUpdate::setLevel, questionDTO.getLevel());
            updateFieldIfNotNull(questionToUpdate::setTestFormat, questionDTO.getTestFormat());

            Question updatedEntity = dao.update(questionToUpdate);
            ctx.status(200).json(new QuestionDTO(updatedEntity));
        }
        catch (Exception ex)
        {
            logger.error("Error updating entity", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    public void delete(Context ctx)
    {
        try
        {
            //long id = Long.parseLong(ctx.pathParam("id"));
            long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            dao.delete(Question.class, id);
            ctx.status(204);
        }
        catch (Exception ex)
        {
            logger.error("Error deleting entity", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    @Override
    public <T> void updateFieldIfNotNull(Consumer<T> setter, T value)
    {
        if (value != null)
        {
            setter.accept(value);
        }
    }

//    public void getCategory(Context ctx)
//    {
//        ErrorMessage error = new ErrorMessage("Error, not implementet yet");
//        ctx.status(501).json(error);
//    }
}

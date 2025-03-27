package dat.controllers;

import dat.dao.CrudDAO;
import dat.dao.GenericDAO;
import dat.dao.HotelDAO;
import dat.dto.QuestionDTO;
import dat.dto.QuestionStudentDTO;
import dat.entities.Question;
import dat.exceptions.ApiException;
import dat.exceptions.DaoException;
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
        dao = new GenericDAO(emf);
    }


    @Override
    public void getAll(Context ctx)
    {
        try
        {
            int pageSize = ctx.queryParamAsClass("pageSize", Integer.class)
                    .check(i -> i > 0, "pageSize must be at least 1")
                    .check(i -> i <= 50, "pageSize must be at most 50")
                    .getOrDefault(10);
            int pageNumber = ctx.queryParamAsClass("pageNumber", Integer.class)
                    .check(i -> i > 0, "pageNumber must be at least 1")
                    .getOrDefault(1);
            ctx.status(200).json(dao.getMany(Question.class, pageSize, pageNumber));

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
            Integer id = ctx.pathParamAsClass("id", Integer.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            String output = ctx.queryParam("output");
            if ("full".equals(output))
            {
                QuestionDTO foundQuestion = new QuestionDTO(dao.getById(Question.class, id));
                ctx.status(200).json(foundQuestion);
            }
            else
            {
            QuestionStudentDTO foundQuestion = new QuestionStudentDTO(dao.getById(Question.class, id));
            ctx.status(200).json(foundQuestion);
            }
        }
        catch (Exception ex)
        {
            logger.error("Error getting question", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    @Override
    public void create(Context ctx)
    {
        try
        {
            QuestionDTO incomingTest = ctx.bodyAsClass(QuestionDTO.class);
            Question question = new Question(incomingTest);
            Question createdQuestion = dao.create(question);
            ctx.status(201).json(new QuestionDTO(createdQuestion));
        }
        catch (Exception ex)
        {
            logger.error("Error creating question", ex);
            throw new ApiException(400, "Field ‘xxx’ is required"); //TODO Lav hjælpe metode til fejlmelding
        }
    }

    public void update(Context ctx)
    {
        try
        {
            QuestionDTO questionDTO = ctx.bodyValidator(QuestionDTO.class)
                    .check(q -> q.getId() != null, "id must not be null")
                    .check(q -> q.getId() > 0, "id must be at least 1")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));

            Question questionToUpdate = dao.getById(Question.class, questionDTO.getId());

            updateQuestionIfNotNull(questionDTO, questionToUpdate);
            Question updatedEntity = dao.update(questionToUpdate);
            ctx.status(200).json(new QuestionDTO(updatedEntity));
        }
        catch (DaoException ex)
        {
            logger.error("Error updating question", ex);
            throw new ApiException(400, "Field ‘xxx’ is required"); //TODO Lav hjælpe metode til fejlmelding
        }
        catch (Exception ex)
        {
            logger.error("Error updating question", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    public void delete(Context ctx)
    {
        try
        {
            Integer id = ctx.pathParamAsClass("id", Integer.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            dao.delete(Question.class, id);
            ctx.status(204);
        }
        catch (Exception ex)
        {
            logger.error("Error deleting question", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    @Override
    public void updateQuestionIfNotNull(QuestionDTO questionDTO, Question questionToUpdate)
    {
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

    }
    @Override
    public <T> void updateFieldIfNotNull(Consumer<T> setter, T value)
    {
        if (value != null)
        {
            setter.accept(value);
        }
    }
}

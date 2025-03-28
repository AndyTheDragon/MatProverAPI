package dat.controllers;

import dat.dao.CrudDAO;
import dat.dao.GenericDAO;
import dat.dto.*;
import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.exceptions.ApiException;
import dat.exceptions.DaoException;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;


public class AssignmentController implements IController
{
    private final CrudDAO dao;
    private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

    public AssignmentController(EntityManagerFactory emf)
    {
        dao = new GenericDAO(emf);
    }

    @Override
    public void create(Context ctx)
    {
        try
        {
            AssignmentInputDTO assignmentDTO = ctx.bodyAsClass(AssignmentInputDTO.class);
            logger.info(assignmentDTO.toString());
            Assignment assignment = new Assignment(assignmentDTO);
            logger.info(assignment.toString());
            MathTeam mathTeam = dao.getById(MathTeam.class, assignmentDTO.getMathTeam().getId());
            logger.info(mathTeam.toString());
            mathTeam.addAssignment(assignment);
            AssignmentOutputDTO output = new AssignmentOutputDTO(dao.create(assignment));
            dao.update(mathTeam);
            ctx.status(201).json(output);
        } catch (Exception ex)
        {
            logger.error("Error creating entity. ", ex);
            throw new ApiException(400, "Error creating entity, check required field values is not null");
        }
    }

    @Override
    public void getAll(Context ctx)
    {
        try
        {
            List<Assignment> assignments = dao.getMany(Assignment.class);
            List<AssignmentOutputDTO> assignmentDTOs = assignments.stream()
                    .map(AssignmentOutputDTO::new)
                    .collect(Collectors.toList());
            ctx.json(assignmentDTOs);
        } catch (Exception ex)
        {
            logger.error("Error getting entity. ", ex);
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
            AssignmentOutputDTO foundEntity = new AssignmentOutputDTO(dao.getById(Assignment.class, id));
            ctx.json(foundEntity);

        } catch (Exception ex)
        {
            logger.error("Error getting entity. ", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    @Override
    public void update(Context ctx)
    {
        try
        {
            Integer id = ctx.pathParamAsClass("id", Integer.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            AssignmentInputDTO incomingAssignment = ctx.bodyAsClass(AssignmentInputDTO.class);
            Assignment assignmentUpdate = dao.getById(Assignment.class, id);
            if (incomingAssignment.getIntroText() != null)
            {
                assignmentUpdate.setIntroText(incomingAssignment.getIntroText());
            }
            Assignment updatedAssignment = dao.update(assignmentUpdate);
            AssignmentOutputDTO returnedAssignment = new AssignmentOutputDTO(updatedAssignment);
            ctx.status(200).json(returnedAssignment + "Assignment updated");
        } catch (DaoException ex)
        {
            logger.error("Error updating entity. ", ex);
            throw new ApiException(400, "Error creating entity, check required field values is not null");
        } catch (Exception ex)
        {
            logger.error("Error updating entity. ", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    @Override
    public void delete(Context ctx)
    {
        try
        {
            Integer id = ctx.pathParamAsClass("id", Integer.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            dao.delete(Assignment.class, id);
            ctx.status(204);
        } catch (Exception ex)
        {
            logger.error("Error updating entity. ", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    public void addQuestionToAssignment(Context ctx)
    {
        try
        {
            Integer assignmentId = ctx.pathParamAsClass("id", Integer.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            QuestionStudentDTO incomingQuestion = ctx.bodyAsClass(QuestionStudentDTO.class);
            Integer questionId = incomingQuestion.getId();
            Assignment assignment = dao.getById(Assignment.class, assignmentId);
            Question question = dao.getById(Question.class, questionId);
            assignment.addQuestion(question);
            dao.update(assignment);
            ctx.status(204);
        } catch (DaoException ex)
        {
            logger.error("Error updating entity. ", ex);
            throw new ApiException(400, "Error creating entity, check required field values is not null");
        } catch (Exception ex)
        {
            logger.error("Error updating entity. ", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }

    public void removeQuestionFromAssignment(Context ctx)
    {
        try
        {
            Integer assignmentId = ctx.pathParamAsClass("id", Integer.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));

            QuestionStudentDTO incomingQuestion = ctx.bodyAsClass(QuestionStudentDTO.class);
            Integer questionId = incomingQuestion.getId();

            Assignment assignment = dao.getById(Assignment.class, assignmentId);
            if (assignment == null)
            {
                logger.warn("Assignment with id {} not found", assignmentId);
                throw new BadRequestResponse("Assignment not found");
            }

            boolean removed = assignment.getQuestions().removeIf(q -> q.getId().equals(questionId));
            if (!removed)
            {
                logger.warn("Question with id {} not found in assignment with id {}", questionId, assignmentId);
                throw new BadRequestResponse("Question not found in assignment");
            }
            dao.update(assignment);

            ctx.status(204);
        } catch (Exception ex)
        {
            logger.error("Error updating entity. ", ex);
            throw new ApiException(404, "No content found for this request");
        }
    }
}

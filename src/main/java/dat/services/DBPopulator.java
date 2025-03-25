package dat.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import dat.dto.AssignmentInfoDTO;
import dat.dto.QuestionDTO;
import dat.dto.UserAccountDTO;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import dat.dao.GenericDAO;


public class DBPopulator
{
    private static final Logger logger = LoggerFactory.getLogger(DBPopulator.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static GenericDAO genericDAO;

    private static final String userAccountJsonDTO = "json/user_account_dto.json";
    private static final String questionJsonDTO = "json/question_dto.json";
    private static final String questionStudentJsonDTO = "json/question_student_dto.json";
    private static final String assignmentInfoJsonDTO = "json/assignment_info_dto.json";
    private static final String mathTeamJsonDTO = "json/math_team_dto.json";

    public static void main(String[] args)
    {

        //readAssignmentInfoDTO();
        readQuestionDTO();
        readMathTeamDTO();
        readUserAccountDTO();

    }

    private static void readQuestionDTO()
    {
        try
        {
            JsonNode node = objectMapper.readTree(new File("src/json/question_dto.json"));
            Set<QuestionDTO> questions = objectMapper.convertValue(node, new TypeReference<Set<QuestionDTO>>() {});
            for (QuestionDTO dto : questions)
            {
                genericDAO.create(new Question(dto));
            }

        } catch (Exception e)
        {
            logger.info("could not create object  : question to database");
        }
    }

    private static void readAssignmentInfoDTO()
    {
        try
        {
            JsonNode node = objectMapper.readTree(new File("src/json/question_dto.json"));
            Set<AssignmentInfoDTO> list = objectMapper.convertValue(node, new TypeReference<Set<AssignmentInfoDTO>>() {});
            for (AssignmentInfoDTO dto : list)
            {
                //genericDAO.create(new Assignment(dto));
            }

        } catch (Exception e)
        {
            logger.info("could not create object  : questionStudentDTO to database");
        }
    }

    private static void readUserAccountDTO()
    {
        try
        {
            JsonNode node = objectMapper.readTree(new File("src/json/user_account_dto.json"));
            Set<UserAccountDTO> list = objectMapper.convertValue(node, new TypeReference<Set<UserAccountDTO>>() {});
            for (UserAccountDTO dto : list)
            {
                genericDAO.create(new UserAccount(dto));
            }

        } catch (Exception e)
        {
            logger.info("could not create object  : questionStudentDTO to database");
        }
    }

    private static void readMathTeamDTO()
    {
        try
        {
            JsonNode node = objectMapper.readTree(new File("src/json/math_team_dto.json"));
            Set<MathTeam> list = objectMapper.convertValue(node, new TypeReference<Set<MathTeam>>() {});
            for (MathTeam dto : list)
            {
                genericDAO.create(new MathTeam(String.valueOf(dto)));
            }

        } catch (Exception e)
        {
            logger.info("could not create object  : questionStudentDTO to database");
        }
    }


}


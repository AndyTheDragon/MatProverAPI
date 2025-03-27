package dat.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dto.MathTeamDTO;
import dat.dto.QuestionDTO;
import dat.dto.UserAccountDTO;
import dat.entities.Question;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import dat.dao.GenericDAO;


public class DBPopulator
{
    private static final Logger logger = LoggerFactory.getLogger(DBPopulator.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static GenericDAO genericDAO = new GenericDAO(emf);

    public static void main(String[] args)
    {
        objectMapper.registerModule(new JavaTimeModule());

        readQuestions();
        //readQuestionDTO(); // fix duplicate values for "questionnumber" in "qustiondto.json"
        readMathTeamDTO();
        //readUserAccountDTO();

    }

    private static void readQuestions()
    {
        try
        {
            JsonNode node = objectMapper.readTree(new File("src/main/resources/json/opgaver.json"));
            Set<Question> questions = objectMapper.convertValue(node, new TypeReference<Set<Question>>() {});
            for (Question q : questions)
            {
                genericDAO.create(q);
            }
        } catch (Exception e)
        {
            logger.info("could not create object  : question(s) to database", e);
        }
    }

    private static void readQuestionDTO()
    {
        try
        {
            JsonNode node = objectMapper.readTree(new File("src/main/resources/json/question_dto.json"));
            //QuestionDTO dto = objectMapper.convertValue(node, new TypeReference<QuestionDTO>() {});
            Set<QuestionDTO> questions = objectMapper.convertValue(node, new TypeReference<Set<QuestionDTO>>() {});
            //genericDAO.create(new Question(dto));
            for (QuestionDTO dto : questions)
            {
                genericDAO.create(new Question(dto));
            }

        } catch (Exception e)
        {
            logger.info("could not create object  : question to database", e);
        }
    }

    private static void readUserAccountDTO()
    {
        try
        {
            JsonNode node = objectMapper.readTree(new File("src/main/resources/json/user_account.json"));
            //QuestionDTO dto = objectMapper.convertValue(node, new TypeReference<QuestionDTO>() {});
            Set<UserAccountDTO> users = objectMapper.convertValue(node, new TypeReference<Set<UserAccountDTO>>() {});
            //genericDAO.create(new Question(dto));
            for (UserAccountDTO dto : users)
            {
                genericDAO.create(new UserAccount(dto));
            }

        } catch (Exception e)
        {
            logger.info("could not create object  : question to database", e);
        }
    }

    private static void readMathTeamDTO()
    {

        try
        {
            JsonNode node = objectMapper.readTree(new File("src/main/resources/json/math_team_dto.json"));
            Set<MathTeamDTO> list = objectMapper.convertValue(node, new TypeReference<Set<MathTeamDTO>>() {});
            for (MathTeamDTO dto : list)
            {
                genericDAO.create(new MathTeam(String.valueOf(new MathTeam(dto))));
            }

        } catch (Exception e)
        {
            logger.info("could not create object  : MathTeamDTO to database", e);
        }
    }


}


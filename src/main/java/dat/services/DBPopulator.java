package dat.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static GenericDAO genericDAO = new GenericDAO(emf);

    public static void main(String[] args)
    {



        readQuestionDTO();
        //readMathTeamDTO();
        //readUserAccountDTO();

    }

    private static void readQuestionDTO()
    {
        objectMapper.registerModule(new JavaTimeModule());

        try
        {
            JsonNode node = objectMapper.readTree(new File("src/main/resources/json/question_dto.json"));
            QuestionDTO dto = objectMapper.convertValue(node, new TypeReference<QuestionDTO>() {});
            //Set<QuestionDTO> questions = objectMapper.convertValue(node, new TypeReference<Set<QuestionDTO>>() {});
            genericDAO.create(new Question(dto));
            /*for (QuestionDTO dto : questions)
            {
                genericDAO.create(new Question(dto));
            }*/

        } catch (Exception e)
        {
            logger.info("could not create object  : question to database", e);
        }
    }

    private static void readUserAccountDTO()
    {
        try
        {
            InputStream inputStream = DBPopulator.class.getClassLoader().getResourceAsStream("json/question_dto.json");
            if (inputStream == null)
            {
                throw new IllegalArgumentException("file not found! ");
            }

            //JsonNode node = objectMapper.readTree(new File("src/json/user_account_dto.json"));
            Set<UserAccountDTO> list = objectMapper.convertValue(inputStream, new TypeReference<Set<UserAccountDTO>>() {});
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


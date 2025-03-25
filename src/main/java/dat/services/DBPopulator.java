package dat.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import dat.dao.GenericDAO;


public class DBPopulator
{
    private static final Logger logger = LoggerFactory.getLogger(DBPopulator.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private GenericDAO genericDAO;

    private static final String userAccountJsonDTO = "json/user_account_dto.json";
    private static final String questionJsonDTO = "json/question_dto.json";
    private static final String questionStudentJsonDTO = "json/question_student_dto.json";
    private static final String assignmentInfoJsonDTO = "json/assignment_info_dto.json";
    private static final String mathTeamJsonDTO = "json/math_team_dto.json";

    public static void main(String[] args)
    {
        // Run this line once to insert data to the DB from the JSON file.

        /*
        populateDB();
        // */
    }

    private void populateDB()
    {

    }

    private void readQuestionDTO()
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

    private void readQuestionStudentDTO()
    {
        try
        {
            JsonNode node = objectMapper.readTree(new File("src/json/question_dto.json"));
            Set<QuestionStudentDTO> list = objectMapper.convertValue(node, new TypeReference<Set<QuestionStudentDTO>>() {});
            for (QuestionStudentDTO dto : list)
            {
                //genericDAO.create(new Question(dto));
            }

        } catch (Exception e)
        {
            logger.info("could not create object  : questionStudentDTO to database");
        }
    }

    private void readAssignmentInfoDTO()
    {
        try
        {
            JsonNode node = objectMapper.readTree(new File("src/json/question_dto.json"));
            Set<AssignmentInfoDTO> list = objectMapper.convertValue(node, new TypeReference<Set<AssignmentInfoDTO>>() {});
            for (AssignmentInfoDTO dto : list)
            {
                genericDAO.create(new Assignment(dto));
            }

        } catch (Exception e)
        {
            logger.info("could not create object  : questionStudentDTO to database");
        }
    }

    private void readUserAccountDTO()
    {

    }

    private void readMathTeamDTO()
    {

    }

    private void createUsers()
    {
        try (Reader reader = new InputStreamReader(
            ReadHotelsFromJson.class.getClassLoader().getResourceAsStream("hotels_with_rooms.json"),
            StandardCharsets.UTF_8))
        {
            if (reader == null)
            {
                throw new FileNotFoundException("Resource not found: hotels_with_rooms.json");
            }

            // Read hotels from json file
            ObjectMapper objectMapper = new ObjectMapper();
            HotelJsonWrapper hotelJsonWrapper = objectMapper.readValue(reader, HotelJsonWrapper.class);
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
            GenericDAO genericDao = GenericDAO.getInstance(emf);

            // emf.close();
        } catch (IOException e)
        {
        }

    }
}


package dat.utils;

import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import dat.enums.TestFormat;
import jakarta.persistence.EntityManager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
public class Populator
{
    private Question q1, q2, q3;
    private UserAccount userA, userB;
    private MathTeam mathTeamA, mathTeamB, mathTeamC;
    private Assignment assignmentA, assignmentB, assignmentC, assignmentD;

    public Populator()
    {
        q1 = new Question(2024,
                "Johnny",
                10,
                1,
                "What is Doofus?",
                "http://example.com/image.jpg",
                "Sample Category",
                "Sample License",
                "Sample Level",
                TestFormat.MED);
        q2 = new Question(2023,
                "Author Name",
                10,
                2,
                "What is Dingus?",
                "http://example.com/image.jpg",
                "Sample Category",
                "Sample License",
                "Sample Level",
                TestFormat.MED);
        q3 = new Question(2022,
                "Author Name",
                5,
                1,
                "What is Dingus?",
                "http://example.com/image.jpg",
                "Sample Category",
                "Sample License",
                "Sample Level",
                TestFormat.MED);
       userA = new UserAccount("Jon",
               "jon@cph.dk",
               "CPH Business",
               "js4325@unilogin.dk",
               "kodeord");
       userB = new UserAccount("Jane",
                "jane@cph.dk",
                "CPH Business",
                "js4326@unilogin.dk",
                "password");

       mathTeamA = new MathTeam("Math Team A", userA);
       mathTeamB = new MathTeam("Math Team B", userB);
       mathTeamC = new MathTeam("Math Team C");
       userA.addMathTeam(mathTeamC);

       assignmentA = new Assignment("Assignment A", mathTeamA, Set.of(q1, q2));
       assignmentB = new Assignment("Assignment B", mathTeamB, Set.of(q1, q3));
       assignmentC = new Assignment("Assignment C", mathTeamC, Set.of(q1, q2));
       assignmentD = new Assignment("Assignment D");
       mathTeamA.addAssignment(assignmentD);
       assignmentD.addQuestion(q3);
       assignmentD.addQuestion(q2);
    }

    public Map<String, Object> getEntites()
    {
        Map<String, Object> entities = new HashMap<>();
        entities.put("question1", q1);
        entities.put("question2", q2);
        entities.put("question3", q3);
        entities.put("userA", userA);
        entities.put("userB", userB);
        entities.put("mathTeamA", mathTeamA);
        entities.put("mathTeamB", mathTeamB);
        entities.put("mathTeamC", mathTeamC);
        entities.put("assignmentA", assignmentA);
        entities.put("assignmentB", assignmentB);
        entities.put("assignmentC", assignmentC);
        entities.put("assignmentD", assignmentD);
        return entities;
    }

    public void resetAndPersistEntities(EntityManager em)
    {
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM assignment_question").executeUpdate();
        em.createQuery("DELETE FROM Question").executeUpdate();
        em.createNativeQuery("DELETE FROM mathteam_assignment").executeUpdate();
        em.createQuery("DELETE FROM Assignment").executeUpdate();
        em.createNativeQuery("DELETE FROM useraccount_mathteam").executeUpdate();
        em.createQuery("DELETE FROM MathTeam").executeUpdate();
        em.createQuery("DELETE FROM UserAccount").executeUpdate();
        for (Object entity : getEntites().values())
        {
            em.persist(entity);
        }
        em.getTransaction().commit();
    }
}

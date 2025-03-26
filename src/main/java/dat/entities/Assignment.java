package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.enums.TestFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import dat.dto.AssignmentDTO;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Assignment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id", nullable = false)
    private Integer id;
    private String introText;
    @ManyToOne
    private MathTeam mathTeam;

    @ManyToOne
    private UserAccount owner;

    @ManyToMany
    private Set<Question> questions;

    private Integer amountOfQuestions;
    private Integer totalPoints;


    public Assignment(AssignmentDTO assignmentDTO)
    {
        this.id = assignmentDTO.getId();
        this.introText = assignmentDTO.getIntroText();
        this.owner = assignmentDTO.getOwner();
        this.questions = assignmentDTO.getQuestions().stream().map(Question::new).collect(Collectors.toSet());
        this.amountOfQuestions = assignmentDTO.getAmountOfQuestions();
        this.totalPoints = assignmentDTO.getTotalPoints();

    }

    public Assignment(String introText, MathTeam mathTeam, UserAccount owner, Set<Question> questions)
    {
        this.introText = introText;
        this.mathTeam = mathTeam;
        this.owner = owner;
        this.questions = questions;
    }

    public int getAmountOfQuestions()
    {
        return questions.size();
    }

    public int getTotalPoints()
    {
        int totalPoints = 0;
        for (Question question : questions)
        {
            totalPoints += question.getPoints();
        }
        return totalPoints;
    }

    public void addQuestion(Question question)
    {
        if (question != null)
        {
            questions.add(question);
        }
    }

    public void removeQuestion(Question question)
    {
        if (question != null)
        {
            questions.remove(question);
        }
    }

    public void setIntroText(String introText)
    {
        this.introText = introText;
    }

    public void setMathTeam(MathTeam mathTeam)
    {
        this.mathTeam = mathTeam;
    }

    public void setOwner(UserAccount owner)
    {
        this.owner = owner;
    }
}

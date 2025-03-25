package dat.entities;

import dat.dto.AssignmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Question> questions = new HashSet<>();

    public Assignment(AssignmentDTO assignmentDTO)
    {
        this.introText = assignmentDTO.getIntroText();
        this.questions = assignmentDTO.getQuestions().stream().map(Question::new).collect(Collectors.toSet());
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

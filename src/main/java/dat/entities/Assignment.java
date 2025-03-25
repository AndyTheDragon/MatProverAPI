package dat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

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

    public Assignment(AssignmentDTO assignmentDTO)
    {
        this.introText = assignmentDTO.getIntroText();
        this.questions = assignmentDTO.getQuestions().stream().map(Question::new).toSet();
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
}

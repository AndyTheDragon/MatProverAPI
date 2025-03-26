package dat.entities;

import dat.dto.MathTeamDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class MathTeam
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "math_team_id", nullable = false)
    private Integer id;
    private String description;

    @OneToMany
    private Set<Assignment> assignments;

    @ManyToOne
    private UserAccount owner;

    @ManyToMany
    private Set<Question> questions;


    public MathTeam(MathTeamDTO MathTeamDTO)
    {
        this.description = MathTeamDTO.getDescription();
        this.assignments = MathTeamDTO.getAssignments().stream().collect(Collectors.toSet());
        this.questions = MathTeamDTO.getQuestions().stream().collect(Collectors.toSet());
    }
    public MathTeam(String description)
    {
        this.description = description;
    }

    public MathTeam(String description, Set<Assignment> assignments, UserAccount owner)
    {
        this.description = description;
        this.assignments = assignments;
        this.owner = owner;
    }

    public MathTeam(String description, Set<Assignment> assignments, UserAccount owner, Set<Question> questions)
    {
        this.description = description;
        this.assignments = assignments;
        this.owner = owner;
        this.questions = questions;
    }

    public void addAssignment(Assignment assignment)
    {
        if (assignment != null)
        {
            assignments.add(assignment);
            assignment.setMathTeam(this);
        }
    }

    public void removeAssignment(Assignment assignment)
    {
        if (assignment != null)
        {
            assignments.remove(assignment);
            assignment.setMathTeam(null);
        }
    }

  public void addQuestion(Question question)
    {
        if (question != null)
        {
            questions.add(question);
            question.addMathTeam(this);
        }
    }

    public void removeQuestion(Question question)
    {
        if (question != null)
        {
            questions.remove(question);
            question.removeMathTeam(this);
        }
    }


    public void setOwner(UserAccount owner)
    {
        this.owner = owner;
    }
}

package dat.dto;

import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import lombok.Getter;

import java.util.Set;

@Getter
public class MathTeamDTO
{
    private Integer id;
    private String description;
    private Set<Assignment> assignments;
    private UserAccount owner;
    private Set<Question> questions;

    public MathTeamDTO(MathTeam mathTeam)
    {
        this.id = mathTeam.getId();
        this.description = mathTeam.getDescription();
        this.assignments = mathTeam.getAssignments();
        this.owner = mathTeam.getOwner();
        this.questions = mathTeam.getQuestions();
    }
}

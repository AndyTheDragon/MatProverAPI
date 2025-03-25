package dat.dto;

import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MathTeamDTO
{
    private Integer id;
    private String description;
    private Set<AssignmentDTO> assignments;
    private UserAccount owner;
    private Set<QuestionDTO> questions;

    public MathTeamDTO(MathTeam mathTeam)
    {
        this.id = mathTeam.getId();
        this.description = mathTeam.getDescription();
        //this.assignments = mathTeam.getAssignments();
        this.owner = mathTeam.getOwner();
        //this.questions = mathTeam.getQuestions();
    }
}

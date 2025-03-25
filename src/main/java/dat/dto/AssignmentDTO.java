package dat.dto;

import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssignmentDTO
{
    private Integer id;
    private String introText;
    private MathTeam mathTeam;
    private UserAccount owner;
    private Set<Question> questions;
    private Integer amountOfQuestions;
    private Integer totalPoints;

    public AssignmentDTO(Assignment assignment)
    {
        this.id = assignment.getId();
        this.owner = assignment.getOwner();
        this.introText = assignment.getIntroText();
        this.amountOfQuestions = assignment.getAmountOfQuestions();
        this.totalPoints = assignment.getTotalPoints();
    }
}

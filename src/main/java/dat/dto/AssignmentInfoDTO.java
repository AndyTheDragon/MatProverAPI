package dat.dto;

import dat.entities.Assignment;
import dat.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssignmentInfoDTO
{
    private Integer Id;
    private OwnerDTO owner;
    private String introText;
    private Integer amountOfQuestions;
    private Integer totalPoints;
    private MathTeamDTO mathTeam;

    public AssignmentInfoDTO(Assignment assignment)
    {
        this.Id = assignment.getId();
        this.owner = new OwnerDTO(assignment.getOwner());
        this.mathTeam = new MathTeamDTO(assignment.getMathTeam());
        this.introText = assignment.getIntroText();
        this.amountOfQuestions = assignment.getAmountOfQuestions();
        this.totalPoints = assignment.getTotalPoints();
    }
}

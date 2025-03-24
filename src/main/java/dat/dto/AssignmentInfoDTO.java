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
    private UserAccount owner;
    private String introText;
    private Integer quantityOfExercises;
    private Integer totalPoints;

    public AssignmentInfoDTO(Assignment assignment)
    {
        this.Id = assignment.getId();
        this.owner = assignment.getOwner();
        this.introText = assignment.getIntroText();
        this.quantityOfExercises = assignment.numberOfQuestionsInAssignment();
        this.totalPoints = assignment.totalPointsInAssignment();
    }
}

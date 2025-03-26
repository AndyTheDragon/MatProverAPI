package dat.dto;

import dat.entities.Assignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssignmentDTO
{
    private Integer id;
    private String introText;
    private MathTeamDTO mathTeam; //only used for output, should be empty on input ?
    private Integer owner; //only used for output, should be empty on input ?
    private Set<QuestionStudentDTO> questions; //only used for output, should be empty on input
    private Integer amountOfQuestions;
    private Integer totalPoints;

    public AssignmentDTO(Assignment assignment)
    {
        this.id = assignment.getId();
        this.introText = assignment.getIntroText();
        this.mathTeam = new MathTeamDTO(assignment.getMathTeam());
        this.owner = assignment.getOwner().getId();
        this.questions = assignment.getQuestions().stream().map(QuestionStudentDTO::new).collect(Collectors.toSet());
        this.amountOfQuestions = assignment.getAmountOfQuestions();
        this.totalPoints = assignment.getTotalPoints();
    }
}

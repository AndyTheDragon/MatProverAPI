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
public class AssignmentOutputDTO
{
    private Integer id;
    private String introText;
    private MathTeamSimpleDTO mathTeam; //only used for output, should be empty on input ?
    private Set<QuestionStudentDTO> questions; //only used for output, should be empty on input
    private Integer amountOfQuestions;
    private Integer totalPoints;

    public AssignmentOutputDTO(Assignment assignment)
    {
        this.id = assignment.getId();
        this.introText = assignment.getIntroText();
        this.mathTeam = new MathTeamSimpleDTO(assignment.getMathTeam());
        this.questions = assignment.getQuestions().stream().map(QuestionStudentDTO::new).collect(Collectors.toSet());
        this.amountOfQuestions = assignment.getAmountOfQuestions();
        this.totalPoints = assignment.getTotalPoints();
    }
}

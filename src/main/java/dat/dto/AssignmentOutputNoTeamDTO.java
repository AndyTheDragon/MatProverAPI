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
public class AssignmentOutputNoTeamDTO
{
    private Integer id;
    private String introText;
    private Set<QuestionStudentDTO> questions;
    private Integer amountOfQuestions;
    private Integer totalPoints;

    public AssignmentOutputNoTeamDTO(Assignment assignment)
    {
        this.id = assignment.getId();
        this.introText = assignment.getIntroText();
        this.questions = assignment.getQuestions().stream().map(QuestionStudentDTO::new).collect(Collectors.toSet());
        this.amountOfQuestions = assignment.getAmountOfQuestions();
        this.totalPoints = assignment.getTotalPoints();
    }
}

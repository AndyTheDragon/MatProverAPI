package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.Question;
import dat.entities.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties
public class MathTeamDTO
{
    private Integer id;
    @JsonProperty("desc")
    private String description;
    private Set<Assignment> assignments;
    private UserAccount owner;
    private Set<Question> questions;
    @JsonProperty("used_examquestions")
    private List<Question> usedExamQuestions;
    private String level;

    public MathTeamDTO(MathTeam mathTeam)
    {
        this.id = mathTeam.getId();
        this.description = mathTeam.getDescription();
        this.assignments = mathTeam.getAssignments();
        this.owner = mathTeam.getOwner();
        this.questions = mathTeam.getQuestions();
    }
}

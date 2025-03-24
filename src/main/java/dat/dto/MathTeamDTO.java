package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MathTeamDTO
{
    @JsonIgnore
    private Long id;
    private UserAccount owner;
    private String desc;
    private String[] usedExamQuestions;
    private String[] aassignments;
}

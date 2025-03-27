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
public class AssignmentInputDTO
{
    private Integer Id;
    private String introText;
    private MathTeamDTO mathTeam;

}

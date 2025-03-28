
package dat.dto;

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
    private Integer id;
    private String introText;
    private MathTeamSimpleDTO mathTeam;

}

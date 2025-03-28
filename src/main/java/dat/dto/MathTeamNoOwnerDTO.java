package dat.dto;

import dat.entities.MathTeam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MathTeamNoOwnerDTO
{
    private Integer id;
    private String description;

    public MathTeamNoOwnerDTO(MathTeam mathTeam)
    {
        this.id = mathTeam.getId();
        this.description = mathTeam.getDescription();
    }
}

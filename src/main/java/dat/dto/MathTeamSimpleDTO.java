package dat.dto;

import dat.entities.MathTeam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MathTeamSimpleDTO
{
    private Integer id;
    private String description;
    private OwnerDTO owner;

    public MathTeamSimpleDTO(MathTeam mathTeam)
    {
        this.id = mathTeam.getId();
        this.description = mathTeam.getDescription();
        this.owner = new OwnerDTO(mathTeam.getOwner());
    }
}

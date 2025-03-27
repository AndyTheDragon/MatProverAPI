package dat.dto;

import dat.entities.MathTeam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MathTeamFullDTO
{
    private Integer id;
    private String description;
    private OwnerDTO owner;
    private Set<AssignmentOutputDTO> assignments;

    public MathTeamFullDTO(MathTeam mathTeam)
    {
        this.id = mathTeam.getId();
        this.description = mathTeam.getDescription();
        this.assignments = mathTeam.getAssignments().stream().map(AssignmentOutputDTO::new).collect(Collectors.toSet());
        this.owner = new OwnerDTO(mathTeam.getOwner());
    }
}

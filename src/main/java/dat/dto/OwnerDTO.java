package dat.dto;

import dat.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO
{
    private Integer id;
    private String name;
    private String workplace;
    private String email;

    public OwnerDTO(UserAccount user)
    {
        this.id = user.getId();
        this.name = user.getName();
        this.workplace = user.getWorkplace();
        this.email = user.getEmail();
    }
}

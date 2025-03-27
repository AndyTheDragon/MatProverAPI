package dat.dto;

import dat.entities.UserAccount;

public class OwnerDTO
{
    private Integer id;
    private String name;
    private String school;
    private String email;

    public OwnerDTO(UserAccount user)
    {
        this.id = user.getId();
        this.name = user.getName();
        this.school = user.getWorkplace();
        this.email = user.getEmail();
    }
}

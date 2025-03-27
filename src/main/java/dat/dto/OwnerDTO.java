package dat.dto;

import dat.entities.UserAccount;

// OwnerDTO skal indeholde id, navn, skole og email. Den skal have en constructor som tager et UserAccount objekt ind
// og sætter de relevante attributter. Plan: OwnerDTO og så test. Herefter UserAccount (SecurityController)
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

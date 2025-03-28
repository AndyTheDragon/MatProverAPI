package dat.dto;

import dat.entities.UserAccount;

import java.util.Set;
import java.util.stream.Collectors;

public class UserAccountDTO
{
    private final Integer id;
    private final String name;
    private final String email;
    private final String workplace;
    private final String uniLogin;
    private final String password;
    private final Set<MathTeamNoOwnerDTO> mathTeams;

    public UserAccountDTO(UserAccount userAccount)
    {
       this.id = userAccount.getId();
       this.name = userAccount.getName();
       this.email = userAccount.getEmail();
       this.workplace = userAccount.getWorkplace();
       this.uniLogin = userAccount.getUniLogin();
       this.password = userAccount.getPassword();
       this.mathTeams = userAccount.getMathTeams().stream().map(MathTeamNoOwnerDTO::new).collect(Collectors.toSet());
    }
}

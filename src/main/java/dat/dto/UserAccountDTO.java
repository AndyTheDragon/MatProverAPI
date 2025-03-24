package dat.dto;

import dat.entities.Assignment;
import dat.entities.MathTeam;
import dat.entities.UserAccount;

import java.util.Set;

public class UserAccountDTO
{
    private Integer id;
    private String name;
    private String email;
    private String workplace;
    private String uniLogin;
    private String password;
    private Set<Role> roles;
    private Set<Assignment> assignments;
    private MathTeam mathTeams;

    public UserAccountDTO(UserAccount userAccount)
    {
       this.id = userAccount.getId();
       this.name = userAccount.getName();
       this.email = userAccount.getEmail();
       this.workplace = userAccount.getWorkplace();
       this.uniLogin = userAccount.getUniLogin();
       this.password = userAccount.getPassword();
       this.roles = userAccount.getRoles();
       this.assignments = userAccount.getAssignments();
       this.mathTeams = userAccount.getMathTeams();
    }
}

package dat.entities;

import dat.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAccount
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String name;
    private String email;
    private String workplace;
    private String uniLogin;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();

    @OneToMany
    private Set<Assignment> assignments = new HashSet<>();

    @OneToMany
    private Set<MathTeam> mathTeams = new HashSet<>();

    public UserAccount(String name, String email, String workplace, String uniLogin, String password)
    {
        this.name = name;
        this.email = email;
        this.workplace = workplace;
        this.uniLogin = uniLogin;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public UserAccount(Integer id, String name, String email, String workplace, String uniLogin, String password, Set<Assignment> assignments)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.workplace = workplace;
        this.uniLogin = uniLogin;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.assignments = assignments;
    }

    public UserAccount(String name, String email, String workplace, String uniLogin, String password, Set<Roles> roles, Set<Assignment> assignments)
    {
        this.name = name;
        this.email = email;
        this.workplace = workplace;
        this.uniLogin = uniLogin;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.roles = roles;
        this.assignments = assignments;
    }

    public UserAccount(String uniLogin, String userPass)
    {
        this.uniLogin = uniLogin;
        this.password = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public Set<String> getRolesAsString()
    {
        return roles.stream().map(Roles::toString).collect(Collectors.toSet());
    }


    public boolean verifyPassword(String pw)
    {
        return BCrypt.checkpw(pw, this.password);
    }


    public void addRole(Roles role)
    {
        if (role != null)
        {
            roles.add(role);
        }
    }

    public void removeRole(Roles role)
    {
        roles.remove(role);
    }

    public void removeRole(String roleName)
    {
        //roles.remove(Roles.valueOf(roleName.toUpperCase()));
        roles.removeIf(r -> r.toString().equals(roleName));
    }


}

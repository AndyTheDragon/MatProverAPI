package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAccountDTO
{
    @JsonIgnore
    private Long id;
    private String name;
    private String email;
    private String skole;
    private String uniLogin;
    //private Role role;
}

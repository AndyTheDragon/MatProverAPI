package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionStudentDTO
{
    @JsonIgnore
    private Long id;
    private String exerciseText;
    private Long point;
    private String billedeUrl;

}

package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dat.dto.MathTeamSimpleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class MathTeam
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "math_team_id", nullable = false)
    private Integer id;
    private String description;
    @JsonBackReference
    @ManyToOne
    private UserAccount owner;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER)
    private final Set<Assignment> assignments = new HashSet<>();


    public MathTeam(MathTeamSimpleDTO mathTeamDTO)
    {
        this.description = mathTeamDTO.getDescription();
    }

    public MathTeam(String description)
    {
        this.description = description;
    }

    public MathTeam(String description,  UserAccount owner)
    {
        this.description = description;
        owner.addMathTeam(this);
    }

    public void addAssignment(Assignment assignment)
    {
        if (assignment != null)
        {
            assignments.add(assignment);
            assignment.setMathTeam(this);
        }
    }

    public void removeAssignment(Assignment assignment)
    {
        if (assignment != null)
        {
            assignments.remove(assignment);
            assignment.setMathTeam(null);
        }
    }

    public void setOwner(UserAccount owner)
    {
        this.owner = owner;
    }

    public Set<Question> getQuestions()
    {
        return assignments.stream()
                .flatMap(assignment -> assignment.getQuestions().stream())
                .collect(Collectors.toSet());
    }
}

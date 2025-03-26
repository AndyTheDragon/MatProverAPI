package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dat.dto.MathTeamDTO;
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

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Assignment> assignments = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    private UserAccount owner;

    public MathTeam(MathTeamDTO mathTeamDTO)
    {
        this.description = mathTeamDTO.getDescription();
        this.assignments = mathTeamDTO.getAssignments().stream().map(Assignment::new).collect(Collectors.toSet());
    }
    public MathTeam(String description)
    {
        this.description = description;
    }

    public MathTeam(String description, Set<Assignment> assignments, UserAccount owner)
    {
        this.description = description;
        this.assignments = assignments;
        this.owner = owner;
    }

    public void addAssignment(Assignment assignment)
    {
        if (assignment != null)
        {
            assignments.add(assignment);
            assignment.setMathTeam(this);
            assignment.setOwner(owner);
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

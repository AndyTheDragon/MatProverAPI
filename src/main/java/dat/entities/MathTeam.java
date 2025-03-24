package dat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

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

    @OneToMany
    private Set<Assignment> assignments;

    @ManyToOne
    private UserAccount owner;

    @ManyToMany
    private Set<Question> questions;

    //TODO: Add constructor(s)

}

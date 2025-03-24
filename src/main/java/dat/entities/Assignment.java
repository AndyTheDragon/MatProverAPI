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
public class Assignment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id", nullable = false)
    private Integer id;
    private String introText;

    @ManyToOne
    private MathTeam mathTeam;

    @ManyToOne
    private UserAccount owner;

    @ManyToMany
    private Set<Question> questions;


    //TODO: Add the follwing methods; numberOfQuestionsInAssignment, totalPointsInAssignment.
    //TODO: Add constructor(s)
}

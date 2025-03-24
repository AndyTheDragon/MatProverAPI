package dat.entities;

import dat.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Integer id;
    private LocalDate termDate;
    private int year;
    private String author;
    private int points; //Har kaldt denne her points da point lød mærkeligt.
    private int questionNumber;
    private String questionText;
    private String pictureURL;

    @ElementCollection
    private List<String> level = new ArrayList<>(Arrays.asList("A", "B", "C", null));
    @ElementCollection
    private List<Integer> testForm = new ArrayList<>(Arrays.asList(1, 2, null)); //Har kaldt denne for testForm istedet for delprøve. Den kan vi lige vende sammen.
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();

    @ManyToMany
    private Set<Assignment> assignments;

    @ManyToMany
    private Set<MathTeam> mathTeams;

}

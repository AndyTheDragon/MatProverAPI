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
    @Column(nullable = false, unique = true)
    private int questionNumber;
    private String questionText;
    private String pictureURL;
    private String category;

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


    public Question(LocalDate termDate, int year, String author, int points, int questionNumber, String questionText)
    {
        this.termDate = termDate;
        this.year = year;
        this.author = author;
        this.points = points;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
    }

    public Question(LocalDate termDate, int year, String author, int points, int questionNumber, String questionText, String pictureURL)
    {
        this.termDate = termDate;
        this.year = year;
        this.author = author;
        this.points = points;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.pictureURL = pictureURL;
    }

    public Question(LocalDate termDate, int year, String author, int points, String questionText, String pictureURL, List<String> level, List<Integer> testForm)
    {
        this.termDate = termDate;
        this.year = year;
        this.author = author;
        this.points = points;
        this.questionText = questionText;
        this.pictureURL = pictureURL;
        this.level = level;
        this.testForm = testForm;
    }

    public Question(LocalDate termDate, int year, String author, int points, String questionText, String pictureURL, List<String> level, List<Integer> testForm, Set<Roles> roles, Set<Assignment> assignments, Set<MathTeam> mathTeams)
    {
        this.termDate = termDate;
        this.year = year;
        this.author = author;
        this.points = points;
        this.questionText = questionText;
        this.pictureURL = pictureURL;
        this.level = level;
        this.testForm = testForm;
        this.roles = roles;
        this.assignments = assignments;
        this.mathTeams = mathTeams;
    }
}

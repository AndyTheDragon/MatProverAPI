package dat.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dto.QuestionDTO;
import dat.enums.TestFormat;
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
@JsonIgnoreProperties
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate termDate;
    private int year;
    private String author;
    private int points;
    @Column(nullable = false, unique = true)
    @JsonProperty("questionnumber")
    private int questionNumber;
    @JsonProperty("questiontext")
    @Column(columnDefinition = "TEXT")
    private String questionText;
    @JsonProperty("pictureurl")
    private String pictureURL;
    private String category;
    @JsonProperty("licens")
    private String license;
    private String level;

    @Enumerated(EnumType.STRING)
    @JsonProperty("testformat")
    private TestFormat testFormat;

    @ManyToMany
    private Set<Assignment> assignments;

    @ManyToMany
    private Set<MathTeam> mathTeams;


    public Question(QuestionDTO questionDTO)
    {
        this.termDate = questionDTO.getTermDate();
        this.year = questionDTO.getYear();
        this.author = questionDTO.getAuthor();
        this.points = questionDTO.getPoints();
        this.questionNumber = questionDTO.getQuestionNumber();
        this.questionText = questionDTO.getQuestionText();
        this.pictureURL = questionDTO.getPictureURL();
        this.category = questionDTO.getCategory();
        this.license = questionDTO.getLicense();
        this.level = questionDTO.getLevel();
        this.testFormat = questionDTO.getTestFormat();
    }
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

    public Question(LocalDate termDate, int year, String author, int points, String questionText, String pictureURL, String level, String license, TestFormat testFormat)
    {
        this.termDate = termDate;
        this.year = year;
        this.author = author;
        this.points = points;
        this.questionText = questionText;
        this.pictureURL = pictureURL;
        this.license = license;
        this.level = level;
        this.testFormat = testFormat;
    }

    public Question(LocalDate termDate, int year, String author, int points, String questionText, String pictureURL, String level, String license, TestFormat testFormat, Set<Assignment> assignments, Set<MathTeam> mathTeams)
    {
        this.termDate = termDate;
        this.year = year;
        this.author = author;
        this.points = points;
        this.questionText = questionText;
        this.pictureURL = pictureURL;
        this.license = license;
        this.level = level;
        this.testFormat = testFormat;
        this.assignments = assignments;
        this.mathTeams = mathTeams;
    }

    public void addAssignment(Assignment assignment)
    {
        if (assignment != null)
        {
            assignments.add(assignment);
            assignment.addQuestion(this);
        }
    }

    public void removeAssignment(Assignment assignment)
    {
        if (assignment != null)
        {
            assignments.remove(assignment);
            assignment.removeQuestion(this);
        }
    }

    public void addMathTeam(MathTeam mathTeam)
    {
        if (mathTeam != null)
        {
            mathTeams.add(mathTeam);
            mathTeam.addQuestion(this);
        }
    }

    public void removeMathTeam(MathTeam mathTeam)
    {
        if (mathTeam != null)
        {
            mathTeams.remove(mathTeam);
            mathTeam.removeQuestion(this);
        }
    }

    public void setMathTeams(Set<MathTeam> mathTeams)
    {
        this.mathTeams = mathTeams;
    }
}

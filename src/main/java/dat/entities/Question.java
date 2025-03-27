package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Integer id;
    private LocalDate termDate;
    private int year;
    private String author;
    private int points;
    @Column(nullable = false)
    private int questionNumber;
    @Column(nullable = false)
    private String questionText;
    private String pictureURL;
    private String category;
    private String license;
    private String level;

    @Enumerated(EnumType.STRING)
    private TestFormat testFormat;

    @JsonIgnore
    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Assignment> assignments;

    public Question(int year, String author, int points, int questionNumber, String questionText, String pictureURL, String category, String license, String level, TestFormat testFormat)
    {
        this.year = year;
        this.author = author;
        this.points = points;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.pictureURL = pictureURL;
        this.category = category;
        this.license = license;
        this.level = level;
        this.testFormat = testFormat;
    }

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

    public Question(LocalDate termDate, int year, String author, int points, String questionText, String pictureURL, String level, String license, TestFormat testFormat, Set<Assignment> assignments)
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

    public void setTermDate(LocalDate termDate)
    {
        this.termDate = termDate;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public void setQuestionNumber(int questionNumber)
    {
        this.questionNumber = questionNumber;
    }

    public void setQuestionText(String questionText)
    {
        this.questionText = questionText;
    }

    public void setPictureURL(String pictureURL)
    {
        this.pictureURL = pictureURL;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public void setTestFormat(TestFormat testFormat)
    {
        this.testFormat = testFormat;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
}

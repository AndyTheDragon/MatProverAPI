package dat.dto;

import dat.entities.Question;
import dat.enums.TestFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionDTO
{
    private Integer id;
    private LocalDate termDate;
    private Integer year;
    private String author;
    private Integer points;
    private Integer questionNumber;
    private String questionText;
    private String pictureURL;
    private String category;
    private String license;
    private String level;
    private TestFormat testFormat;

    public QuestionDTO(Question question)
    {
        this.id = question.getId();
        this.termDate = question.getTermDate();
        this.year = question.getYear();
        this.author = question.getAuthor();
        this.points = question.getPoints();
        this.questionNumber = question.getQuestionNumber();
        this.questionText = question.getQuestionText();
        this.pictureURL = question.getPictureURL();
        this.category = question.getCategory();
        this.license = question.getLicense();
        this.level = question.getLevel();
        this.testFormat = question.getTestFormat();
    }
}

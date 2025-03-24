package dat.dto;

import dat.entities.Question;

import java.time.LocalDate;

public class QuestionDTO
{
    private Integer id;
    private LocalDate termDate;
    private int year;
    private String author;
    private int points;
    private int questionNumber;
    private String questionText;
    private String pictureURL;

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
    }
}

package dat.dto;

import dat.entities.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionStudentDTO
{
    private Integer id;
    private String questionText;
    private Integer points;
    private String pictureURL;

    public QuestionStudentDTO(Question question)
    {
        this.id = question.getId();
        this.questionText = question.getQuestionText();
        this.points = question.getPoints();
        this.pictureURL = question.getPictureURL();
    }
}

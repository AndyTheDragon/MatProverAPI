package dat.controllers;

import dat.dto.QuestionDTO;
import dat.entities.Question;

import java.util.function.Consumer;

public interface IQuestionController {
    <T> void updateFieldIfNotNull(Consumer<T> setter, T value);
    void updateQuestionIfNotNull(QuestionDTO questionDTO, Question questionToUpdate);
}

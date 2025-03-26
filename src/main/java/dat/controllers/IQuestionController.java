package dat.controllers;

import java.util.function.Consumer;

public interface IQuestionController {
    <T> void updateFieldIfNotNull(Consumer<T> setter, T value);
}

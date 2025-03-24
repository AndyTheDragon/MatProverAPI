package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionDTO
{
    @JsonIgnore
    private Long id;
    private String niveau;
    private LocalDate termin;
    private Long year;
    private String[] category;
    private String licens;
    private String author;
    private Long point;
    private Long opgaveNummer;
    private String opgaveTekst;
    private String billedeUrl;
}

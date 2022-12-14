package ru.pfr.overpayments.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateDeserializerRuAndEn;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateSerializerRu;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class FIODto {

    @NotNull(message = "surname cannot be null")
    private String surname;
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "patronymic cannot be null")
    private String patronymic;
    @NotNull(message = "dateOfBirth cannot be null")
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate dateOfBirth;

}

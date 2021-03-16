package ru.psu.job.search.web.dto;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Полное DTO вакансии", example =
        """
            {
              "id": 1,
              "name": "Junior React Developer",
              "city": "Perm",
              "requirements": "Live in Perm",
              "salaryFrom": 80000,
              "salaryTo": 150000
            }
        """
)
@Data
public class VacancyDto {

    @Schema(description = "Идентификатор вакансии")
    Long id;

    @Schema(description = "Наименование вакансии")
    @NotNull
    String name;

    @Schema(description = "Город")
    String city;

    @Schema(description = "Требования")
    String requirements;

    @Schema(description = "Нижняя граница вилки з/п (руб.)")
    Long salaryFrom;

    @Schema(description = "Верхняя граница вилки з/п (руб.)")
    Long salaryTo;

}

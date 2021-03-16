package ru.psu.job.search.web.dto;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Полное DTO вакансии",
        example =
            "{\n" +
            "  \"id\": 1,\n" +
            "  \"name\": \"Junior React Developer\",\n" +
            "  \"city\": \"Perm\",\n" +
            "  \"requirements\": \"Live in Perm\",\n" +
            "  \"salaryFrom\": 80000,\n" +
            "  \"salaryTo\": 150000\n" +
            "}\n"
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

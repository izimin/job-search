package ru.psu.job.search.web.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class VacancyDto {

    Long id;

    @NotNull
    String name;

    String city;

    String requirements;

    Long salaryFrom;

    Long salaryTo;

}

package ru.psu.job.search.web.request;

import lombok.Data;

@Data
public class VacancySearchRequest {

    String search;
    String name;
    String city;

}

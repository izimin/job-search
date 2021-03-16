package ru.psu.job.search.service.vacancy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.psu.job.search.model.Vacancy;
import ru.psu.job.search.service.specification.SpecificationBuilder;

public interface VacancyService {

    Page<Vacancy> findAll(SpecificationBuilder<Vacancy> specification, Pageable pageable);

    Vacancy findById(Long vacancyId);

    Vacancy create(Vacancy vacancy);

    Vacancy update(Vacancy vacancy);

    void delete(Long id);

}

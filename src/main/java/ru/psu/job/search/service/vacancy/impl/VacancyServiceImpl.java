package ru.psu.job.search.service.vacancy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.psu.job.search.model.Vacancy;
import ru.psu.job.search.repository.VacancyRepository;
import ru.psu.job.search.service.mapper.VacancyUpdateMapper;
import ru.psu.job.search.service.specification.SpecificationBuilder;
import ru.psu.job.search.service.vacancy.VacancyService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository repository;
    private final VacancyUpdateMapper mapper;

    @Override
    public Page<Vacancy> findAll(SpecificationBuilder<Vacancy> specification, Pageable pageable) {
        return repository.findAll(specification.build(), pageable);
    }

    @Override
    public Vacancy findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не должен быть null");
        }

        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Вакансия с идентификатором %d не найдена", id)));
    }

    @Override
    public Vacancy create(Vacancy model) {
        if (model.getId() != null) {
            throw new IllegalArgumentException("ID должен быть null");
        }

        return repository.save(model);
    }

    @Override
    public Vacancy update(Vacancy model) {
        Vacancy vacancy = findById(model.getId());

        mapper.update(model, vacancy);

        return repository.save(vacancy);
    }

    @Override
    public void delete(Long id) {
        Vacancy vacancy = findById(id);

        repository.deleteById(id);
    }
}

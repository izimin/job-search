package ru.psu.job.search.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.psu.job.search.model.Vacancy;
import ru.psu.job.search.service.specification.vacancy.VacancySpecificationBuilder;
import ru.psu.job.search.service.vacancy.VacancyService;
import ru.psu.job.search.web.dto.PageDto;
import ru.psu.job.search.web.dto.VacancyDto;
import ru.psu.job.search.web.mapper.VacancyMapper;
import ru.psu.job.search.web.request.VacancySearchRequest;
import ru.psu.job.search.web.response.SuccessResponse;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("vacancies")
public class VacancyController {

    private final VacancyService service;
    private final VacancyMapper mapper;

    @GetMapping
    public ResponseEntity<PageDto<VacancyDto>> findAll(@Valid VacancySearchRequest request, Pageable pageable) {
        final VacancySpecificationBuilder specification = VacancySpecificationBuilder.builder()
                .search(request.getSearch())
                .name(request.getName())
                .city(request.getCity())
                .build();
        return SuccessResponse.of(mapper.toPageDto(service.findAll(specification, pageable))).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<VacancyDto> findById(@PathVariable Long id) {
        final Vacancy vacancy = service.findById(id);

        return SuccessResponse.of(mapper.toDto(vacancy)).build();
    }

    @PostMapping
    public ResponseEntity<VacancyDto> create(@Valid @RequestBody VacancyDto vacancyDto) {
        final Vacancy vacancy = mapper.toEntity(vacancyDto);

        final Vacancy actual = service.create(vacancy);

        return SuccessResponse.of(mapper.toDto(actual)).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<VacancyDto> update(@PathVariable Long id, @Valid @RequestBody VacancyDto vacancyDto) {
        final Vacancy vacancy = mapper.toEntity(vacancyDto);

        vacancy.setId(id);

        final Vacancy actual = service.update(vacancy);

        return SuccessResponse.of(mapper.toDto(actual)).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return SuccessResponse.ofEmpty().build();
    }
}

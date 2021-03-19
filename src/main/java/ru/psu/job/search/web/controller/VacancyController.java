package ru.psu.job.search.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "VacancyController", description = "API по работе с вакансиями")
@RequiredArgsConstructor
@RestController
@RequestMapping("vacancies")
public class VacancyController {

    private final VacancyService service;
    private final VacancyMapper mapper;

    @Operation(summary = "Список вакансий", description = "Выдает список вакансий в формате страниц с поиском и сортировкой")
    @GetMapping
    public ResponseEntity<PageDto<VacancyDto>> findAll(@Valid @Parameter(description = "Параметры поиска") VacancySearchRequest request,
                                                       @Parameter(description = "Параметры пагинации, если нужно") Pageable pageable) {
        final VacancySpecificationBuilder specification = VacancySpecificationBuilder.builder()
                .search(request.getSearch())
                .name(request.getName())
                .city(request.getCity())
                .build();
        return SuccessResponse.of(mapper.toPageDto(service.findAll(specification, pageable))).build();
    }

    @Operation(summary = "Получение вакансии по идентификатору", description = "Выдает объект вакансии со всеми ее атрибутами")
    @GetMapping("{id}")
    public ResponseEntity<VacancyDto> findById(@Parameter(description = "Идентификатор вакансии") @PathVariable Long id) {
        final Vacancy vacancy = service.findById(id);

        return SuccessResponse.of(mapper.toDto(vacancy)).build();
    }

    @Operation(summary = "Создание вакансии", description = "Создает вакансию и возвращает созданную вакансию с проставленным идентификатором")
    @PostMapping
    public ResponseEntity<VacancyDto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Полный объект вакансии с заполненными полями, id должно быть null")
            @Valid @RequestBody VacancyDto vacancyDto) {
        final Vacancy vacancy = mapper.toEntity(vacancyDto);

        final Vacancy actual = service.create(vacancy);

        return SuccessResponse.of(mapper.toDto(actual)).build();
    }

    @Operation(summary = "Обновление вакансии", description = "Обновляет вакансию и возвращает обновленную версию вакансии")
    @PutMapping("{id}")
    public ResponseEntity<VacancyDto> update(@Parameter(description = "Идентификатор вакансии") @PathVariable Long id,
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Полный объект вакансии с обновленными полями, id может быть null")
                                             @Valid @RequestBody VacancyDto vacancyDto) {
        final Vacancy vacancy = mapper.toEntity(vacancyDto);

        vacancy.setId(id);

        final Vacancy actual = service.update(vacancy);

        return SuccessResponse.of(mapper.toDto(actual)).build();
    }

    @Operation(summary = "Удаление вакансии", description = "Удаляет вакансию без возможности восстановления")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Идентификатор вакансии") @PathVariable Long id) {
        service.delete(id);

        return SuccessResponse.ofEmpty().build();
    }
}

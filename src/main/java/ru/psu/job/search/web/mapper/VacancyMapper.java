package ru.psu.job.search.web.mapper;

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import ru.psu.job.search.web.dto.PageDto;
import ru.psu.job.search.model.Vacancy;
import ru.psu.job.search.web.dto.VacancyDto;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    VacancyDto toDto(Vacancy entity);

    List<VacancyDto> toDto(Collection<Vacancy> entities);

    Vacancy toEntity(VacancyDto dto);

    default PageDto<VacancyDto> toPageDto(Page<Vacancy> page) {
        PageDto<VacancyDto> pageDto = new PageDto<>();
        pageDto.setContent(toDto(page.getContent()));
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setHasNext(page.hasNext());
        pageDto.setHasPrevious(page.hasPrevious());
        pageDto.setCurrentPage(page.getNumber());
        pageDto.setSize(page.getSize());
        pageDto.setNumberOfElements(page.getNumberOfElements());
        return pageDto;
    }
}

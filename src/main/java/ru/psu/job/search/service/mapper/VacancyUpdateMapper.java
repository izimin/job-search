package ru.psu.job.search.service.mapper;

import org.mapstruct.*;
import ru.psu.job.search.model.Vacancy;

@Mapper(componentModel = "spring")
public interface VacancyUpdateMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name")
    @Mapping(target = "city")
    @Mapping(target = "requirements")
    @Mapping(target = "salaryFrom")
    @Mapping(target = "salaryTo")
    void update(Vacancy source, @MappingTarget Vacancy target);

}

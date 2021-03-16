package ru.psu.job.search.service.specification.vacancy;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import ru.psu.job.search.model.Vacancy;
import ru.psu.job.search.service.specification.SpecificationBuilder;

@Data
@Builder
public class VacancySpecificationBuilder implements SpecificationBuilder<Vacancy> {

    private String search;
    private String name;
    private String city;

    @Override
    public Specification<Vacancy> build() {
        return Specification.where(specificationBySearch())
                .and(specificationByName())
                .and(specificationByCity());
    }

    private Specification<Vacancy> specificationBySearch() {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(search)) {
                return null;
            }

            search = "%" + search.trim().toLowerCase() + "%";

            return cb.or(cb.like(cb.lower(root.get("name")), search),
                    cb.like(cb.lower(root.get("city")), search),
                    cb.like(cb.lower(root.get("requirements")), search)
            );
        };
    }

    private Specification<Vacancy> specificationByName() {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(name)) {
                return null;
            }

            return cb.equal(cb.lower(root.get("name")), name.toLowerCase());
        };
    }

    private Specification<Vacancy> specificationByCity() {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(city)) {
                return null;
            }

            return cb.equal(cb.lower(root.get("city")), city.toLowerCase());
        };
    }
}

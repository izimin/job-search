package ru.psu.job.search.service.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {

    Specification<T> build();

}

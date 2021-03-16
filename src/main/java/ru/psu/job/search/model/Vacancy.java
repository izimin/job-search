package ru.psu.job.search.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vacancy")
public class Vacancy {

    @Id
    @SequenceGenerator(name = "vacancy_id_seq_gen", sequenceName = "vacancy_vacancy_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancy_id_seq_gen")
    @Column(name = "vacancy_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "salary_from")
    private Long salaryFrom;

    @Column(name = "salary_to")
    private Long salaryTo;

}

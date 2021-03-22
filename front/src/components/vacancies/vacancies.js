import React, {Component} from 'react';
import {Api} from '../../api/api';

import classes from "./vacancies.module.css";

class Vacancies extends Component {

    state = {
        vacancies: [],
        displayedVacancies: [],
        filters: {},
        cities: []
    }

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        if (this.props.history.location.state) {
            this.setState({
                vacancies: this.props.history.location.state,
                displayedVacancies: this.props.history.location.state
            })
        } else {
            this.getVacancies();
        }
    }

    async getVacancies() {
        try {
            const {data: {content}} = await Api.get('/job-search/api/vacancies');
            let cities = [];
            content.forEach(({city}) => {
                if (!cities.includes(city))
                    cities.push(city);
            });

            this.setState({
                vacancies: content,
                displayedVacancies: content,
                cities
            });

        } catch ({error}) {
            console.log(error);
        }
    }

    openVacancy(id) {
        const vacancy = this.state.vacancies.find(vac => vac.id === id);
        if (id) {
            this.props.history.push({
                pathname: `/vacancies/${id}`,
                state: {vacancies: this.state.vacancies, vacancy}
            })
        }
    }

    createVacancy() {
        this.props.history.push({
            pathname: '/vacancies/create',
            state: {vacancies: this.state.vacancies}
        })
    }

    handleFilter(filterName, value) {
        const filters = JSON.parse(JSON.stringify(this.state.filters));
        let displayedVacancies = JSON.parse(JSON.stringify(this.state.vacancies));

        if (filterName === 'salaryFrom' && (/^\d+$/.test(value) || value === '')) {

            if (value === ''){
                filters.salaryFrom = undefined;
            } else {
                filters[filterName] = value;
                displayedVacancies = displayedVacancies.filter(vac => +vac.salaryFrom >= +value);
            }

            if (filters.city) {
                displayedVacancies = displayedVacancies.filter(vac => vac.city === filters.city);
            }
        }

        if (filterName === 'city') {
            filters[filterName] = value;
            displayedVacancies = displayedVacancies.filter(vac => vac.city === filters.city);

            if (filters.salaryFrom) {
                displayedVacancies = displayedVacancies.filter(vac => +vac.salaryFrom >= +value);
            }
        }

        this.setState({
            displayedVacancies
        });

        this.setState({
            filters
        });
    }

    refreshFilters() {
        const displayedVacancies = this.state.vacancies;
        this.setState({
            filters: {},
            displayedVacancies
        });
    }

    render() {
        return (
            <div className={classes.container}>
                <button onClick={() => this.createVacancy()} className={classes.createVacancyButton}>Создать вакансию</button>
                <div className={classes.filters}>
                    <select
                        onChange={(evt) => this.handleFilter('city', evt.target.value)}
                        className={classes.cityFilter}
                        value={this.state.filters.city ? this.state.filters.city : "DEFAULT"}
                        readOnly
                    >
                        <option value="DEFAULT" disabled>Выберите город</option>
                        {this.state.cities.map((city, idx) =>
                            <option
                                key={idx}
                            >
                                {city}
                            </option>)}
                    </select>

                    <input
                        className={classes.salaryFromFilter}
                        placeholder="Мин. з/п"
                        onChange={({target: {value}}) => this.handleFilter('salaryFrom', value)}
                        value={this.state.filters && this.state.filters.salaryFrom ? this.state.filters.salaryFrom : ''}
                    />

                    <button
                        className={classes.refreshFiltersButton}
                        onClick={() => this.refreshFilters()}
                    >
                        Сбросить фильтры
                    </button>
                </div>
                {this.state.displayedVacancies.map(({id, city, name, requirements, salaryFrom, salaryTo}) => (
                    <div
                        key={id}
                        className={classes.vacancy}
                        onClick={() => this.openVacancy(id)}
                    >
                        <div className={classes.vacancyDescription}>
                            <div className={classes.vacancyName}>{name}</div>
                            <div className={classes.vacancyCity}>{city}</div>
                            <div className={classes.vacancyRequirements}>{requirements}</div>
                        </div>

                        <div className={classes.vacancyMoney}>
                            <div>{salaryFrom}</div>
                            <div>-</div>
                            <div>{salaryTo} руб.</div>
                        </div>
                    </div>
                ))}
            </div>
        );
    }
}

export default Vacancies;
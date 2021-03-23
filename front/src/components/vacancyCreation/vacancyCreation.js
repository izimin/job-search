import React, {Component} from 'react';

import {Api} from '../../api/api';

import classes from "./vacancyCreation.module.css";

class VacancyCreation extends Component {

    constructor(props) {
        super(props);
        this.state = this.props.history.location.state;
        this.state.vacancy = {};
    }

    async createVacancy() {

        if (this.state.vacancy.name &&
            this.state.vacancy.city &&
            this.state.vacancy.requirements &&
            this.state.vacancy.salaryFrom &&
            this.state.vacancy.salaryTo
        ) {
            const {data} = await Api.post('/job-search/api/vacancies', this.state.vacancy);
            const vacancies = JSON.parse(JSON.stringify(this.state.vacancies));
            vacancies.push(data);
            this.setState({
                vacancies
            });

            this.props.history.push({
                pathname: '/vacancies/',
                state: this.state.vacancies
            });
        }else {
            console.log('Введите все поля')
        }
    }

    changeVacancy(fieldName, value) {
        const vacancy = JSON.parse(JSON.stringify(this.state.vacancy));

        if (((fieldName === 'salaryFrom' || fieldName === 'salaryTo') && /^\d+$/.test(value)) || value === '') {
            vacancy[fieldName] = value;
        }

        if(fieldName !== 'salaryFrom' && fieldName !== 'salaryTo'){
            vacancy[fieldName] = value;
        }

        this.setState({
            vacancy
        });
    }

    goBackToVacancies() {
        this.props.history.push({
            pathname: '/vacancies/',
            state: this.state.vacancies
        });
    }

    render() {
        return (
            <div className={classes.container}>
                <input
                    className={classes.vacancyInput}
                    placeholder="Название вакансии"
                    title="Название вакансии"
                    value={this.state.vacancy.name ? this.state.vacancy.name : ''}
                    onChange={({target: {value}}) => this.changeVacancy('name', value)}
                />

                <input
                    className={classes.vacancyInput}
                    placeholder="Город"
                    title="Город"
                    value={this.state.vacancy.city ? this.state.vacancy.city : ''}
                    onChange={({target: {value}}) => this.changeVacancy('city', value)}
                />

                <textarea
                    className={classes.vacancyTextarea}
                    placeholder="Требования"
                    title="Требования"
                    value={this.state.vacancy.requirements ? this.state.vacancy.requirements : ''}
                    onChange={({target: {value}}) => this.changeVacancy('requirements', value)}
                />

                <input
                    className={classes.vacancyInput}
                    placeholder="Минимальные зарплатные ожидания"
                    title="Минимальная з/п"
                    value={this.state.vacancy.salaryFrom ? this.state.vacancy.salaryFrom : ''}
                    onChange={({target: {value}}) => this.changeVacancy('salaryFrom', value)}
                />

                <input
                    className={classes.vacancyInput}
                    placeholder="Максимальные зарплатные ожидания"
                    title="Максимальная з/п"
                    value={this.state.vacancy.salaryTo ? this.state.vacancy.salaryTo : ''}
                    onChange={({target: {value}}) => this.changeVacancy('salaryTo', value)}
                />

                <button className={classes.createButton} onClick={() => this.createVacancy()}>Создать</button>
                <button className={classes.backButton} onClick={() => this.goBackToVacancies()}>Назад</button>
            </div>
        )
    }

}

export default VacancyCreation;
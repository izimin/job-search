import React, {Component} from 'react';
import {Api} from '../../api/api';

import classes from './vacancy.module.css';

class Vacancy extends Component {

    constructor(props) {
        super(props);
        this.state = this.props.history.location.state;
    }

    componentDidMount() {
        this.setState({edit: false});
        this.setState({
            editedVacancy: {
                id: this.state.vacancy.id,
                city: this.state.vacancy.city,
                name: this.state.vacancy.name,
                requirements: this.state.vacancy.requirements,
                salaryFrom: this.state.vacancy.salaryFrom,
                salaryTo: this.state.vacancy.salaryTo
            }
        });
        this.setState({
            comments: []
        });

        this.fetchComments()
    }

    fetchComments() {
        Api.get('https://jsonplaceholder.typicode.com/comments').then(({data}) =>
            this.setState({
            comments: data
        }));
    }

    editVacancy(fieldName, value) {
        const editedVacancy = JSON.parse(JSON.stringify(this.state.editedVacancy));

        if (((fieldName === 'salaryFrom' || fieldName === 'salaryTo') && /^\d+$/.test(value)) || value === '') {
            editedVacancy[fieldName] = value;
        }

        if(fieldName !== 'salaryFrom' && fieldName !== 'salaryTo'){
            editedVacancy[fieldName] = value;
        }

        this.setState({
            editedVacancy
        })
    }

    goToVacancies() {
        this.props.history.push({
            pathname: '/vacancies/',
            state: this.state.vacancies
        });
    }

    async saveVacancy() {
        if (this.state.editedVacancy.name !== '' &&
            this.state.editedVacancy.city !== '' &&
            this.state.editedVacancy.requirements !== '' &&
            this.state.editedVacancy.salaryFrom !== '' &&
            this.state.editedVacancy.salaryTo !== ''
        ) {
            const vacancies = JSON.parse(JSON.stringify(this.state.vacancies));

            vacancies.forEach((el, idx) => {
                if (el.id === this.state.editedVacancy.id){
                    vacancies[idx] = JSON.parse(JSON.stringify(this.state.editedVacancy));
                }
            })

            this.setState({
                vacancies,
                vacancy: this.state.editedVacancy,
                edit: false
            })

            await Api.put(`/job-search/api/vacancies/${this.state.vacancy.id}`, this.state.vacancy);
        } else {
            console.log('Введите все поля');
        }
    }

    async deleteVacancy(id) {

        await Api.delete(`/job-search/api/vacancies/${id}`);

        const {data: {content}} = await Api.get('/job-search/api/vacancies');

        this.props.history.push({
            pathname: '/vacancies/',
            state: content
        });
    }

    render() {
        return (
            this.state.edit ?
                <div className={classes.container}>
                    <div className={classes.editingVacancy}>
                        <input
                            className={classes.vacancyInput}
                            type='text'
                            onChange={({target: {value}}) => this.editVacancy('name', value)}
                            value={this.state.editedVacancy.name}
                            placeholder="Введите название вакансии"
                            title="Название вакансии"
                        />
                        <input
                            className={classes.vacancyInput}
                            type='text'
                            value={this.state.editedVacancy.city}
                            onChange={({target: {value}}) => this.editVacancy('city', value)}
                            placeholder="Введите название города"
                            title="Город"
                        />
                        <textarea
                            className={classes.vacancyTextarea}
                            type='text'
                            value={this.state.editedVacancy.requirements}
                            onChange={({target: {value}}) => this.editVacancy('requirements', value)}
                            placeholder="Введите необходимые для работы требования"
                            title="Требования"
                        />
                        <input
                            className={classes.vacancyInput}
                            type='text'
                            value={this.state.editedVacancy.salaryFrom}
                            onChange={({target: {value}}) => this.editVacancy('salaryFrom', value)}
                            placeholder="Введите минимальные зарплатные ожидания в рублях"
                            title="Минимальная з/п"
                        />
                        <input
                            className={classes.vacancyInput}
                            type='text'
                            value={this.state.editedVacancy.salaryTo}
                            onChange={({target: {value}}) => this.editVacancy('salaryTo', value)}
                            placeholder="Введите максимальные зарплатные ожидания в рублях"
                            title="Максимальная з/п"
                        />
                    </div>
                    <button className={classes.saveButton} onClick={() => this.saveVacancy()}>Сохранить</button>
                    <button className={classes.backButton} onClick={() => this.setState({edit: false})}>Отмена</button>
                </div>
                :
                <div className={classes.container}>
                    <div className={classes.vacancy}>
                        <div className={classes.vacancyDescription}>
                            <div className={classes.vacancyName}>{this.state.vacancy.name}</div>
                            <div className={classes.vacancyCity}>{this.state.vacancy.city}.</div>
                            <div className={classes.vacancyRequirements}>{this.state.vacancy.requirements}</div>
                        </div>
                        <div className={classes.vacancyMoney}>
                            <div>{this.state.vacancy.salaryFrom}</div>
                            <div>-</div>
                            <div>{this.state.vacancy.salaryTo} руб.</div>
                        </div>
                    </div>
                    <button className={classes.editButton} onClick={() => this.setState({edit: true})}>Редактировать</button>
                    <button className={classes.deleteButton} onClick={() => this.deleteVacancy(this.state.vacancy.id)}>Удалить</button>
                    <button className={classes.backButton} onClick={() => this.goToVacancies()}>Назад</button>

                    <div className={classes.commentsTitle}>Комментарии</div>
                    {this.state.comments ? this.state.comments.map(({id, body, email, name}) => (
                        <div key={id} className={classes.comment}>
                            <div className={classes.commentName}>{name}</div>
                            <div className={classes.commentEmail}>{email}</div>
                            <div className={classes.commentBody}>{body}</div>
                        </div>
                    )):null}
                </div>

        )
    }
}

export default Vacancy;
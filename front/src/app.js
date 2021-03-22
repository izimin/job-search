import React from 'react';
import {Route, Switch, Redirect} from "react-router";

import Vacancies from "./components/vacancies/vacancies";
import Vacancy from "./components/vacancy/vacancy";
import VacancyCreation from "./components/vacancyCreation/vacancyCreation";

function App() {
    return (
        <Switch>
            <Route
                path='/vacancies/create'
                component={VacancyCreation}
                exact
            />

            <Route
                path='/vacancies/'
                component={Vacancies}
                exact
            />

            <Route
                path='/vacancies/:id'
                component={Vacancy}
                exact
            />

            <Redirect from='/' to='/vacancies/'/>
        </Switch>
    )
}

export default App;
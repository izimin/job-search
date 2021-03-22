import React from 'react';
import ReactDOM from 'react-dom';
import App from './app';

import './fonts/Oswald-ExtraLight.ttf';
import './fonts/Oswald-Light.ttf';
import './fonts/Oswald-Regular.ttf';
import './fonts/Oswald-SemiBold.ttf';

import './index.css';

import {BrowserRouter} from "react-router-dom";

ReactDOM.render(
    <BrowserRouter>
        <App />
    </BrowserRouter>,
  document.getElementById('root')
);
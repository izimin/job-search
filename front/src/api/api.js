import axios from "axios";

export const Api = axios.create({
    baseURL: 'http://185.233.80.25:7878',
});
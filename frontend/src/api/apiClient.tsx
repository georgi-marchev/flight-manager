import axios from 'axios';


const BASE_URL = '/api';

export const apiClient = axios.create({
    baseURL: BASE_URL,
    headers: { 'Content-Type': 'application/json' }
});

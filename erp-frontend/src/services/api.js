import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api', // Confirme se a porta e o prefixo batem com o backend
});

// Interceptor para adicionar o token JWT em todas as requisições
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token'); // Ou o nome da chave onde salvou o token no login
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

export default api;
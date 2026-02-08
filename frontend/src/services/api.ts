import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
})

export const logsAPI = {
  getAll: () => api.get('/logs'),
  search: (query: string) => api.get(`/logs/search?q=${query}`),
  getById: (id: string) => api.get(`/logs/${id}`),
}

export const alertsAPI = {
  getAll: () => api.get('/alerts'),
  getById: (id: string) => api.get(`/alerts/${id}`),
  create: (alert: unknown) => api.post('/alerts', alert),
  update: (id: string, alert: unknown) => api.put(`/alerts/${id}`, alert),
}

export const auditAPI = {
  getAll: () => api.get('/audit'),
  search: (query: string) => api.get(`/audit/search?q=${query}`),
}

export const authAPI = {
  login: (username: string, password: string) =>
    api.post('/auth/login', { username, password }),
  logout: () => api.post('/auth/logout'),
  getProfile: () => api.get('/auth/profile'),
}

export default api

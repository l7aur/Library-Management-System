const API_BASE_URL = 'http://localhost:8080';

const AUTHORS_ENDPOINT = `${API_BASE_URL}/authors`;
const BOOKS_ENDPOINT = `${API_BASE_URL}/books`;
const PUBLISHERS_ENDPOINT = `${API_BASE_URL}/publishers`;

export const AUTHORS_GET_ALL_ENDPOINT = `${AUTHORS_ENDPOINT}/all`;
export const BOOKS_GET_ALL_ENDPOINT = `${BOOKS_ENDPOINT}/all`;
export const PUBLISHERS_GET_ALL_ENDPOINT = `${PUBLISHERS_ENDPOINT}/all`;

export const PUBLISHERS_ADD_ENDPOINT = `${PUBLISHERS_ENDPOINT}/add`;
export const BOOKS_ADD_ENDPOINT = `${BOOKS_ENDPOINT}/add`;
export const AUTHORS_ADD_ENDPOINT = `${AUTHORS_ENDPOINT}/add`;


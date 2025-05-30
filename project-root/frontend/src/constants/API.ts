const API_BASE_URL = 'http://localhost:8080';

const AUTHORS_ENDPOINT = `${API_BASE_URL}/authors`;
const BOOKS_ENDPOINT = `${API_BASE_URL}/books`;
const PUBLISHERS_ENDPOINT = `${API_BASE_URL}/publishers`;
const APP_USERS_ENDPOINT = `${API_BASE_URL}/app_users`;

export const AUTHORS_GET_ALL_ENDPOINT = `${AUTHORS_ENDPOINT}/all`;
export const BOOKS_GET_ALL_ENDPOINT = `${BOOKS_ENDPOINT}/all`;
export const PUBLISHERS_GET_ALL_ENDPOINT = `${PUBLISHERS_ENDPOINT}/all`;
export const APP_USERS_GET_ALL_ENDPOINT = `${APP_USERS_ENDPOINT}/all`;

export const AUTHORS_ADD_ENDPOINT = `${AUTHORS_ENDPOINT}/add`;
export const BOOKS_ADD_ENDPOINT = `${BOOKS_ENDPOINT}/add`;
export const PUBLISHERS_ADD_ENDPOINT = `${PUBLISHERS_ENDPOINT}/add`;
export const APP_USERS_ADD_ENDPOINT = `${APP_USERS_ENDPOINT}/add`;

export const AUTHORS_DELETE_ENDPOINT = `${AUTHORS_ENDPOINT}/delete`;
export const BOOKS_DELETE_ENDPOINT = `${BOOKS_ENDPOINT}/delete`;
export const PUBLISHERS_DELETE_ENDPOINT = `${PUBLISHERS_ENDPOINT}/delete`;
export const APP_USER_DELETE_ENDPOINT = `${APP_USERS_ENDPOINT}/delete`;

export const AUTHORS_EDIT_ENDPOINT = `${AUTHORS_ENDPOINT}/edit`;
export const BOOKS_EDIT_ENDPOINT = `${BOOKS_ENDPOINT}/edit`;
export const PUBLISHERS_EDIT_ENDPOINT = `${PUBLISHERS_ENDPOINT}/edit`;
export const APP_USERS_UPDATE_ENDPOINT = `${APP_USERS_ENDPOINT}/edit`;

export const APP_USERS_LOGIN_ENDPOINT = `${APP_USERS_ENDPOINT}/login`;
export const APP_USERS_FILTER_ENDPOINT =`${APP_USERS_ENDPOINT}/filter`;
export const BOOKS_FILTER_ENDPOINT = `${BOOKS_ENDPOINT}/filter`;

export const CHANGE_PASSWORD_ENDPOINT = `${APP_USERS_ENDPOINT}/change_password`;
export const SEND_MAIL_ENDPOINT = `${API_BASE_URL}/sendMail`;

export const ORDER_BASE_URL = `${API_BASE_URL}/orders`;
export const PLACE_ORDER_ENDPOINT = `${ORDER_BASE_URL}/place`;
export const GET_ORDER_NUMBER = `${ORDER_BASE_URL}/get_order_number`;
export const SEND_CONF_EMAIL = `${ORDER_BASE_URL}/send_email`;
export const GET_ORDER_HISTORY = `${ORDER_BASE_URL}/order_history`;
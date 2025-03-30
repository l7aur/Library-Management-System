import {AppUserType} from "../types/AppUserType.tsx";
import {APP_USERS_GET_ALL_ENDPOINT} from "../constants/API.ts";

export const findAll = async (): Promise<AppUserType[]> => {
    const response = await fetch(APP_USERS_GET_ALL_ENDPOINT);
    return response.json();
};
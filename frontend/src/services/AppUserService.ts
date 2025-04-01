import {AppUserType} from "../types/AppUserType.tsx";
import {APP_USER_DELETE_ENDPOINT, APP_USERS_ADD_ENDPOINT, APP_USERS_GET_ALL_ENDPOINT} from "../constants/API.ts";

export const findAll = async (): Promise<AppUserType[]> => {
    const response = await fetch(APP_USERS_GET_ALL_ENDPOINT);
    return response.json();
};

export const add = async (newUser: AppUserType): Promise<AppUserType> => {
    const response = await fetch(APP_USERS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newUser),
    });
    return response.json();
}

export const del = async (ids: string[]): Promise<string> => {
    const response = await fetch(APP_USER_DELETE_ENDPOINT, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ids: ids}),
    });
    return response.text();
}
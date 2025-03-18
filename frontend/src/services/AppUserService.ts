import {AppUserT} from "../types/AppUserT.tsx";
import {APP_USERS_ADD_ENDPOINT, APP_USERS_GET_ALL_ENDPOINT} from "../constants/api.ts";

export const fetchAppUsers = async (): Promise<AppUserT[]> => {
    const response = await fetch(APP_USERS_GET_ALL_ENDPOINT);
    return response.json();
};

export const addAppUser = async (newAppUser: AppUserT): Promise<AppUserT> => {
    const response = await fetch(APP_USERS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newAppUser),
    });

    if (!response.ok) {
        throw new Error('Failed to add app user');
    }

    return response.json();
};
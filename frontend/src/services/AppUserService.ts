import {AppUserType} from "../types/AppUserType.tsx";
import {APP_USERS_ADD_ENDPOINT, APP_USERS_GET_ALL_ENDPOINT} from "../constants/API.ts";

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
    if(!response.ok) {
        throw new Error("Failed to add user!");
    }
    return response.json();
}
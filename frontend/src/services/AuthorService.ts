import {AuthorType} from "../types/AuthorType.tsx";
import {AUTHORS_GET_ALL_ENDPOINT} from "../constants/API.ts";

export const findAll = async (): Promise<AuthorType[]> => {
    const response = await fetch(AUTHORS_GET_ALL_ENDPOINT);
    return response.json();
};
import AuthorT from "../types/AuthorT.tsx";
import {AUTHORS_ADD_ENDPOINT, AUTHORS_GET_ALL_ENDPOINT} from "../constants/api.ts";

export const fetchAuthors = async (): Promise<AuthorT[]> => {
    const response = await fetch(AUTHORS_GET_ALL_ENDPOINT);
    return response.json();
};

export const addAuthor = async (newAuthor: AuthorT): Promise<AuthorT> => {
    const response = await fetch(AUTHORS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newAuthor),
    });

    if (!response.ok) {
        throw new Error('Failed to add author');
    }

    return response.json();
};
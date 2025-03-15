import AuthorT from "../types/AuthorT.tsx";
import {API_BASE_URL} from "../constants/api.ts";

const API_URL = `${API_BASE_URL}/authors`;

export const fetchAuthors = async (): Promise<AuthorT[]> => {
    const response = await fetch(API_URL);
    return response.json();
};

export const addAuthor = async (newAuthor: AuthorT): Promise<AuthorT> => {
    const response = await fetch(`${API_URL}/add`, {
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
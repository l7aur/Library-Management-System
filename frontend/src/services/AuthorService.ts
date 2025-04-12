import {AuthorType} from "../types/AuthorType.tsx";
import {
    AUTHORS_ADD_ENDPOINT,
    AUTHORS_DELETE_ENDPOINT,
    AUTHORS_EDIT_ENDPOINT,
    AUTHORS_GET_ALL_ENDPOINT,
} from "../constants/API.ts";

export const findAll = async (): Promise<AuthorType[]> => {
    const response = await fetch(AUTHORS_GET_ALL_ENDPOINT);
    return response.json();
};

export const add = async (newAuthor: AuthorType): Promise<AuthorType> => {
    const response = await fetch(AUTHORS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newAuthor),
    });
    return response.json();
}

export const del = async (ids: string[]): Promise<number> => {
    const response = await fetch(AUTHORS_DELETE_ENDPOINT, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ids: ids}),
    });
    return response.status;
}

export const update = async (author: AuthorType): Promise<AuthorType> => {
    const response = await fetch(AUTHORS_EDIT_ENDPOINT, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(author),
    });
    return response.json();
}
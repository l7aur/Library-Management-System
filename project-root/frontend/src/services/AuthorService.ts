import {AuthorType} from "../types/AuthorType.tsx";
import {
    AUTHORS_ADD_ENDPOINT,
    AUTHORS_DELETE_ENDPOINT,
    AUTHORS_EDIT_ENDPOINT,
    AUTHORS_GET_ALL_ENDPOINT,
} from "../constants/API.ts";

export const findAll = async (token: string | null): Promise<AuthorType[]> => {
    const headers: HeadersInit = {
        'Content-Type': 'application/json',
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`; // Or whatever your auth scheme is
    }

    const response = await fetch(AUTHORS_GET_ALL_ENDPOINT, {
        method: 'GET', // Explicitly set the method
        headers: headers,
    });

    if (!response.ok) {
        // Handle errors appropriately.  This is crucial.
        let errorMessage = `Failed to fetch users: ${response.status}`;
        try {
            const errorJson = await response.json();
            if (errorJson && errorJson.message) {
                errorMessage += ` - ${errorJson.message}`;
            }
        } catch (parseError) {
            console.log(parseError);
        }
        throw new Error(errorMessage);
    }

    return response.json();
};

export const add = async (newAuthor: AuthorType, token: string | null): Promise<AuthorType> => {
    const response = await fetch(AUTHORS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(newAuthor),
    });

    if(!response.ok)
        throw new Error("Unable to add author!");
    return response.json();
}

export const del = async (ids: string[], token: string | null): Promise<number> => {
    const response = await fetch(AUTHORS_DELETE_ENDPOINT, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ids: ids}),
    });
    return response.status;
}

export const update = async (author: AuthorType, token: string | null): Promise<AuthorType> => {
    const response = await fetch(AUTHORS_EDIT_ENDPOINT, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(author),
    });

    if(!response.ok)
        throw new Error("Unable to update author!");
    return response.json();
}
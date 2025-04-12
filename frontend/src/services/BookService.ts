import BookType from "../types/BookType.tsx";
import {
    BOOKS_ADD_ENDPOINT, BOOKS_DELETE_ENDPOINT, BOOKS_EDIT_ENDPOINT, BOOKS_FILTER_ENDPOINT,
    BOOKS_GET_ALL_ENDPOINT
} from "../constants/API.ts";

export const findAll = async (): Promise<BookType[]> => {
    const response = await fetch(BOOKS_GET_ALL_ENDPOINT);
    return response.json();
};

export const findFiltered = async (title?: string, stock?: number): Promise<BookType[]> => {
    try {
        let url = BOOKS_FILTER_ENDPOINT;
        const queryParams = [];
        if (title)
            queryParams.push(`title=${encodeURIComponent(title)}`);
        if (stock)
            queryParams.push(`stock=${encodeURIComponent(stock)}`);
        if (queryParams.length > 0)
            url += `?${queryParams.join('&')}`;

        const response = await fetch(url);

        if (!response.ok) {
            const errorData = await response.json();
            console.error('Error fetching filtered users:', errorData);
            throw new Error(`Failed to fetch users: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('An error occurred while fetching filtered users:', error);
        return [];
    }
};

export const add = async (newBook: BookType): Promise<BookType> => {
    const response = await fetch(BOOKS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newBook),
    });
    return response.json();
}

export const del = async (ids: string[]): Promise<number> => {
    const response = await fetch(BOOKS_DELETE_ENDPOINT, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ids: ids}),
    });
    return response.status;
}

export const update = async (book: BookType): Promise<BookType> => {
    const response = await fetch(BOOKS_EDIT_ENDPOINT, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(book),
    });
    return response.json();
}
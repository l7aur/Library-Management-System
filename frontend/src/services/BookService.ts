import {BookType, BookTypeImpl} from "../types/BookType.tsx";
import {
    BOOKS_ADD_ENDPOINT, BOOKS_DELETE_ENDPOINT, BOOKS_EDIT_ENDPOINT, BOOKS_FILTER_ENDPOINT,
    BOOKS_GET_ALL_ENDPOINT
} from "../constants/API.ts";
import BookTypeDTO from "../types/BookTypeDTO.tsx";

export const findAll = async (token: string | null): Promise<BookTypeImpl[]> => {
    const headers: HeadersInit = {
        'Content-Type': 'application/json',
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`; // Or whatever your auth scheme is
    }

    const response = await fetch(BOOKS_GET_ALL_ENDPOINT, {
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

export const findFiltered = async (token: string | null, title?: string, stock?: number): Promise<BookType[]> => {
    try {
        let url = BOOKS_FILTER_ENDPOINT;
        const queryParams = [];
        if (title)
            queryParams.push(`title=${encodeURIComponent(title)}`);
        if (stock)
            queryParams.push(`stock=${encodeURIComponent(stock)}`);
        if (queryParams.length > 0)
            url += `?${queryParams.join('&')}`;

        const headers: HeadersInit = {
            "Content-Type": "application/json",
        };
        if (token) {
            headers["Authorization"] = `Bearer ${token}`; // Add Authorization header if token exists
        }

        const response = await fetch(url, {
            headers: headers,
        });

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

export const add = async (newBook: BookTypeDTO, token: string | null): Promise<BookType> => {
    const response = await fetch(BOOKS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(newBook),
    });
    return response.json();
}

export const del = async (ids: string[], token: string | null): Promise<number> => {
    const response = await fetch(BOOKS_DELETE_ENDPOINT, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ids: ids}),
    });
    return response.status;
}

export const update = async (book: BookTypeDTO, token: string | null): Promise<BookType> => {
    const response = await fetch(BOOKS_EDIT_ENDPOINT, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(book),
    });
    return response.json();
}
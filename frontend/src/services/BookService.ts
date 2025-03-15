import { BookT } from "../types/BookT.tsx";
import {API_BASE_URL} from "../constants/api.ts";

const API_URL = `${API_BASE_URL}/books`;

export const fetchBooks = async (): Promise<BookT[]> => {
    const response = await fetch(API_URL);
    return response.json();
};

export const addBook = async (newBook: BookT): Promise<BookT> => {
    const response = await fetch(`${API_URL}/books/add`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newBook),
    });

    if (!response.ok) {
        throw new Error('Failed to add book');
    }

    return response.json();  // Return the added book data
};
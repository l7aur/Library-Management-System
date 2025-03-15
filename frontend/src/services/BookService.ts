import { BookT } from "../types/BookT.tsx";

const API_URL = "http://localhost:8080/books";

export const fetchBooks = async (): Promise<BookT[]> => {
    const response = await fetch(API_URL);
    return response.json();
};

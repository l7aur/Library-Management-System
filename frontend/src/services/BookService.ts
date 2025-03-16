import {API_BASE_URL} from "../constants/api.ts";
import {BookT} from "../types/BookT.tsx";

const API_URL = `${API_BASE_URL}/books`;

export const fetchBooks = async (): Promise<BookT[]> => {
    const response = await fetch(API_URL);
    return response.json();
};

interface BookJSON {
    id: string;
    isbn: string;
    publishYear: number;
    title: string;
    publisherId: string;
    authorIds: string[];
    price: number;
    stock: number;
}

export const addBook = async (newBook: BookT): Promise<BookT> => {
    const bookJSON: BookJSON = {
        id: newBook.id,
        isbn: newBook.isbn,
        publishYear: newBook.publishYear,
        title: newBook.title,
        publisherId: newBook.publisher.id,
        authorIds: newBook.authors.map((author) => author.id),
        price: newBook.price,
        stock: newBook.stock
    };
    const response = await fetch(`${API_URL}/books/add`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(bookJSON),
    });

    if (!response.ok) {
        throw new Error('Failed to add book');
    }

    return response.json();  // Return the added book data
};
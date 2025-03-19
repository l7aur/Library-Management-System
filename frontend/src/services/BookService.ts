import {BookT} from "../types/BookT.tsx";
import {BOOKS_ADD_ENDPOINT, BOOKS_GET_ALL_ENDPOINT} from "../constants/api.ts";


export const fetchBooks = async (): Promise<BookT[]> => {
    const response = await fetch(BOOKS_GET_ALL_ENDPOINT);
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
        authorIds: newBook.authors,
        price: newBook.price,
        stock: newBook.stock
    };
    console.log("Querying with: ", bookJSON);
    console.log("Querying with: ", newBook.authors);
    const response = await fetch(BOOKS_ADD_ENDPOINT, {
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
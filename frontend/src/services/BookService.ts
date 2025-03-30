import BookType from "../types/BookType.tsx";
import {BOOKS_GET_ALL_ENDPOINT} from "../constants/API.ts";

export const findAll = async (): Promise<BookType[]> => {
    const response = await fetch(BOOKS_GET_ALL_ENDPOINT);
    return response.json();
};
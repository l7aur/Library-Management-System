import { useState } from "react";
import {API_BASE_URL} from "../constants/api.ts";
import {BookT} from "../types/BookT.tsx";

const API_URL = `${API_BASE_URL}/books`;

const useAddBook = () => {
    const [newBook, setNewBook] = useState<BookT>({ id: "", isbn: "", publishYear: 0, title: "", publisher: {id: "", name: "", location: "", foundingYear: 0}, authors: [], price: 0.0, stock: 0 });
    const [isAdding, setIsAdding] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleAddBook = async () => {
        try {
            const response = await fetch(`${API_URL}/add`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(newBook),
            });

            if (!response.ok) throw new Error("Failed to add book");

            // Reset form
            setNewBook({ id: "", isbn: "", publishYear: 0, title: "", publisher:  {id: "", name: "", location: "", foundingYear: 0}, authors: [], price: 0.0, stock: 0 });
            setIsAdding(false);
        } catch {
            setError("Error adding book");
        }
    };

    return { isAdding, newBook, setNewBook, handleAddBook, setIsAdding, error };
};

export default useAddBook;

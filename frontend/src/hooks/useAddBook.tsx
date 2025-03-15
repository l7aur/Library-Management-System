import { useState } from "react";
import {API_BASE_URL} from "../constants/api.ts";

const API_URL = `${API_BASE_URL}/books`;

const useAddBook = () => {
    const [newBook, setNewBook] = useState({ id: "", isbn: "", publishYear: 0, title: "", publisherId: "", price: 0.0, stock: 0 });
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
            setNewBook({ id: "", isbn: "", publishYear: 0, title: "", publisherId: "", price: 0.0, stock: 0 });
            setIsAdding(false);
        } catch {
            setError("Error adding book");
        }
    };

    return { isAdding, newBook, setNewBook, handleAddBook, setIsAdding, error };
};

export default useAddBook;

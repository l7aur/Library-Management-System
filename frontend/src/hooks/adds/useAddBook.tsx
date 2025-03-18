import { useState } from "react";
import {BookT} from "../../types/BookT.tsx";
import {addBook} from "../../services/BookService.ts";

const useAddBook = () => {
    const [newBook, setNewBook] = useState<BookT>({ id: "", isbn: "", publishYear: 0, title: "", publisher: {id: "", name: "", location: "", foundingYear: 0}, authors: [], price: 0.0, stock: 0 });
    const [isAdding, setIsAdding] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleAddBook = async () => {
        try {
            const addedBook = await addBook(newBook);
            console.log("Book added:", addedBook);
            setIsAdding(false);
            setNewBook({ id: "", isbn: "", publishYear: 0, title: "", publisher:  {id: "", name: "", location: "", foundingYear: 0}, authors: [], price: 0.0, stock: 0 });

        } catch (err) {
            setError("Error adding publisher: " + err);
            console.error(err);
        }
    };

    return {
        isAdding,
        newBook: newBook,
        setNewBook: setNewBook,
        handleAddBook,
        setIsAdding,
        error };
};

export default useAddBook;

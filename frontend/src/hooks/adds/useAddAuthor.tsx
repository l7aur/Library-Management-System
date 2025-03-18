import {useState} from "react";
import AuthorT from "../../types/AuthorT.tsx";
import {addAuthor} from "../../services/AuthorService.ts";

const useAddAuthor = () => {
    const [isAdding, setIsAdding] = useState(false);
    const [newAuthor, setNewAuthor] = useState<AuthorT>({ id: "", firstName: "", lastName: "", alias: "", nationality: "", books: [] });
    const [error, setError] = useState<string | null>(null);

    const handleAddAuthor = async () => {
        try {
            const addedAuthor = await addAuthor(newAuthor);
            console.log("Author added:", addedAuthor);
            setIsAdding(false);
            setNewAuthor({ id: "", firstName: "", lastName: "", alias: "", nationality: "", books: [] });
        } catch (err) {
            setError("Error adding publisher: " + err);
            console.error(err);
        }
    };

    return {
        isAdding,
        newAuthor: newAuthor,
        setNewAuthor: setNewAuthor,
        handleAddAuthor,
        setIsAdding,
        error,
    };
};

export default useAddAuthor;

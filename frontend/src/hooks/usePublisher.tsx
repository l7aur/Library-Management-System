import {useState} from "react";
import PublisherT from "../types/PublisherT.tsx";
import {addPublisher, editPublisher} from "../services/PublisherService.ts";

const usePublisher = () => {
    const [isAdding, setIsAdding] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [newPublisher, setNewPublisher] = useState<PublisherT>({ id: "", name: "", location: "", foundingYear: 0, books: [] });
    const [error, setError] = useState<string | null>(null);

    const handleAddPublisher = async () => {
        try {
            const addedPublisher = await addPublisher(newPublisher);
            console.log("Publisher added:", addedPublisher);
            setIsAdding(false);
            setNewPublisher({ id: "", name: "", location: "", foundingYear: 0, books: [] }); // Clear the form fields
        } catch (err) {
            setError("Error adding publisher: " + err);
            console.error(err);
        }
    };

    const handleEditPublisher = async () => {
        try {
            const editedPublisher = await editPublisher(newPublisher);
            console.log("Publisher edited:", editedPublisher);
            setIsEditing(false);
            setNewPublisher({ id: "", name: "", location: "", foundingYear: 0, books: [] });
        } catch (err) {
            setError("Error editing publisher: " + err);
            console.error(err);
        }
    }

    return {
        isAdding,
        isEditing,
        newPublisher: newPublisher,
        setNewPublisher: setNewPublisher,
        handleAddPublisher,
        handleEditPublisher,
        setIsAdding,
        setIsEditing,
        error,
    };
};

export default usePublisher;

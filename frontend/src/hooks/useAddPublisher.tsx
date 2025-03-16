import {useState} from "react";
import PublisherT from "../types/PublisherT.tsx";
import {addPublisher} from "../services/PublisherService.ts";

const useAddPublisher = () => {
    const [isAdding, setIsAdding] = useState(false);
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

    return {
        isAdding,
        newPublisher: newPublisher,
        setNewPublisher: setNewPublisher,
        handleAddPublisher,
        setIsAdding,
        error,
    };
};

export default useAddPublisher;

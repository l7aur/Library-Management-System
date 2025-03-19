import React from "react";
import PublisherT from "../../types/PublisherT.tsx";

interface AddPublisherFormProps {
    command: string;
    newPublisher: PublisherT;
    selectedPublisher: PublisherT;
    setNewPublisher: React.Dispatch<React.SetStateAction<PublisherT>>;
    handleAddPublisher: () => void;
    handleEditPublisher: (editPublisher: PublisherT) => void;
    error: string | null;
}

const AddPublisherForm: React.FC<AddPublisherFormProps> = ({
                                                     command,
                                                     newPublisher,
                                                     selectedPublisher,
                                                     setNewPublisher,
                                                     handleAddPublisher,
                                                     handleEditPublisher,
                                                     error,
                                                 }) => {
    return (
        <div className="mb-4">
            <h2 className="text-xl mb-2">Add|Edit publisher</h2>
            {
                error && <p className="text-red-500">{error}</p>
            }
            <input
                type="text"
                placeholder="Name"
                value={newPublisher.name}
                onChange={(e) => setNewPublisher({...newPublisher, name: e.target.value})}
                className="mb-2 p-2 border border-gray-300 rounded w-full"/>
            <input
                type="text"
                placeholder="Location"
                value={newPublisher.location}
                onChange={(e) => setNewPublisher({ ...newPublisher, location: e.target.value })}
                className="mb-2 p-2 border border-gray-300 rounded w-full"/>
            <input
                type="text"
                placeholder="Founding year"
                value={newPublisher.foundingYear}
                onChange={(e) => {
                    const value = e.target.value;
                    if (/^\d*$/.test(value)) {
                        setNewPublisher({
                            ...newPublisher,
                            foundingYear: value ? Number(value) : 0});}}}
                className="mb-2 p-2 border border-gray-300 rounded w-full"/>
            <button
                onClick={(command == "add") ? handleAddPublisher : (() => (newPublisher.id = selectedPublisher.id, handleEditPublisher(newPublisher)))}
                className="p-2 bg-green-500 text-white rounded">Send request
            </button>
        </div>
    );
};

export default AddPublisherForm;

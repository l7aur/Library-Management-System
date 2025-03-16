import React from "react";
import PublisherT from "../../types/PublisherT.tsx";

interface AddPublisherFormProps {
    newPublisher: PublisherT;
    setNewPublisher: React.Dispatch<React.SetStateAction<PublisherT>>;
    handleAddPublisher: () => void;
    error: string | null;
}

const AddPublisherForm: React.FC<AddPublisherFormProps> = ({
                                                     newPublisher,
                                                     setNewPublisher,
                                                     handleAddPublisher,
                                                     error,
                                                 }) => {
    return (
        <div className="mb-4">
            <h2 className="text-xl mb-2">Add a New Publisher</h2>
            {error && <p className="text-red-500">{error}</p>}
            <input
                type="text"
                placeholder="Name"
                value={newPublisher.name}
                onChange={(e) => setNewPublisher({...newPublisher, name: e.target.value})}
                className="mb-2 p-2 border border-gray-300 rounded w-full"
            />
            <input
                type="text"
                placeholder="Location"
                value={newPublisher.location}
                onChange={(e) => setNewPublisher({...newPublisher, location: e.target.value})}
                className="mb-2 p-2 border border-gray-300 rounded w-full"
            />
            <input
                type="text"
                placeholder="Founding year"
                value={newPublisher.foundingYear.toString()} // Ensure the value is a string
                onChange={(e) => {
                    const value = e.target.value;
                    // Only update if the value is a valid number
                    if (/^\d*$/.test(value)) {
                        setNewPublisher({
                            ...newPublisher,
                            foundingYear: value ? Number(value) : 0 // Convert to number
                        });
                    }
                }}
                className="mb-2 p-2 border border-gray-300 rounded w-full"
            />

            {/*<input*/}
            {/*    type="text"*/}
            {/*    placeholder="Books (comma separated)"*/}
            {/*    value={newPublisher.books.join(', ')} // Display books as a comma-separated string*/}
            {/*    onChange={(e) => {*/}
            {/*        const value = e.target.value;*/}
            {/*        setNewPublisher({*/}
            {/*            ...newPublisher,*/}
            {/*            books: value.split(',').map(book => book.trim()) // Convert to an array of books*/}
            {/*        });*/}
            {/*    }}*/}
            {/*    className="mb-2 p-2 border border-gray-300 rounded w-full"*/}
            {/*/>*/}

            <button
                onClick={handleAddPublisher}
                className="p-2 bg-green-500 text-white rounded"
            >
                Send request
            </button>
        </div>
    );
};

export default AddPublisherForm;

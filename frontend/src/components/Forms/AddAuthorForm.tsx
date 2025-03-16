import AuthorT from "../../types/AuthorT.tsx";
import React from "react";

interface AddAuthorFormProps {
    newAuthor: AuthorT;
    setNewAuthor: React.Dispatch<React.SetStateAction<AuthorT>>;
    handleAddAuthor: () => void;
    error: string | null;
}

const AddAuthorForm: React.FC<AddAuthorFormProps> = ({
                                                               newAuthor,
                                                               setNewAuthor,
                                                               handleAddAuthor,
                                                               error,
                                                           }) => {
    return (
        <div className="mb-4">
            <h2 className="text-xl mb-2">Add a New Author</h2>
            {error && <p className="text-red-500">{error}</p>}
            <input
                type="text"
                placeholder="First name"
                value={newAuthor.firstName}
                onChange={(e) => setNewAuthor({...newAuthor, firstName: e.target.value})}
                className="mb-2 p-2 border border-gray-300 rounded w-full"
            />
            <input
                type="text"
                placeholder="Last name"
                value={newAuthor.lastName}
                onChange={(e) => setNewAuthor({...newAuthor, lastName: e.target.value})}
                className="mb-2 p-2 border border-gray-300 rounded w-full"
            />
            <input
                type="text"
                placeholder="Alias"
                value={newAuthor.alias}
                onChange={(e) => setNewAuthor({...newAuthor, alias: e.target.value})}
                className="mb-2 p-2 border border-gray-300 rounded w-full"
            />
            <input
                type="text"
                placeholder="Nationality"
                value={newAuthor.nationality}
                onChange={(e) => setNewAuthor({...newAuthor, nationality: e.target.value})}
                className="mb-2 p-2 border border-gray-300 rounded w-full"
            />

            <button
                onClick={handleAddAuthor}
                className="p-2 bg-green-500 text-white rounded"
            >
                Add author
            </button>
        </div>
    );
};

export default AddAuthorForm;

import React, {useState} from "react";
import AuthorsTable from "../components/Tables/AuthorsTable.tsx";
import useFetchAuthors from "../hooks/useFetchAuthors.tsx";
import useAddAuthor from "../hooks/useAddAuthor.tsx";
import AddAuthorForm from "../components/Forms/AddAuthorForm.tsx";
import AuthorT from "../types/AuthorT.tsx";

const Authors: React.FC = () => {
    const {data, loading, isError} = useFetchAuthors();
    const {isAdding, newAuthor, setNewAuthor, handleAddAuthor, setIsAdding, error} = useAddAuthor();

    const [selectedAuthors, setSelectedAuthors] = useState<AuthorT[]>([]);
    const handleRowSelect = (state: { selectedRows: AuthorT[] }) => {
        setSelectedAuthors(state.selectedRows);
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved authors</h1>
            {isError && <p className="text-red-500">Failed to load books</p>}

            {isAdding && (
                <AddAuthorForm
                    newAuthor={newAuthor}
                    setNewAuthor={setNewAuthor}
                    handleAddAuthor={handleAddAuthor}
                    error={error}
                />
            )}
            <div className="flex items-start gap-[5px] mb-4">
                {!isAdding && (
                    <button
                        onClick={() => setIsAdding(true)}
                        className="mb-4 p-2 bg-blue-500 text-white rounded"
                    >
                        Add author
                    </button>)}
                {isAdding && (
                    <button
                        onClick={() => setIsAdding(false)}
                        className="mb-4 p-2 bg-blue-500 text-white rounded"
                    >
                        Cancel
                    </button>
                )}
                <button
                    onClick={() => console.log("Delete clicked")}
                    disabled={selectedAuthors.length === 0} // Disable when no books are selected
                    className={`mb-4 p-2 text-white rounded ${
                        selectedAuthors.length === 0
                            ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                            : "bg-red-500 hover:bg-red-700"
                    }`}
                >
                    Delete
                </button>
                <button
                    onClick={() => console.log("Edit clicked")}
                    disabled={selectedAuthors.length === 0 || selectedAuthors.length > 1}
                    className={`mb-4 p-2 text-white rounded ${
                        selectedAuthors.length === 0 || selectedAuthors.length > 1
                            ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                            : "bg-red-500 hover:bg-red-700"
                    }`}
                >
                    Edit
                </button>
            </div>
            <AuthorsTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={handleRowSelect}
            />
        </div>
    );
};

export default Authors;

import React from "react";
import AuthorsTable from "../components/AuthorsTable.tsx";
import useFetchAuthors from "../hooks/useFetchAuthors.tsx";
import useAddAuthor from "../hooks/useAddAuthor.tsx";
import AddAuthorForm from "../components/Forms/AddAuthorForm.tsx";

const Authors: React.FC = () => {
    const {data, loading, isError} = useFetchAuthors();
    const {isAdding, newAuthor, setNewAuthor, handleAddAuthor, setIsAdding, error} = useAddAuthor();
    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved authors</h1>
            {isError && <p className="text-red-500">Failed to load books</p>}

            {!isAdding && (
            <button
                onClick={() => setIsAdding(true)}
                className="mb-4 p-2 bg-blue-500 text-white rounded"
            >
                Add author
            </button>)}

            {isAdding && (
                <AddAuthorForm
                    newAuthor={newAuthor}
                    setNewAuthor={setNewAuthor}
                    handleAddAuthor={handleAddAuthor}
                    error={error}
                />
            )}
            {isAdding && (
                <button
                    onClick={() => setIsAdding(false)}
                    className="mb-4 p-2 bg-blue-500 text-white rounded"
                >
                    Quit form
                </button>
            )}
            <AuthorsTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={(author) => console.log("Selected book:", author)}
            />
        </div>
    );
};

export default Authors;

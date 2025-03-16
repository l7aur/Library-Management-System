import React from "react";
import BooksTable from "../components/Tables/BooksTable.tsx";
import useFetchBooks from "../hooks/useFetchBooks.tsx";
import useAddBook from "../hooks/useAddBook.tsx";
import AddBookForm from "../components/Forms/AddBookForm.tsx";

const Books: React.FC = () => {
    const { data, loading, isError } = useFetchBooks();
    const {isAdding, newBook, setNewBook, handleAddBook, setIsAdding, error} = useAddBook();

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved books</h1>
            {isError && <p className="text-red-500">Failed to load books</p>}
            {!isAdding && (
            <button
                onClick={() => setIsAdding(true)}
                className="mb-4 p-2 bg-blue-500 text-white rounded"
            >
                Add book
            </button>
            )}
            {isAdding && (
                <AddBookForm
                    newBook={newBook}
                    setNewBook={setNewBook}
                    handleAddBook={handleAddBook}
                    error={error}
                />
            )}
            {isAdding && (
                <button
                    onClick={() => setIsAdding(false)}
                    className="mb-4 p-2 bg-blue-500 text-white rounded"
                >
                    Cancel
                </button>)}

            <BooksTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={(book) => console.log("Selected book:", book)}
            />
        </div>
    );
};

export default Books;

import BooksTable from "../components/Tables/BooksTable.tsx";
import useFetchBooks from "../hooks/fetches/useFetchBooks.tsx";
import useAddBook from "../hooks/adds/useAddBook.tsx";
import AddBookForm from "../components/Forms/AddBookForm.tsx";
import React, {useState} from "react";
import {BookT} from "../types/BookT.tsx";
import {BOOKS_DELETE_ENDPOINT} from "../constants/api.ts";

const Books: React.FC = () => {
    const {data, setData, loading, isError} = useFetchBooks();
    const {isAdding, newBook, setNewBook, handleAddBook, setIsAdding, error} = useAddBook();
    const [clearSelectedRows, setClearSelectedRows] = useState<boolean>(false);

    const [selectedBooks, setSelectedBooks] = useState<BookT[]>([]);
    const handleRowSelect = (state: { selectedRows: BookT[] }) => {
        setSelectedBooks(state.selectedRows);
    };

    const deleteBooks = async (bookObjects: BookT[]) => {
        const bookIds = bookObjects.map(book => book.id);
        const response = await fetch(BOOKS_DELETE_ENDPOINT, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ids: bookIds})
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return await response.text();
    };

    const handleDelete = () => {
        deleteBooks(selectedBooks)
            .then(response => {
                console.log('Authors deleted successfully:', response);
                const updatedData = data.filter(
                    book => !selectedBooks.some(selected => selected.id === book.id)
                );
                setData(updatedData);
                setClearSelectedRows(true);
            })
            .catch(error => {
                console.error('Error deleting authors:', error);
            });
        setClearSelectedRows(false);
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved books</h1>
            {isError && <p className="text-red-500">Failed to load books</p>}
            {isAdding && (
                <AddBookForm
                    newBook={newBook}
                    setNewBook={setNewBook}
                    handleAddBook={handleAddBook}
                    error={error}
                />
            )}
            <div className="flex items-start gap-[5px] mb-4">
                {!isAdding && (
                    <button
                        onClick={() => setIsAdding(true)}
                        className="mb-4 p-2 bg-blue-500 text-white rounded"
                    >
                        Add book
                    </button>
                )}
                {isAdding && (
                    <button
                        onClick={() => setIsAdding(false)}
                        className="mb-4 p-2 bg-blue-500 text-white rounded"
                    >
                        Cancel
                    </button>)}
                <button
                    onClick={handleDelete}
                    disabled={selectedBooks.length === 0} // Disable when no books are selected
                    className={`mb-4 p-2 text-white rounded ${
                        selectedBooks.length === 0
                            ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                            : "bg-red-500 hover:bg-red-700"
                    }`}
                >
                    Delete
                </button>
                <button
                    onClick={() => console.log("Edit clicked")}
                    disabled={selectedBooks.length === 0 || selectedBooks.length > 1}
                    className={`mb-4 p-2 text-white rounded ${
                        selectedBooks.length === 0 || selectedBooks.length > 1
                            ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                            : "bg-red-500 hover:bg-red-700"
                    }`}
                >
                    Edit
                </button>
            </div>
            <BooksTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={handleRowSelect}
                clearSelection={clearSelectedRows}
            />
        </div>
    );
};

export default Books;

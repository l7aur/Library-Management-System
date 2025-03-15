import React from "react";
import useFetchAuthors from "../hooks/useFetchAuthors.tsx";
import useFetchPublishers from "../hooks/useFetchPublishers.tsx";
import {BookT} from "../types/BookT.tsx";

interface AddBookFormProps {
    newBook: BookT;
    setNewBook: React.Dispatch<React.SetStateAction<BookT>>;
    handleAddBook: () => void;
    error: string | null;
}

const AddBookForm: React.FC<AddBookFormProps> = ({ newBook, setNewBook, handleAddBook, error }) => {

    const {data: authors, loading: loadingAuthors, isError: errorAuthors} = useFetchAuthors();
    const {data: publishers, loading: loadingPublishers, isError: errorPublishers} = useFetchPublishers();

    return (
        <div className="mb-4">
            <h2 className="text-xl font-bold mb-2">Add a New Book</h2>
            {error && <p className="text-red-500">{error}</p>}

            {(loadingAuthors || loadingPublishers) ? (
                    <p>Loading authors & publishers...</p>
                ) :
                (errorAuthors || errorPublishers) ?
                    <h2 className="text-xl font-bold mb-2">Error while retrieving author/publisher data</h2>
                    :
                    (
                        <>
                            <input
                                type="text"
                                placeholder="ISBN"
                                value={newBook.isbn}
                                onChange={(e) => setNewBook({...newBook, isbn: e.target.value})}
                                className="mb-2 p-2 border border-gray-300 rounded w-full"
                            />
                            <input
                                type="text"
                                placeholder="Book Title"
                                value={newBook.title}
                                onChange={(e) => setNewBook({...newBook, title: e.target.value})}
                                className="mb-2 p-2 border border-gray-300 rounded w-full"
                            />

                            {/*<select*/}
                            {/*    value={newBook}  // Ensure value is either the ID or an empty string*/}
                            {/*    onChange={(e) => setNewBook({ ...newBook, authorId: Number(e.target.value) })}*/}
                            {/*    className="mb-2 p-2 border border-gray-300 rounded w-full"*/}
                            {/*>*/}
                            {/*    <option value="" style={{ color: "black" }}>Select Author</option>*/}
                            {/*    {authors.map((author) => (*/}
                            {/*        <option key={author.id} value={`${author.firstName} ${author.lastName}`} style={{ color: "black" }}>*/}
                            {/*            {author.firstName} {author.lastName}*/}
                            {/*        </option>*/}
                            {/*    ))}*/}
                            {/*</select>*/}

                            <select
                                value={newBook.publisherId}
                                onChange={(e) => setNewBook({...newBook, publisherId: e.target.value})}
                                className="mb-2 p-2 border border-gray-300 rounded w-full"
                            >
                                <option value="" style={{color: "black"}}>Select Publisher</option>
                                {publishers.map((publisher) => (
                                    <option key={publisher.id} value={publisher.name} style={{ color: "black" }}>
                                        {publisher.name}
                                    </option>
                                ))}
                            </select>

                            <input
                                type="text"
                                placeholder="Price"
                                value={newBook.price.toString()}
                                onChange={(e) => {
                                    const value = e.target.value;
                                    if (/^\d*\.?\d*$/.test(value)) {
                                        setNewBook({
                                            ...newBook,
                                            price: value ? Number(value) : 0
                                        });
                                    }
                                }}
                                className="mb-2 p-2 border border-gray-300 rounded w-full"
                            />
                            <input
                                type="text"
                                placeholder="Stock"
                                value={newBook.stock.toString()} // Ensure the value is a string
                                onChange={(e) => {
                                    const value = e.target.value;
                                    if (/^\d*$/.test(value)) {
                                        setNewBook({
                                            ...newBook,
                                            stock: value ? Number(value) : 0
                                        });
                                    }
                                }}
                                className="mb-2 p-2 border border-gray-300 rounded w-full"
                            />

                            <button onClick={handleAddBook} className="p-2 bg-green-500 text-white rounded">
                                Add Book
                            </button>
                        </>
                    )}
        </div>
    );
};

export default AddBookForm;

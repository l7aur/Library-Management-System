import React, {useState} from "react";
import useFetchAuthors from "../../hooks/useFetchAuthors.tsx";
import useFetchPublishers from "../../hooks/useFetchPublishers.tsx";
import {AuthorRed, BookT} from "../../types/BookT.tsx";
import {MultiSelect} from "react-multi-select-component";

interface AddBookFormProps {
    newBook: BookT;
    setNewBook: React.Dispatch<React.SetStateAction<BookT>>;
    handleAddBook: () => void;
    error: string | null;
}

interface AuthorOption {
    label: string;
    value: AuthorRed;
}

const AddBookForm: React.FC<AddBookFormProps> = ({ newBook, setNewBook, handleAddBook, error }) => {

    const {data: authors, loading: loadingAuthors, isError: errorAuthors} = useFetchAuthors();
    const {data: publishers, loading: loadingPublishers, isError: errorPublishers} = useFetchPublishers();


    const [selected, setSelected] = useState<AuthorOption[]>([]);

    const authorOptions = authors.map((author) => ({
        value: author.id,
        label: `${author.firstName} ${author.lastName}`,
    }));

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
                            <input
                                type="number"
                                placeholder="Publish Year"
                                value={newBook.publishYear || ''}
                                onChange={(e) =>
                                    setNewBook({
                                        ...newBook,
                                        publishYear: e.target.value === '' ? 0 : parseInt(e.target.value),
                                    })
                                }
                                className="mb-2 p-2 border border-gray-300 rounded w-full"
                            />

                            <MultiSelect
                                options={authorOptions}
                                value={selected}
                                onChange={(selectedOptions: AuthorOption[]) => {
                                    setSelected(selectedOptions);
                                    setNewBook({
                                        ...newBook,
                                        authors: selectedOptions.map(option => option.value),
                                    });
                                }}
                                className="dark"
                                labelledBy="Select Authors"
                                hasSelectAll={false}
                            />

                            <select
                                value={newBook.publisher.name}
                                onChange={(e) => {
                                    const selectedPublisher = publishers.find(publisher => publisher.name === e.target.value);
                                    if (selectedPublisher) {
                                        setNewBook({ ...newBook, publisher: selectedPublisher });
                                    }
                                }}
                                className="mb-2 p-2 border border-gray-300 rounded w-full"
                            >
                                {publishers.map((publisher) => (
                                    <option key={publisher.id} value={publisher.name}>
                                        {publisher.name}
                                    </option>
                                ))}
                            </select>


                            <input
                                type="number"
                                placeholder="Price"
                                value={newBook.price || ''}
                                onChange={(e) =>
                                    setNewBook({
                                        ...newBook,
                                        price: e.target.value === '' ? 0 : parseFloat(e.target.value),
                                    })
                                }
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

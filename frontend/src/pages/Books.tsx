import React from "react";
import BooksTable from "../components/BooksTable.tsx";
import useFetchBooks from "../hooks/useFetchBooks.tsx";

const Books: React.FC = () => {
    const { data, loading, isError } = useFetchBooks();

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved books</h1>
            {isError && <p className="text-red-500">Failed to load books</p>}
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

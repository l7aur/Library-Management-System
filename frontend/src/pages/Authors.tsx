import React from "react";
import AuthorsTable from "../components/AuthorsTable.tsx";
import useFetchAuthors from "../hooks/useFetchAuthors.tsx";

const Authors: React.FC = () => {
    const {data, loading, isError} = useFetchAuthors();

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved authors</h1>
            {isError && <p className="text-red-500">Failed to load books</p>}
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

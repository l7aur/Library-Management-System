import React from "react";
import PublishersTable from "../components/PublishersTable.tsx";
import useFetchPublishers from "../hooks/useFetchPublishers.tsx"

const Publishers: React.FC = () => {
    const { data, loading, isError } = useFetchPublishers();

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved publishers</h1>
            {isError && <p className="text-red-500">Failed to load publishers</p>}
            <PublishersTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={(publisher) => console.log("Selected publisher:", publisher)}
            />
        </div>
    );
};


export default Publishers;

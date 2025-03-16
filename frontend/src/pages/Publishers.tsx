import React from "react";
import PublishersTable from "../components/PublishersTable.tsx";
import AddPublisherForm from "../components/Forms/AddPublisherForm.tsx";
import useAddPublisher from "../hooks/useAddPublisher.tsx";
import useFetchPublishers from "../hooks/useFetchPublishers.tsx";

const Publishers: React.FC = () => {
    const {data, loading, isError} = useFetchPublishers();
    const {isAdding, newPublisher, setNewPublisher, handleAddPublisher, setIsAdding, error} = useAddPublisher();

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved publishers</h1>
            {isError && <p className="text-red-500">Failed to load publishers</p>}

            {!isAdding && (
            <button
                onClick={() => setIsAdding(true)}
                className="mb-4 p-2 bg-blue-500 text-white rounded"
            >
                Add publisher
            </button>
            )}
            {isAdding && (
                <AddPublisherForm
                    newPublisher={newPublisher}
                    setNewPublisher={setNewPublisher}
                    handleAddPublisher={handleAddPublisher}
                    error={error}
                />
            )}
            {isAdding && (
                <button
                    onClick={() => setIsAdding(false)}
                    className="mb-4 p-2 bg-blue-500 text-white rounded"
                >
                    Quit form
                </button>)}


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

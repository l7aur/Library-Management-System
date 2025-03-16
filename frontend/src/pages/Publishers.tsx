import React, {useState} from "react";
import PublishersTable from "../components/Tables/PublishersTable.tsx";
import AddPublisherForm from "../components/Forms/AddPublisherForm.tsx";
import useAddPublisher from "../hooks/useAddPublisher.tsx";
import useFetchPublishers from "../hooks/useFetchPublishers.tsx";
import PublisherT from "../types/PublisherT.tsx";

const Publishers: React.FC = () => {
    const {data, loading, isError} = useFetchPublishers();
    const {isAdding, newPublisher, setNewPublisher, handleAddPublisher, setIsAdding, error} = useAddPublisher();

    const [selectedPublishers, setSelectedPublishers] = useState<PublisherT[]>([]);
    const handleRowSelect = (state: { selectedRows: PublisherT[] }) => {
        setSelectedPublishers(state.selectedRows);
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved publishers</h1>
            {isError && <p className="text-red-500">Failed to load publishers</p>}

            {isAdding && (
                <AddPublisherForm
                    newPublisher={newPublisher}
                    setNewPublisher={setNewPublisher}
                    handleAddPublisher={handleAddPublisher}
                    error={error}
                />
            )}
            <div className="flex items-start gap-[5px] mb-4">
                {!isAdding && (
                    <button
                        onClick={() => setIsAdding(true)}
                        className="mb-4 p-2 bg-blue-500 text-white rounded"
                    >
                        Add publisher
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
                    onClick={() => console.log("Delete clicked")}
                    disabled={selectedPublishers.length === 0} // Disable when no books are selected
                    className={`mb-4 p-2 text-white rounded ${
                        selectedPublishers.length === 0
                            ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                            : "bg-red-500 hover:bg-red-700"
                    }`}
                >
                    Delete
                </button>
                <button
                    onClick={() => console.log("Edit clicked")}
                    disabled={selectedPublishers.length === 0 || selectedPublishers.length > 1}
                    className={`mb-4 p-2 text-white rounded ${
                        selectedPublishers.length === 0 || selectedPublishers.length > 1
                            ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                            : "bg-red-500 hover:bg-red-700"
                    }`}
                >
                    Edit
                </button>
            </div>
            <PublishersTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={handleRowSelect}
            />
        </div>
    );
};

export default Publishers;

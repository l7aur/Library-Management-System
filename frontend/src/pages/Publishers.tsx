import React, {useState} from "react";
import PublishersTable from "../components/Tables/PublishersTable.tsx";
import AddPublisherForm from "../components/Forms/AddPublisherForm.tsx";
import useAddPublisher from "../hooks/adds/useAddPublisher.tsx";
import useFetchPublishers from "../hooks/fetches/useFetchPublishers.tsx";
import PublisherT from "../types/PublisherT.tsx";
import {PUBLISHERS_DELETE_ENDPOINT} from "../constants/api.ts";

const Publishers: React.FC = () => {
    const {data, setData, loading, isError} = useFetchPublishers();
    const {isAdding, newPublisher, setNewPublisher, handleAddPublisher, setIsAdding, error} = useAddPublisher();
    const [clearSelectedRows, setClearSelectedRows] = useState<boolean>(false);

    const [selectedPublishers, setSelectedPublishers] = useState<PublisherT[]>([]);
    const handleRowSelect = (state: { selectedRows: PublisherT[] }) => {
        setSelectedPublishers(state.selectedRows);
    };

    const deleteAuthors = async (publisherObjects: PublisherT[]) => {
        const publisherIds = publisherObjects.map(publisher => publisher.id);
        const response = await fetch(PUBLISHERS_DELETE_ENDPOINT, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ids: publisherIds})
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return await response.text();
    };

    const handleDelete = () => {
        deleteAuthors(selectedPublishers)
            .then(response => {
                console.log('Authors deleted successfully:', response);
                const updatedData = data.filter(
                    publisher => !selectedPublishers.some(selected => selected.id === publisher.id)
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
                    onClick={handleDelete}
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
                clearSelection={clearSelectedRows}
            />
        </div>
    );
};

export default Publishers;

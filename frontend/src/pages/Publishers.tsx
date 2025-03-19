import React, {useState} from "react";
import PublishersTable from "../components/Tables/PublishersTable.tsx";
import AddPublisherForm from "../components/Forms/AddPublisherForm.tsx";
import usePublisher from "../hooks/usePublisher.tsx";
import useFetchPublishers from "../hooks/fetches/useFetchPublishers.tsx";
import PublisherT from "../types/PublisherT.tsx";
import {PUBLISHERS_DELETE_ENDPOINT} from "../constants/api.ts";

const Publishers: React.FC = () => {
    const {data, setData, loading, isError} = useFetchPublishers();
    const {
        isAdding, isEditing,
        newPublisher, setNewPublisher,
        handleAddPublisher, handleEditPublisher,
        setIsAdding, setIsEditing,
        error
    } = usePublisher();
    const [clearSelectedRows, setClearSelectedRows] = useState<boolean>(false);
    const [selectedPublishers, setSelectedPublishers] = useState<PublisherT[]>([]);

    const handleRowSelect = (state: { selectedRows: PublisherT[] }) => {
        setSelectedPublishers(state.selectedRows);
    };

    const deletePublishers = async (publisherObjects: PublisherT[]) => {
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
        deletePublishers(selectedPublishers)
            .then(response => {
                console.log('Publishers deleted successfully:', response);
                const updatedData = data.filter(
                    publisher => !selectedPublishers.some(selected => selected.id === publisher.id)
                );
                setData(updatedData);
                setClearSelectedRows(true);
            })
            .catch(error => {
                console.error('Error deleting publishers:', error);
            });
        setClearSelectedRows(false);
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Our beloved publishers</h1>
            {
                isError && <p className="text-red-500">Failed to load publishers</p>
            }
            {
                isAdding && (
                    <AddPublisherForm
                        command="add"
                        newPublisher={newPublisher}
                        setNewPublisher={setNewPublisher}
                        selectedPublisher={selectedPublishers[0]}
                        handleAddPublisher={handleAddPublisher}
                        handleEditPublisher={handleEditPublisher}
                        error={error}
                    />
                )
            }
            {
                isEditing && (
                    <AddPublisherForm
                        command="edit"
                        newPublisher={newPublisher}
                        selectedPublisher={selectedPublishers[0]}
                        setNewPublisher={setNewPublisher}
                        handleAddPublisher={handleAddPublisher}
                        handleEditPublisher={handleEditPublisher}
                        error={error}
                    />
                )
            }
            <div className="flex items-start gap-[5px] mb-4">
                {
                    !isAdding && (
                        <button
                            onClick={() => setIsAdding(true)}
                            className="mb-4 p-2 bg-blue-500 text-white rounded"
                        >Add publisher
                        </button>)
                }
                {
                    (isAdding || isEditing) && (
                        <button
                            onClick={() => (setIsAdding(false), setIsEditing(false))}
                            className="mb-4 p-2 bg-blue-500 text-white rounded"
                        >Cancel
                        </button>)
                }
                <button
                    onClick={handleDelete}
                    disabled={selectedPublishers.length === 0 || isEditing}
                    className={`mb-4 p-2 text-white rounded ${
                        (selectedPublishers.length === 0 || isEditing)
                            ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                            : "bg-red-500 hover:bg-red-700"}`
                    }>Delete
                </button>
                {!isEditing && (
                    <button
                        onClick={() => setIsEditing(selectedPublishers.length === 1)}
                        disabled={selectedPublishers.length === 0 || selectedPublishers.length > 1}
                        className={`mb-4 p-2 text-white rounded ${
                            (selectedPublishers.length === 0 || selectedPublishers.length > 1)
                                ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                                : "bg-red-500 hover:bg-red-700"}`
                        }>Edit
                    </button>)
                }
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

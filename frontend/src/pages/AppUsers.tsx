import React, {useState} from "react";
import {AppUserT} from "../types/AppUserT.tsx";
import {APP_USER_DELETE_ENDPOINT} from "../constants/api.ts";
import AppUsersTable from "../components/Tables/AppUsersTable.tsx";
import useFetchAppUsers from "../hooks/fetches/useFetchAppUsers.tsx";
import {useNavigate} from "react-router-dom";

const AppUsers: React.FC = () => {
    const {data, setData, loading, isError} = useFetchAppUsers();
    const [clearSelectedRows, setClearSelectedRows] = useState<boolean>(false);
    const [selectedAppUsers, setSelectedAppUsers] = useState<AppUserT[]>([]);

    const navigate = useNavigate();

    const handleRowSelect = (state: { selectedRows: AppUserT[] }) => {
        setSelectedAppUsers(state.selectedRows);
    };

    const deleteAppUsers = async (appUserObjects: AppUserT[]) => {
        const appUserIds = appUserObjects.map(appUser => appUser.id);
        const response = await fetch(APP_USER_DELETE_ENDPOINT, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ids: appUserIds})
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return await response.text();
    };

    const handleDelete = () => {
        deleteAppUsers(selectedAppUsers)
            .then(response => {
                console.log('App users deleted successfully:', response);
                const updatedData = data.filter(
                    appUser => !selectedAppUsers.some(selected => selected.id === appUser.id)
                );
                setData(updatedData);
                setClearSelectedRows(true);
            })
            .catch(error => {
                console.error('Error deleting app users:', error);
            });
        setClearSelectedRows(false);
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">App users</h1>
            {isError && <p className="text-red-500">Failed to load users</p>}
            <div className="flex items-start gap-[5px] mb-4">
                    <button
                        onClick={() => {navigate('/register')}}
                        className="mb-4 p-2 bg-blue-500 text-white rounded"
                    >
                        Add app user
                    </button>
                <button
                    onClick={handleDelete}
                    disabled={selectedAppUsers.length === 0}
                    className={`mb-4 p-2 text-white rounded ${
                        selectedAppUsers.length === 0
                            ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                            : "bg-red-500 hover:bg-red-700"
                    }`}
                >
                    Delete
                </button>
                <button
                    onClick={() => console.log("Edit clicked")}
                    disabled={selectedAppUsers.length === 0 || selectedAppUsers.length > 1}
                    className={`mb-4 p-2 text-white rounded ${
                        selectedAppUsers.length === 0 || selectedAppUsers.length > 1
                            ? "bg-gray-400 cursor-not-allowed opacity-50 disabled:pointer-events-none"
                            : "bg-red-500 hover:bg-red-700"
                    }`}
                >
                    Edit
                </button>
            </div>
            <AppUsersTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={handleRowSelect}
                clearSelection={clearSelectedRows}
            />
        </div>
    );
};

export default AppUsers;

import AppUsersTable from "../components/tables/AppUsersTable.tsx";
import {AppUserType} from "../types/AppUserType.tsx";
import {useState} from "react";
import useFindAllAppUsers from "../hooks/useFindAllAppUsers.tsx";
import "./Page.css"
import {CRUDMenu} from "../components/CRUDMenu.tsx";
import CreateAppUserForm from "../components/forms/CreateAppUserForm.tsx";

const AppUsersPage = () => {
    const {data, setData, loading, isError, refetch} = useFindAllAppUsers();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedAppUsers, setSelectedAppUsers] = useState<AppUserType[]>([]);
    const [isCreateFormOpen, setIsCreateFormOpen] = useState(false);

    const handleCreate = () => {
        setIsCreateFormOpen(true);
    }

    const handleRead = () => {
        setIsCreateFormOpen(false);
        refetch().catch((error) => console.error("Error:", error));
    };

    const handleUpdate = () => console.log("Update clicked");
    const handleDelete = () => console.log("Delete clicked");

    const onRowSelect = (state: { selectedRows: AppUserType[] }) => {
        setSelectedAppUsers(state.selectedRows);
    };

    return (
        <div className="page_container">
            <AppUsersTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={onRowSelect}
                clearSelection={clearSelection}
            />
            <CRUDMenu
                onCreate={() => setIsCreateFormOpen(true)}
                onRead={handleRead}
                onUpdate={handleUpdate}
                onDelete={handleDelete}
            />
            {isCreateFormOpen && (
                <CreateAppUserForm
                    onClose={() => setIsCreateFormOpen(false)}
                    onSubmit={handleCreate}
                />
            )}
        </div>
    )
}

export default AppUsersPage;
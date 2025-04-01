import AppUsersTable from "../components/tables/AppUsersTable.tsx";
import {AppUserType} from "../types/AppUserType.tsx";
import {useState} from "react";
import useFindAllAppUsers from "../hooks/useFindAllAppUsers.tsx";
import "./Page.css"
import {CRUDMenu} from "../components/CRUDMenu.tsx";
import CreateAppUserForm from "../components/forms/CreateAppUserForm.tsx";
import UserFormDataType from "../types/UserFormDataType.tsx";
import {add} from "../services/AppUserService.ts";

const AppUsersPage = () => {
    const {fData, setFData, fLoading, isFError, refetch} = useFindAllAppUsers();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedAppUsers, setSelectedAppUsers] = useState<AppUserType[]>([]);
    const [isCreateFormOpen, setIsCreateFormOpen] = useState(false);

    const handleCreate = (newUser: UserFormDataType) => {
        add(newUser)
            .then( (response: AppUserType) => {
                if (fData.length > 0) {
                    setFData((prevFData) => [
                        ...prevFData,
                        { id: response.id, role: response.role, firstName: response.firstName, lastName: response.lastName, username: response.username, password: response.password },
                    ]);
                } else {
                    setFData([{ id: response.id, role: response.role, firstName: response.firstName, lastName: response.lastName, username: response.username, password: response.password }]);
                }
            })
            .catch( (error: Error) => {console.log(error)});
    };


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
                data={fData}
                loading={fLoading}
                isError={isFError}
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
                    onSubmit={(newUserData) => handleCreate(newUserData)}
                />
            )}
        </div>
    )
}

export default AppUsersPage;
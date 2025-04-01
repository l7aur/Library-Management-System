import AppUsersTable from "../components/tables/AppUsersTable.tsx";
import {AppUserType} from "../types/AppUserType.tsx";
import {useState} from "react";
import useFindAllAppUsers from "../hooks/useFindAllAppUsers.tsx";
import "./Page.css"
import {CRUDMenu} from "../components/CRUDMenu.tsx";
import CreateAppUserForm from "../components/forms/CreateAppUserForm.tsx";
import UserFormDataType from "../types/UserFormDataType.tsx";
import {add, del} from "../services/AppUserService.ts";

const AppUsersPage = () => {
    const {fData, setFData, fLoading, isFError, refetch} = useFindAllAppUsers();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedAppUsers, setSelectedAppUsers] = useState<AppUserType[]>([]);
    const [isCreateFormOpen, setIsCreateFormOpen] = useState(false);
    const [error, setError] = useState<string[]>([]);
    const [okays, setOkays] = useState<string[]>([]);

    const isValidUser = (user: AppUserType): boolean => {
        return Boolean(user.username)
            && Boolean(user.firstName)
            && Boolean(user.lastName)
            && Boolean(user.role)
            && Boolean(user.password)
            && Boolean(user.id)
    };

    const handleCreate = (newUser: UserFormDataType) => {
        setError([]);
        setOkays([]);
        if(newUser.password !== newUser.confirmation) {
            setError(["Password and its confirmation mismatch!"]);
            return;
        }
        add(newUser)
            .then((response: AppUserType) => ({
                id: response.id,
                role: response.role,
                firstName: response.firstName,
                lastName: response.lastName,
                username: response.username,
                password: response.password,
            }))
            .then((formattedResponse) => {
                if (isValidUser(formattedResponse)) {
                    setFData((prevFData) =>
                        prevFData.length > 0
                            ? [...prevFData, formattedResponse]
                            : [formattedResponse]
                    );
                }
                else {
                    const err: string[] = [formattedResponse.id
                        , formattedResponse.username
                        , formattedResponse.password
                        , formattedResponse.role
                        , formattedResponse.firstName
                        , formattedResponse.lastName
                        ];
                    throw new Error(err.filter((x) => x != undefined).join("\n"));
                }
            })
            .catch((error) => {
                const err: string[] = error.message.split("\n").filter((x: string) => x !== "");
                setError(err);
            });
    };

    const handleRead = () => {
        setError([]);
        setOkays([]);
        setIsCreateFormOpen(false);
        refetch();
    };

    const handleUpdate = () => console.log("Update clicked");
    const handleDelete = () => {
        setError([]);
        setOkays([]);
        const ids = selectedAppUsers.map((item) => item.id);
        del(ids)
            .then(r => setOkays([r]));
        setClearSelection(true);
        setFData((prevFData) => prevFData.filter((item) => !ids.includes(item.id)));
    }

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
                err={error}
                oks={okays}
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
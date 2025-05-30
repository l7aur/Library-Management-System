import AppUsersTable from "../components/tables/AppUsersTable.tsx";
import {AppUserType} from "../types/AppUserType.tsx";
import {useState} from "react";
import useFindAllAppUsers from "../hooks/useFindAllAppUsers.tsx";
import "./Page.css"
import {CRUDMenu} from "../components/CRUDMenu.tsx";
import CreateAppUserForm from "../components/forms/CreateAppUserForm.tsx";
import AppUserFormDataType from "../types/FormDataType.tsx";
import {add, del, findFiltered, update} from "../services/AppUserService.ts";
import AppUserSearchBar from "../components/AppUserSearchBar.tsx";
import {useAuth} from "../config/GlobalState.tsx";

const AppUsersPage = () => {
    const {fData, setFData, fLoading, isFError, refetch} = useFindAllAppUsers();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedAppUsers, setSelectedAppUsers] = useState<AppUserType[]>([]);
    const [isCreateFormOpen, setIsCreateFormOpen] = useState(false);
    const [error, setError] = useState<string[]>([]);
    const [okays, setOkays] = useState<string[]>([]);
    const [formFillData, setFormFillData] = useState<AppUserType>({
        id: "",
        username: "",
        password: "",
        role: "",
        firstName: "",
        lastName: "",
        email: ""
    });
    const { token } = useAuth();

    const isValidUser = (user: AppUserType): boolean => {
        return Boolean(user.username)
            && Boolean(user.firstName)
            && Boolean(user.lastName)
            && Boolean(user.role)
            && Boolean(user.password)
            && Boolean(user.id)
            && Boolean(user.email)
    };
    const handleCreate = (newUser: AppUserFormDataType) => {
        setError([]);
        setOkays([]);
        if (newUser.password !== newUser.confirmation) {
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
                message: response.message,
                email: response.email
            }))
            .then((formattedResponse) => {
                if (isValidUser(formattedResponse)) {
                    setFData((prevFData) =>
                        prevFData.length > 0
                            ? [...prevFData, formattedResponse]
                            : [formattedResponse]
                    );
                } else {
                    const err: string[] =
                        [formattedResponse.id
                            , formattedResponse.username
                            , formattedResponse.password
                            , formattedResponse.role
                            , formattedResponse.firstName
                            , formattedResponse.lastName
                            , formattedResponse.email
                        ];
                    if(formattedResponse.message != undefined) {
                        err.push(formattedResponse.message);
                    }
                    throw new Error(err.filter((x) => x != undefined).join("\n"));
                }
            })
            .catch((error) => {
                const err: string[] = error.message.split("\n").filter((x: string) => x !== "");
                setError(err);
            });
    };
    const HandleRead = () => {
        refetch();
    };
    const HandleFilteredSearch = (username: string, role: string, firstName: string, lastName: string) => {
        setFData([]);
        setError([]);
        findFiltered(token, username, role, firstName, lastName)
            .then(response => setFData(response))
            .catch((error) => {setError([error])})
    };
    const HandleUpdate = (newUserData: AppUserFormDataType) => {
        setError([]);
        setOkays([]);
        if (newUserData.password !== newUserData.confirmation) {
            setError(["Password and its confirmation mismatch!"]);
            return;
        }
        const newUser: AppUserType = {
            id: newUserData.id,
            firstName: newUserData.firstName,
            lastName: newUserData.lastName,
            username: newUserData.username,
            password: newUserData.password,
            role: newUserData.role,
            email: newUserData.email
        }
        update(newUser, token)
            .then((response: AppUserType) => ({
                id: response.id,
                role: response.role,
                firstName: response.firstName,
                lastName: response.lastName,
                username: response.username,
                password: response.password,
                email: response.email
            }))
            .then((formattedResponse) => {
                if (isValidUser(formattedResponse)) {
                    setFData((prevFData) =>
                        prevFData.map((entry) =>
                            entry.id === formattedResponse.id ? {...entry, ...formattedResponse} : entry
                        ));
                } else {
                    const err: string[] =
                        [formattedResponse.id
                            , formattedResponse.username
                            , formattedResponse.password
                            , formattedResponse.role
                            , formattedResponse.firstName
                            , formattedResponse.lastName
                            , formattedResponse.email
                        ];
                    throw new Error(err.filter((x) => x != undefined).join("\n"));
                }
            })
            .catch((error) => {
                const err: string[] = error.message.split("\n").filter((x: string) => x !== "");
                setError(err);
            })
    }
    const HandleDelete = () => {
        setError([]);
        setOkays([]);
        const ids = selectedAppUsers.map((item) => item.id);
        del(ids, token)
            .then((r) => {
                if (200 === r) {
                    setOkays(["Success!"]);
                } else {
                    setError(["Error!"]);
                }
            })
            .finally(() => {
                setClearSelection(true);
                setSelectedAppUsers([]);
                setFData((prevFData) => prevFData.filter((item) => !ids.includes(item.id)));
            });
    }
    const onRowSelect = (state: { selectedRows: AppUserType[] }) => {
        setSelectedAppUsers(state.selectedRows);
    };

    return (
        <div className="page_container">
            <AppUserSearchBar
                onSearch={HandleFilteredSearch}
            />
            <AppUsersTable
                data={fData}
                loading={fLoading}
                isError={isFError}
                onRowSelect={onRowSelect}
                clearSelection={clearSelection}
            />
            {!isCreateFormOpen &&
                (<CRUDMenu
                        err={error}
                        oks={okays}
                        onCreate={() => setIsCreateFormOpen(true)}
                        onRead={() => {
                            setError([]);
                            setOkays([]);
                            setIsCreateFormOpen(false);
                            if (selectedAppUsers && selectedAppUsers.length > 0) {
                                setError(["Cannot update the table if users are selected!"]);
                                setSelectedAppUsers([]);
                            } else {
                                HandleRead();
                            }
                        }}
                        onUpdate={() => {
                            setError([]);
                            setOkays([]);
                            if (selectedAppUsers && selectedAppUsers.length === 1) {
                                setIsCreateFormOpen(true);
                                setFormFillData(selectedAppUsers[0]);
                            } else {
                                setError(["Can update just one user at a time!"]);
                            }
                        }}
                        onDelete={HandleDelete}
                    />
                )}
            {isCreateFormOpen && (
                <CreateAppUserForm
                    data={formFillData}
                    onClose={() => {
                        setIsCreateFormOpen(false);
                        setFormFillData({id: "", username: "", password: "", role: "", firstName: "", lastName: "", email: ""});
                    }}
                    onSubmitCreate={(newUserData) => handleCreate(newUserData)}
                    onSubmitUpdate={(newUserData) => HandleUpdate(newUserData)}
                />
            )}
        </div>
    )
}

export default AppUsersPage;
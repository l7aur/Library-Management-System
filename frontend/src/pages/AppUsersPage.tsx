import AppUsersTable from "../components/tables/AppUsersTable.tsx";
import {AppUserType} from "../types/AppUserType.tsx";
import {useState} from "react";
import useFindAllAppUsers from "../hooks/useFindAllAppUsers.tsx";

const AppUsersPage = () => {
    const {data, setData, loading, isError} = useFindAllAppUsers();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedAppUsers, setSelectedAppUsers] = useState<AppUserType[]>([]);

    const onRowSelect = (state: { selectedRows: AppUserType[] }) => {
        setSelectedAppUsers(state.selectedRows);
    };

    return (
        <div>
            <AppUsersTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={onRowSelect}
                clearSelection={clearSelection}
            />
        </div>
    )
}

export default AppUsersPage;
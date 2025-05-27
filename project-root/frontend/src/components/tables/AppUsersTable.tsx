import {AppUserType} from "../../types/AppUserType.tsx";
import DataTable, {TableColumn} from "react-data-table-component";
import "./Table.css"

interface Props {
    data: AppUserType[]
    loading: boolean
    isError: boolean
    onRowSelect: (state: {selectedRows: AppUserType[]}) => void
    clearSelection: boolean
}

export default function AppUsersTable({data, loading, isError, onRowSelect, clearSelection}: Props) {
    const columns: TableColumn<AppUserType>[] = [
        {name: 'First Name', selector: (row) => row.firstName, sortable: true, id: 1},
        {name: 'Last Name', selector: (row) => row.lastName, sortable: true},
        {name: 'Role', selector: (row) => row.role, sortable: true},
        {name: 'Username', selector: (row) => row.username, sortable: true}
        // {name: 'Password', selector: (row) => row.password, sortable: true}
    ];

    const isEmpty = data && data.length === 0;

    if (loading) {
        return (<p className="info-text">Loading...</p>);
    }

    if (isError) {
        return (<p className="error-text">Error...</p>);
    }

    if (isEmpty) {
        return (<p className="info-text">No users have been found</p>)
    }

    return (
        <DataTable
            theme={'dark'}
            title="Users"
            columns={columns}
            data={data}
            pagination
            highlightOnHover
            selectableRows
            onSelectedRowsChange={onRowSelect}
            defaultSortFieldId={1}
            clearSelectedRows={clearSelection}
        />
    )
}
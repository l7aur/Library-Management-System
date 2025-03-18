import {AppUserT} from "../../types/AppUserT.tsx";
import DataTable, {TableColumn} from "react-data-table-component";

interface AppUserTableProps {
    data: AppUserT[];
    loading: boolean;
    isError: boolean;
    onRowSelect: (state: { selectedRows: AppUserT[] }) => void;
    clearSelection: boolean;
}

function AppUsersTable({ data, loading, isError, onRowSelect, clearSelection }: AppUserTableProps) {
    const columns: TableColumn<AppUserT>[] = [
        { name: 'First Name', selector: (row) => row.firstName, sortable: true, id: 1 },
        { name: 'Last Name', selector: (row) => row.lastName, sortable: true },
        { name: 'Role', selector: (row) => row.role, sortable: true },
        { name: 'Username', selector: (row) => row.userName, sortable: true },
        { name: 'Password', selector: (row) => row.password, sortable: true },
    ];

    return (
        <>
            {loading ? (
                <p className="loading-text">Loading...</p>
            ) : isError ? (
                <p className="error-text">An error occurred while fetching data!</p>
            ) : data.length === 0 ? (
                <p className="empty-text">No app users found.</p>
            ) : (
                <div className="app-user-table-container"
                     style={{
                         width: "90vw",
                         margin: "20px auto",
                         overflowX: "auto",
                     }}>
                    <DataTable
                        theme={'dark'}
                        title="App Users"
                        columns={columns}
                        data={data}
                        pagination
                        highlightOnHover
                        selectableRows
                        onSelectedRowsChange={onRowSelect}
                        defaultSortFieldId={1}
                        clearSelectedRows={clearSelection}
                    />
                </div>

            )}
        </>
    );
}

export default AppUsersTable;

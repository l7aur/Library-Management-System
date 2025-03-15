import DataTable, { TableColumn } from 'react-data-table-component';
import AuthorT from "../types/AuthorT.tsx";

interface AuthorsTableProps {
    data: AuthorT[];
    loading: boolean;
    isError: boolean;
    onRowSelect: (state: { selectedRows: AuthorT[] }) => void;
}

function AuthorsTable({ data, loading, isError, onRowSelect }: AuthorsTableProps) {
    const columns: TableColumn<AuthorT>[] = [
        { name: 'ID', selector: (row) => row.id, sortable: true, id: 1 },
        { name: 'First Name', selector: (row) => row.firstName, sortable: true },
        { name: 'Last Name', selector: (row) => row.lastName, sortable: true },
        { name: 'Alias', selector: (row) => row.alias, sortable: true },
        { name: 'Nationality', selector: (row) => row.nationality, sortable: true },
        // { name: 'Books', selector: (row) => row.books, sortable: true },
    ];

    return (
        <>
            {loading ? (
                <p className="loading-text">Loading...</p>
            ) : isError ? (
                <p className="error-text">An error occurred while fetching data!</p>
            ) : data.length === 0 ? (
                <p className="empty-text">No authors found.</p>
            ) : (
                <div className="authors-table-container">
                    <DataTable
                        title="Authors"
                        columns={columns}
                        data={data}
                        pagination
                        highlightOnHover
                        selectableRows
                        onSelectedRowsChange={onRowSelect}
                        defaultSortFieldId={3}
                    />
                </div>
            )}
        </>
    );
}

export default AuthorsTable;

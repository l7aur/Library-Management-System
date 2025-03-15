import PublisherT from "../types/PublisherT.tsx";
import DataTable, { TableColumn } from 'react-data-table-component';

interface PublishersTableProps {
    data: PublisherT[];
    loading: boolean;
    isError: boolean;
    onRowSelect: (state: { selectedRows: PublisherT[] }) => void;
}

function PublishersTable({ data, loading, isError, onRowSelect }: PublishersTableProps) {
    const columns: TableColumn<PublisherT>[] = [
        { name: 'ID', selector: (row) => row.id, sortable: true, id: 1 },
        { name: 'Name', selector: (row) => row.name, sortable: true },
        { name: 'Location', selector: (row) => row.location, sortable: true },
        { name: 'FoundingYear', selector: (row) => row.foundingYear, sortable: true },
    ];

    return (
        <>
            {loading ? (
                <p className="loading-text">Loading...</p>
            ) : isError ? (
                <p className="error-text">An error occurred while fetching data!</p>
            ) : data.length === 0 ? (
                <p className="empty-text">No publishers found.</p>
            ) : (
                <div className="publishers-table-container">
                    <DataTable
                        title="Publishers"
                        columns={columns}
                        data={data}
                        pagination
                        highlightOnHover
                        selectableRows
                        onSelectedRowsChange={onRowSelect}
                        defaultSortFieldId={1}
                    />
                </div>
            )}
        </>
    );
}

export default PublishersTable;

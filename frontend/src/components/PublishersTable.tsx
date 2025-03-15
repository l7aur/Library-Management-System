import PublisherT from "../types/PublisherT.tsx";
import DataTable, {TableColumn} from 'react-data-table-component';

interface PublishersTableProps {
    data: PublisherT[];
    loading: boolean;
    isError: boolean;
    onRowSelect: (state: {selectedRows: PublisherT[]}) => void;
    theme: 'light' | 'dark';
}

function PublishersTable({data, loading, isError, onRowSelect, theme}: PublishersTableProps) {
    const columns: TableColumn < PublisherT > [] = [
        {name: 'ID', selector: (row: PublisherT) => row.id, sortable: true},
        {name: 'Name', selector: (row: PublisherT) => row.name, sortable: true},
        {name: 'Location', selector: (row: PublisherT) => row.location, sortable: true},
        {name: 'FoundingYear', selector: (row: PublisherT) => row.foundingYear, sortable: true}
        //{name: 'Books', selector: (row: PublisherT) => row.id, sortable: true},
    ];

    return (
        <>
            {loading ? (
                <p className="loading-text">Loading...</p>
            ) : isError ? (
                <p className="error-text">An error occurred while fetching data!</p>
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
                        theme={theme === "dark" ? "dark" : "light"}
                    />
                </div>
            )}
        </>
    );
}

export default PublishersTable;
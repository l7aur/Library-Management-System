import Publisher from "../model/publisher.model.ts";
import DataTable, {TableColumn} from 'react-data-table-component';

interface PublishersTableProps {
    data: Publisher[];
    loading: boolean;
    isError: boolean;
    onRowSelect: (state: {selectedRows: Publisher[]}) => void;
    theme: 'light' | 'dark';
}

function PublishersTable({data, loading, isError, onRowSelect, theme}: PublishersTableProps) {
    const columns: TableColumn < Publisher > [] = [
        {name: 'ID', selector: (row: Publisher) => row.id, sortable: true},
        {name: 'Name', selector: (row: Publisher) => row.name, sortable: true},
        {name: 'Location', selector: (row: Publisher) => row.location, sortable: true},
        {name: 'FoundingYear', selector: (row: Publisher) => row.foundingYear, sortable: true}
        //{name: 'Books', selector: (row: Publisher) => row.id, sortable: true},
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
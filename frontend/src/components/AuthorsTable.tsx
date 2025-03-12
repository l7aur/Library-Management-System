import Author from "../model/author.model.ts";
import DataTable, {TableColumn} from "react-data-table-component";

interface AuthorsTableProps {
    data: Author[];
    loading: boolean;
    isError: boolean;
    onRowSelect: (state: {selectedRows: Author[]}) => void;
    theme: 'dark' | 'light';
}

function AuthorsTable({data, loading, isError, onRowSelect, theme}: AuthorsTableProps) {
    const columns: TableColumn<Author>[] = [
        {name: 'ID', selector: (row: Author) => row.id, sortable: true},
        {name: 'First Name', selector: (row: Author) => row.firstName, sortable: true},
        {name: 'Last Name', selector: (row: Author) => row.lastName, sortable: true},
        {name: 'Alias', selector: (row: Author) => row.alias, sortable: true},
        {name: 'Nationality', selector: (row: Author) => row.nationality, sortable: true},
        // {name: 'Books', selector: (row: Author) => row.books, sortable: true},
    ];

    return (
        <>
            {loading ? (
                <p className="loading-text">Loading...</p>
            ) : isError ? (
                <p className="error-text">An error occurred while fetching data!</p>
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
                        theme={theme === "dark" ? "dark" : "light"}
                    />
                </div>
            )}
        </>);
}

export default AuthorsTable;
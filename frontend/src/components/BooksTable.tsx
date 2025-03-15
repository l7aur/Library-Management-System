import DataTable, { TableColumn } from 'react-data-table-component';
import {BookT} from "../types/BookT.tsx";

interface BooksTableProps {
    data: BookT[];
    loading: boolean;
    isError: boolean;
    onRowSelect: (state: { selectedRows: BookT[] }) => void;
}

function BooksTable({ data, loading, isError, onRowSelect }: BooksTableProps) {
    const columns: TableColumn<BookT>[] = [
        { name: 'ID', selector: (row) => row.id, sortable: true, id: 1 },
        { name: 'ISBN', selector: (row) => row.isbn, sortable: true },
        { name: 'Title', selector: (row) => row.title, sortable: true },
        // { name: 'Author', selector: (row) => row.author, sortable: true },
        { name: 'Publisher', selector: (row) => row.publisher, sortable: true },
        { name: 'Price', selector: (row) => row.price, sortable: true },
        { name: 'Stock', selector: (row) => row.stock, sortable: true },
    ];

    return (
        <>
            {loading ? (
                <p className="loading-text">Loading...</p>
            ) : isError ? (
                <p className="error-text">An error occurred while fetching data!</p>
            ) : data.length === 0 ? (
                <p className="empty-text">No books found.</p>
            ) : (
                <div className="books-table-container">
                    <DataTable
                        title="Books"
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

export default BooksTable;

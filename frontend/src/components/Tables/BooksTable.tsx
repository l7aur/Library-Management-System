import DataTable, { TableColumn } from 'react-data-table-component';
import { BookT } from "../../types/BookT.tsx";

interface BooksTableProps {
    data: BookT[];
    loading: boolean;
    isError: boolean;
    onRowSelect: (state: { selectedRows: BookT[] }) => void;
}

function BooksTable({ data, loading, isError, onRowSelect }: BooksTableProps) {
    const columns: TableColumn<BookT>[] = [
        { name: 'ISBN', selector: (row) => row.isbn, sortable: true, id: 1 },
        { name: 'Title', selector: (row) => row.title, sortable: true },
        {
            name: 'Authors',
            selector: (row) => row.authors.map((author) => `${author.firstName} ${author.lastName}`).join(', '), // Join authors' names
            sortable: true,
        },
        {
            name: 'Publisher',
            selector: (row) => row.publisher.name,
            sortable: true,
        },
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
                <div className="books-table-container"
                     style={{
                         width: "90vw",
                         margin: "20px auto",
                         overflowX: "auto",
                     }}>
                    <DataTable
                        theme={'dark'}
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

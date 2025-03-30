import DataTable, {TableColumn} from "react-data-table-component";
import BookType from "../../types/BookType.tsx";

interface Props {
    data: BookType[]
    loading: boolean
    isError: boolean
    onRowSelect: (state: {selectedRows: BookType[]}) => void
    clearSelection: boolean
}

export default function BooksTable({data, loading, isError, onRowSelect, clearSelection}: Props) {
    const columns: TableColumn<BookType>[] = [
        { name: 'ISBN', selector: (row) => row.isbn, sortable: true, id: 1 },
        { name: 'Title', selector: (row) => row.title, sortable: true },
        { name: 'Price', selector: (row) => row.price, sortable: true },
        { name: 'Stock', selector: (row) => row.stock, sortable: true }
    ];

    const isEmpty = data && data.length === 0;

    if (loading) {
        return (<p className="info-text">Loading...</p>);
    }

    if (isError) {
        return (<p className="error-text">Error...</p>);
    }

    if (isEmpty) {
        return (<p className="info-text">No books have been found</p>)
    }

    return (
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
            clearSelectedRows={clearSelection}
        />
    )
}
import DataTable, {TableColumn} from "react-data-table-component";
import {BookTypeImpl} from "../../types/BookType.tsx";

interface Props {
    data: BookTypeImpl[]
    loading: boolean
    isError: boolean
    onRowSelect: (state: {selectedRows: BookTypeImpl[]}) => void
    clearSelection: boolean
}

export default function BooksTable({data, loading, isError, onRowSelect, clearSelection}: Props) {
    console.log("table ", data);
    const columns: TableColumn<BookTypeImpl>[] = [
        { name: 'ISBN', selector: (row) => row.isbn, sortable: true, id: 1 },
        { name: 'Title', selector: (row) => row.title, sortable: true },
        { name: 'Authors', selector: (row) => row.authors.map(a => a.firstName + " " + a.lastName).join(", "), sortable: true },
        { name: 'Publication Year', selector: (row) => row.publishYear, sortable: true },
        { name: 'Publisher', selector: (row) => row.publisher.name, sortable: true },
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
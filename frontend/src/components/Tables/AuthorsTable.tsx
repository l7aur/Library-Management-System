import DataTable, {TableColumn} from "react-data-table-component";
import "./Table.css"
import {AuthorType} from "../../types/AuthorType.tsx";

interface Props {
    data: AuthorType[]
    loading: boolean
    isError: boolean
    onRowSelect: (state: {selectedRows: AuthorType[]}) => void
    clearSelection: boolean
}

export default function AuthorsTable({data, loading, isError, onRowSelect, clearSelection}: Props) {
    const columns: TableColumn<AuthorType>[] = [
        { name: 'First Name', selector: (row) => row.firstName, sortable: true, id: 1 },
        { name: 'Last Name', selector: (row) => row.lastName, sortable: true },
        { name: 'Alias', selector: (row) => row.alias, sortable: true },
        { name: 'Nationality', selector: (row) => row.nationality, sortable: true },
        { name: 'Books', selector: (row) => row.books?.join(", "), sortable: true }
    ];

    const isEmpty = data && data.length === 0;

    if (loading) {
        return (<p className="info-text">Loading...</p>);
    }

    if (isError) {
        return (<p className="error-text">Error...</p>);
    }

    if (isEmpty) {
        return (<p className="info-text">No authors have been found</p>)
    }

    return (
        <DataTable
            theme={'dark'}
            title="Authors"
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
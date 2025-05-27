import DataTable, {TableColumn} from "react-data-table-component";
import "./Table.css"
import PublisherType from "../../types/PublisherType.tsx";

interface Props {
    data: PublisherType[]
    loading: boolean
    isError: boolean
    onRowSelect: (state: {selectedRows: PublisherType[]}) => void
    clearSelection: boolean
}

export default function PublishersTable({data, loading, isError, onRowSelect, clearSelection}: Props) {
    const columns: TableColumn<PublisherType>[] = [
        { name: 'Name', selector: (row) => row.name, sortable: true, id: 1 },
        { name: 'Location', selector: (row) => row.location, sortable: true },
        { name: 'FoundingYear', selector: (row) => row.foundingYear, sortable: true }
    ];

    const isEmpty = data && data.length === 0;

    if (loading) {
        return (<p className="info-text">Loading...</p>);
    }

    if (isError) {
        return (<p className="error-text">Error...</p>);
    }

    if (isEmpty) {
        return (<p className="info-text">No publishers have been found</p>)
    }

    return (
        <DataTable
            theme={'dark'}
            title="Publishers"
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
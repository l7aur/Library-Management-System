import {useState} from "react";
import PublisherType from "../types/PublisherType.tsx";
import PublishersTable from "../components/tables/PublishersTable.tsx";
import useFindAllPublishers from "../hooks/useFindAllPublishers.tsx";

const PublishersPage = () => {
    const {data, setData, loading, isError} = useFindAllPublishers();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedPublishers, setSelectedPublishers] = useState<PublisherType[]>([]);

    const onRowSelect = (state: { selectedRows: PublisherType[] }) => {
        setSelectedPublishers(state.selectedRows);
    };

    return (
        <div>
            <PublishersTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={onRowSelect}
                clearSelection={clearSelection}
            />
        </div>
    )
}

export default PublishersPage;
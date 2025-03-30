import {useState} from "react";
import useFindAllAuthors from "../hooks/useFindAllAuthors.tsx";
import {AuthorType} from "../types/AuthorType.tsx";
import AuthorsTable from "../components/tables/AuthorsTable.tsx";

const AuthorsPage = () => {
    const {data, setData, loading, isError} = useFindAllAuthors();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedAuthors, setSelectedAuthors] = useState<AuthorType[]>([]);

    const onRowSelect = (state: { selectedRows: AuthorType[] }) => {
        setSelectedAuthors(state.selectedRows);
    };

    return (
        <div>
            <AuthorsTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={onRowSelect}
                clearSelection={clearSelection}
            />
        </div>
    )
}

export default AuthorsPage;
import {useState} from "react";
import BookType from "../types/BookType.tsx";
import BooksTable from "../components/tables/BooksTable.tsx";
import useFindAllBooks from "../hooks/useFindAllBooks.tsx";

const BooksPage = () => {
    const {data, setData, loading, isError} = useFindAllBooks();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedBooks, setSelectedBooks] = useState<BookType[]>([]);

    const onRowSelect = (state: { selectedRows: BookType[] }) => {
        setSelectedBooks(state.selectedRows);
    };

    return (
        <div>
            <BooksTable
                data={data}
                loading={loading}
                isError={isError}
                onRowSelect={onRowSelect}
                clearSelection={clearSelection}
            />
        </div>
    )
}

export default BooksPage;
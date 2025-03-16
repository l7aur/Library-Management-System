import React from "react";
import {fetchBooks} from "../services/BookService.ts";
import {BookT} from "../types/BookT.tsx";

function useFetchBooks() {
    const [data, setData] = React.useState<BookT[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [isError, setIsError] = React.useState<boolean>(false);

    React.useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                const response = await fetchBooks();
                setData(response);
            } catch (error) {
                console.error(error);
                setIsError(true);
            }
            finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    return { data, loading, isError };
}

export default useFetchBooks;
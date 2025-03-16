import AuthorT from "../types/AuthorT.tsx";
import {fetchAuthors} from "../services/AuthorService.ts";
import React from "react";

function useFetchAuthors() {
    const [data, setData] = React.useState<AuthorT[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [isError, setIsError] = React.useState<boolean>(false);

    React.useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                const response = await fetchAuthors();
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

export default useFetchAuthors;
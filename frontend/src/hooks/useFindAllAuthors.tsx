import * as React from "react";
import {findAll} from "../services/AuthorService.ts";
import {AuthorType} from "../types/AuthorType.tsx";

export default function useFindAllAuthors() {
    const [data, setData] = React.useState<AuthorType[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [isError, setIsError] = React.useState<boolean>(false);

    React.useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                const response = await findAll();
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

    return { data, setData, loading, isError };
}
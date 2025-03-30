import * as React from "react";
import PublisherType from "../types/PublisherType.tsx";
import {findAll} from "../services/PublisherService.ts";

export default function useFindAllBooks() {
    const [data, setData] = React.useState<PublisherType[]>([]);
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
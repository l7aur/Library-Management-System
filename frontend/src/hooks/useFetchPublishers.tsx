import React from "react";
import PublisherT from "../types/PublisherT.tsx";
import {fetchPublishers} from "../services/PublisherService.ts";

function useFetchPublishers() {
    const [data, setData] = React.useState<PublisherT[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [isError, setIsError] = React.useState<boolean>(false);

    React.useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                const response = await fetchPublishers();
                setData(response);
                setLoading(false);
            } catch (error) {
                console.error(error);
                setIsError(true);
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    return { data, loading, isError };
}

export default useFetchPublishers;
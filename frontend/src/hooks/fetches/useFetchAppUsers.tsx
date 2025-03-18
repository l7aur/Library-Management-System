import {AppUserT} from "../../types/AppUserT.tsx";
import React from "react";
import {fetchAppUsers} from "../../services/AppUserService.ts";

function useFetchAppUsers() {
    const [data, setData] = React.useState<AppUserT[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [isError, setIsError] = React.useState<boolean>(false);

    React.useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                const response = await fetchAppUsers();
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

export default useFetchAppUsers;
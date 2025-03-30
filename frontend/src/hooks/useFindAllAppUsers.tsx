import * as React from "react";
import {AppUserType} from "../types/AppUserType.tsx";
import {findAll} from "../services/AppUserService.ts";

export default function useFindAllAppUsers() {
    const [data, setData] = React.useState<AppUserType[]>([]);
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
import * as React from "react";
import {AppUserType} from "../types/AppUserType.tsx";
import {findAll} from "../services/AppUserService.ts";
import {useEffect} from "react";

export default function useFindAllAppUsers() {
    const [data, setData] = React.useState<AppUserType[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [isError, setIsError] = React.useState<boolean>(false);

    const fetchFindData = async () => {
        try {
            setLoading(true);
            const response = await findAll();
            setData(response || []);
        } catch (error) {
            console.error("Find err ", error);
            setIsError(true);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchFindData().catch((error) => console.error("Error fetching data:", error));
    }, []);

    return {fData: data, setFData: setData, fLoading: loading, isFError: isError, refetch: fetchFindData};
}
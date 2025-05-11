import * as React from "react";
import {findAll} from "../services/AuthorService.ts";
import {AuthorType} from "../types/AuthorType.tsx";
import {useEffect} from "react";
import {useAuth} from "../config/GlobalState.tsx";

export default function useFindAllAuthors() {
    const [data, setData] = React.useState<AuthorType[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [isError, setIsError] = React.useState<boolean>(false);
    const { token } = useAuth();

    const fetchFindData = async (token: string | null) => { //token is already in scope, but we keep it as parameter for clarity
        try {
            setLoading(true);
            setIsError(false); // Reset error state before fetching
            const response = await findAll(token);
            setData(response || []);
        } catch (error) {
            console.error("Find error ", error);
            setIsError(true);
            setData([]); //clear old data
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchFindData(token).catch((error) => console.error("Error fetching data:", error));
    }, [token]); // Dependency on token

    const refetch = () => {
        fetchFindData(token).catch((error) => console.error("Error fetching data:", error));
    }

    return { fData: data, setFData: setData, fLoading: loading, isFError: isError, refetch };
}
import { useState, useEffect } from 'react';
import { AppUserType } from "../types/AppUserType.tsx";
import { findAll } from "../services/AppUserService.ts";
import {useAuth} from "../config/GlobalState.tsx"; // Make sure this path is correct

export default function useFindAllAppUsers() {
    const [data, setData] = useState<AppUserType[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [isError, setIsError] = useState<boolean>(false);
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
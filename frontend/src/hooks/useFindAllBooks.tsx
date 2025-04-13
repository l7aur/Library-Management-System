import * as React from "react";
import BookType from "../types/BookType.tsx";
import {findAll} from "../services/BookService.ts";
import {useEffect} from "react";

export default function useFindAllBooks() {
    const [data, setData] = React.useState<BookType[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [isError, setIsError] = React.useState<boolean>(false);

    const fetchFindData = async () => {
        try {
            setLoading(true);
            const response = await findAll();
            setData(response || []);
        } catch (error) {
            console.error("Find error ", error);
            setIsError(true);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchFindData().catch((error) => console.error("Error fetching data:", error));
    }, []);
    return { fData: data, setFData: setData, fLoading: loading, isFError: isError, refetch: fetchFindData };
}
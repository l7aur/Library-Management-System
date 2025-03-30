import PublisherType from "../types/PublisherType.tsx";
import {PUBLISHERS_GET_ALL_ENDPOINT} from "../constants/API.ts";

export const findAll = async (): Promise<PublisherType[]> => {
    const response = await fetch(PUBLISHERS_GET_ALL_ENDPOINT);
    return response.json();
};
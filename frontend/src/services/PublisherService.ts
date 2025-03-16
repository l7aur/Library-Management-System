import PublisherT from "../types/PublisherT.tsx";
import {PUBLISHERS_ADD_ENDPOINT, PUBLISHERS_GET_ALL_ENDPOINT} from "../constants/api.ts";


export const fetchPublishers = async (): Promise<PublisherT[]> => {
    const response = await fetch(PUBLISHERS_GET_ALL_ENDPOINT);
    return response.json();
};

export const addPublisher = async (newPublisher: PublisherT): Promise<PublisherT> => {
    const response = await fetch(PUBLISHERS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newPublisher),
    });

    if (!response.ok) {
        throw new Error('Failed to add publisher');
    }

    return response.json();
};
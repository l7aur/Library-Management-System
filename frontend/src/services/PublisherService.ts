import PublisherT from "../types/PublisherT.tsx";
import {PUBLISHERS_ADD_ENDPOINT, PUBLISHERS_EDIT_ENDPOINT, PUBLISHERS_GET_ALL_ENDPOINT} from "../constants/api.ts";


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

export const editPublisher = async (newPublisher: PublisherT): Promise<PublisherT> => {
    console.log("Sending request with:", newPublisher);
    const response = await fetch(PUBLISHERS_EDIT_ENDPOINT, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newPublisher),
    });

    if (!response.ok) {
        throw new Error('Failed to edit publisher');
    }

    return response.json();
}
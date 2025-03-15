import PublisherT from "../types/PublisherT.tsx";
import {API_BASE_URL} from "../constants/api.ts";

const API_URL = `${API_BASE_URL}/publishers`;

export const fetchPublishers = async (): Promise<PublisherT[]> => {
    const response = await fetch(API_URL);
    return response.json();
};

export const addPublisher = async (newPublisher: PublisherT): Promise<PublisherT> => {
    const response = await fetch(`${API_URL}/add`, {
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
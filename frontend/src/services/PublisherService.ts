import PublisherType from "../types/PublisherType.tsx";
import {
    PUBLISHERS_ADD_ENDPOINT, PUBLISHERS_DELETE_ENDPOINT, PUBLISHERS_EDIT_ENDPOINT,
    PUBLISHERS_GET_ALL_ENDPOINT
} from "../constants/API.ts";

export const findAll = async (): Promise<PublisherType[]> => {
    const response = await fetch(PUBLISHERS_GET_ALL_ENDPOINT);
    return response.json();
};

export const add = async (newPublisher: PublisherType): Promise<PublisherType> => {
    const response = await fetch(PUBLISHERS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newPublisher),
    });
    return response.json();
}

export const del = async (ids: string[]): Promise<number> => {
    const response = await fetch(PUBLISHERS_DELETE_ENDPOINT, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ids: ids}),
    });
    return response.status;
}

export const update = async (publisher: PublisherType): Promise<PublisherType> => {
    const response = await fetch(PUBLISHERS_EDIT_ENDPOINT, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(publisher),
    });
    return response.json();
}
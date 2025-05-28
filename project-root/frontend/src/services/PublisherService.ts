import PublisherType from "../types/PublisherType.tsx";
import {
    PUBLISHERS_ADD_ENDPOINT,
    PUBLISHERS_DELETE_ENDPOINT,
    PUBLISHERS_EDIT_ENDPOINT,
    PUBLISHERS_GET_ALL_ENDPOINT
} from "../constants/API.ts";

export const findAll = async (token: string | null): Promise<PublisherType[]> => {
    const headers: HeadersInit = {
        'Content-Type': 'application/json',
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`; // Or whatever your auth scheme is
    }

    const response = await fetch(PUBLISHERS_GET_ALL_ENDPOINT, {
        method: 'GET', // Explicitly set the method
        headers: headers,
    });

    if (!response.ok) {
        // Handle errors appropriately.  This is crucial.
        let errorMessage = `Failed to fetch users: ${response.status}`;
        try {
            const errorJson = await response.json();
            if (errorJson && errorJson.message) {
                errorMessage += ` - ${errorJson.message}`;
            }
        } catch (parseError) {
            console.log(parseError);
        }
        throw new Error(errorMessage);
    }

    return response.json();
};

export const add = async (newPublisher: PublisherType, token: string | null): Promise<PublisherType> => {
    const response = await fetch(PUBLISHERS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(newPublisher),
    });

    if(!response.ok)
        throw new Error("Unable to add publisher!");
    return response.json();
}

export const del = async (ids: string[], token: string | null): Promise<number> => {
    const response = await fetch(PUBLISHERS_DELETE_ENDPOINT, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ids: ids}),
    });
    return response.status;
}

export const update = async (publisher: PublisherType, token: string | null): Promise<PublisherType> => {
    const response = await fetch(PUBLISHERS_EDIT_ENDPOINT, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(publisher),
    });

    if(!response.ok)
        throw new Error("Unable to update publisher!");
    return response.json();
}
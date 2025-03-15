import PublisherT from "../types/PublisherT.tsx";

const API_URL = "http://localhost:8080/publishers";

export const fetchPublishers = async (): Promise<PublisherT[]> => {
    const response = await fetch(API_URL);
    return response.json();
};

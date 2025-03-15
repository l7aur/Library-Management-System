import AuthorT from "../types/AuthorT.tsx";

const API_URL = "http://localhost:8080/authors";

export const fetchAuthors = async (): Promise<AuthorT[]> => {
    const response = await fetch(API_URL);
    return response.json();
};

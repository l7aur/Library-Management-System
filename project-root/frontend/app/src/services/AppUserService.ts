import {AppUserType} from "../types/AppUserType.tsx";
import {
    APP_USER_DELETE_ENDPOINT,
    APP_USERS_ADD_ENDPOINT,
    APP_USERS_FILTER_ENDPOINT,
    APP_USERS_GET_ALL_ENDPOINT,
    APP_USERS_LOGIN_ENDPOINT,
    APP_USERS_UPDATE_ENDPOINT
} from "../constants/API.ts";
import LoginType from "../types/LoginType.tsx";
import LoginResponseType from "../types/LoginResponseType.tsx";

export const findAll = async (token: string | null): Promise<AppUserType[]> => {
    const headers: HeadersInit = {
        'Content-Type': 'application/json',
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`; // Or whatever your auth scheme is
    }

    const response = await fetch(APP_USERS_GET_ALL_ENDPOINT, {
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

export const findFiltered = async (
    token: string | null,
    username?: string,
    role?: string,
    firstName?: string,
    lastName?: string
): Promise<AppUserType[]> => {
    try {
        let url = APP_USERS_FILTER_ENDPOINT;
        const queryParams = [];
        if (username)
            queryParams.push(`username=${encodeURIComponent(username)}`);
        if (role)
            queryParams.push(`role=${encodeURIComponent(role)}`);
        if (firstName)
            queryParams.push(`firstName=${encodeURIComponent(firstName)}`);
        if (lastName)
            queryParams.push(`lastName=${encodeURIComponent(lastName)}`);
        if (queryParams.length > 0)
            url += `?${queryParams.join('&')}`;

        const headers: HeadersInit = {
            "Content-Type": "application/json",
        };
        if (token) {
            headers["Authorization"] = `Bearer ${token}`; // Add Authorization header if token exists
        }

        const response = await fetch(url, {
            headers: headers,
        });

        if (!response.ok) {
            const errorData = await response.json();
            console.error('Error fetching filtered users:', errorData);
            throw new Error(`Failed to fetch users: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('An error occurred while fetching filtered users:', error);
        return [];
    }
};

export const add = async (newUser: AppUserType): Promise<AppUserType> => {
    const response = await fetch(APP_USERS_ADD_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newUser),
    });
    return response.json();
}

export const del = async (ids: string[], token: string | null): Promise<number> => {
    const response = await fetch(APP_USER_DELETE_ENDPOINT, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ids: ids}),
    });
    return response.status;
}

export const update = async (user: AppUserType, token: string | null): Promise<AppUserType> => {
    const response = await fetch(APP_USERS_UPDATE_ENDPOINT, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(user),
    });
    return response.json();
}

export const login1 = async (lr: LoginType): Promise<LoginResponseType> => {
    console.log(lr);
    const response = await fetch(APP_USERS_LOGIN_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(lr),
    });

    return await response.json();
};
import CartItemType from "../types/CartItemType.tsx";
import {GET_ORDER_HISTORY, GET_ORDER_NUMBER, PLACE_ORDER_ENDPOINT, SEND_CONF_EMAIL} from "../constants/API.ts";
import {AppUserType} from "../types/AppUserType.tsx";
import {FullOrder, OrderType} from "../types/OrderType.tsx";

export const placeOrder = async(item: CartItemType, user: AppUserType, orderNumber: number, token: string | null): Promise<string> => {
    const order: OrderType = {
        username: user.username,
        orderNumber: orderNumber,
        orderDate: Date.now(),
        bookId: item.id,
        price: item.price,
        quantity: item.quantity
    }
    console.log(order);
    const response = await fetch(PLACE_ORDER_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(order)
    });
    return response.text();
}

export const getOrderNumber = async(token: string | null): Promise<number> => {
    const response = await fetch(GET_ORDER_NUMBER, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        }
    });
    return  response.text().then(parseInt);
}

export const sendConfirmationEmail = async(email: string, orderNumber: number, token: string | null): Promise<string> => {
    const payload = {
        to: email,
        orderNumber: orderNumber,
    }
    console.log(payload);
    const response = await fetch(SEND_CONF_EMAIL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...(token && { Authorization: `Bearer ${token}` }),
        },
        body: JSON.stringify(payload)
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Failed to send confirmation email: ${response.status} - ${errorText}`);
    }

    return response.text();
}

export const getOrderHistory = async (username: string, token: string | null): Promise<Record<string, FullOrder>> => {
    // Construct the URL with the username as a query parameter.
    // encodeURIComponent ensures special characters in the username are properly encoded.
    const url = `${GET_ORDER_HISTORY}?username=${encodeURIComponent(username)}`;

    try {
        // Perform the fetch request to the backend API.
        const response = await fetch(url, {
            method: 'GET', // HTTP GET method for retrieving data
            headers: {
                'Content-Type': 'application/json', // Inform the server we expect JSON
                // Include the Authorization header with the bearer token if available.
                // This is crucial for authenticated endpoints.
                ...(token && {Authorization: `Bearer ${token}`}), // Only add if token exists
            },
        });

        // Check if the HTTP response status indicates an error (e.g., 4xx or 5xx).
        if (!response.ok) {
            let errorMessage = `HTTP error! status: ${response.status}`;
            // Attempt to parse error details from the response body if it's JSON.
            try {
                const errorData = await response.json();
                if (errorData && errorData.message) {
                    errorMessage += ` - ${errorData.message}`;
                } else if (errorData) {
                    errorMessage += ` - ${JSON.stringify(errorData)}`;
                }
            } catch (jsonError) {
                // If parsing JSON fails, the body might not be JSON or might be empty.
                errorMessage += ` - Could not parse error response body.` + jsonError;
            }

            // Throw an error with a descriptive message.
            // This allows the calling code to catch and handle specific error scenarios.
            throw new Error(errorMessage);
        }

        // Parse the JSON response body into a JavaScript object.
        // Note: response.json() will parse a JSON object into a plain JavaScript object,
        // not a JavaScript Map. The type `Record<string, FullOrder>` reflects this.
        return await response.json();

    } catch (error) {
        // Catch any network errors (e.g., no internet connection, DNS issues)
        // or errors thrown from the `if (!response.ok)` block.
        console.error("Failed to fetch order history:", error);
        // Re-throw the error to allow the calling component to handle it (e.g., display an error message to the user).
        throw error;
    }
}
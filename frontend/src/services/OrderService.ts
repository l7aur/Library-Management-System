import CartItemType from "../types/CartItemType.tsx";
import {GET_ORDER_NUMBER, PLACE_ORDER_ENDPOINT, SEND_CONF_EMAIL} from "../constants/API.ts";
import {AppUserType} from "../types/AppUserType.tsx";
import OrderType from "../types/OrderType.ts";

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
import React, {useEffect, useState} from 'react';
import "./OrderPage.css"
import {FullOrder} from "../types/OrderType.tsx";
import {getOrderHistory} from "../services/OrderService.ts";
import {useAuth} from "../config/GlobalState.tsx";

const OrderHistoryPage: React.FC = () => {
    const [orders, setOrders] = useState<Record<number, FullOrder>>({});
    const {user, token} = useAuth();

    useEffect(() => {
        const fetchOrders = async () => {
            const fetchedOrders = await getOrderHistory(user ? user.username : "err", token);
            console.log(fetchedOrders);
            setOrders(fetchedOrders);
        };

        if (user?.username && token) {
            fetchOrders().then(r => console.log(r));
        }
    }, [user, token]);

    function computeTotal(fullOrder: FullOrder) {
        let total = 5.0;
        fullOrder.items.forEach(item => {
            total += item.quantity * item.price
        });
        return total;
    }

    if (Object.keys(orders).length === 0) {
        return <>
            <div className="container">
                <div className="content-wrapper">
                    <h1 className="title">Your Order History</h1>
                    <div className="no-orders-message">
                        No order history found for {user?.username}.
                    </div>
                </div>
            </div>
        </>
    }

    return (
        <>
            <div className="container">
                <div className="content-wrapper">
                    <h1 className="title">
                        Your Order History
                    </h1>
                    <div className="orders-list">
                        {Object.entries(orders)
                            .reverse()
                            .map(([id, order]) => (
                                <div
                                    key={id}
                                    className="order-card">
                                    <div className="order-header">
                                        <div>
                                            <h2 className="order-id">Order ID: <span>{id}</span></h2>
                                            <p className="order-date">Date: {order.date}</p>
                                        </div>
                                        <div className="order-summary">
                                            <span className={`status-badge status-processing`}>Processing</span>
                                            <span className="order-total">Total: ${computeTotal(order).toFixed(2)}</span>
                                        </div>
                                    </div>
                                    <div className="order-items-section">
                                        <h3 className="order-items-title">Items:</h3>
                                        <ul className="order-items-list">
                                            {order.items.map((item, index) => (
                                                <li key={index} className="order-item">
                                                    <span>{item.title} (x{item.quantity})</span>
                                                    <span>${item.price.toFixed(2)}</span>
                                                </li>))}
                                        </ul>
                                    </div>
                                </div>
                            ))}
                    </div>
                </div>
            </div>
        </>
    );
};

export default OrderHistoryPage;

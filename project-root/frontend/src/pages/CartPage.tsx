import { useState, useEffect } from "react";
import { useAuth } from "../config/GlobalState.tsx";
import styles from "./CartPage.module.css";
import CartItemType from "../types/CartItemType.tsx";
import bookImg from "./bookImg.png";
import {getOrderNumber, placeOrder, sendConfirmationEmail} from "../services/OrderService.ts";

const useCart = () => {
    const [cartItems, setCartItems] = useState<CartItemType[]>(() => {
        try {
            const savedCart = localStorage.getItem('shoppingCart');
            return savedCart ? JSON.parse(savedCart) : [];
        } catch (error) {
            console.error("Failed to load cart from local storage:", error);
            return [];
        }
    });

    useEffect(() => {
        try {
            // Save cart to local storage whenever it changes
            localStorage.setItem('shoppingCart', JSON.stringify(cartItems));
        } catch (error) {
            console.error("Failed to save cart to local storage:", error);
        }
    }, [cartItems]);

    const addToCart = (item: Omit<CartItemType, 'quantity'>) => {
        setCartItems((prevItems) => {
            const existingItem = prevItems.find((cartItem) => cartItem.id === item.id);
            if (existingItem) {
                return prevItems.map((cartItem) =>
                    cartItem.id === item.id
                        ? { ...cartItem, quantity: cartItem.quantity + 1 }
                        : cartItem
                );
            }
            return [...prevItems, { ...item, quantity: 1 }];
        });
    };

    const removeFromCart = (id: string) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.id !== id));
    };

    const updateQuantity = (id: string, newQuantity: number) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.id === id ? { ...item, quantity: Math.max(1, newQuantity) } : item
            )
        );
    };

    const clearCart = () => {
        setCartItems([]);
    };

    return { cartItems, addToCart, removeFromCart, updateQuantity, clearCart };
};

function CartPage() {
    const { isAuthenticated, user, token } = useAuth();
    const { cartItems, removeFromCart, updateQuantity, clearCart } = useCart();

    const subtotal = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
    const shippingCost = subtotal > 0 ? 5.00 : 0;
    const total = subtotal + shippingCost;

    const handleQuantityChange = (id: string, change: number) => {
        const item = cartItems.find(item => item.id === id);
        if (item) {
            updateQuantity(id, item.quantity + change);
        }
    };

    const handleRemoveItem = (id: string) => {
        removeFromCart(id);
    };

    const handleCheckout = async () => {
        if (!isAuthenticated || !user) {
            alert("Please log in to proceed to checkout.");
            return;
        }

        try {
            const orderNumber = await getOrderNumber(token);

            if (!orderNumber) {
                throw new Error("Failed to generate order number.");
            }

            await Promise.all(
                cartItems.map(item =>
                    placeOrder(item, user, orderNumber, token).then((response: string) => {
                        if (response !== "ok") throw new Error(response);
                    })
                )
            );

            const emailResponse = await sendConfirmationEmail(user.email, orderNumber, token);
            if (emailResponse !== "ok") {
                throw new Error(emailResponse);
            }

        } catch (err) {
            console.error("Checkout failed:", err);
            alert("There was an issue processing your order. Please try again.");
        }
    };



    return (
        <div className={styles.cartPage}>
            <header className={styles.cartHeader}>
                <h1>Your Shopping Cart</h1>
                {cartItems.length > 0 && (
                    <button className={styles.clearCartButton} onClick={clearCart}>
                        Clear Cart
                    </button>
                )}
            </header>

            {cartItems.length === 0 ? (
                <div className={styles.emptyCartMessage}>
                    <p>Your cart is empty.</p>
                    <p>Why not browse our amazing books?</p>
                </div>
            ) : (
                <div className={styles.cartContent}>
                    <div className={styles.cartItemsList}>
                        {cartItems.map((item) => (
                            <div key={item.id} className={styles.cartItem}>
                                <img src={bookImg} alt={item.name} className={styles.itemImage} />
                                <div className={styles.itemDetails}>
                                    <h3>{item.name}</h3>
                                    <p>Price: ${item.price.toFixed(2)}</p>
                                    <div className={styles.quantityControl}>
                                        <button onClick={() => handleQuantityChange(item.id, -1)} disabled={item.quantity === 1}>
                                            -
                                        </button>
                                        <span>{item.quantity}</span>
                                        <button onClick={() => handleQuantityChange(item.id, 1)}>
                                            +
                                        </button>
                                    </div>
                                    <p>Total: ${(item.price * item.quantity).toFixed(2)}</p>
                                    <button
                                        className={styles.removeItemButton}
                                        onClick={() => handleRemoveItem(item.id)}
                                    >
                                        Remove
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>

                    <div className={styles.cartSummary}>
                        <h2>Order Summary</h2>
                        <div className={styles.summaryRow}>
                            <span>Subtotal:</span>
                            <span>${subtotal.toFixed(2)}</span>
                        </div>
                        <div className={styles.summaryRow}>
                            <span>Shipping:</span>
                            <span>{shippingCost > 0 ? `$${shippingCost.toFixed(2)}` : 'FREE'}</span>
                        </div>
                        <div className={`${styles.summaryRow} ${styles.totalRow}`}>
                            <span>Total:</span>
                            <span>${total.toFixed(2)}</span>
                        </div>
                        <button className={styles.checkoutButton} onClick={handleCheckout}>
                            Proceed to Checkout
                        </button>
                    </div>
                </div>
            )}

            <footer className="title_container">
                <p>&copy; 2025 L7aur's Bookshop. All rights reserved.</p>
            </footer>
        </div>
    );
}

export default CartPage;
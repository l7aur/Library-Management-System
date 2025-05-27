import React, { useState, useEffect } from 'react';
import { useAuth } from "../config/GlobalState.tsx";
import useFindAllBooks from "../hooks/useFindAllBooks.tsx";
import {BookTypeImpl} from "../types/BookType.tsx";
import "./Buy.css";
import bookImg from "./bookImg.png";
import CartItemType from "../types/CartItemType.tsx";

// BookCard Component
interface BookCardProps {
    book: BookTypeImpl;
    onAddToCart: (book: BookTypeImpl) => void;
}

const BookCard: React.FC<BookCardProps> = ({ book, onAddToCart }) => {
    return (
        <div className="book_card">
            <img src={bookImg} alt={book.title} className="book_image" />
            <h3>{book.title}</h3>
            <p>
                by {Array.isArray(book.authors)
                ? book.authors
                    .map(a => `${a.firstName || "Unknown"} ${a.lastName || ""}`)
                    .join(', ')
                : "Unknown"}
            </p>

            <p className="book_price">${book.price.toFixed(2)}</p>
            <button onClick={() => onAddToCart(book)} className="add_to_cart_button">
                Add to Cart
            </button>
        </div>
    );
};

function BuyPage() {
    const { isAuthenticated, user } = useAuth();
    const { fData, fLoading, isFError, refetch } = useFindAllBooks();

    const [cart, setCart] = useState<CartItemType[]>(() => {
        try {
            const storedCart = localStorage.getItem('shoppingCart');
            return storedCart ? JSON.parse(storedCart) : [];
        } catch (error) {
            console.error("Failed to parse cart from localStorage:", error);
            return [];
        }
    });

    useEffect(() => {
        try {
            localStorage.setItem('shoppingCart', JSON.stringify(cart));
        } catch (error) {
            console.error("Failed to save cart to localStorage:", error);
        }
    }, [cart]);

    const handleAddToCart = (bookToAdd: BookTypeImpl) => {
        setCart(prevCart => {
            const existingItem = prevCart.find(item => item.id === bookToAdd.isbn);
            if (existingItem) {
                return prevCart.map(item =>
                    item.id === bookToAdd.id ? { ...item, quantity: item.quantity + 1, price: item.price, name: item.name } : item
                );
            } else {
                return [...prevCart, { ...bookToAdd, quantity: 1, id: bookToAdd.id, price: bookToAdd.price, name: bookToAdd.title }];
            }
        });
    };

    useEffect(() => {
        if (isFError) {
            console.error("Error fetching books, attempting to refetch...");
        }
    }, [isFError, refetch]);

    return (
        <div className="page_container">
            <header className="title_container">
                <h1>Our Books</h1>
            </header>
            <div className="title_container">
                {isAuthenticated && user && <p>Welcome, {user.username}!</p>}
                {!isAuthenticated && <p>Please log in to make a purchase.</p>}
            </div>
            <main className="buy_page_content">
                <section className="books_list_section">
                    <h2>Available Books</h2>
                    {fLoading && <p>Loading books...</p>}
                    {isFError && <p className="error_message">Error loading books. Please try again.</p>}
                    {!fLoading && !isFError && fData && fData.length > 0 ? (
                        <div className="books_grid">
                            {fData.map((book: BookTypeImpl) => (
                                (book.stock > 0) && <BookCard key={book.id} book={book} onAddToCart={handleAddToCart} />)
                            )}
                        </div>
                    ) : (
                        !fLoading && !isFError && <p>No books available at the moment.</p>
                    )}
                </section>
            </main>

            <footer className="title_container">
                <p>&copy; 2025 L7aur's Bookshop. All rights reserved.</p>
            </footer>
        </div>
    );
}

export default BuyPage;
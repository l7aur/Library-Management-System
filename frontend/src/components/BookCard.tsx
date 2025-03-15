import React from "react";
import {BookT} from "../types/BookT.tsx";

interface BookCardProps {
    book: BookT;
}

const BookCard: React.FC<BookCardProps> = ({ book }) => {
    return (
        <div className="border p-4 rounded shadow">
            <h3 className="text-lg font-bold mt-2">{book.title}</h3>
            <p className="text-sm text-gray-600">{book.author}</p>
            <button className="mt-2 px-4 py-2 bg-blue-500 text-white rounded">Add to Cart</button>
        </div>
    );
};

export default BookCard;

import { useState } from 'react';
import "./FilterSearchBar.css"
import * as React from "react";

type BookSearchBarProps = {
    onSearch: (title: string, stock: number) => void;
};

function BookSearchBar({ onSearch } : BookSearchBarProps) {
    const [title, setTitle] = useState('');
    const [stock, setStock] = useState(0);

    const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setTitle(event.target.value);
    };
    const handleStockChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setStock(parseInt(event.target.value));
    };

    const handleSearchClick = () => {
        if (onSearch) {
            onSearch(title, stock);
        }
    };

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                    <input
                        className="input_container"
                        type="text"
                        id="title"
                        placeholder="Title containing..."
                        value={title}
                        onChange={handleTitleChange}
                    />
                    <input
                        className="input_container"
                        type="number"
                        id="stock"
                        placeholder="Stock smaller than..."
                        value={stock}
                        onChange={handleStockChange}
                    />
            </div>
            <button onClick={handleSearchClick}>Search</button>
        </div>
    );
}

export default BookSearchBar;
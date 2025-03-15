import { Link } from "react-router-dom";
import React from "react";

const Home: React.FC = () => {

    return (
        <div className="flex flex-col items-center min-h-screen w-full max-w-screen">

                <h1 className="text-4xl font-bold">Welcome to Laurentiu's Bookstore</h1>
                <p className="mt-2 text-lg">Discover our books</p>
                <Link to="/books">
                    <button className="mt-4 px-6 py-3 bg-white text-blue-600 font-semibold rounded shadow-md hover:bg-gray-200">
                        Browse Books
                    </button>
                </Link>

                <p className="mt-2 text-lg">Discover our authors</p>
                <Link to="/authors">
                    <button className="mt-4 px-6 py-3 bg-white text-blue-600 font-semibold rounded shadow-md hover:bg-gray-200">
                        Browse Authors
                    </button>
                </Link>

                <p className="mt-2 text-lg">Discover our publishers</p>
                <Link to="/publishers">
                    <button className="mt-4 px-6 py-3 bg-white text-blue-600 font-semibold rounded shadow-md hover:bg-gray-200">
                        Browse Publishers
                    </button>
                </Link>

            <footer className="bg-gray-800 text-white text-center py-4 mt-auto w-full flex justify-center">
                <p>&copy; 2025 Laurentiu. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default Home;

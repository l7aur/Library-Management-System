import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Books from "./pages/Books";
import NotFound from "./pages/NotFound";
import Authors from "./pages/Authors.tsx";
import Publishers from "./pages/Publishers.tsx";
import MyNavbar from "./components/MyNavbar.tsx";
import Cart from "./pages/Cart.tsx";

const App: React.FC = () => {
    return (
        <div className="flex flex-col items-center min-h-screen w-full max-w-screen">
            <Router>
                <MyNavbar/>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/books" element={<Books/>}/>
                    <Route path="/authors" element={<Authors/>}/>
                    <Route path="/publishers" element={<Publishers/>}/>
                    <Route path="/cart" element={<Cart/>}/>
                    <Route path="*" element={<NotFound/>}/>
                </Routes>
            </Router>
        </div>
    );
};

export default App;

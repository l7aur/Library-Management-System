import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Books from "./pages/Books";
import NotFound from "./pages/NotFound";
import Authors from "./pages/Authors.tsx";
import Publishers from "./pages/Publishers.tsx";
import MyNavbar from "./components/MyNavbar.tsx";
import Cart from "./pages/Cart.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";

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
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                    <Route path="*" element={<NotFound/>}/>
                </Routes>
            </Router>
        </div>
    );
};

export default App;

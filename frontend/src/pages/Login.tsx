import React, { useState } from "react";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        setError("");
        console.log("Logging in with:", { username, password });
        // TODO: Add authentication logic (API call)
    };

    return (
        <div className="flex items-center justify-center min-h-screen" style={{ backgroundColor: "#242424" }}>
            <div className="p-6 rounded-2xl shadow-lg w-96 flex flex-col" style={{ backgroundColor: "#343434" }}>
                <h2 className="text-2xl font-semibold text-center text-white mb-4">Login</h2>
                {error && <p className="text-red-500 text-sm text-center">{error}</p>}
                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="p-2 border rounded-lg focus:outline-none focus:ring focus:ring-white text-white" style={{ backgroundColor: "#242424" }}
                        autoComplete="username"
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="p-2 border rounded-lg focus:outline-none focus:ring focus:ring-white text-white" style={{ backgroundColor: "#242424" }}
                        autoComplete="current-password"
                    />
                    <button
                        type="submit"
                        className="bg-blue-500 text-white py-2 rounded-lg hover:ring-white transition"
                    >
                        Login
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Login;

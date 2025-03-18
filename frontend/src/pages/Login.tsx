import React, { useState } from "react";
import {APP_USERS_LOGIN_ENDPOINT} from "../constants/api.ts";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        setError("");
        console.log("Logging in with:", {username, password});

        try {
            const response = await fetch(APP_USERS_LOGIN_ENDPOINT, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    username,
                    password,
                })
            });

            if (!response.ok) {
                let errMsg = "";
                const contentType = response.headers.get("Content-Type");

                if (contentType && contentType.includes("application/json")) {
                    try {
                        const responseData = await response.json();
                        errMsg = Object.values(responseData).join("\n");
                    } catch {
                        errMsg = "Failed to parse JSON response";
                    }
                } else {
                    errMsg = await response.text();
                }

                console.log(errMsg);
                throw new Error(errMsg);
            }
            console.log("Successful login");

        } catch (error) {
            setError((error instanceof Error) ? error.message : "Unable to log in!");
        }
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

import React, { useState } from "react";
import {APP_USERS_ADD_ENDPOINT} from "../constants/api.ts";

const Register = () => {
    const [username, setUsername] = useState("");
    const [role, setRole] = useState("");
    const [fName, setFName] = useState("");
    const [lName, setLName] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirmation, setPasswordConfirmation] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (password != passwordConfirmation) {
            setError("Password and its confirmation must match!");
            return;
        }

        setError("");
        console.log("Logging in with:", {id: "", username, password, role, firstName: `${fName}`, lastName: `${lName}`});
        try {
            const response = await fetch(APP_USERS_ADD_ENDPOINT, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    id: "",
                    username,
                    password,
                    role,
                    firstName: fName,
                    lastName: lName
                })
            });

            if (!response.ok) {
                let errMsg;
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

            console.log("Success:", error);
        } catch (error) {
            setError((error instanceof Error) ? error.message : "Unknown error");
        }

    };

    return (
        <div className="flex items-center justify-center min-h-screen" style={{backgroundColor: "#242424"}}>
            <div className="p-6 rounded-2xl shadow-lg w-96 flex flex-col" style={{backgroundColor: "#343434"}}>
                <h2 className="text-2xl font-semibold text-center text-white mb-4">Welcome to Laurentiu's bookshop</h2>
                {error && <p className="text-red-500 text-sm text-center">{error}</p>}
                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <input
                        type="username"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="p-2 border rounded-lg focus:outline-none focus:ring focus:ring-white text-white"
                        style={{backgroundColor: "#242424"}}
                    />
                    <select
                        value={role}
                        onChange={(e) => setRole(e.target.value)}
                        className="p-2 border rounded-lg focus:outline-none focus:ring focus:ring-white text-white"
                        style={{ backgroundColor: "#242424" }}
                    >
                        <option value="ADMIN">Admin</option>
                        <option value="EMPLOYEE">Employee</option>
                        <option value="CUSTOMER">Customer</option>
                        <option value="UNKNOWN">Unknown</option>
                        <option value="" disabled>Select a role</option>
                    </select>

                    <input
                        type="text"
                        placeholder="First Name"
                        value={fName}
                        onChange={(e) => setFName(e.target.value)}
                        className="p-2 border rounded-lg focus:outline-none focus:ring focus:ring-white text-white"
                        style={{backgroundColor: "#242424"}}
                        autoComplete="given-name"
                    />
                    <input
                        type="text"
                        placeholder="Last Name"
                        value={lName}
                        onChange={(e) => setLName(e.target.value)}
                        className="p-2 border rounded-lg focus:outline-none focus:ring focus:ring-white text-white"
                        style={{backgroundColor: "#242424"}}
                        autoComplete="family-name"
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="p-2 border rounded-lg focus:outline-none focus:ring focus:ring-white text-white"
                        style={{backgroundColor: "#242424"}}
                        autoComplete="new-password"
                    />
                    <input
                        type="password"
                        placeholder="Confirm Password"
                        value={passwordConfirmation}
                        onChange={(e) => setPasswordConfirmation(e.target.value)}
                        className="p-2 border rounded-lg focus:outline-none focus:ring focus:ring-white text-white"
                        style={{backgroundColor: "#242424"}}
                        autoComplete="new-password"
                    />
                    <button
                        type="submit"
                        className="bg-blue-500 text-white py-2 rounded-lg hover:ring-white transition"
                    >
                        Register
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Register;

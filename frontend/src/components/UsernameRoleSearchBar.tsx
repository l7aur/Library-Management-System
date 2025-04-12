import { useState } from 'react';
import "./UsernameRoleSearchBar.css"
import * as React from "react";

type UsernameRoleSearchBarProps = {
    onSearch: (username: string, role: string, firstName: string, lastName: string) => void;
};

function UsernameRoleSearchBar({ onSearch } : UsernameRoleSearchBarProps) {
    const [username, setUsername] = useState('');
    const [role, setRole] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');

    const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(event.target.value);
    };

    const handleRoleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setRole(event.target.value);
    };

    const handleFirstNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFirstName(event.target.value);
    };

    const handleLastNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setLastName(event.target.value);
    };

    const handleSearchClick = () => {
        if (onSearch) {
            onSearch(username, role, firstName, lastName);
        }
    };

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <div className="horizontal-container">
                    <input
                        className="input_container"
                        type="text"
                        id="username"
                        placeholder="Username"
                        value={username}
                        onChange={handleUsernameChange}
                    />
                    <select className="select_container" name="role" value={role} onChange={handleRoleChange}>
                        <option value="">Select a role</option>
                        <option value="ADMIN">Admin</option>
                        <option value="EMPLOYEE">Employee</option>
                        <option value="CUSTOMER">Customer</option>
                    </select>
                </div>
                <div className="horizontal-container">
                    <input
                        className="input_container"
                        type="text"
                        id="firstName"
                        placeholder="First Name"
                        value={firstName}
                        onChange={handleFirstNameChange}
                    />
                    <input
                        className="input_container"
                        type="text"
                        id="lastName"
                        placeholder="Last Name"
                        value={lastName}
                        onChange={handleLastNameChange}
                    />
                </div>
            </div>
            <button onClick={handleSearchClick}>Search</button>
        </div>
    );
}

export default UsernameRoleSearchBar;
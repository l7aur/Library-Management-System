// LoginForm.tsx
import * as React from "react";
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import LoginType from "../../types/LoginType.tsx";
import { login1 } from "../../services/AppUserService.ts";
import { HOME_PATH } from "../../constants/Paths";
import {useAuth} from "../../config/GlobalState.tsx";

const LoginForm: React.FC<LoginType> = () => {
    const [formData, setFormData] = useState({
        username: "",
        password: ""
    });
    const [error, setError] = useState<string>("");
    const {login} = useAuth();
    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent): Promise<void> => {
        e.preventDefault();
        setError("");

        try {
            const result = await login1(formData);
            if(result.appUser === null)
                setError("Failed to login: " + result.errorMessage);
            else{
                login(result);
                navigate(HOME_PATH);
            }
        } catch (err) {
            setError('An unexpected error occurred during login: ' + err);
        }
    };

    return (
        <form className="create_form_container">
            {error.length > 0 && (
                <div className="error-text">
                    {error}
                </div>
            )}
            <input
                className="input_container"
                type="text"
                name="username"
                placeholder="Username"
                value={formData.username}
                onChange={handleChange}
                required
                autoComplete="username"
            />
            <input
                className="input_container"
                type="password"
                name="password"
                placeholder="Password"
                value={formData.password}
                onChange={handleChange}
                required
                autoComplete="current-password"
            />
            <button className="button_container" type="submit" onClick={handleSubmit}>
                Submit
            </button>
        </form>
    );
};

export default LoginForm;
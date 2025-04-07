// LoginForm.tsx
import * as React from "react";
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { useAuth } from "../../config/globalState";
import LoginType from "../../types/LoginType.tsx";
import { login } from "../../services/AppUserService.ts";
import { HOME_PATH } from "../../constants/Paths";

const LoginForm: React.FC<LoginType> = () => {
    const [formData, setFormData] = useState({
        username: "",
        password: ""
    });
    const [error, setError] = useState<string>("");
    const { dispatch } = useAuth();
    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent): Promise<void> => {
        e.preventDefault();
        setError("");
        dispatch({ type: 'AUTH_REQUEST' });

        try {
            const result = await login(formData);

            if ('message' in result) {
                setError(result.message);
                dispatch({ type: 'AUTH_FAILURE', payload: result.message });
            } else {
                console.log('Login successful:', result);
                dispatch({ type: 'LOGIN_SUCCESS', payload: result });
                navigate(HOME_PATH);
            }
        } catch (err) {
            console.error('An unexpected error occurred:', err);
            setError('An unexpected error occurred during login.');
            dispatch({ type: 'AUTH_FAILURE', payload: 'An unexpected error occurred during login.' });
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
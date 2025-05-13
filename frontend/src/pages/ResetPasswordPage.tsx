import React, { useState } from 'react';
import {CHANGE_PASSWORD_ENDPOINT, SEND_MAIL_ENDPOINT} from "../constants/API.ts";
import {useNavigate} from "react-router-dom";
import {HOME_PATH} from "../constants/Paths.ts";

interface ForgotPasswordPageState {
    email: string;
    message: string;
    error: string;
    password: string;
    confirmation: string;
    securityCode: string;
}

const ForgotPasswordPage = () => {
    const [state, setState] = useState<ForgotPasswordPageState>({
        email: '',
        message: '',
        error: '',
        password: '',
        confirmation: '',
        securityCode: ''
    });
    const navigate = useNavigate();

    const sendEmail = async (e: React.FormEvent) => {
        e.preventDefault();
        setState(prevState => ({...prevState, error: '', message: ''}));

        // Basic client-side validation
        if (!state.email) {
            setState(prevState => ({
                ...prevState,
                error: 'Please enter your email address.',
            }));
            return;
        }

        try {
            const response = await fetch(SEND_MAIL_ENDPOINT, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({"to": state.email}),
            });

            if (!response.ok) {
                const errorData = "Unable to send email! Please try again!"
                throw new Error(errorData);
            }

            const result = await response.text() === "true" ? "Success!" : "Failure!";
            setState(prevState => ({...prevState, message: result}));
        } catch (err: unknown) {
            let errorMessage = 'An error occurred. Please try again.';
            if (err instanceof Error) {
                errorMessage = err.message;
            } else if (typeof err === 'string') {
                errorMessage = err;
            }
            setState(prevState => ({
                ...prevState,
                error: errorMessage,
            }));
        } finally {
            setState(prevState => ({...prevState, loading: false}));
        }
    };
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setState(prevState => ({...prevState, error: '', message: ''}));

        try {
            const response = await fetch(CHANGE_PASSWORD_ENDPOINT, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    "email": state.email,
                    "password": state.password,
                    "confirmation": state.confirmation,
                    "securityCode": state.securityCode
                }),
            });

            if (!response.ok) {
                const errorData =  "An error occurred! Failed to reset password! Please generate a new security code!";
                throw new Error(errorData);
            }

            const result = await response.text() === "ok" ? "Success!" : "Failure!";
            setState(prevState => ({...prevState, message: result}));
        } catch (err: unknown) {
            let errorMessage = 'An error occurred. Please try again.';
            if (err instanceof Error) {
                errorMessage = err.message;
            } else if (typeof err === 'string') {
                errorMessage = err;
            }
            setState(prevState => ({
                ...prevState,
                error: errorMessage,
            }));
        } finally {
            setState(prevState => ({...prevState, loading: false}));
        }
    }
    const handleCancel = async (e: React.FormEvent) => {
        e.preventDefault();
        setState(prevState => ({...prevState, error: '', message: ''}));
        navigate(HOME_PATH)
    }

    return (
        <div className="page_container">
            <div className="title_container">
                <h2>Did you forget your password?</h2>
            </div>
            <div className="title_container">
                Fill in your email address to receive the security code, and provide your new password!
            </div>
            <form onSubmit={handleSubmit} className="create_form_container">
                <input
                    className="input_container"
                    id="email"
                    type="email"
                    placeholder="Enter your email"
                    value={state.email}
                    onChange={(e) => setState(prevState => ({...prevState, email: e.target.value}))}
                    autoComplete="email"
                />
                <input
                    className="input_container"
                    id="security-code"
                    type="text"
                    placeholder="Enter the code you received on e-mail!"
                    value={state.securityCode}
                    onChange={(e) => setState(prevState => ({...prevState, securityCode: e.target.value}))}
                />
                <input
                    className="input_container"
                    id="password"
                    type="password"
                    placeholder="Enter your new password"
                    value={state.password}
                    onChange={(e) => setState(prevState => ({...prevState, password: e.target.value}))}
                    autoComplete="current-password"
                />
                <input
                    className="input_container"
                    id="confirmation"
                    type="password"
                    placeholder="Enter your password confirmation"
                    value={state.confirmation}
                    onChange={(e) => setState(prevState => ({...prevState, confirmation: e.target.value}))}
                    autoComplete={"new-password"}
                />
                {state.error && (
                    <p className="text-red-500 text-sm mt-1">{state.error}</p>
                )}
            </form>
            <div className="title_container">
                <button type="submit" className="w-full" onClick={sendEmail}>
                    Send Reset Link
                </button>
                <button type="submit" className="w-full" onClick={handleSubmit}>
                    Submit
                </button>
                <button type="submit" className="w-full" onClick={handleCancel}>
                    Cancel
                </button>
            </div>
            {state.message && (
                <p className="info-text">{state.message}</p>
            )}
        </div>
    );
};

export default ForgotPasswordPage;

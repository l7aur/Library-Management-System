import React, { useState } from 'react';

interface ForgotPasswordPageState {
    email: string;
    message: string;
    error: string;
    loading: boolean;
    password: string;
    confirmation: string;
    securityCode: string;
}

const ForgotPasswordPage = () => {
    const [state, setState] = useState<ForgotPasswordPageState>({
        email: '',
        message: '',
        error: '',
        loading: false,
        password: '',
        confirmation: '',
        securityCode: ''
    });

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setState(prevState => ({ ...prevState, error: '', message: '', loading: true }));

        // Basic client-side validation
        if (!state.email) {
            setState(prevState => ({
                ...prevState,
                error: 'Please enter your email address.',
                loading: false
            }));
            return;
        }

        // Simulate sending a password reset request (replace with your actual API call)
        try {
            // Replace this with your actual API endpoint
            // const response = await fetch('/api/forgot-password', {
            //     method: 'POST',
            //     headers: {
            //         'Content-Type': 'application/json',
            //     },
            //     body: JSON.stringify({ email }),
            // });

            // if (!response.ok) {
            //     const errorData = await response.json();
            //     throw new Error(errorData.message || 'Failed to send reset email.');
            // }

            // const result = await response.json();
            // setMessage(result.message || 'Password reset email sent. Please check your inbox.');

            // Simulate a successful response after 2 seconds
            await new Promise(resolve => setTimeout(resolve, 2000));
            setState(prevState => ({
                ...prevState,
                message: 'Password reset email sent. Please check your inbox.',
            }));

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
            setState(prevState => ({ ...prevState, loading: false }));
        }
    };

    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <div className="w-[350px] p-6 bg-white rounded-lg shadow-md">
                <h2 className="text-2xl font-semibold mb-4">Forgot Password</h2>
                <p className="text-gray-600 mb-6">
                    Enter your email address to receive a password reset link.
                </p>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div className="space-y-2">
                        <label htmlFor="email">Email</label>
                        <input
                            id="email"
                            type="email"
                            placeholder="Enter your email"
                            value={state.email}
                            onChange={(e) => setState(prevState => ({ ...prevState, email: e.target.value }))}
                            autoComplete="email"
                        />
                    </div>
                    <div className="space-y-2">
                        <label htmlFor="security-code">Security Code</label>
                        <input
                            id="security-code"
                            type="text"
                            placeholder="Enter the code you received on e-mail!"
                            value={state.securityCode}
                            onChange={(e) => setState(prevState => ({ ...prevState, securityCode: e.target.value }))}
                        />
                    </div>
                    <div className="space-y-2">
                        <label htmlFor="password">New Password</label>

                        <input
                            id="password"
                            type="password"
                            placeholder="Enter your new password"
                            value={state.password}
                            onChange={(e) => setState(prevState => ({ ...prevState, password: e.target.value }))}
                            autoComplete="current-password"
                        />
                    </div>
                    <div className="space-y-2">
                        <label htmlFor="confirmation">Password Confirmation</label>
                        <input
                            id="confirmation"
                            type="password"
                            placeholder="Enter your password confirmation"
                            value={state.confirmation}
                            onChange={(e) => setState(prevState => ({ ...prevState, confirmation: e.target.value }))}
                            autoComplete={"new-password"}
                        />
                        {state.error && (
                            <p className="text-red-500 text-sm mt-1">{state.error}</p>
                        )}
                    </div>
                    <button type="submit" className="w-full" disabled={state.loading}>
                        {state.loading ? 'Sending...' : 'Send Reset Link'}
                    </button>
                    <button type="submit" className="w-full">
                        Submit
                    </button>
                </form>
                {state.message && (
                    <p className="text-green-500 text-sm mt-4">{state.message}</p>
                )}
                <div className="mt-4">
                    {/* You might want to add a link back to the login page */}
                    {/* <Link to="/login" className="text-blue-500 hover:underline">
                        Back to Login
                    </Link> */}
                </div>
            </div>
        </div>
    );
};

export default ForgotPasswordPage;

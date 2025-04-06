import * as React from "react";
import {useState} from "react";
import LoginType from "../../types/LoginType.tsx";
import {login} from "../../services/AppUserService.ts";

const LoginForm: React.FC<LoginType> = ({username, password}) => {
   const [formData, setFormData] = useState({
       username: username,
       password: password
   })
    const [error, setError] = useState<string>("");

   const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
       setFormData({...formData, [e.target.name]: e.target.value});
   }

    const handleSubmit = async (e: React.FormEvent): Promise<void> => {
        e.preventDefault();
        setError(""); // Clear any previous error

        try {
            const result = await login(formData);

            if ('message' in result) {
                setError(result.message);
            } else {
                console.log('Login successful:', result);
            }
        } catch (err) {
            console.error('An unexpected error occurred:', err);
            setError('An unexpected error occurred during login.');
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
                autoComplete="new-password"
            />
            <button className="button_container" type="submit" onClick={handleSubmit}>
            Submit
        </button>
        </form>
    )
}

export default LoginForm;
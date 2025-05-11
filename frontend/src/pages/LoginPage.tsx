import LoginForm from "../components/forms/LoginForm.tsx";
import { Link } from 'react-router-dom';
import {RESET_PASSWORD_PATH} from "../constants/Paths.ts"; // Import the Link component

const LoginPage = () => {
    return (
        <div className="page_container">
            <div className="title_container">
                <h2>Hello, please login into your account!</h2>
            </div>
            <LoginForm
                username=""
                password=""
            />
            <div className="forgot_password_link">
                <Link to={RESET_PASSWORD_PATH}>Forgot your password?</Link>
            </div>
        </div>
    );
}

export default LoginPage;
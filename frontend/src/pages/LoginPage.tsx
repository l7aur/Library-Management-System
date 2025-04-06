import LoginForm from "../components/forms/LoginForm.tsx";

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
        </div>
    )
}

export default LoginPage;
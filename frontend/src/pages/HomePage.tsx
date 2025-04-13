import {useAuth} from "../config/globalState.tsx";

function HomePage () {
    const {isAuthenticated, user} = useAuth();

    return (
        <div className="home_page">
            {(!isAuthenticated) &&
                <h1>Welcome to L7aur's Bookshop!</h1>
            }
            {(isAuthenticated) && (
                <>
                    <h1>Hello, {user?.firstName || 'User'}!</h1>
                    <p>Welcome back. We're glad you're here.</p>
                </>
            )}
        </div>
    )
}

export default HomePage;
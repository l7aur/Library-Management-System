import {
    APP_USERS_PATH, AUTHORS_PATH, BOOKS_PATH, CART_PATH, HOME_PATH,
    LOGIN_PATH, PUBLISHERS_PATH, REGISTER_PATH
} from "../constants/Paths.ts";
import "./MyNavbar.css"
import {useAuth} from "../config/globalState.tsx";

function MyNavbar () {
    const {isAuthenticated, user, logout} = useAuth();

    return (
        <ul className="my_navbar_container">
            <li>
                <a href={HOME_PATH}>Home</a>
            </li>
            <li>
                <a href={BOOKS_PATH}>Books</a>
            </li>
            <li>
                <a href={AUTHORS_PATH}>Authors</a>
            </li>
            <li>
                <a href={PUBLISHERS_PATH}>Publishers</a>
            </li>
            {(isAuthenticated && user != null && user.role == "ADMIN") &&
                <li>
                    <a href={APP_USERS_PATH}>Users</a>
                </li>
            }
            {isAuthenticated && user != null && user.role != "ADMIN" &&
                <li>
                    <a href={CART_PATH}>Cart</a>
                </li>
            }
            {(!isAuthenticated) &&
                <li>
                    <a href={LOGIN_PATH}>Login</a>
                </li>
            }
            {(!isAuthenticated) &&
                <li>
                    <a href={REGISTER_PATH}>Register</a>
                </li>
            }
            {(isAuthenticated) &&
                <li>
                    <a onClick={logout}>Log out</a>
                </li>
            }
        </ul>
    );
}

export default MyNavbar;
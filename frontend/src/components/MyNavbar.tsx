import {
    APP_USERS_PATH, AUTHORS_PATH, BOOKS_PATH, CART_PATH, HOME_PATH,
    LOGIN_PATH, PUBLISHERS_PATH, REGISTER_PATH
} from "../constants/Paths.ts";
import "./MyNavbar.css"

const MyNavbar = () => {
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
            <li>
                <a href={APP_USERS_PATH}>Users</a>
            </li>
            <li>
                <a href={CART_PATH}>Cart</a>
            </li>
            <li>
                <a href={LOGIN_PATH}>Login</a>
            </li>
            <li>
                <a href={REGISTER_PATH}>Register</a>
            </li>
        </ul>
    );
}

export default MyNavbar;
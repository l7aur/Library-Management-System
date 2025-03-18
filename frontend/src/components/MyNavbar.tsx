import { Nav, Navbar } from "react-bootstrap";
import {useGlobalState} from "../config/GlobalState.tsx";

function MyNavbar() {
    const {isAuthenticated, globalUsername} = useGlobalState();


    return (
        <Navbar className="navbar-custom" variant="dark" expand="lg">
            <div className="flex flex-row justify-between items-center">

                <Nav.Link href="/" className="nav-link-custom mx-3">Home</Nav.Link>
                <Nav.Link href="/books" className="nav-link-custom mx-3">Books</Nav.Link>
                <Nav.Link href="/authors" className="nav-link-custom mx-3">Authors</Nav.Link>
                <Nav.Link href="/publishers" className="nav-link-custom mx-3">Publishers</Nav.Link>
                <Nav.Link href="/app_users" className="nav-link-custom mx-3">AppUsers - Admin View</Nav.Link>
                <Nav.Link href="/cart" className="nav-link-custom mx-3">Cart</Nav.Link>

                {!isAuthenticated && (
                    <>
                        <Nav.Link href="/login" className="nav-link-custom mx-3">Login</Nav.Link>
                        <Nav.Link href="/register" className="nav-link-custom mx-3">Register</Nav.Link>
                    </>
                )}
                {isAuthenticated && (
                    <>
                        <h2>Hello, {globalUsername} </h2>
                    </>
                )}
            </div>
        </Navbar>
    );
}

export default MyNavbar;

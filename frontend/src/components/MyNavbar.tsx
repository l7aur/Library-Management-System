import { Nav, Navbar } from "react-bootstrap";

function MyNavbar() {
    return (
        <Navbar className="navbar-custom" variant="dark" expand="lg">
            <div className="">
                <Nav.Link href="/" className="nav-link-custom mx-3">Home</Nav.Link>
                <Nav.Link href="/books" className="nav-link-custom mx-3">Books</Nav.Link>
                <Nav.Link href="/authors" className="nav-link-custom mx-3">Authors</Nav.Link>
                <Nav.Link href="/publishers" className="nav-link-custom mx-3">Publishers</Nav.Link>
                <Nav.Link href="/cart" className="nav-link-custom mx-3">Cart</Nav.Link>

                <Nav.Link href="/login" className="nav-link-custom mx-3">Login</Nav.Link>
                <Nav.Link href="/register" className="nav-link-custom mx-3">Register</Nav.Link>
            </div>
        </Navbar>
    );
}

export default MyNavbar;

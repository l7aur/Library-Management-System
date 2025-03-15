import { Container, Nav, Navbar } from 'react-bootstrap';

function MyNavbar() {
    return (
        <Navbar className="navbar-custom" variant="dark" expand="lg">
            <Container>
                <Nav className="me-auto justify-content-center w-100">
                    <Nav.Link href="/" className="nav-link-custom mx-3">Home</Nav.Link>
                    <Nav.Link href="/books" className="nav-link-custom mx-3">Books</Nav.Link>
                    <Nav.Link href="/authors" className="nav-link-custom mx-3">Authors</Nav.Link>
                    <Nav.Link href="/publishers" className="nav-link-custom mx-3">Publishers</Nav.Link>
                    <Nav.Link href="/cart" className="nav-link-custom mx-3">Cart</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    );
}

export default MyNavbar;
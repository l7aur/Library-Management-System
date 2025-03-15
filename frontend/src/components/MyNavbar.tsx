import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

function MyNavbar() {
    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Container>
                <Nav className="me-auto justify-content-center w-100">
                    <Nav.Link href="/" className="mx-3">Home</Nav.Link>
                    <Nav.Link href="/books" className="mx-3">Books</Nav.Link>
                    <Nav.Link href="/authors" className="mx-3">Authors</Nav.Link>
                    <Nav.Link href="/publishers" className="mx-3">Publishers</Nav.Link>
                    <Nav.Link href="/cart" className="mx-3">Cart</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    );
}

export default MyNavbar;

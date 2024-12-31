import { Navbar, Nav, Container } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import AuthLinks from './AuthLinks';
import Users from './Employees';

const Header = () => {
    return (
    <Navbar expand="lg" className="bg-body-tertiary">
        <Container>
            <Link className="navbar-brand" to="/">Flight Manager</Link>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="me-auto">
                    <Link className="nav-link" to="/">Home</Link>
                    <Link className="nav-link" to="/flights">Flights</Link>
                    <Link className="nav-link" to="/employees">Служители</Link>
                    <AuthLinks/>
                </Nav>
            </Navbar.Collapse>
        </Container>
    </Navbar>
    )
}

export default Header
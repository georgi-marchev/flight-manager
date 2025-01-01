import { Navbar, Nav, Container } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import useAuth from '../hooks/useAuth';

const Header = () => {

    const { auth } = useAuth();
    console.log(auth);
    const isAmind = auth && auth.authorities && auth.authorities.includes('ROLE_ADMIN');

    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Link className="navbar-brand" to="/">Flight Manager</Link>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Link className="nav-link" to="/">Home</Link>
                        <Link className="nav-link" to="/flights">Flights</Link>
                        {isAmind && (
                            <Link className="nav-link" to="/employees">Служители</Link>
                        )}
                        {!auth.accessToken 
                            ? (<Link className="nav-link" to="/login">Влез</Link>)
                            : (<Link className="nav-link" to="/logout">Излез</Link>)
                        }
                    </Nav>
                </Navbar.Collapse>
                {auth.username && (
                    <span className="fw-bold">{auth.username}</span>
                )}
            </Container>
        </Navbar>
    )
}

export default Header
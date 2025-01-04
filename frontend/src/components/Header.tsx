import { Navbar, Nav, Container } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import useAuth from '../hooks/useAuth';
import CurrentUser from './CurrentUser';

const Header = () => {

    const { auth } = useAuth();
    const isAmind = Boolean(auth && auth.authorities && auth.authorities.includes('ROLE_ADMIN'));
    const isLoggedIn = Boolean(auth && auth.accessToken);

    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Link className="navbar-brand" to="/">Flight Manager</Link>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Link className="nav-link" to="/">Полети</Link>
                        {!isAmind || <Link className="nav-link" to="/flights/create">Създаване на полет</Link>}
                        {!isLoggedIn || <Link className="nav-link" to="/reservations">Резервации</Link>}
                        {!isAmind || <Link className="nav-link" to="/employees">Служители</Link>}
                        {!isAmind || <Link className="nav-link" to="/employees/create">Създаване на служител</Link>}
                    </Nav>
                </Navbar.Collapse>
                {!isLoggedIn 
                    ? (<Link className="nav-link" to="/login">Вход</Link>)
                    : (<CurrentUser />)
                }
            </Container>
        </Navbar>
    )
}

export default Header
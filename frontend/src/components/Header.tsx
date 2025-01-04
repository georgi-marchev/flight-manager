import { Navbar, Nav, Container, NavDropdown  } from 'react-bootstrap'
import { Link, NavLink, useLocation } from 'react-router-dom'
import useAuth from '../hooks/useAuth';
import CurrentUser from './CurrentUser';

const Header = () => {

    const { auth } = useAuth();
    const isAdmin = Boolean(auth && auth.authorities && auth.authorities.includes('ROLE_ADMIN'));
    const isLoggedIn = Boolean(auth && auth.accessToken);
    const location = useLocation(); // Get the current location (URL)
    const isDropdownActive = (path: string) => location.pathname.startsWith(path);
  

    return (
        <header>
            <Navbar expand="lg" bg="dark" variant="dark">
                <Container>
                    <Link className="navbar-brand" to="/">Flight Manager</Link>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <NavLink className="nav-link" to="/" end>Начало</NavLink>
                            
                            {!isAdmin 
                                ? (<NavLink className='nav-link' to="/flights" end>Списък с полети</NavLink>)
                            : (
                                <>
                                    <NavDropdown
                                        title="Полети"
                                        id="flights-nav-dropdown"
                                        menuVariant="dark"
                                        drop="down"
                                        className={isDropdownActive('/flights') ? 'active' : ''}
                                    >
                                        <NavDropdown.Item>
                                            <NavLink to="/flights" className='nav-link' end>Списък с полети</NavLink>
                                        </NavDropdown.Item>
                                        <NavDropdown.Item>
                                            <NavLink to="/flights/create" className='nav-link'>Създаване на полет</NavLink>
                                        </NavDropdown.Item>
                                    </NavDropdown>
                                    <NavDropdown
                                        title="Служители"
                                        id="employees-nav-dropdown"
                                        menuVariant="dark"
                                        drop="down"
                                        className={isDropdownActive('/employees') ? 'active' : ''}
                                    >
                                        <NavDropdown.Item>
                                            <NavLink to="/employees" className='nav-link' end>Списък със служители</NavLink>
                                        </NavDropdown.Item>
                                        <NavDropdown.Item>
                                            <NavLink to="/employees/create" className='nav-link'>Създаване на служител</NavLink>
                                        </NavDropdown.Item>
                                    </NavDropdown>
                                </>
                            )}

                            {!isLoggedIn || <NavLink to="/reservations" className='nav-link'>Резервации</NavLink>}
                            
                            
                        </Nav>
                        <Nav className="ms-auto">
                        {!isLoggedIn 
                                ? (<NavLink className="nav-link" to="/login" end>Вход</NavLink>)
                            : (<CurrentUser />)}
                        </Nav>
                    </Navbar.Collapse>
                    
                </Container>
            </Navbar>
        </header>
        
    )
}

export default Header
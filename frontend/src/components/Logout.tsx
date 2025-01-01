import useAuth from '../hooks/useAuth';
import { Form, Button, Container } from 'react-bootstrap'; 
import { useNavigate } from 'react-router-dom';

const Logout = () => {

    const { setAuth} = useAuth();

    // TODO: Create and implement backend endpoint for logout
    const navigate = useNavigate();

    const handleLogout = async (e) => {
        e.preventDefault();
        // TODO: Extract method
        setAuth({
            accessToken: null,
            refreshToken: null,
            authorities: [],
            username: null
        });
        navigate('/login')
    };

    return (
        <main>
            <Container className="mt-5">
                <h4>Излизане от профил</h4>
                <Form onSubmit={handleLogout}>
                    <Button variant="primary" type="submit">Изход</Button>
                </Form>
            </Container>
        </main>  
    );
};

export default Logout;

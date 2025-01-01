import { useState } from 'react';
import useAuth from '../hooks/useAuth';
import { apiClient } from '../api/apiClient';
import { Form, Button, Col, Container, Row } from 'react-bootstrap'; 
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();
    const { setAuth } = useAuth(); 

    const handleLogin = async (e) => {
        e.preventDefault();
        
        try {
            const response = await apiClient.post('/auth/login', { username, password });
            const { accessToken, refreshToken, authorities } = response.data;
            // TODO: extract method
            setAuth({ accessToken, refreshToken, authorities, username});
            setErrorMessage('');
            navigate(`/`);
        } catch (error) {
            console.log(error);
            setErrorMessage('Login failed');
        }
    };

    return (
        <main className="bg-light py-5">
            <Container className="mt-5">
                <h4>Влизане в профил</h4>
                <Form onSubmit={handleLogin}>
                    <Row>
                        <Form.Group as={Col} className="mb-3" controlId="formUsername">
                            <Form.Label>Потребителско име</Form.Label>
                            <Form.Control 
                            type="text" 
                            placeholder="Потребителско име" 
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                            />
                        </Form.Group>
                    </Row>
                    <Row>
                        <Form.Group as={Col} className="mb-3" controlId="formPassword">
                            <Form.Label>Парола</Form.Label>
                            <Form.Control 
                            type="password" 
                            placeholder="Парола"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            />
                        </Form.Group>
                    </Row>
                    <Button variant="primary" type="submit">Вход</Button>
                </Form>
                {errorMessage && <p>{errorMessage}</p>}
            </Container>
        </main>  
    );
};

export default Login;

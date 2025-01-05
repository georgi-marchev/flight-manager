import { useState } from 'react';
import useAuth from '../hooks/useAuth';
import { apiClient } from '../api/apiClient';
import { Alert, Form, Button, Col, Container, Row, Spinner } from 'react-bootstrap'; 
import { useNavigate } from 'react-router-dom';
import { getErrorMessageOrDefault } from '../utils/responseUtil';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
    const { setAuth } = useAuth(); 

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        
        try {
            const response = await apiClient.post('/auth/login', { username, password });
            const { accessToken, refreshToken, authorities } = response.data;
            setAuth({ accessToken, refreshToken, authorities, username});
            setErrorMessage('');
            navigate(`/`);
        } catch (error) {
            setErrorMessage(getErrorMessageOrDefault(error, 'Неуспешен вход'));
        } finally {
            setIsLoading(false);
        };
    };

    return (
        <main className="bg-light py-5">
            <Container className="mt-5">
                <h1>Влизане в профил</h1>
                {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
                {isLoading && (
                    <div className="text-center">
                        <Spinner animation="border" role="status" />
                    </div>
                )}
                <Form onSubmit={handleLogin} className="border p-4 rounded shadow-sm">
                    <Row>
                        <Form.Group as={Col} controlId="formUsername">
                            <Form.Label>Потребителско име<span className='required-element'>*</span></Form.Label>
                            <Form.Control 
                            type="text" 
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                            />
                        </Form.Group>
                        <Form.Group as={Col} controlId="formPassword">
                            <Form.Label>Парола<span className='required-element'>*</span></Form.Label>
                            <Form.Control 
                            type="password" 
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            />
                        </Form.Group>
                    </Row>
                    <Row>
                        
                    </Row>
                    <Row className="text-center mt-5 mb-3">
                        <Form.Group as={Col} controlId={'submitButton'}>
                            <Button variant="primary" type="submit" disabled={isLoading}>Вход</Button>
                        </Form.Group>
                    </Row>
                </Form>
            </Container>
        </main>  
    );
};

export default Login;

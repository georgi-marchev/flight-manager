import { useState } from 'react';
import { Form, Button, Alert, Spinner, Container, Row, Col } from 'react-bootstrap';
import useAuthenticatedApiClient from '../hooks/useAuthenticatedApiClient';
import { getErrorMessageOrDefault } from '../utils/responseUtil';


function createEmptyEmployeeObj() {
    return {
        username: '',
        password: '',
        email: '',
        firstName: '',
        lastName: '',
        personalIdentificationNumber: '',
        address: '',
        phoneNumber: ''
    };
}

const CreateEmployee = () => {
    const authenticatedApiClient = useAuthenticatedApiClient();

    const [employeeData, setEmployeeData] = useState(createEmptyEmployeeObj());
    
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [loading, setLoading] = useState(false);

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setEmployeeData({ ...employeeData, [name]: value });
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setLoading(true);
        setErrorMessage('');
        try {
            await authenticatedApiClient.post('/employee-accounts', employeeData);
            alert('Служител създаден успешно!');
            setEmployeeData(createEmptyEmployeeObj());
        } catch (err) {
            setErrorMessage(getErrorMessageOrDefault(err, 'Грешка при създаване на служител.'));
            window.scrollTo(0, 0)
        } finally {
            setLoading(false);
        };
    };

    return (
        <main className="bg-light py-5">
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Container>
                <h1 className="text-center text-primary mb-4">Създаване на служител</h1>
                {loading && (
                    <div className="text-center">
                        <Spinner animation="border" role="status" />
                    </div>
                )}

                <section>
                    <Form onSubmit={handleSubmit} className="border p-4 rounded shadow-sm">
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="username">
                                <Form.Label>Потребителско име:</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="username"
                                    value={employeeData.username}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="password">
                                <Form.Label>Парола:</Form.Label>
                                <Form.Control
                                    type="password"
                                    name="password"
                                    value={employeeData.password}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-3"> 
                            <Form.Group as={Col} controlId="firstName">
                                <Form.Label>Име:</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="firstName"
                                    value={employeeData.firstName}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="lastName">
                                <Form.Label>Фамилия</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="lastName"
                                    value={employeeData.lastName}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                        </Row>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="email">
                                <Form.Label>Имейл:</Form.Label>
                                <Form.Control
                                    type="email"
                                    name="email"
                                    value={employeeData.email}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="phoneNumber">
                                <Form.Label>Телефонен номер:</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="phoneNumber"
                                    value={employeeData.phoneNumber}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="personalIdentificationNumber">
                                <Form.Label>ЕГН:</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="personalIdentificationNumber"
                                    value={employeeData.personalIdentificationNumber}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="address">
                                <Form.Label>Адрес:</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="address"
                                    value={employeeData.address}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                        </Row>

                        <Row className="text-center mt-5 mb-3">
                            <Form.Group as={Col} controlId={'submitButton'}>
                                <Button variant="primary" type="submit" disabled={loading}>
                                    Създай служител
                                </Button>
                            </Form.Group>
                        </Row>
                    </Form>
                </section>
            </Container>
        </main>
    );
};

export default CreateEmployee;

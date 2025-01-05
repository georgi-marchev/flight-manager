import { useState, useEffect } from 'react';
import { Form, Button, Alert, Spinner, Container, Row, Col } from 'react-bootstrap';
import useAuthenticatedApiClient from '../hooks/useAuthenticatedApiClient';
import { getErrorMessageOrDefault } from '../utils/responseUtil';
import { useParams, useNavigate } from "react-router-dom";

const UpdateEmployee = () => {

    const { employeeId } = useParams<{ employeeId: string }>();
    const navigate = useNavigate();
    const authenticatedApiClient = useAuthenticatedApiClient();
    const [employeeData, setEmployeeData] = useState({
        username: '',
        newPassword: '',
        email: '',
        firstName: '',
        lastName: '',
        personalIdentificationNumber: '',
        address: '',
        phoneNumber: '',
    });
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const accountResponse = await authenticatedApiClient.get(`/employee-accounts/${employeeId}`);
                setEmployeeData((prev) => ({
                    ...prev,
                    username: accountResponse.data.username,
                    email: accountResponse.data.email,
                    firstName: accountResponse.data.firstName,
                    lastName: accountResponse.data.lastName,
                    personalIdentificationNumber: accountResponse.data.personalIdentificationNumber,
                    address: accountResponse.data.address,
                    phoneNumber: accountResponse.data.phoneNumber,
                  }));
            } catch (error) {
                setErrorMessage(getErrorMessageOrDefault(error, "Грешка при изтегляне на данни. Моля опитайте отново!"));
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [employeeId]);

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setEmployeeData({ ...employeeData, [name]: value });
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setLoading(true);
        setErrorMessage('');
        try {
            console.log(employeeData);
            await authenticatedApiClient.put(`/employee-accounts/${employeeId}`, employeeData);
            alert('Служител актуализиран успешно!');
            navigate('/employees')
        } catch (err) {
            console.log(err);
            setErrorMessage(getErrorMessageOrDefault(err, 'Грешка при актуализиране на служител.'));
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
                                <Form.Label>Потребителско име<span className='required-element'>*</span></Form.Label>
                                <Form.Control
                                    type="text"
                                    name="username"
                                    value={employeeData.username}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="newPassword">
                                <Form.Label>Парола</Form.Label>
                                <Form.Control
                                    type="newPassword"
                                    name="newPassword"
                                    value={employeeData.newPassword}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-3"> 
                            <Form.Group as={Col} controlId="firstName">
                                <Form.Label>Име<span className='required-element'>*</span></Form.Label>
                                <Form.Control
                                    type="text"
                                    name="firstName"
                                    value={employeeData.firstName}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="lastName">
                                <Form.Label>Фамилия<span className='required-element'>*</span></Form.Label>
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
                                <Form.Label>Имейл<span className='required-element'>*</span></Form.Label>
                                <Form.Control
                                    type="email"
                                    name="email"
                                    value={employeeData.email}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="phoneNumber">
                                <Form.Label>Телефонен №<span className='required-element'>*</span></Form.Label>
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
                                <Form.Label>ЕГН<span className='required-element'>*</span></Form.Label>
                                <Form.Control
                                    type="text"
                                    name="personalIdentificationNumber"
                                    value={employeeData.personalIdentificationNumber}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="address">
                                <Form.Label>Адрес<span className='required-element'>*</span></Form.Label>
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
                                    Обнови служител
                                </Button>
                            </Form.Group>
                        </Row>
                    </Form>
                </section>
            </Container>
        </main>
    );
};

export default UpdateEmployee;

import { useState, useEffect } from 'react';
import useAuthenticatedApiClient from '../hooks/useAuthenticatedApiClient';
import Pagination from './Pagination';
import { Alert, Col, Container, Form, Row, Card, ListGroup, Spinner } from 'react-bootstrap';
import { getErrorMessageOrDefault } from '../utils/responseUtil';

const PAGE_SIZES = [10, 25, 50];

interface Employee {
    id: number;
    username: "string";
    email: "string";
    firstName: "string";
    lastName: "string";
}

const Employees = () => {
    const [employees, setEmployees] = useState([]);
    const authenticatedClient = useAuthenticatedApiClient();
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(PAGE_SIZES[0]);
    const [hasNext, setHasNext] = useState(false);
    const [filters, setFilters] = useState({
        username: '',
        email: '',
        firstName: '',
        lastName: ''
    });
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {     
        const fetchEmployees = async () => {
            setIsLoading(true);
            try {
                const response = await authenticatedClient.get('/employee-accounts', {
                    params: {
                        ...filters,
                        page: page,
                        size: size
                    }
                });
                setEmployees(response.data.content);
                setHasNext(response.data.hasNext);

            } catch (error) {
                setErrorMessage(getErrorMessageOrDefault(error, 'Вмомента не може да бъде показана информация за служителите. Опитайте отново!'));
            } finally {
                setIsLoading(false);
            };
        };
    
        fetchEmployees();
    }, [page, size, filters]);

    const handleNextPage = () => {
        if (hasNext) {
            setPage(page + 1);
        }
    };

    const handlePreviousPage = () => {
        if (page > 0) {
            setPage(page - 1);
        }
    };

    const handleSizeChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        setSize(Number(event.target.value));
        setPage(0);
    };

    const handleFilterChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value } = event.target;
        setFilters((prevFilters) => ({
            ...prevFilters,
            [name]: value
        }));
    };

    return (
        <main className="bg-light py-5">
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Container>
                <h1 className="text-center text-primary">Служители</h1>
                {isLoading && (
                    <div className="text-center">
                        <Spinner animation="border" role="status" />
                    </div>
                )}
                <section>
                    <Form>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="username">
                                <Form.Label>Потребителско име</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Потребителско име" 
                                    name="username"
                                    value={filters.username}
                                    onChange={handleFilterChange} 
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="email">
                                <Form.Label>Имейл</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Имейл" 
                                    name="email"
                                    value={filters.email}
                                    onChange={handleFilterChange}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="firstName">
                                <Form.Label>Име</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Име" 
                                    name="firstName"
                                    value={filters.firstName}
                                    onChange={handleFilterChange}
                                />
                            </Form.Group>

                            
                            <Form.Group as={Col} controlId="lastName">
                                <Form.Label>Фамилия</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Фамилия" 
                                    name="lastName"
                                    value={filters.lastName}
                                    onChange={handleFilterChange}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="pageSize">
                                <Form.Label>Резултати на страница</Form.Label>
                                <Form.Select 
                                    aria-label="Резултати на страница" 
                                    value={size}
                                    onChange={handleSizeChange}>
                                        {PAGE_SIZES.map((pageSize) => (
                                            <option key={pageSize} value={pageSize}>
                                                {pageSize}
                                            </option>
                                        ))}
                                </Form.Select>
                            </Form.Group>
                        </Row>
                    </Form>
                </section>
                <section aria-labelledby="employee-list">
                    <Row xs={1} sm={2} md={3} lg={4} className="g-4">
                        {employees.map((employee: Employee) => (

                            <Col key={employee.id} md={6} lg={4} className="mb-4">
                                <Card className="shadow-sm bg-white rounded p-4 d-flex flex-column align-items-center justify-content-center text-center">
                                    <Card.Body>
                                        <Card.Title>
                                            <strong>{employee.firstName} {employee.lastName}</strong>
                                        </Card.Title>
                                        <ListGroup variant="flush">
                                            <ListGroup.Item>
                                                Потребителско име: {employee.username}
                                            </ListGroup.Item>
                                            <ListGroup.Item>
                                                Имейл: <span className="ms-3"><a href={`mailto:${employee.email}`} className="link-primary"> {employee.email}</a></span>
                                            </ListGroup.Item>
                                        </ListGroup>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                </section>
                <section>
                    <Pagination 
                        handlePreviousPage={handlePreviousPage} 
                        handleNextPage={handleNextPage} 
                        pageNum={page} 
                        hasNext={hasNext}
                    />
                </section>
            </Container>
        </main>
    );
}

export default Employees
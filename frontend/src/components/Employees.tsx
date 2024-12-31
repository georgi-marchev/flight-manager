import { useState, useEffect } from 'react';
import useAuthenticatedApiClient from '../hooks/useAuthenticatedApiClient';
import Pagination from './Pagination';
import { Alert, Col, Container, Form, Row, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom'

const PAGE_SIZES = [1, 25, 50];

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

            try {
                const response = await authenticatedClient.get('/employee-accounts', {
                    params: {
                        ...filters,
                        page: page,
                        size: size
                    }
                });

                const data = response.data;
                setEmployees(data.content);
                setHasNext(data.hasNext);

            } catch (err) {
                console.log(err);
                setErrorMessage('Вмомента не може да бъде показана информация за служителите. Опитайте отново!')
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
        setSize(Number(event.target.value)); // Update page size and reset to first page
        setPage(0); // Reset page to 0 when page size changes
    };

    const handleFilterChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value } = event.target;
        setFilters((prevFilters) => ({
            ...prevFilters,
            [name]: value
        }));
    };

    return (
        <main>
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Container>
                <header>
                    <h1>Служители</h1>
                </header>
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
                <section aria-labelledby="employees">
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>Потребителско име</th>
                                <th>Имейл</th>
                                <th>Име</th>
                                <th>Фамилия</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {employees.map((employee: Employee) => (
                            <tr key={employee.id}>
                                <td>{employee.username}</td>
                                <td>{employee.email}</td>
                                <td>{employee.firstName}</td>
                                <td>{employee.lastName}</td>
                                <td>TODO</td>
                            </tr>
                            ))}
                        </tbody>
                    </Table>
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
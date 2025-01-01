import { useState, useEffect } from 'react';
import useAuthenticatedApiClient from '../hooks/useAuthenticatedApiClient';
import Pagination from './Pagination';
import { Alert, Col, Container, Form, Row, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom'

const PAGE_SIZES = [1, 25, 50];

interface Reservation {
    id: number;
    reservationFlight: number;
    contactEmail: "string";
}
    

const Reservations = () => {
    const [reservations, setReservations] = useState([]);
    const authenticatedClient = useAuthenticatedApiClient();

    const [page, setPage] = useState(0);
    const [size, setSize] = useState(PAGE_SIZES[0]);
    const [hasNext, setHasNext] = useState(false);
    const [filters, setFilters] = useState({
        contactEmail: ''
    });
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {     
        const fetchReservations = async () => {

            try {
                const response = await authenticatedClient.get('/reservations', {
                    params: {
                        ...filters,
                        page: page,
                        size: size
                    }
                });

                const data = response.data;
                setReservations(data.content);
                setHasNext(data.hasNext);

            } catch (err) {
                console.log(err);
                setErrorMessage('Вмомента не може да бъде показана информация за резервациите. Моля, опитайте отново!')
            };
        };
    
        fetchReservations();
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
        <main>
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Container>
                <header>
                    <h1>Резервации</h1>
                </header>
                <section>
                    <Form>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="contactEmail">
                                <Form.Label>Имейл</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Имейл" 
                                    name="contactEmail"
                                    value={filters.contactEmail}
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
                                <th>Имейл</th>
                                <th>Полет</th>
                                <th>Детайли</th>
                            </tr>
                        </thead>
                        <tbody>
                            {reservations.map((res: Reservation) => (
                            <tr key={res.id}>
                                <td>{res.contactEmail}</td>
                                <td>
                                    <Link className='link-primary' to={`/flights/${res.reservationFlight}`}>Виж полет</Link>
                                </td>
                                <td>
                                    <Link className='link-primary' to={`/reservations/${res.id}`}> Виж резервация</Link>
                                </td>
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

export default Reservations
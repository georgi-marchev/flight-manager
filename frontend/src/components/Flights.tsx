import { useState, useEffect } from 'react';
import { apiClient } from '../api/apiClient';
import { formatDateTime } from '../utils/dateHelper';
import { Alert, Col, Container, Form, Row, Card, Spinner, ListGroup } from 'react-bootstrap';
import Pagination from './Pagination';
import { Link } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import { getErrorMessageOrDefault } from '../utils/responseUtil';

const PAGE_SIZES = [10, 25, 50];

interface Flight {
    id: number;
    departureTime: string;
    arrivalTime: string;
    departureLocation: string;
    destinationLocation: string;
}

const Flights = () => {
    const now = new Date();
    const localDateNow = new Date(now.getTime() - (now.getTimezoneOffset() * 60000)).toISOString().split('T')[0];
    const [flights, setFlights] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(PAGE_SIZES[0]);
    const [hasNext, setHasNext] = useState(false);
    const [filters, setFilters] = useState({
        departureDate: localDateNow,
        departureLocation: '',
        destinationLocation: '',
        availableSeatsEconomy: '',
        availableSeatsBusiness: ''
    });
    const [errorMessage, setErrorMessage] = useState('');
    const [isLoading, setIsLoading] = useState<boolean>(false);

    const { auth } = useAuth();
    const isLoggedIn = Boolean(auth && auth.accessToken);

    useEffect(() => {
        const fetchFlights = async () => {
            setIsLoading(true);
            try {
                const response = await apiClient.get('/flights', {
                    params: {
                        ...filters,
                        page: page,
                        size: size
                    }
                });
                setFlights(response.data.content);
                setHasNext(response.data.hasNext);
            } catch (err) {
                setErrorMessage(getErrorMessageOrDefault(err, 'Вмомента не може да бъде показана информация за полетите. Опитайте отново!'));
            } finally {
                setIsLoading(false);
            };
        };

        fetchFlights();
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
                <h1 className="text-center text-primary">Полети</h1>
                {isLoading && (
                    <div className="text-center">
                        <Spinner animation="border" role="status" />
                    </div>
                )}
                <section className='mb-5'>
                    <Form>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="departureDate">
                                <Form.Label>Дата:</Form.Label>
                                <Form.Control 
                                    type="date" 
                                    placeholder="Въведете дата" 
                                    name="departureDate"
                                    value={filters.departureDate}
                                    onChange={handleFilterChange} 
                                />
                            </Form.Group>
                        
                            <Form.Group as={Col} controlId="departureLocation">
                                <Form.Label>От:</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Държава, град, или летище" 
                                    name="departureLocation"
                                    value={filters.departureLocation}
                                    onChange={handleFilterChange} 
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="destinationLocation">
                                <Form.Label>До:</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Държава, град, или летище" 
                                    name="destinationLocation"
                                    value={filters.destinationLocation}
                                    onChange={handleFilterChange}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="availableSeatsEconomy">
                                <Form.Label>Места (икономична):</Form.Label>
                                <Form.Control
                                    type="number"
                                    placeholder="Въведете брой" 
                                    name="availableSeatsEconomy"
                                    value={filters.availableSeatsEconomy}
                                    onChange={handleFilterChange}
                                    min={1}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="availableSeatsBusiness">
                                <Form.Label>Места (бизнес):</Form.Label>
                                <Form.Control
                                    type="number"
                                    placeholder="Въведете брой" 
                                    name="availableSeatsBusiness"
                                    value={filters.availableSeatsBusiness}
                                    onChange={handleFilterChange}
                                    min={1}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="pageSize">
                                <Form.Label>Резултати на страница:</Form.Label>
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
                <section className="mb-4">
                    <Row xs={1} sm={2} md={3} lg={4} className="g-4">
                        {flights.map((flight: Flight) => (
                        <Col key={flight.id}>
                            <Card className="shadow-sm d-flex flex-column align-items-center justify-content-center text-center">
                            <Card.Body>
                                <Card.Title>
                                    <strong>{flight.departureLocation}</strong> → <strong>{flight.destinationLocation}</strong>
                                </Card.Title>
                                <Card.Text>
                                    {formatDateTime(flight.departureTime)} - {formatDateTime(flight.arrivalTime)}
                                </Card.Text>
                                <ListGroup variant="flush">
                                    <ListGroup.Item className="d-flex justify-content-center">
                                        <Link to={`${flight.id}/create-reservation`}  className="btn btn-outline-primary me-2">Резервирай</Link>
                                        {isLoggedIn && <Link to={`${flight.id}`}  className="btn btn-outline-primary">Виж полет</Link>}
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
};

export default Flights;
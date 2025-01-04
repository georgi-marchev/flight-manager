import { useState, useEffect } from 'react';
import useAuthenticatedApiClient from '../hooks/useAuthenticatedApiClient';
import Pagination from './Pagination';
import { Alert, Col, Container, Form, Row, Card, ListGroup, Spinner } from 'react-bootstrap';
import { Link } from 'react-router-dom'

const PAGE_SIZES = [10, 25, 50];

interface Reservation {
    id: number;
    reservationFlight: number;
    contactEmail: "string";
}
    

const Reservations = () => {
    const [reservations, setReservations] = useState([]);
    const authenticatedClient = useAuthenticatedApiClient();
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(PAGE_SIZES[0]);
    const [hasNext, setHasNext] = useState(false);
    const [filters, setFilters] = useState({
        contactEmail: ''
    });
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        
        const fetchReservations = async () => {


            setIsLoading(true);

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
            } finally {
                setIsLoading(false);
            }
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
        <main className="bg-light py-5">
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Container>
                
                <header className="mb-4 mt-3">
                    <h1 className="text-center text-primary">Резервации</h1>
                </header>
                <section>
                    <Form>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="contactEmail">
                                <Form.Label>Имейл:</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Имейл" 
                                    name="contactEmail"
                                    value={filters.contactEmail}
                                    onChange={handleFilterChange} 
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
                <section aria-labelledby="reservations-list">
                    
                    {isLoading && (
                        <div className="text-center">
                            <Spinner animation="border" role="status" />
                        </div>
                    )}
                
                    {(reservations?.length === 0 && !isLoading) ? (
                        <Alert variant="primary">Няма резервации</Alert>
                    ) : (
                    <Row xs={1} sm={2} md={3} lg={4} className="g-4">
                        {reservations.map((res: Reservation) => (
                            <Col key={res.id}>
                                <Card className="mt-3 shadow-sm bg-white rounded d-flex flex-column align-items-center justify-content-center text-center">
                                    <Card.Body>
                                        <Card.Title id="reservation" className="mb-3">
                                            <h2><strong>Резервация {res.id}</strong></h2>
                                        </Card.Title>
                                        <ListGroup variant="flush">
                                            <ListGroup.Item className="d-flex justify-content-between">
                                                Имейл: <span className="ms-3"><a href={`mailto:${res.contactEmail}`} className="link-primary"> {res.contactEmail}</a></span>
                                            </ListGroup.Item>
                                            <ListGroup.Item className="d-flex justify-content-between">
                                                
                                                <Link
                                                    to={`/reservations/${res.id}`}
                                                    className="btn btn-outline-primary text-decoration-none"
                                                >
                                                    Виж детайли
                                                </Link>
                                                <Link
                                                    to={`/flights/${res.reservationFlight}`}
                                                    className="btn btn-outline-primary text-decoration-none"
                                                >
                                                    Виж полет
                                                </Link>
                                            </ListGroup.Item>
                                            
                                        </ListGroup>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                    )}
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
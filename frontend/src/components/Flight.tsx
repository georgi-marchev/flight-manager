import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Alert, Container, Card, Col, Form, ListGroup, Row } from 'react-bootstrap';
import { apiClient } from '../api/apiClient';
import useAuthenticatedApiClient from '../hooks/useAuthenticatedApiClient';
import { formatDateTime, getDuration } from '../utils/dateHelper';
import { Link } from 'react-router-dom';
import Pagination from './Pagination';

const PAGE_SIZES = [10, 25, 50];

interface Flight {
    id: number;
    departureLocation: string;
    destinationLocation: string;
    departureTime: string;
    arrivalTime: string;
    availableSeatsEconomy: number;
    availableSeatsBusiness: number;
}

interface Passenger {
    id: number;
    reservationId: number;
    firstName: string;
    middleName: string;
    lastName: string;
    personalIdentificationNumber: string;
}

const Flight = () => {

    const { id } = useParams<{ id: string }>();
    
    const [flight, setFlight] = useState<Flight | null>(null);
    const [passengers, setPassengers] = useState<Passenger[]>([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(PAGE_SIZES[0]);
    const [hasNext, setHasNext] = useState(false);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const authenticatedApiClient = useAuthenticatedApiClient();

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const fetchFlight = async () => {
            try {
                const flightResponse = await apiClient.get(`/flights/${id}`);
                const passengerResponse = await authenticatedApiClient.get(`/flights/${id}/passengers`, {
                    params: {
                        page: page,
                        size: size
                    }
                });
               
                console.log(passengerResponse);

                if (isMounted) {
                    setFlight(flightResponse.data);
                    setPassengers(passengerResponse.data.content);
                    setHasNext(passengerResponse.data.hasNext);
                }
            } catch (err) {
                if (isMounted) {
                    console.log(err);
                    setErrorMessage('Error fetching flight details.');
                }
            };
        };

        fetchFlight();

        return () => {
            isMounted = false;
            controller.abort();
        }
    }, [id, page, size]);

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

    return (
        <main className="bg-light py-5">
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            {!flight ||
                <Container>
                    <section className='mt-5 mb-3'>
                        <h2 className="text-center text-primary mb-4">Полет</h2>
                        <Card className="shadow-sm d-flex flex-column align-items-center justify-content-center text-center">
                            <Card.Body>
                                <Card.Title>{flight.departureLocation} → {flight.destinationLocation}</Card.Title>
                                <ListGroup variant="flush">
                                    <ListGroup.Item>Излита: {formatDateTime(flight.departureTime)}</ListGroup.Item>
                                    <ListGroup.Item>Времетраене: {getDuration(flight.departureTime, flight.arrivalTime)}</ListGroup.Item>
                                    <ListGroup.Item>Свободни места (икономична): {flight.availableSeatsEconomy}</ListGroup.Item>
                                    <ListGroup.Item>Свободни места (бизнес): {flight.availableSeatsBusiness}</ListGroup.Item>
                                </ListGroup>
                            </Card.Body>
                        </Card>
                    </section>

                    
                    <section className="mt-5 mb-4">
                        <h2 className="text-center text-primary mb-4">Пътници</h2>
                        <Row>
                            {passengers.length > 0 ? (
                                passengers.map((passenger) => (
                                    <Col md={4} key={passenger.id} className="mb-4">
                                        <Card className="shadow-sm d-flex flex-column align-items-center justify-content-center text-center">
                                            <Card.Body>
                                                <Card.Title>{`${passenger.firstName} ${passenger.middleName} ${passenger.lastName}`}</Card.Title>
                                                <ListGroup variant="flush">
                                                    <ListGroup.Item>ЕГН: {passenger.personalIdentificationNumber}</ListGroup.Item>
                                                </ListGroup>
                                                <ListGroup.Item className="d-flex justify-content-center">
                                                    <Link
                                                        to={`/reservations/${passenger.reservationId}`}
                                                        className="btn btn-outline-primary text-decoration-none"
                                                    >
                                                        Виж резервация
                                                    </Link>
                                                </ListGroup.Item>
                                            </Card.Body>
                                        </Card>
                                    </Col>
                                ))
                            ) : (
                                <p>Няма пътници за този полет.</p>
                            )}
                        </Row>
                    </section>
                    <section>
                        <Form>
                            <Row className="justify-content-end">
                                <Col xs="auto">
                                    <Form.Group controlId="pageSize" className="align-items-center">
                                        <Form.Label className="mr-3 mb-0">Резултати на страница:</Form.Label>
                                        <Form.Select
                                            aria-label="Резултати на страница"
                                            value={size}
                                            onChange={handleSizeChange}
                                            className=""
                                        >
                                            {PAGE_SIZES.map((pageSize) => (
                                                <option key={pageSize} value={pageSize}>
                                                    {pageSize}
                                                </option>
                                            ))}
                                        </Form.Select>
                                    </Form.Group>
                                </Col>
                            </Row>
                        </Form>
                        
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
            }
        </main>
    );
}

export default Flight
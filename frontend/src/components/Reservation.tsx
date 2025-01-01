import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import useAuthenticatedApiClient from "../hooks/useAuthenticatedApiClient";
import { Alert, Card, ListGroup, Container, Row, Col } from 'react-bootstrap';

interface Passenger {
    firstName: string;
    middleName: string;
    lastName: string;
    personalIdentificationNumber: string;
    phoneNumber: string;
    nationality: string;
    seatType: "ECONOMY" | "BUSINESS";
}

interface Reservation {
    contactEmail: string;
    reservationFlight: number;
    passengers: Passenger[];
}

const seatTypeTranslations = {
    "BUSINESS": "бизнес",
    "ECONOMY": "икономична"
};

const Reservation = () => {

    const { id } = useParams<{ id: string }>();
    const authenticatedClient = useAuthenticatedApiClient();
    const [reservation, setReservation] = useState<Reservation | null>(null);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const fetchReservation = async () => {
            try {
                const response = await authenticatedClient.get(`/reservations/${id}`);
                isMounted && setReservation(response.data);
            } catch (err) {
                if (isMounted) {
                    console.log(err);
                    setErrorMessage('Error fetching reservation details.');
                }
            };
        };

        fetchReservation();

        return () => {
            isMounted = false;
            controller.abort();
        }
    }, [id]);

    return (
        <main className="bg-light py-5">
            {errorMessage && (
                <Alert variant="danger" className="mt-4" role="alert">
                    {errorMessage}
                </Alert>
            )}

            <Container>
                <section className="mb-5" aria-labelledby="reservation">
                    <header>
                        <Card className="mt-3 shadow-sm bg-white rounded">
                            <Card.Body>
                                <Card.Title id="reservation" className="text-primary mb-3">
                                    <h2>Резервация</h2>
                                </Card.Title>
                                <ListGroup variant="flush">
                                    <ListGroup.Item className="d-flex justify-content-between">
                                        <strong>Имейл за връзка:</strong>
                                        <span>{reservation?.contactEmail}</span>
                                    </ListGroup.Item>
                                    <ListGroup.Item className="d-flex justify-content-between">
                                        <strong>Полет:</strong>
                                        <Link
                                            to={`/flights/${reservation?.reservationFlight}`}
                                            className="btn btn-outline-primary text-decoration-none"
                                        >
                                            Виж полет
                                        </Link>
                                    </ListGroup.Item>
                                </ListGroup>
                            </Card.Body>
                        </Card>
                    </header>
                </section>

                <section aria-labelledby="passenger-list">
                    <h5 id="passenger-list" className="mb-4 text-muted">Пътници</h5>

                    <Row>
                        {reservation?.passengers.map((passenger) => (
                            <Col key={passenger.personalIdentificationNumber} md={6} lg={4} className="mb-4">
                                <Card className="shadow-sm bg-white rounded p-4">
                                    <Card.Body>
                                        <Card.Title className="text-primary">
                                            {passenger.firstName} {passenger.middleName} {passenger.lastName}
                                        </Card.Title>
                                        <ListGroup variant="flush">
                                            <ListGroup.Item>
                                                <strong>ЕГН:</strong> {passenger.personalIdentificationNumber}
                                            </ListGroup.Item>
                                            <ListGroup.Item>
                                                <strong>Националност:</strong> {passenger.nationality}
                                            </ListGroup.Item>
                                            <ListGroup.Item>
                                                <strong>Телефон:</strong> {passenger.phoneNumber}
                                            </ListGroup.Item>
                                            <ListGroup.Item>
                                                <strong>Вид място:</strong> {seatTypeTranslations[passenger.seatType] || passenger.seatType}
                                            </ListGroup.Item>
                                        </ListGroup>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                </section>
            </Container>
        </main>
    )
}

export default Reservation
import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import useAuthenticatedApiClient from "../hooks/useAuthenticatedApiClient";
import { Alert, Card, ListGroup, Container, Row, Col, Spinner } from 'react-bootstrap';
import { getErrorMessageOrDefault } from "../utils/responseUtil";

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
    const [isLoading, setIsLoading] = useState<boolean>(false);

    useEffect(() => {
        const fetchReservation = async () => {
            setIsLoading(true);
            try {
                const response = await authenticatedClient.get(`/reservations/${id}`);
                setReservation(response.data);
            } catch (err) {
                setErrorMessage(getErrorMessageOrDefault(err, 'Error fetching reservation details.'));
            } finally {
                setIsLoading(false);
            };
        };

        fetchReservation();
    }, [id]);

    return (
        <main className="bg-light py-5">
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}

            <Container>
                {isLoading && (
                    <div className="text-center">
                        <Spinner animation="border" role="status" />
                    </div>
                )}
                <section className="mb-5" aria-labelledby="reservation">
                    <header>
                        <Card className="mt-3 shadow-sm bg-white rounded d-flex flex-column align-items-center justify-content-center text-center">
                            <Card.Body>
                                <Card.Title id="reservation" className="mb-3">
                                    <h2><strong>Резервация</strong></h2>
                                </Card.Title>
                                <ListGroup variant="flush">
                                    <ListGroup.Item className="d-flex justify-content-between">
                                        Имейл за връзка: <span className="ms-3"><a href={`mailto:${reservation?.contactEmail}`} className="link-primary"> {reservation?.contactEmail}</a></span>
                                    </ListGroup.Item>
                                    <ListGroup.Item className="d-flex justify-content-center">
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
                    <h3 className="text-center text-primary mb-4">Пътници</h3>

                    <Row>
                        {reservation?.passengers.map((passenger) => (
                            <Col key={passenger.personalIdentificationNumber} md={6} lg={4} className="mb-4">
                                <Card className="shadow-sm bg-white rounded p-4 d-flex flex-column align-items-center justify-content-center text-center">
                                    <Card.Body>
                                        <Card.Title>
                                            <strong>{passenger.firstName} {passenger.middleName} {passenger.lastName}</strong>
                                        </Card.Title>
                                        <ListGroup variant="flush">
                                            <ListGroup.Item>
                                                ЕГН: {passenger.personalIdentificationNumber}
                                            </ListGroup.Item>
                                            <ListGroup.Item>
                                                Националност: {passenger.nationality}
                                            </ListGroup.Item>
                                            <ListGroup.Item>
                                                Телефон: {passenger.phoneNumber}
                                            </ListGroup.Item>
                                            <ListGroup.Item>
                                                Вид място: {seatTypeTranslations[passenger.seatType] || passenger.seatType}
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
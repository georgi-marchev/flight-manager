import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Alert, Container, Button, Card, Col, Form, ListGroup, Row, Spinner } from 'react-bootstrap';
import { apiClient } from '../api/apiClient';
import { formatDateTime, getDuration } from '../utils/dateHelper';
import { getErrorMessageOrDefault } from '../utils/responseUtil';

interface Flight {
    id: number;
    departureLocation: string;
    destinationLocation: string;
    departureTime: string;
    arrivalTime: string;
    availableSeatsEconomy: number;
    availableSeatsBusiness: number;
}

interface ReservationRequestPassenger {
    firstName: string;
    middleName: string;
    lastName: string;
    personalIdentificationNumber: string;
    phoneNumber: string;
    nationality: string;
    seatType: "ECONOMY" | "BUSINESS";
}

interface ReservationRequest {
    flightId: number;
    contactEmail: string;
    passengers: ReservationRequestPassenger[];
}

function createEmptyPassenger(): ReservationRequestPassenger {
    return {
        firstName: '',
        middleName: '',
        lastName: '',
        personalIdentificationNumber: '',
        phoneNumber: '',
        nationality: '',
        seatType: 'ECONOMY'
    };
}

const CreateReservation = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    
    const [flight, setFlight] = useState<Flight | null>(null);
    const [contactEmail, setContactEmail] = useState<string>('');
    const [passengers, setPassengers] = useState<ReservationRequestPassenger[]>([createEmptyPassenger()]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string>('');

    useEffect(() => {
        setIsLoading(true);
        const fetchFlight = async () => {
            try {
                const response = await apiClient.get(`/flights/${id}`);
                setFlight(response.data);
            } catch (err) {
                setErrorMessage(getErrorMessageOrDefault(err, 'Не може да бъде свалена информация за полет. Моля опитайте отново!'));
            } finally {
                setIsLoading(false);
            };
        };
        fetchFlight();
    }, [id]);

    const handlePassengerChange = (index: number, event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setPassengers(prev => {
            const newPassengers = [...prev];
            newPassengers[index] = { ...newPassengers[index], [name]: value };
            return newPassengers;
        });
    };

    const handleRemovePassenger = (index: number) => {
        setPassengers(passengers.filter((_, i) => i !== index));
    };

    const addPassenger = () => {
        setPassengers(prev => [...prev, createEmptyPassenger()]);
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setIsLoading(true);
        setErrorMessage('');
        const reservationRequest: ReservationRequest = {
            flightId: Number(id),
            contactEmail,
            passengers,
        };
        try {

            await apiClient.post('reservations', reservationRequest);
            alert('Резевацията е създадена успешно!');
            navigate(`/`);
        } catch (error) {
            setErrorMessage(getErrorMessageOrDefault(error, 'Грешка при създаване на резервация'));
            window.scrollTo(0, 0)
        } finally {
            setIsLoading(false);
        };
    };

    if (!flight) {
        return <div>Loading flight details...</div>;
    }

    return (
        <main className="bg-light py-5">
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Container>
                {isLoading && (
                    <div className="text-center">
                        <Spinner animation="border" role="status" />
                    </div>
                )}
                <section className='mt-5 mb-3'>
                    <h1 className="text-center text-primary mb-4">Полет</h1>
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
                    <h1 className="text-center text-primary mb-4">Резервация</h1>
                    <Form onSubmit={handleSubmit} className="border p-4 rounded shadow-sm">
                        <Row className="mb-4">
                            <Form.Group as={Col} controlId="contactEmail">
                                <Form.Label>Имейл:</Form.Label>
                                <Form.Control
                                    type="email"
                                    placeholder="Въведете имейл адрес"
                                    value={contactEmail}
                                    onChange={(e) => setContactEmail(e.target.value)}
                                    required
                                />
                            </Form.Group>
                        </Row>

                        {passengers.map((passenger, index) => (
                            <Row key={index} className="border rounded p-4 m-1 shadow-sm">
                                <h4 className="text-center mb-3">Пътник {index + 1}</h4>
                                 <Row className="mb-3">
                                    <Form.Group as={Col} controlId={`firstName${index}`} className="mb-3">
                                        <Form.Label>Име:</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="firstName"
                                            value={passenger.firstName}
                                            onChange={(e: React.ChangeEvent<HTMLInputElement>) => handlePassengerChange(index, e)}
                                            required
                                        />
                                    </Form.Group>
                                    <Form.Group as={Col} controlId={`middleName${index}`} className="mb-3">
                                        <Form.Label>Презиме:</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="middleName"
                                            value={passenger.middleName}
                                            onChange={(e: React.ChangeEvent<HTMLInputElement>) => handlePassengerChange(index, e)}
                                            required
                                        />
                                    </Form.Group>
                                    <Form.Group as={Col} controlId={`lastName${index}`} className="mb-3">
                                        <Form.Label>Фамилия:</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="lastName"
                                            value={passenger.lastName}
                                            onChange={(e: React.ChangeEvent<HTMLInputElement>) => handlePassengerChange(index, e)}
                                            required
                                        />
                                    </Form.Group>
                                </Row>
                                <Row className="mb-3">
                                    <Form.Group as={Col} controlId={`personalIdentificationNumber${index}`} className="mb-3">
                                        <Form.Label>ЕГН:</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name={'personalIdentificationNumber'}
                                            value={passenger.personalIdentificationNumber}
                                            onChange={(e: React.ChangeEvent<HTMLInputElement>) => handlePassengerChange(index, e)}
                                            required
                                        />
                                    </Form.Group>
                                    <Form.Group as={Col} controlId={`nationality${index}`} className="mb-3">
                                        <Form.Label>Гражданство:</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="nationality"
                                            value={passenger.nationality}
                                            onChange={(e: React.ChangeEvent<HTMLInputElement>) => handlePassengerChange(index, e)}
                                            required
                                        />
                                    </Form.Group>
                                    <Form.Group as={Col} controlId={`phoneNumber${index}`} className="mb-3">
                                        <Form.Label>Тел. №:</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name={'phoneNumber'}
                                            value={passenger.phoneNumber}
                                            onChange={(e: React.ChangeEvent<HTMLInputElement>) => handlePassengerChange(index, e)}
                                            required
                                        />
                                    </Form.Group>
                                    <Form.Group as={Col} controlId={`seatType${index}`} className="mb-3">
                                        <Form.Label>Вид място:</Form.Label>
                                        <Form.Control
                                            as="select"
                                            name="seatType"
                                            value={passenger.seatType}
                                            onChange={(e: React.ChangeEvent<HTMLInputElement>) => handlePassengerChange(index, e)}
                                            required
                                        >
                                            <option value="ECONOMY">Икономична</option>
                                            <option value="BUSINESS">Бизнес</option>
                                        </Form.Control>
                                    </Form.Group>
                                </Row>

                                <Row>
                                <Form.Group as={Col} controlId={`removePassenger${index}`} className="d-flex justify-content-center">
                                    <Button
                                        variant="danger"
                                        onClick={() => handleRemovePassenger(index)}
                                        className="px-4 py-2"
                                    >
                                        Изтрий
                                    </Button>
                                </Form.Group>
                                </Row>
                            </Row>
                        ))}

                        <Row className="text-center mb-4 mt-3">
                            <Form.Group as={Col} controlId={'addPassenger'}>
                                <Button variant="secondary" onClick={addPassenger}>
                                    Добави пътник
                                </Button>
                            </Form.Group>
                        </Row>

                        {/* Submit Button */}
                        <Row className="text-center mt-5 mb-3">
                            <Form.Group as={Col} controlId={'submitButton'}>
                                <Button variant="primary" type="submit" disabled={isLoading}>
                                    {isLoading ? <Spinner animation="border" size="sm" /> : 'Изпрати'}
                                </Button>
                            </Form.Group>
                        </Row>
                    </Form>
                </section>
            </Container>
        </main>

    );
};

export default CreateReservation;

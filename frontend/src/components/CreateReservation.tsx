import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Alert, Container, Button, Card, Col, Form, ListGroup, Row, Spinner } from 'react-bootstrap';
import { apiClient } from '../api/apiClient';
import { formatDateTime, getDuration } from '../utils/dateHelper';
import { pinIsValid, phoneIsValid } from '../utils/validator';

const PIN_NAME = 'personalIdentificationNumber';
const PHONE_NUMBER_NAME = 'phoneNumber';

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
    const [invalidFields, setInvalidFields] = useState({
        [PHONE_NUMBER_NAME]: [false],
        [PIN_NAME]: [false]
    });

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const fetchFlight = async () => {
            try {
                const response = await apiClient.get(`/flights/${id}`);
                isMounted && setFlight(response.data);
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

        let hasInvalidField = false;
        passengers.forEach((passenger, index) => {

            const fieldsToValidate = [
                { name: PHONE_NUMBER_NAME, value: passenger.phoneNumber, validationFn: phoneIsValid },
                { name: PIN_NAME, value: passenger.personalIdentificationNumber, validationFn: pinIsValid }
            ];

            fieldsToValidate.forEach(({ name, value, validationFn }) => {
                let isInvalid = false;
                if (!validationFn(value)) {
                    hasInvalidField = true;
                    isInvalid = true;
                }
                setInvalidFields(prev => {
                    const newInvalidFields = { ...prev };
                    newInvalidFields[name][index] = isInvalid;
                    return newInvalidFields;
                });
            });
        });

        if (hasInvalidField) {
            setIsLoading(false);
            return;
        }

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
            alert(error);
            setErrorMessage('Error creating reservation. Please try again.');
            console.error('Reservation error:', error);
        } finally {
            setIsLoading(false);
        }
    };

    if (!flight) {
        return <div>Loading flight details...</div>;
    }

    return (
        <main>
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Container>
                <section className='mt-5 mb-3'>
                    <h2>Полет</h2>
                    <Card>
                        <Card.Body>
                            <Card.Title>{flight.departureLocation} до {flight.destinationLocation}</Card.Title>
                            <ListGroup variant="flush">
                                <ListGroup.Item>Излита: {formatDateTime(flight.departureTime)}</ListGroup.Item>
                                <ListGroup.Item>Времетраене: {getDuration(flight.departureTime, flight.arrivalTime)}</ListGroup.Item>
                                <ListGroup.Item>Свободни места (икономична): {flight.availableSeatsEconomy}</ListGroup.Item>
                                <ListGroup.Item>Свободни места (бизнес): {flight.availableSeatsBusiness}</ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </section>
                <section>
                    <h2>Резервация: </h2>
                    <Form onSubmit={handleSubmit} className="border">
                        <Row className="border p-3 m-3">
                            <Form.Group as={Col} controlId="contactEmail">
                                <Form.Label>Имейл</Form.Label>
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
                            <Row key={index} className="border p-3 m-3">
                                <h3>Пътник {index + 1}</h3>
                                <Form.Group as={Col} controlId={`firstName${index}`}>
                                    <Form.Label>Име</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="firstName"
                                        value={passenger.firstName}
                                        onChange={(e) => handlePassengerChange(index, e)}
                                        required
                                    />
                                </Form.Group>
                                <Form.Group as={Col} controlId={`middleName${index}`}>
                                    <Form.Label>Презиме</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="middleName"
                                        value={passenger.middleName}
                                        onChange={(e) => handlePassengerChange(index, e)}
                                        required
                                    />
                                </Form.Group>
                                <Form.Group as={Col} controlId={`lastName${index}`}>
                                    <Form.Label>Фамилия</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="lastName"
                                        value={passenger.lastName}
                                        onChange={(e) => handlePassengerChange(index, e)}
                                        required
                                    />
                                </Form.Group>
                                <Form.Group as={Col} controlId={`personalIdentificationNumber${index}`}>
                                    <Form.Label>ЕГН</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name={PIN_NAME}
                                        value={passenger.personalIdentificationNumber}
                                        onChange={(e) => handlePassengerChange(index, e)}
                                        required
                                        isInvalid={invalidFields[PIN_NAME][index]}
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Невалиден формат на ЕГН.
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <Form.Group as={Col} controlId={`nationality${index}`}>
                                    <Form.Label>Гражданство</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="nationality"
                                        value={passenger.nationality}
                                        onChange={(e) => handlePassengerChange(index, e)}
                                        required
                                    />
                                </Form.Group>
                                <Form.Group as={Col} controlId={`phoneNumber${index}`}>
                                    <Form.Label>Тел. №</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name={PHONE_NUMBER_NAME}
                                        value={passenger.phoneNumber}
                                        onChange={(e) => handlePassengerChange(index, e)}
                                        required
                                        isInvalid={invalidFields[PHONE_NUMBER_NAME][index]}
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Невалиден телефонен номер. Валидни номера могат да започват с '+' и да съдържат само цирфи.
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <Form.Group as={Col} controlId={`seatType${index}`}>
                                    <Form.Label>Вид място</Form.Label>
                                    <Form.Control
                                        as="select"
                                        name="seatType"
                                        value={passenger.seatType}
                                        onChange={(e) => handlePassengerChange(index, e)}
                                        required
                                    >
                                        <option value="ECONOMY">Икономична</option>
                                        <option value="BUSINESS">Бизнес</option>
                                    </Form.Control>
                                </Form.Group>
                                <Form.Group as={Col} controlId={`seatType${index}`}>
                                    <Button
                                        variant="danger"
                                        onClick={() => handleRemovePassenger(index)}
                                    >
                                    Изтрий
                                    </Button>
                                </Form.Group>
                            </Row>
                        ))}

                        <Row className="mx-auto">
                            <Form.Group as={Col} controlId={'submitButton'}>
                                <Button variant="secondary" onClick={addPassenger}>
                                    Добави пътник
                                </Button>
                            </Form.Group>
                        </Row>
                        <Row className="mx-auto mt-5 mb-3">
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

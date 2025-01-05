import { useState, useEffect } from "react";
import { Button, Form, Col, Row, Container, Alert, Spinner } from "react-bootstrap";
import useAuthenticatedApiClient from "../hooks/useAuthenticatedApiClient";
import { getErrorMessageOrDefault } from "../utils/responseUtil";
import { useParams, useNavigate } from "react-router-dom";

interface Pilot {
    id: number;
    firstName: string;
    lastName: string;
}

const UpdateFlight = () => {
    const { flightId } = useParams<{ flightId: string }>();
    const navigate = useNavigate();
    const [pilots, setPilots] = useState<Pilot[]>([]);
    const [loading, setLoading] = useState(true); 
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [flightData, setFlightData] = useState({
        departureTime: "",
        arrivalTime: "",
        departureLocation: "",
        destinationLocation: "",
        pilotId: "",
    });

    const authenticatedApiClient = useAuthenticatedApiClient();

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const pilotsResponse = await authenticatedApiClient.get("/pilots");
                setPilots(pilotsResponse.data);

                const flightResponse = await authenticatedApiClient.get(`/flights/${flightId}`);
                setFlightData({
                    departureTime: formatDateForInput(flightResponse.data.departureTime),
                    arrivalTime: formatDateForInput(flightResponse.data.arrivalTime),
                    departureLocation: flightResponse.data.departureLocation,
                    destinationLocation: flightResponse.data.destinationLocation,
                    pilotId: flightResponse.data.pilotId,
                });
            } catch (error) {
                setErrorMessage(getErrorMessageOrDefault(error, "Грешка при изтегляне на данни. Моля опитайте отново!"));
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [flightId]);

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
       
        const { name, value } = event.target;
        setFlightData({ ...flightData, [name]: value });
    };

    const formatDateWithTimeZone = (dateStr: string) => {
        const date = new Date(dateStr);
        return date.toISOString();
    };

    const formatDateForInput = (dateStr: string) => {
        const date = new Date(dateStr);
    
        // Get the parts of the date
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
    
        // Return the formatted string in YYYY-MM-DDTHH:mm
        return `${year}-${month}-${day}T${hours}:${minutes}`;
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setLoading(true);
        setErrorMessage('');

        const formattedDepartureTime = formatDateWithTimeZone(flightData.departureTime);
        const formattedArrivalTime = formatDateWithTimeZone(flightData.arrivalTime);

        try {
            await authenticatedApiClient.put(`/flights/${flightId}`, {
                pilotId: flightData.pilotId,
                departureTime: formattedDepartureTime,
                arrivalTime: formattedArrivalTime,
            });

            alert("Полет обновен успешно!");
            navigate(`/flights/${flightId}`); // Navigate to the updated flight's page or list page
        } catch (error) {
            setErrorMessage(getErrorMessageOrDefault(error, 'Грешка при обновяване на полет. Моля опитайте отново!'));
            window.scrollTo(0, 0)
        } finally {
            setLoading(false);
        }
    };

    return (
        <main className="bg-light py-5">
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Container>
                <h1 className="text-center text-primary mb-4">Актуализиране на полет</h1>
                {loading && (
                    <div className="text-center">
                        <Spinner animation="border" role="status" />
                    </div>
                )}

                <section>
                    <Form onSubmit={handleSubmit} className="border p-4 rounded shadow-sm">
                        <Row className="mb-3">

                            <Form.Group as={Col} controlId="departureLocation">
                                <Form.Label>От</Form.Label>
                                <Form.Control
                                    as="input"
                                    name="departureLocation"
                                    value={`${flightData.departureLocation})`}
                                    disabled
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="destinationLocation">
                                <Form.Label>До</Form.Label>
                                <Form.Control
                                    as="input"
                                    name="destinationLocation"
                                    value={`${flightData.destinationLocation})`}
                                    disabled
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-3">
                            
                            <Form.Group as={Col} controlId="pilotId">
                                <Form.Label>Пилот<span className='required-element'>*</span></Form.Label>
                                <Form.Control
                                    as="select"
                                    name="pilotId"
                                    value={flightData.pilotId}
                                    onChange={handleInputChange}
                                    required
                                >
                                    <option value="">Избери пилот</option>
                                    {pilots.map((pilot) => (
                                        <option key={pilot.id} value={pilot.id}>
                                            {pilot.firstName} {pilot.lastName}
                                        </option>
                                    ))}
                                </Form.Control>
                            </Form.Group>
                            <Form.Group as={Col} controlId="departureTime">
                                <Form.Label>Излита<span className='required-element'>*</span></Form.Label>
                                <Form.Control
                                    type="datetime-local"
                                    name="departureTime"
                                    value={flightData.departureTime}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="arrivalTime">
                                <Form.Label>Каца<span className='required-element'>*</span></Form.Label>
                                <Form.Control
                                    type="datetime-local"
                                    name="arrivalTime"
                                    value={flightData.arrivalTime}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                        </Row>

                        <Row className="text-center mt-5 mb-3">
                            <Form.Group as={Col} controlId={'submitButton'}>
                                <Button variant="primary" type="submit" disabled={loading}>
                                    Актуализирай полет
                                </Button>
                            </Form.Group>
                        </Row>
                    </Form>
                </section>
            </Container>
        </main>
    );
};

export default UpdateFlight;

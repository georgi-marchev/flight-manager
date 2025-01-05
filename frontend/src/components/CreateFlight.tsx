import { useState, useEffect } from "react";
import { Button, Form, Col, Row, Container, Alert, Spinner } from "react-bootstrap";
import useAuthenticatedApiClient from "../hooks/useAuthenticatedApiClient";
import { getErrorMessageOrDefault } from "../utils/responseUtil";

interface Location {
    id: number;
    airportName: string;
    city: string;
    country: string;
}

interface Airplane {
    id: number;
    serialNumber: string;
    modelNumber: string;
    capacityEconomy: number;
    capacityBusiness: number;
}

interface Pilot {
    id: number;
    firstName: string;
    lastName: string;
}

const CreateFlight = () => {
    const [pilots, setPilots] = useState<Pilot[]>([]);
    const [airplanes, setAirplanes] = useState<Airplane[]>([]);
    const [locations, setLocations] = useState<Location[]>([]);
    const [loading, setLoading] = useState(true); 
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [flightData, setFlightData] = useState({
        flightPilot: "",
        flightAirplane: "",
        flightDepartureLocation: "",
        flightDestinationLocation: "",
        departureTime: "",
        arrivalTime: "",
    });

    const authenticatedApiClient = useAuthenticatedApiClient();

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                
                const pilotsResponse = await authenticatedApiClient.get("/pilots");
                setPilots(pilotsResponse.data);

                const airplanesResponse = await authenticatedApiClient.get("/airplanes");
                setAirplanes(airplanesResponse.data);

                const locationsResponse = await authenticatedApiClient.get("/locations");
                setLocations(locationsResponse.data);
            } catch (error) {
                setErrorMessage(getErrorMessageOrDefault(error, "Грешка при изтегляне на данни. Моля опитайте отново!"));
            } finally {
                setLoading(false);
            };
        };
        fetchData();
    }, []);

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setFlightData({ ...flightData, [name]: value });
    };

    const formatDateWithTimeZone = (dateStr: string) => {
        const date = new Date(dateStr);
        return date.toISOString(); // This will include the 'Z' suffix for UTC
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setLoading(true);
        setErrorMessage('');

        const formattedDepartureTime = formatDateWithTimeZone(flightData.departureTime);
        const formattedArrivalTime = formatDateWithTimeZone(flightData.arrivalTime);

        try {
            await authenticatedApiClient.post("/flights", {
                ...flightData,
                departureTime: formattedDepartureTime,
                arrivalTime: formattedArrivalTime,
            });
           
            setFlightData({
                flightPilot: "",
                flightAirplane: "",
                flightDepartureLocation: "",
                flightDestinationLocation: "",
                departureTime: "",
                arrivalTime: "",
            });
            alert("Полет създаден успешно!");
        } catch (error) {
            setErrorMessage(getErrorMessageOrDefault(error, 'Грешка при създаване на полет. Моля опитайте отново!'));
            window.scrollTo(0, 0)
        } finally {
            setLoading(false);
        }
    };

    return (
         <main className="bg-light py-5">
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Container>
                <h1 className="text-center text-primary mb-4">Създаване на полет</h1>
                {loading && (
                    <div className="text-center">
                        <Spinner animation="border" role="status" />
                    </div>
                )}

                <section>
                    <Form onSubmit={handleSubmit} className="border p-4 rounded shadow-sm">
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="flightPilot">
                                <Form.Label>Пилот:</Form.Label>
                                <Form.Control
                                    as="select"
                                    value={flightData.flightPilot}
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

                            <Form.Group as={Col} controlId="flightAirplane">
                                <Form.Label>Самолет:</Form.Label>
                                <Form.Control
                                    as="select"
                                    value={flightData.flightAirplane}
                                    onChange={handleInputChange}
                                    required
                                >
                                    <option value="">Избери самолет</option>
                                    {airplanes.map((airplane) => (
                                        <option key={airplane.id} value={airplane.id}>
                                            {airplane.serialNumber} - {airplane.modelNumber}
                                        </option>
                                    ))}
                                </Form.Control>
                            </Form.Group>
                        </Row>

                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="flightDepartureLocation">
                                <Form.Label>От:</Form.Label>
                                <Form.Control
                                    as="select"
                                    value={flightData.flightDepartureLocation}
                                    onChange={handleInputChange}
                                    required
                                >
                                    <option value="">Избери локация</option>
                                    {locations.map((location) => (
                                        <option key={location.id} value={location.id}>
                                            {`${location.city}, ${location.country} (${location.airportName})`}
                                        </option>
                                    ))}
                                </Form.Control>
                            </Form.Group>

                            <Form.Group as={Col} controlId="flightDestinationLocation">
                                <Form.Label>До:</Form.Label>
                                <Form.Control
                                    as="select"
                                    value={flightData.flightDestinationLocation}
                                    onChange={handleInputChange}
                                    required
                                >
                                    <option value="">Избери локация</option>
                                    {locations.map((location) => (
                                        <option key={location.id} value={location.id}>
                                            {`${location.city}, ${location.country} (${location.airportName})`}
                                        </option>
                                    ))}
                                </Form.Control>
                            </Form.Group>
                        </Row>

                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="departureTime">
                                <Form.Label>Излита:</Form.Label>
                                <Form.Control
                                    type="datetime-local"
                                    value={flightData.departureTime}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="arrivalTime">
                                <Form.Label>Каца</Form.Label>
                                <Form.Control
                                    type="datetime-local"
                                    value={flightData.arrivalTime}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Form.Group>
                        </Row>

                        <Row className="text-center mt-5 mb-3">
                            <Form.Group as={Col} controlId={'submitButton'}>
                                <Button variant="primary" type="submit" disabled={loading}>
                                    Създай полет
                                </Button>
                            </Form.Group>
                        </Row>

                        
                    </Form>
                </section>
                
                
            </Container>
        </main>
    );
};

export default CreateFlight;

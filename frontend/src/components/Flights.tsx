import { useState, useEffect } from 'react';
import api from '../api/flightManager';
import { formatDateTime } from '../utils/dateHelper';
import { Alert, Col, Container, Form, Row, Table } from 'react-bootstrap';
import Pagination from './Pagination';
import { Link } from 'react-router-dom';

const PAGE_SIZES = [1, 25, 50];

interface Flight {
    id: number;
    departureTime: string;
    arrivalTime: string;
    departureLocation: string;
    destinationLocation: string;
}

const Flights = () => {
    const [flights, setFlights] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(PAGE_SIZES[0]);
    const [hasNext, setHasNext] = useState(false);
    const [filters, setFilters] = useState({
        departureDate: new Date().toISOString().split('T')[0],
        departureLocation: '',
        destinationLocation: '',
        availableSeatsEconomy: '',
        availableSeatsBusiness: ''
    });
    const [errorMessage, setErrorMessage] = useState('');

    // Fetch flights based on current filters and pagination
    useEffect(() => {
        
        const fetchFlights = async () => {
            
            try {
                const response = await api.get('/flights', {
                    params: {
                        ...filters,
                        page: page,
                        size: size
                    }
                });

                console.log(response.data);
                const data = response.data;
                setFlights(data.content);
                setHasNext(data.hasNext);

            } catch (err) {
                console.log(err);
                setErrorMessage('Вмомента не може да бъде показана информация за полетите. Опитайте отново!')
            };
        };

        fetchFlights();
    }, [page, size, filters]); // The request re-runs when `page`, `size`, or `filters` change

    const handleNextPage = () => {
        if (hasNext) {
            setPage(page + 1); // Move to the next page if there is a next page
        }
    };

    const handlePreviousPage = () => {
        if (page > 0) {
            setPage(page - 1); // Decrement page if there is a previous page
        }
    };

    const handleSizeChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        setSize(Number(event.target.value)); // Update page size and reset to first page
        setPage(0); // Reset page to 0 when page size changes
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
                    <h1>Полети</h1>
                </header>
                <section>
                    <Form>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="departureDate">
                                <Form.Label>Заминаване</Form.Label>
                                <Form.Control 
                                    type="date" 
                                    placeholder="Въведете дата" 
                                    name="departureDate"
                                    value={filters.departureDate}
                                    onChange={handleFilterChange} 
                                />
                            </Form.Group>
                        
                            <Form.Group as={Col} controlId="departureLocation">
                                <Form.Label>От</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Държава, град, или летище" 
                                    name="departureLocation"
                                    value={filters.departureLocation}
                                    onChange={handleFilterChange} 
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="destinationLocation">
                                <Form.Label>До</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Държава, град, или летище" 
                                    name="destinationLocation"
                                    value={filters.destinationLocation}
                                    onChange={handleFilterChange}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="availableSeatsEconomy">
                                <Form.Label>Места (икономична)</Form.Label>
                                <Form.Control
                                    type="number"
                                    placeholder="Въведете брой" 
                                    name="availableSeatsEconomy"
                                    value={filters.availableSeatsEconomy}
                                    onChange={handleFilterChange}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="availableSeatsBusiness">
                                <Form.Label>Места (бизнес)</Form.Label>
                                <Form.Control
                                    type="number"
                                    placeholder="Въведете брой" 
                                    name="availableSeatsBusiness"
                                    value={filters.availableSeatsBusiness}
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
                <section aria-labelledby="flights">
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>Заминаване</th>
                                <th>Пристигане</th>
                                <th>От</th>
                                <th>До</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {flights.map((flight: Flight) => (
                            <tr key={flight.departureTime}>
                                <td>{formatDateTime(flight.departureTime)}</td>
                                <td>{formatDateTime(flight.arrivalTime)}</td>
                                <td>{flight.departureLocation}</td>
                                <td>{flight.destinationLocation}</td>
                                <td><Link className='link-primary' to={`flights/${flight.id}/create-reservation`}>Резервирай</Link></td>
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
};

export default Flights;
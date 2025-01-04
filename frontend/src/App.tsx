import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import { Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import Flights from './components/Flights';
import CreateReservation from './components/CreateReservation';
import Missing from './components/Missing';
import Login from './components/Login';
import Employees from './components/Employees.tsx';
import CreateEmployee from './components/CreateEmployee.tsx';
import RequireAuthentication from './components/RequireAuthentication.tsx';
import RequireAuthorization from './components/RequireAuthorization.tsx';
import Reservations from './components/Reservations.tsx';
import Reservation from './components/Reservation.tsx';
import Flight from './components/Flight.tsx';
import CreateFlight from './components/CreateFlight.tsx';

function App() {
    return (
        <Routes>
            <Route path="/" element={<Layout />}>
                <Route path='login' element={<Login />} />
                <Route index element={<Flights />} />
                
                <Route element={<RequireAuthentication />}>
                    <Route path="/reservations" element={<Reservations />} />
                    <Route path="/reservations/:id" element={<Reservation />} />
                    <Route element={<RequireAuthorization allowedRoles={['ROLE_ADMIN']} />}>
                        <Route path="/employees" element={<Employees />} />
                        <Route path="/employees/create" element={<CreateEmployee />} />
                        <Route path="/flights/:id" element={<Flight />} />
                        <Route path="/flights/create" element={<CreateFlight />} />
                    </Route>
                </Route>
                
                <Route path="/flights/:id/create-reservation" element={<CreateReservation />} />
                <Route path="*" element={<Missing />} />
            </Route>
        </Routes>
    )
}

export default App

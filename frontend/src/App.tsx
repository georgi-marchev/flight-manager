import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import { Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import Flights from './components/Flights';
import CreateReservation from './components/CreateReservation';
import Missing from './components/Missing';
import Login from './components/Login';
import Logout from './components/Logout.tsx';
import Employees from './components/Employees.tsx';


function App() {
    return (
        <Routes>
            <Route path="/" element={<Layout />}>
                <Route path='login' element={<Login />} />
                <Route path='logout' element={<Logout />} />
                <Route index element={<Flights />} />
                <Route path="/flights/:id/create-reservation" element={<CreateReservation />} />
                <Route path='/employees' element={<Employees />} />
                <Route path="*" element={<Missing />} />
            </Route>
        </Routes>
    )
}

export default App

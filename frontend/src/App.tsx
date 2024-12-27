import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import { Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import Flights from './components/Flights';
import Missing from './components/Missing';


function App() {

  //const navigate = useNavigate();
  // history.push('/') -> navigate('/');

  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Flights />} />
        <Route path="*" element={<Missing />} />
      </Route>
    </Routes>
  )
}

export default App

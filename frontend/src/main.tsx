import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { AuthProvider } from './context/AuthProvider.tsx'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <Router>
            <AuthProvider>
                <Routes>
                    <Route path='/*' element={ <App />} />
                </Routes>
            </AuthProvider>
        </Router>
        </StrictMode>
)

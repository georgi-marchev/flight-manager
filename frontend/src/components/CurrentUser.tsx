import { useState } from 'react';
import { Nav, Modal, Button } from 'react-bootstrap';
import useAuth from '../hooks/useAuth';
import { useNavigate } from 'react-router-dom';

const CurrentUser = () => {
    const { auth, setAuth } = useAuth();
    const [showModal, setShowModal] = useState(false); 
    const navigate = useNavigate();
    const handleShowModal = () => setShowModal(true);
    const handleCloseModal = () => setShowModal(false);
    const handleLogout = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setAuth({
            accessToken: null,
            refreshToken: null,
            authorities: [],
            username: null
        });
        navigate('/login')
        handleCloseModal();
    };

    return (
        <>
            <Nav.Item>
                <Button variant="link" onClick={handleShowModal} className="text-white">
                    {auth.username}
                </Button>
            </Nav.Item>
            <Modal show={showModal} onHide={handleCloseModal} className='align-items-center justify-content-center text-center'>
                <Modal.Header closeButton>
                    <Modal.Title>{auth.username}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Button variant="danger" onClick={handleLogout}>
                        Изход
                    </Button>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default CurrentUser
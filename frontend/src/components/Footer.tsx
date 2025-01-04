import { Container, Row, Col } from "react-bootstrap";

const Footer = () => {
    return (
        <footer className="bg-dark text-white mt-5 p-4 text-center"> 
            <Container> 
                <Row> 
                    <Col> 
                        <p>© {new Date().getFullYear()} Georgi Marchev</p> 
                    </Col> 
                </Row> 
            </Container> 
        </footer>
    )
}

export default Footer
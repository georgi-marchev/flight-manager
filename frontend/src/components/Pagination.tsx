import { Button, Col, Form, Row } from 'react-bootstrap';

interface PaginationProps {
    handlePreviousPage: () => void;
    handleNextPage: () => void;
    pageNum: number;
    hasNext: boolean;
}

const Pagination: React.FC<PaginationProps> = ({ handlePreviousPage, handleNextPage, pageNum, hasNext }) => {
    return (
        <Form>
            <Row className="mb-3 d-flex justify-content-end">
                <Col xs="auto" className="my-1">
                    <Button
                        onClick={handlePreviousPage}
                        disabled={pageNum === 0}
                    >
                        {'<'}
                    </Button>
                </Col>
                <Col xs="auto" className="my-1">
                    <Button
                        onClick={handleNextPage}
                        disabled={!hasNext}
                    >
                        {'>'}
                    </Button>
                    </Col>
            </Row>
        </Form>
      )
}

export default Pagination
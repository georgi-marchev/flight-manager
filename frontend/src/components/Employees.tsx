import { useState, useEffect } from 'react';
import useAuthenticatedApiClient from '../hooks/useAuthenticatedApiClient';
import Pagination from './Pagination';

const Users = () => {

    const [users, setUsers] = useState([]);
    const authenticatedClient = useAuthenticatedApiClient();

    useEffect(() => {

        // TODO
        const filters = {};
            
        const fetchUsers = async () => {
            
            try {
                const response = await authenticatedClient.get('/employee-accounts', {
                    params: {
                        ...filters,
                        page: 0,
                        size: 10
                    }
                });

                console.log(response.data);
                const data = response.data;
                // setUsers(data.content);
                // setHasNext(data.hasNext);

            } catch (err) {
                console.log(err);
                // setErrorMessage('Вмомента не може да бъде показана информация за полетите. Опитайте отново!')
            };
        };
    
        fetchUsers();
    }, []);

    return (
        <div>Users</div>
    )
}

export default Users
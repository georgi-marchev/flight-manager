import { apiClient } from "../api/apiClient";
import { useEffect } from "react";
import useAuth from "./useAuth";
import { useNavigate } from "react-router-dom";

const REFRESH_TOKEN_PATH = '/auth/refresh-token';

const useAuthenticatedApiClient = () => {
    const { auth, setAuth } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {

        const requestIntercept = apiClient.interceptors.request.use(
            config => {
                const accessToken = auth.accessToken

                if (accessToken && !config.headers['Authorization'] && config.url !== REFRESH_TOKEN_PATH) {
                    config.headers['Authorization'] = `Bearer ${auth?.accessToken}`;
                }
                return config;
            }, (error) => Promise.reject(error)
        );

        const responseIntercept = apiClient.interceptors.response.use(
            response => response,
            async (error) => {

                const { response, config } = error;

                if (config.url !== REFRESH_TOKEN_PATH) {
                    
                    const refreshToken = auth.refreshToken;

                    if (response.status === 401 && !config._isRetried && refreshToken) {
                        config._isRetried = true;

                        try {
                            const response = await apiClient.post(REFRESH_TOKEN_PATH, {refreshToken});
                            setAuth(prev => {
                                
                                return {
                                    ...prev,
                                    refreshToken: response.data.refreshToken,
                                    accessToken: response.data.accessToken
                                }
                            });
                            config.headers['Authorization'] = `Bearer ${response.data.accessToken}`;
                            
                        } catch (error) {
                            // TODO: Extract method
                            setAuth({
                                accessToken: null,
                                refreshToken: null
                            });
                            navigate('/login')
                            return Promise.reject(error);
                        }

                        return apiClient(config);
                    }
                }
                return Promise.reject(error);
            }
        );

        return () => {
            apiClient.interceptors.request.eject(requestIntercept);
            apiClient.interceptors.response.eject(responseIntercept);
        }
    }, [auth])

    return apiClient;
}

export default useAuthenticatedApiClient;
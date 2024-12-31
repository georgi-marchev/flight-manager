import { createContext, useState, useEffect } from "react";

const ACCESS_TOKEN_KEY = 'access_token';
const REFRESH_TOKEN_KEY = 'refresh_token';

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState(() => {
        return {
            accessToken: localStorage.getItem(ACCESS_TOKEN_KEY) || null,
            refreshToken: localStorage.getItem(REFRESH_TOKEN_KEY) || null
        };
    });

    // Save tokens to localStorage whenever they change
    useEffect(() => {
        if (auth.accessToken) {
            localStorage.setItem(ACCESS_TOKEN_KEY, auth.accessToken);
        }
        if (auth.refreshToken) {
            localStorage.setItem(REFRESH_TOKEN_KEY, auth.refreshToken);
        }
    }, [auth.accessToken, auth.refreshToken]);

    return (
        <AuthContext.Provider value={{ auth, setAuth }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;
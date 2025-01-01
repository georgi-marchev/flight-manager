import { createContext, useState, useEffect } from "react";

const ACCESS_TOKEN_KEY = 'access_token';
const REFRESH_TOKEN_KEY = 'refresh_token';
const AUTHORITIES_KEY = 'authorities';
const USERNAME_KEY = 'username';

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState(() => {
        return {
            accessToken: localStorage.getItem(ACCESS_TOKEN_KEY) || null,
            refreshToken: localStorage.getItem(REFRESH_TOKEN_KEY) || null,
            authorities: localStorage.getItem(AUTHORITIES_KEY) || [],
            username: localStorage.getItem(AUTHORITIES_KEY) || null
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
        if (auth.authorities) {
            localStorage.setItem(AUTHORITIES_KEY, auth.authorities);
        }
        if (auth.username) {
            localStorage.setItem(USERNAME_KEY, auth.username);
        }
    }, [auth.accessToken, auth.refreshToken, auth.authorities]);

    return (
        <AuthContext.Provider value={{ auth, setAuth }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;
import { createContext, useState, useEffect, ReactNode } from "react";

const ACCESS_TOKEN_KEY = 'access_token';
const REFRESH_TOKEN_KEY = 'refresh_token';
const AUTHORITIES_KEY = 'authorities';
const USERNAME_KEY = 'username';

export interface AuthState {
    accessToken: string | null;
    refreshToken: string | null;
    authorities: string[];
    username: string | null;
}
  
export interface AuthContextType {
    auth: AuthState;
    setAuth: React.Dispatch<React.SetStateAction<AuthState>>;
}
  
const AuthContext = createContext<AuthContextType | undefined>(undefined);
  
interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
    const [auth, setAuth] = useState<AuthState>(() => {

        const authorities = localStorage.getItem(AUTHORITIES_KEY);
        return {
            accessToken: localStorage.getItem(ACCESS_TOKEN_KEY) || null,
            refreshToken: localStorage.getItem(REFRESH_TOKEN_KEY) || null,
            authorities: authorities ? JSON.parse(authorities) : [],
            username: localStorage.getItem(USERNAME_KEY) || null
        };
    });

    // Save tokens to localStorage whenever they change
    useEffect(() => {
        auth.accessToken ? localStorage.setItem(ACCESS_TOKEN_KEY, auth.accessToken) : localStorage.removeItem(ACCESS_TOKEN_KEY);
        auth.refreshToken ? localStorage.setItem(REFRESH_TOKEN_KEY, auth.refreshToken) : localStorage.removeItem(REFRESH_TOKEN_KEY);
        auth.authorities ? localStorage.setItem(AUTHORITIES_KEY, JSON.stringify(auth.authorities)) : localStorage.removeItem(AUTHORITIES_KEY);
        auth.username ? localStorage.setItem(USERNAME_KEY, auth.username) : localStorage.removeItem(USERNAME_KEY);
    }, [auth.accessToken, auth.refreshToken, auth.authorities, auth.username]);

    return (
        <AuthContext.Provider value={{ auth, setAuth }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;
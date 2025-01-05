import useAuth from './useAuth'

export const useIsAdmin = (): boolean => {

    const { auth } = useAuth();
    return Boolean(auth && auth.authorities && auth.authorities.includes('ROLE_ADMIN'));
};

export const useIsLoggedIn = (): boolean => {

    const { auth } = useAuth();
    return Boolean(auth && auth.accessToken);
};

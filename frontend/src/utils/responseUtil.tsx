import { AxiosError } from 'axios';

const isAxiosError = (error: any): error is AxiosError => {
    return error.isAxiosError === true;
};

export const getErrorMessageOrDefault = (error: any, defaultMessage: string): string => {
    if (isAxiosError(error)) {
        const errorMessage = (error.response?.data as { message?: string })?.message;
        return errorMessage || defaultMessage;
    }
    
    return defaultMessage;
};
export const getErrorMessageOrDefault = (error, defaultMessage): string => {
    return error.response.data.message || defaultMessage;
};
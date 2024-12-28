export const pinIsValid = (pin: string): boolean => {
    const EGN_PATTERN = /^[0-9]{10}$/;
    const FOREIGNER_PIN_PATTERN = /^P\d{7}$/;
  
    return EGN_PATTERN.test(pin) || FOREIGNER_PIN_PATTERN.test(pin);
};

export const phoneIsValid = (phoneNumber: string): boolean => {
    const PHONE_PATTERN = /^\+?[0-9\s]*$/; 
  
    return PHONE_PATTERN.test(phoneNumber);
};
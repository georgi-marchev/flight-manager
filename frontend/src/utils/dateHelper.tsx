import { format } from 'date-fns';

export const formatDateTime = (dateString: string) => {
  const date = new Date(dateString);
  return format(date, 'dd.MM.yyyy HH:mm:ss');
};

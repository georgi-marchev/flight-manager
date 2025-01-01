import { format } from 'date-fns';

export const formatDateTime = (dateString: string) => {
	const date = new Date(dateString);
	return format(date, 'dd.MM.yyyy HH:mm');
};

export const getDuration = (start: string, end: string) => {
	const startDate = new Date(start);
    const endDate = new Date(end);
  
    const durationMs = endDate.getTime() - startDate.getTime();
    const durationMinutes = Math.floor(durationMs / (1000 * 60));

    if (durationMinutes < 60) {
        return `${durationMinutes} минути`;
    } else {
        const hours = Math.floor(durationMinutes / 60);
        const minutes = durationMinutes % 60;
        const hourText = hours === 1 ? 'час' : 'часа';
        const minuteText = minutes > 0 ? ` ${minutes} минути` : '';
        return `${hours} ${hourText}${minuteText}`;
    }
};

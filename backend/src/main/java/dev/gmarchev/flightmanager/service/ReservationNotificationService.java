package dev.gmarchev.flightmanager.service;

import java.time.format.DateTimeFormatter;

import dev.gmarchev.flightmanager.model.Location;
import dev.gmarchev.flightmanager.model.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReservationNotificationService {

	private final JavaMailSender mailSender;

	private final String senderEmail;

	private final String reservationMailSubject;
	
	private final String reservationMailBodyTemplate;

	public ReservationNotificationService(
			JavaMailSender mailSender, @Value("${spring.mail.from}") String senderEmail,
			@Value("${spring.mail.reservation.subject}") String reservationMailSubject,
			@Value("${spring.mail.reservation.body-template}") String reservationMailBodyTemplate) {

		this.mailSender = mailSender;
		this.senderEmail = senderEmail;
		this.reservationMailSubject = reservationMailSubject;
		this.reservationMailBodyTemplate = reservationMailBodyTemplate;
	}

	public void sendCompletedReservationMessage(Reservation reservation) {

		String to = reservation.getContactEmail();
		String body = reservationMailBodyTemplate
				.replace(
						"{from}",
						getFormattedLocation(reservation.getReservationFlight().getFlightDepartureLocation()))
				.replace(
						"{to}",
						getFormattedLocation(reservation.getReservationFlight().getFlightDestinationLocation()))
				.replace(
						"{departureTime}",
						DateTimeFormatter
								.ofPattern("HH:mm, dd.MM.yyyy")
								.format(reservation.getReservationFlight().getDepartureTime()))
				.replace(
						"{passengerCount}",
						String.valueOf(reservation.getPassengers().size()));

		sendEmail(to, body);
	}

	private static String getFormattedLocation(Location location) {

		return String.format("%s, %s (%s)", location.getCity(), location.getCountry(), location.getAirportName());
	}

	private void sendEmail(String to, String body) {

		try {

			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(reservationMailSubject);
			message.setText(body);
			message.setFrom(senderEmail);
			mailSender.send(message);

		} catch (Exception e) {

			log.error("Could not send email", e);
		}
	}
}
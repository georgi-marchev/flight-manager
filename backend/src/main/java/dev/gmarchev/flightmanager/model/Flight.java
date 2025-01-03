package dev.gmarchev.flightmanager.model;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;

	private ZonedDateTime departureTime;

	private ZonedDateTime arrivalTime;

	private int availableSeatsEconomy;

	private int availableSeatsBusiness;

	@ManyToOne
	@JoinColumn(name = "flightdeparture_location_id", referencedColumnName = "id")
	private Location flightDepartureLocation;

	@ManyToOne
	@JoinColumn(name = "flight_destination_location_id", referencedColumnName = "id")
	private Location flightDestinationLocation;

	@ManyToOne
	@JoinColumn(name = "flight_airplane_id", referencedColumnName = "id")
	private Airplane flightAirplane;

	@ManyToOne
	@JoinColumn(name = "flight_pilot_id", referencedColumnName = "id")
	private Pilot flightPilot;
}

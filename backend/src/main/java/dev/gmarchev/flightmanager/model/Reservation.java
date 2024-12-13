package dev.gmarchev.flightmanager.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String contactEmail;

	@ManyToOne
	@JoinColumn(name = "reservation_flight_id", referencedColumnName = "id")
	private Flight reservationFlight;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "reservation_id", referencedColumnName = "id")
	private List<Passenger> passengers;
}

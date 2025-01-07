package dev.gmarchev.flightmanager.dto.flight;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightUpdateRequest {

	@NotNull(message = "Време на излитане е задължителено.")
	private ZonedDateTime departureTime;

	@NotNull(message = "Време на кацане е задължителено.")
	private ZonedDateTime arrivalTime;

	@NotNull(message = "Идентификатор на пилот е задължителен.")
	private Long pilotId;
}

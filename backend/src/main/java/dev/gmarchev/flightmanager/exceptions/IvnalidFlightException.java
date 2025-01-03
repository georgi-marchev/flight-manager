package dev.gmarchev.flightmanager.exceptions;

import lombok.Getter;

@Getter
public class IvnalidFlightException extends RuntimeException {

	private final String details;

	public IvnalidFlightException(String message, String details) {

		super(message);

		this.details = details;
	}
}

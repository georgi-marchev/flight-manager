package dev.gmarchev.flightmanager.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

	private final String details;

	public EntityNotFoundException(String message, String details) {

		super(message);

		this.details = details;
	}
}

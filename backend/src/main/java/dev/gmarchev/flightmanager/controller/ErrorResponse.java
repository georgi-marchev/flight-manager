package dev.gmarchev.flightmanager.controller;

public record ErrorResponse(String error, String message) {

	public static ErrorResponse of(String error, String message) {

		return new ErrorResponse(error, message);
	}
}

package dev.gmarchev.flightmanager.service;

import java.util.List;

import dev.gmarchev.flightmanager.dto.LocationCreateRequest;
import dev.gmarchev.flightmanager.dto.LocationResponse;
import dev.gmarchev.flightmanager.model.Location;
import dev.gmarchev.flightmanager.repository.LocationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepository;

	public List<LocationResponse> getLocations() {

		return locationRepository
				.findAll()
				.stream().map(l -> new LocationResponse(
						l.getId(),
						l.getAirportName(),
						l.getCity(),
						l.getCountry()))
				.toList();
	}

	public Long createLocation(@Valid LocationCreateRequest locationCreateRequest) {

		return locationRepository
				.save(Location
						.builder()
						.airportName(locationCreateRequest.getAirportName())
						.city(locationCreateRequest.getCity())
						.country(locationCreateRequest.getCountry())
						.build())
				.getId();
	}
}

package dev.gmarchev.flightmanager.service;

import java.util.List;

import dev.gmarchev.flightmanager.dto.AirplaneCreateRequest;
import dev.gmarchev.flightmanager.dto.AirplaneResponse;
import dev.gmarchev.flightmanager.exceptions.EntityNotFoundException;
import dev.gmarchev.flightmanager.model.Airplane;
import dev.gmarchev.flightmanager.model.AirplaneModel;
import dev.gmarchev.flightmanager.repository.AirplaneModelRepository;
import dev.gmarchev.flightmanager.repository.AirplaneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirplaneService {

	private final AirplaneRepository airplaneRepository;

	private final AirplaneModelRepository airplaneModelRepository;

	public List<AirplaneResponse> getAirplanes() {

		return airplaneRepository
				.findAll()
				.stream()
				.map(a -> new AirplaneResponse(
						a.getId(),
						a.getSerialNumber(),
						a.getAirplaneAirplaneModel().getModelNumber(),
						a.getAirplaneAirplaneModel().getCapacityEconomy(),
						a.getAirplaneAirplaneModel().getCapacityBusiness()))
				.toList();
	}

	public long createAirplane(AirplaneCreateRequest airplaneCreateRequest) {

		AirplaneModel airplaneModel = airplaneModelRepository.findById(airplaneCreateRequest.getAirplaneModelId())
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(
								"Airplane model with ID %d was not found in the database",
								airplaneCreateRequest.getAirplaneModelId()),
						"Модел на самолет не може да бъде намерен."));

		Airplane newAirplane = airplaneRepository.save(Airplane
				.builder()
				.serialNumber(airplaneCreateRequest.getSerialNumber())
				.airplaneAirplaneModel(airplaneModel)
				.build());

		return newAirplane.getId();
	}
}

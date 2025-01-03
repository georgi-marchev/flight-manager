package dev.gmarchev.flightmanager.service;

import java.util.List;

import dev.gmarchev.flightmanager.dto.AirplaneModelResponse;
import dev.gmarchev.flightmanager.repository.AirplaneModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirplaneModelService {

	private final AirplaneModelRepository airplaneModelRepository;

	public List<AirplaneModelResponse> getAirplaneModels() {

		return airplaneModelRepository
				.findAll()
				.stream()
				.map(m -> new AirplaneModelResponse(
						m.getId(),
						m.getModelNumber(),
						m.getCapacityEconomy(),
						m.getCapacityBusiness()))
				.toList();
	}
}

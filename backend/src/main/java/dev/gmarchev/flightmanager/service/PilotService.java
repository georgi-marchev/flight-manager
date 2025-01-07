package dev.gmarchev.flightmanager.service;

import java.util.List;

import dev.gmarchev.flightmanager.dto.pilot.PilotCreateRequest;
import dev.gmarchev.flightmanager.dto.pilot.PilotResponse;
import dev.gmarchev.flightmanager.model.Pilot;
import dev.gmarchev.flightmanager.repository.PilotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PilotService {

	private final PilotRepository pilotRepository;

	public List<PilotResponse> getPilots() {

		return pilotRepository
				.findAll()
				.stream()
				.map(p -> new PilotResponse(p.getId(), p.getFirstName(), p.getLastName()))
				.toList();
	}

	public Long createPilot(PilotCreateRequest pilotCreateRequest) {

		Pilot newPilot = pilotRepository.save(
				Pilot.builder()
						.firstName(pilotCreateRequest.getFirstName())
						.lastName(pilotCreateRequest.getLastName())
						.build());

		return newPilot.getId();
	}
}

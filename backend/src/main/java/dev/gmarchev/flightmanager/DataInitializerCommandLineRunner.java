package dev.gmarchev.flightmanager;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.gmarchev.flightmanager.config.AccountMapping;
import dev.gmarchev.flightmanager.dto.AccountCreateRequest;
import dev.gmarchev.flightmanager.dto.FlightCreateRequest;
import dev.gmarchev.flightmanager.model.Airplane;
import dev.gmarchev.flightmanager.model.AirplaneModel;
import dev.gmarchev.flightmanager.model.Location;
import dev.gmarchev.flightmanager.model.Pilot;
import dev.gmarchev.flightmanager.model.RoleType;
import dev.gmarchev.flightmanager.repository.AirplaneModelRepository;
import dev.gmarchev.flightmanager.repository.AirplaneRepository;
import dev.gmarchev.flightmanager.repository.LocationRepository;
import dev.gmarchev.flightmanager.repository.PilotRepository;
import dev.gmarchev.flightmanager.service.AccountService;
import dev.gmarchev.flightmanager.service.FlightService;
import dev.gmarchev.flightmanager.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializerCommandLineRunner implements CommandLineRunner {

	private final RoleService roleService;

	private final AccountService accountService;

	private final AccountMapping accountMapping;

	private final AirplaneModelRepository airplaneModelRepository;

	private final AirplaneRepository airplaneRepository;

	private final FlightService flightService;

	private final LocationRepository locationRepository;

	private final PilotRepository pilotRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		log.info("Running data initialization sequence...");

		createMissingRoles();

		createMissingAdminAccount();

		createFlightData();

		log.info("Data initialization completed.");
	}

	private void createMissingRoles() {

		List<RoleType> rolesToCreate = new ArrayList<>();

		for (RoleType roleType : RoleType.values()) {

			if (!roleService.getRole(roleType).isPresent()) {

				rolesToCreate.add(roleType);
			}
		}

		if (!rolesToCreate.isEmpty()) {

			roleService.createRoles(rolesToCreate);
		}
	}

	private void createMissingAdminAccount() {

		if (!accountService.accountForRoleExists(RoleType.ADMIN)) {

			accountService.createAccount(accountMapping.admin(), RoleType.ADMIN);
		}
	}

	// TODO
	private void createFlightData() {

		accountService.createAccount(
				AccountCreateRequest.builder()
						.username("employee")
						.password("1234")
						.email("f@fake.com")
						.firstName("Employeename")
						.lastName("EMployeelastname")
						.address("Some fake address")
						.personalIdentificationNumber("123123213")
						.build(), RoleType.EMPLOYEE);

		AirplaneModel airplaneModel = AirplaneModel.builder().modelNumber("111").capacityEconomy(5).capacityBusiness(2).build();
		airplaneModelRepository.save(airplaneModel);

		Airplane airplane = Airplane.builder().airplaneAirplaneModel(airplaneModel).serialNumber("123").build();
		airplaneRepository.save(airplane);

		Pilot pilot = Pilot.builder().firstName("Top").lastName("Gun").build();
		pilotRepository.save(pilot);

		Location location1 = Location.builder().airportName("Heathrow Airport").city("London").country("UK").build();
		Location location2 = Location.builder().airportName("John F. Kennedy International Airport").city("New York").country("USA").build();
		Location location3 = Location.builder().airportName("Tokyo Haneda Airport").city("Tokyo").country("Japan").build();
		Location location4 = Location.builder().airportName("Charles de Gaulle Airport").city("Paris").country("France").build();
		locationRepository.saveAll(List.of(location1, location2, location3, location4));

		ZonedDateTime now = ZonedDateTime.now();
		FlightCreateRequest flight1 = FlightCreateRequest.builder()
				.flightAirplane(airplane.getId())
				.flightPilot(pilot.getId())
				.flightDepartureLocation(location1.getId())
				.flightDestinationLocation(location2.getId())
				.departureTime(now)
				.arrivalTime(now.plusHours(1))
				.build();
		FlightCreateRequest flight2 = FlightCreateRequest.builder()
				.flightAirplane(airplane.getId())
				.flightPilot(pilot.getId())
				.flightDepartureLocation(location3.getId())
				.flightDestinationLocation(location4.getId())
				.departureTime(now.plusHours(2))
				.arrivalTime(now.plusHours(3))
				.build();
		flightService.createFlight(flight1);
		flightService.createFlight(flight2);
	}
}

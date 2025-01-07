package dev.gmarchev.flightmanager;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.gmarchev.flightmanager.config.AccountMapping;
import dev.gmarchev.flightmanager.dto.account.AccountCreateRequest;
import dev.gmarchev.flightmanager.dto.flight.FlightCreateRequest;
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

		for (int i = 1; i <= 10; i++) {

			accountService.createAccount(
					AccountCreateRequest.builder()
							.username("employee" + i)
							.password("1234")
							.email(i + "employee@fake.com")
							.firstName("EmployeeName" + i)
							.lastName("EmployeeLastname" + i)
							.address("Fake address" + i)
							.phoneNumber(String.valueOf(1000000000 + i))
							.personalIdentificationNumber(String.valueOf(1000000000 + i))
							.build(), RoleType.EMPLOYEE);
		}


		AirplaneModel airplaneModel1 = AirplaneModel.builder()
				.modelNumber("AAA").capacityEconomy(5).capacityBusiness(2).build();
		airplaneModelRepository.save(airplaneModel1);
		AirplaneModel airplaneModel2 = AirplaneModel.builder()
				.modelNumber("BBB").capacityEconomy(5).capacityBusiness(2).build();
		airplaneModelRepository.save(airplaneModel2);
		AirplaneModel airplaneModel3 = AirplaneModel.builder()
				.modelNumber("CCC").capacityEconomy(5).capacityBusiness(2).build();
		airplaneModelRepository.save(airplaneModel3);
		AirplaneModel airplaneModel4 = AirplaneModel.builder()
				.modelNumber("DDD").capacityEconomy(5).capacityBusiness(2).build();
		airplaneModelRepository.save(airplaneModel4);

		Airplane airplane1 = Airplane.builder().airplaneAirplaneModel(airplaneModel1).serialNumber("111").build();
		airplaneRepository.save(airplane1);
		Airplane airplane2 = Airplane.builder().airplaneAirplaneModel(airplaneModel2).serialNumber("222").build();
		airplaneRepository.save(airplane2);
		Airplane airplane3 = Airplane.builder().airplaneAirplaneModel(airplaneModel3).serialNumber("333").build();
		airplaneRepository.save(airplane3);
		Airplane airplane4 = Airplane.builder().airplaneAirplaneModel(airplaneModel4).serialNumber("444").build();
		airplaneRepository.save(airplane4);

		Pilot pilot1 = Pilot.builder().firstName("Петър").lastName("Петров").build();
		pilotRepository.save(pilot1);
		Pilot pilot2 = Pilot.builder().firstName("Ивелина").lastName("Иванова").build();
		pilotRepository.save(pilot2);
		Pilot pilot3 = Pilot.builder().firstName("Иван").lastName("Иванов").build();
		pilotRepository.save(pilot3);
		Pilot pilot4 = Pilot.builder().firstName("Господин").lastName("Господинов").build();
		pilotRepository.save(pilot4);

		List<Location> locationList = Arrays.asList(
				new String[] { "Летище Лондон Хийтроу", "Лондон", "Великобритания" },
				new String[] { "Летище Париж Шарл де Гол", "Париж", "Франция" },
				new String[] { "Летище Франкфурт", "Франкфурт", "Германия" },
				new String[] { "Летище Амстердам Схипхол", "Амстердам", "Нидерландия" },
				new String[] { "Летище Дубай", "Дубай", "Обединени арабски емирства" },
				new String[] { "Летище Истанбул", "Истанбул", "Турция" },
				new String[] { "Летище Токио Нарита", "Токио", "Япония" },
				new String[] { "Летище Лос Анджелис", "Лос Анджелис", "САЩ" },
				new String[] { "Летище Сидни", "Сидни", "Австралия" },
				new String[] { "Летище Рим Фиумичино", "Рим", "Италия" },
				new String[] { "Летище Пекин Капитал", "Пекин", "Китай" },
				new String[] { "Летище Мадрид-Барахас", "Мадрид", "Испания" },
				new String[] { "Летище Хонконг", "Хонконг", "Китай" },
				new String[] { "Летище Куала Лумпур", "Куала Лумпур", "Малайзия" },
				new String[] { "Летище Мумбай", "Мумбай", "Индия" },
				new String[] { "Летище Чикаго О'Хеър", "Чикаго", "САЩ" },
				new String[] { "Летище Канкун", "Канкун", "Мексико" },
				new String[] { "Летище Банкок Суварнабхуми", "Банкок", "Тайланд" },
				new String[] { "Летище Сингапур Чанги", "Сингапур", "Сингапур" },
				new String[] { "Летище Сеул Инчон", "Сеул", "Южна Корея" }
		).stream().map(l -> Location.builder().airportName(l[0]).city(l[1]).country(l[2]).build()).toList();

		locationRepository.saveAll(locationList);

		Location location1 = locationList.get(0);
		Location location2 = locationList.get(1);
		Location location3 = locationList.get(3);
		Location location4 = locationList.get(4);

		ZonedDateTime now = ZonedDateTime.now();
		FlightCreateRequest flight1 = FlightCreateRequest.builder()
				.flightAirplane(airplane1.getId())
				.flightPilot(pilot1.getId())
				.flightDepartureLocation(location1.getId())
				.flightDestinationLocation(location2.getId())
				.departureTime(now)
				.arrivalTime(now.plusHours(1))
				.build();
		FlightCreateRequest flight2 = FlightCreateRequest.builder()
				.flightAirplane(airplane2.getId())
				.flightPilot(pilot2.getId())
				.flightDepartureLocation(location3.getId())
				.flightDestinationLocation(location4.getId())
				.departureTime(now.plusHours(2))
				.arrivalTime(now.plusHours(3))
				.build();

		ZonedDateTime tomorrow = now.plusDays(1);
		FlightCreateRequest flight3 = FlightCreateRequest.builder()
				.flightAirplane(airplane1.getId())
				.flightPilot(pilot1.getId())
				.flightDepartureLocation(location1.getId())
				.flightDestinationLocation(location2.getId())
				.departureTime(tomorrow)
				.arrivalTime(tomorrow.plusHours(1))
				.build();
		FlightCreateRequest flight4 = FlightCreateRequest.builder()
				.flightAirplane(airplane2.getId())
				.flightPilot(pilot2.getId())
				.flightDepartureLocation(location3.getId())
				.flightDestinationLocation(location4.getId())
				.departureTime(tomorrow.plusHours(2))
				.arrivalTime(tomorrow.plusHours(3))
				.build();
		flightService.createFlight(flight1);
		flightService.createFlight(flight2);
		flightService.createFlight(flight3);
		flightService.createFlight(flight4);
	}
}

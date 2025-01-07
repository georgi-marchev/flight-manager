# Documentation for OpenAPI definition

<a name="documentation-for-api-endpoints"></a>

## Authentication and Authorization

The API uses JWT access and refresh tokens. Registered users are assigned roles and some endpoints require a role to be accessed.

## Documentation for API Endpoints

| Class                         | Method                                                                                 | HTTP request                               | Authenticated | Authenticated |
| ----------------------------- | -------------------------------------------------------------------------------------- | ------------------------------------------ | ------------- | ------------- |
| _AccountControllerApi_        | [**createEmployeeAccount**](Apis/AccountControllerApi.md#createEmployeeAccount)        | **POST** /api/employee-accounts            | Yes           | ADMIN         |
| _AccountControllerApi_        | [**getEmployeeAccountById**](Apis/AccountControllerApi.md#getemployeeaccountbyid)      | **GET** /api/employee-accounts/{accountId} | Yes           | ADMIN         |
| _AccountControllerApi_        | [**getEmployeeAccounts**](Apis/AccountControllerApi.md#getemployeeaccounts)            | **GET** /api/employee-accounts             | Yes           | ADMIN         |
| _AccountControllerApi_        | [**updateEmployeeAccount**](Apis/AccountControllerApi.md#updateemployeeaccount)        | **PUT** /api/employee-accounts/{accountId} | Yes           | ADMIN         |
| _AirplaneControllerApi_       | [**createAirplane**](Apis/AirplaneControllerApi.md#createairplane)                     | **POST** /api/airplanes                    | Yes           | ADMIN         |
| _AirplaneControllerApi_       | [**getAirplanes**](Apis/AirplaneControllerApi.md#getairplanes)                         | **GET** /api/airplanes                     | Yes           | ADMIN         |
| _AirplaneModelControllerApi_  | [**getAirplanesModels**](Apis/AirplaneModelControllerApi.md#getAirplanesModels)        | **GET** /api/airplane-models               | Yes           | ADMIN         |
| _AuthenticationControllerApi_ | [**login**](Apis/AuthenticationControllerApi.md#login)                                 | **POST** /api/auth/login                   | No            |               |
| _AuthenticationControllerApi_ | [**refreshToken**](Apis/AuthenticationControllerApi.md#refreshtoken)                   | **POST** /api/auth/refresh-token           | No            |               |
| _FlightControllerApi_         | [**createFlight**](Apis/FlightControllerApi.md#createFlight)                           | **POST** /api/flights                      | Yes           | ADMIN         |
| _FlightControllerApi_         | [**getFlightById**](Apis/FlightControllerApi.md#getflightbyid)                         | **GET** /api/flights/{flightId}            | No            |               |
| _FlightControllerApi_         | [**getFlightReservationsById**](Apis/FlightControllerApi.md#getflightreservationsbyid) | **GET** /api/flights/{id}/passengers       | Yes           |               |
| _FlightControllerApi_         | [**getFlights**](Apis/FlightControllerApi.md#getflights)                               | **GET** /api/flights                       | No            |               |
| _FlightControllerApi_         | [**updateFlight**](Apis/FlightControllerApi.md#updateflight)                           | **PUT** /api/flights/{flightId}            | Yes           | ADMIN         |
| _LocationControllerApi_       | [**createLocation**](Apis/LocationControllerApi.md#createLocation)                     | **POST** /api/locations                    | Yes           | ADMIN         |
| _LocationControllerApi_       | [**getLocations**](Apis/LocationControllerApi.md#getLocations)                         | **GET** /api/locations                     | Yes           | ADMIN         |
| _PilotControllerApi_          | [**createPilot**](Apis/PilotControllerApi.md#createPilot)                              | **POST** /api/pilots                       | Yes           | ADMIN         |
| _PilotControllerApi_          | [**getPilots**](Apis/PilotControllerApi.md#getpilots)                                  | **GET** /api/pilots                        | Yes           | ADMIN         |
| _ReservationControllerApi_    | [**getReservationById**](Apis/ReservationControllerApi.md#getreservationbyid)          | **GET** /api/reservations/{id}             | Yes           |               |
| _ReservationControllerApi_    | [**getResrevations**](Apis/ReservationControllerApi.md#getresrevations)                | **GET** /api/reservations                  | Yes           |               |
| _ReservationControllerApi_    | [**makeReservation**](Apis/ReservationControllerApi.md#makereservation)                | **POST** /api/reservations                 | No            |               |

<a name="documentation-for-models"></a>

## Documentation for Models

- [AccountCreateRequest](./Models/AccountCreateRequest.md)
- [AccountPageItem](./Models/AccountPageItem.md)
- [AccountResponse](./Models/AccountResponse.md)
- [AirplaneCreateRequest](./Models/AirplaneCreateRequest.md)
- [AirplaneModelResponse](./Models/AirplaneModelResponse.md)
- [AirplaneResponse](./Models/AirplaneResponse.md)
- [AuthenticationRequest](./Models/AuthenticationRequest.md)
- [EmployeeUpdateRequest](./Models/EmployeeUpdateRequest.md)
- [FlightCreateRequest](./Models/FlightCreateRequest.md)
- [FlightPageItem](./Models/FlightPageItem.md)
- [FlightPassengerPageItem](./Models/FlightPassengerPageItem.md)
- [FlightResponse](./Models/FlightResponse.md)
- [FlightUpdateRequest](./Models/FlightUpdateRequest.md)
- [LocationCreateRequest](./Models/LocationCreateRequest.md)
- [LocationResponse](./Models/LocationResponse.md)
- [PageResponseAccountPageItem](./Models/PageResponseAccountPageItem.md)
- [PageResponseFlightPageItem](./Models/PageResponseFlightPageItem.md)
- [PageResponseFlightPassengerPageItem](./Models/PageResponseFlightPassengerPageItem.md)
- [PageResponseReservationPageItem](./Models/PageResponseReservationPageItem.md)
- [PilotCreateRequest](./Models/PilotCreateRequest.md)
- [PilotResponse](./Models/PilotResponse.md)
- [RefreshTokenRequest](./Models/RefreshTokenRequest.md)
- [ReservationPageItem](./Models/ReservationPageItem.md)
- [ReservationPassengerResponse](./Models/ReservationPassengerResponse.md)
- [ReservationRequest](./Models/ReservationRequest.md)
- [ReservationRequestPassenger](./Models/ReservationRequestPassenger.md)
- [ReservationResponse](./Models/ReservationResponse.md)

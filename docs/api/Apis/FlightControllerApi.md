# FlightControllerApi

All URIs are relative to _http://localhost:8080_

| Method                                                                            | HTTP request                         | Description |
| --------------------------------------------------------------------------------- | ------------------------------------ | ----------- |
| [**create2**](FlightControllerApi.md#create2)                                     | **POST** /api/flights                |             |
| [**getFlightById**](FlightControllerApi.md#getFlightById)                         | **GET** /api/flights/{flightId}      |             |
| [**getFlightReservationsById**](FlightControllerApi.md#getFlightReservationsById) | **GET** /api/flights/{id}/passengers |             |
| [**getFlights**](FlightControllerApi.md#getFlights)                               | **GET** /api/flights                 |             |
| [**updateFlight**](FlightControllerApi.md#updateFlight)                           | **PUT** /api/flights/{flightId}      |             |

<a name="create2"></a>

# **create2**

> String create2(FlightCreateRequest)

### Parameters

| Name                    | Type                                                        | Description | Notes |
| ----------------------- | ----------------------------------------------------------- | ----------- | ----- |
| **FlightCreateRequest** | [**FlightCreateRequest**](../Models/FlightCreateRequest.md) |             |       |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

<a name="getFlightById"></a>

# **getFlightById**

> FlightResponse getFlightById(flightId)

### Parameters

| Name         | Type     | Description | Notes             |
| ------------ | -------- | ----------- | ----------------- |
| **flightId** | **Long** |             | [default to null] |

### Return type

[**FlightResponse**](../Models/FlightResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

<a name="getFlightReservationsById"></a>

# **getFlightReservationsById**

> PageResponseFlightPassengerPageItem getFlightReservationsById(id, page, size)

### Parameters

| Name     | Type        | Description | Notes             |
| -------- | ----------- | ----------- | ----------------- |
| **id**   | **Long**    |             | [default to null] |
| **page** | **Integer** |             | [default to null] |
| **size** | **Integer** |             | [default to null] |

### Return type

[**PageResponseFlightPassengerPageItem**](../Models/PageResponseFlightPassengerPageItem.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

<a name="getFlights"></a>

# **getFlights**

> PageResponseFlightPageItem getFlights(page, size, departureDate, departureLocation, destinationLocation, availableSeatsEconomy, availableSeatsBusiness)

### Parameters

| Name                       | Type        | Description | Notes                        |
| -------------------------- | ----------- | ----------- | ---------------------------- |
| **page**                   | **Integer** |             | [default to null]            |
| **size**                   | **Integer** |             | [default to null]            |
| **departureDate**          | **date**    |             | [optional] [default to null] |
| **departureLocation**      | **String**  |             | [optional] [default to null] |
| **destinationLocation**    | **String**  |             | [optional] [default to null] |
| **availableSeatsEconomy**  | **Integer** |             | [optional] [default to null] |
| **availableSeatsBusiness** | **Integer** |             | [optional] [default to null] |

### Return type

[**PageResponseFlightPageItem**](../Models/PageResponseFlightPageItem.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

<a name="updateFlight"></a>

# **updateFlight**

> String updateFlight(flightId, FlightUpdateRequest)

### Parameters

| Name                    | Type                                                        | Description | Notes             |
| ----------------------- | ----------------------------------------------------------- | ----------- | ----------------- |
| **flightId**            | **Long**                                                    |             | [default to null] |
| **FlightUpdateRequest** | [**FlightUpdateRequest**](../Models/FlightUpdateRequest.md) |             |                   |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

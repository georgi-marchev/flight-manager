# ReservationControllerApi

All URIs are relative to _http://localhost:8080_

| Method                                                                   | HTTP request                   | Description |
| ------------------------------------------------------------------------ | ------------------------------ | ----------- |
| [**getReservationById**](ReservationControllerApi.md#getReservationById) | **GET** /api/reservations/{id} |             |
| [**getResrevations**](ReservationControllerApi.md#getResrevations)       | **GET** /api/reservations      |             |
| [**makeReservation**](ReservationControllerApi.md#makeReservation)       | **POST** /api/reservations     |             |

<a name="getReservationById"></a>

# **getReservationById**

> ReservationResponse getReservationById(id)

### Parameters

| Name   | Type     | Description | Notes             |
| ------ | -------- | ----------- | ----------------- |
| **id** | **Long** |             | [default to null] |

### Return type

[**ReservationResponse**](../Models/ReservationResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

<a name="getResrevations"></a>

# **getResrevations**

> PageResponseReservationPageItem getResrevations(page, size, contactEmail)

### Parameters

| Name             | Type        | Description | Notes                        |
| ---------------- | ----------- | ----------- | ---------------------------- |
| **page**         | **Integer** |             | [default to null]            |
| **size**         | **Integer** |             | [default to null]            |
| **contactEmail** | **String**  |             | [optional] [default to null] |

### Return type

[**PageResponseReservationPageItem**](../Models/PageResponseReservationPageItem.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

<a name="makeReservation"></a>

# **makeReservation**

> String makeReservation(ReservationRequest)

### Parameters

| Name                   | Type                                                      | Description | Notes |
| ---------------------- | --------------------------------------------------------- | ----------- | ----- |
| **ReservationRequest** | [**ReservationRequest**](../Models/ReservationRequest.md) |             |       |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

# AirplaneControllerApi

All URIs are relative to _http://localhost:8080_

| Method                                                        | HTTP request            | Description |
| ------------------------------------------------------------- | ----------------------- | ----------- |
| [**createAirplane**](AirplaneControllerApi.md#createAirplane) | **POST** /api/airplanes |             |
| [**getAirplanes**](AirplaneControllerApi.md#getAirplanes)     | **GET** /api/airplanes  |             |

<a name="createAirplane"></a>

# **createAirplane**

> Object createAirplane(AirplaneCreateRequest)

### Parameters

| Name                      | Type                                                            | Description | Notes |
| ------------------------- | --------------------------------------------------------------- | ----------- | ----- |
| **AirplaneCreateRequest** | [**AirplaneCreateRequest**](../Models/AirplaneCreateRequest.md) |             |       |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

<a name="getAirplanes"></a>

# **getAirplanes**

> List getAirplanes()

### Parameters

This endpoint does not need any parameter.

### Return type

[**List**](../Models/AirplaneResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

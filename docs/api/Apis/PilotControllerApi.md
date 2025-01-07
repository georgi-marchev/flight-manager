# PilotControllerApi

All URIs are relative to _http://localhost:8080_

| Method                                           | HTTP request         | Description |
| ------------------------------------------------ | -------------------- | ----------- |
| [**create**](PilotControllerApi.md#create)       | **POST** /api/pilots |             |
| [**getPilots**](PilotControllerApi.md#getPilots) | **GET** /api/pilots  |             |

<a name="create"></a>

# **create**

> Object create(PilotCreateRequest)

### Parameters

| Name                   | Type                                                      | Description | Notes |
| ---------------------- | --------------------------------------------------------- | ----------- | ----- |
| **PilotCreateRequest** | [**PilotCreateRequest**](../Models/PilotCreateRequest.md) |             |       |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

<a name="getPilots"></a>

# **getPilots**

> List getPilots()

### Parameters

This endpoint does not need any parameter.

### Return type

[**List**](../Models/PilotResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

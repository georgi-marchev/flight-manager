# LocationControllerApi

All URIs are relative to _http://localhost:8080_

| Method                                                | HTTP request            | Description |
| ----------------------------------------------------- | ----------------------- | ----------- |
| [**create1**](LocationControllerApi.md#create1)       | **POST** /api/locations |             |
| [**getPilots1**](LocationControllerApi.md#getPilots1) | **GET** /api/locations  |             |

<a name="create1"></a>

# **create1**

> Object create1(LocationCreateRequest)

### Parameters

| Name                      | Type                                                            | Description | Notes |
| ------------------------- | --------------------------------------------------------------- | ----------- | ----- |
| **LocationCreateRequest** | [**LocationCreateRequest**](../Models/LocationCreateRequest.md) |             |       |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

<a name="getPilots1"></a>

# **getPilots1**

> List getPilots1()

### Parameters

This endpoint does not need any parameter.

### Return type

[**List**](../Models/LocationResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

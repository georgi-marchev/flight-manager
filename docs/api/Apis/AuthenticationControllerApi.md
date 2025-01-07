# AuthenticationControllerApi

All URIs are relative to _http://localhost:8080_

| Method                                                          | HTTP request                     | Description |
| --------------------------------------------------------------- | -------------------------------- | ----------- |
| [**login**](AuthenticationControllerApi.md#login)               | **POST** /api/auth/login         |             |
| [**refreshToken**](AuthenticationControllerApi.md#refreshToken) | **POST** /api/auth/refresh-token |             |

<a name="login"></a>

# **login**

> Object login(AuthenticationRequest)

### Parameters

| Name                      | Type                                                            | Description | Notes |
| ------------------------- | --------------------------------------------------------------- | ----------- | ----- |
| **AuthenticationRequest** | [**AuthenticationRequest**](../Models/AuthenticationRequest.md) |             |       |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

<a name="refreshToken"></a>

# **refreshToken**

> Object refreshToken(RefreshTokenRequest)

### Parameters

| Name                    | Type                                                        | Description | Notes |
| ----------------------- | ----------------------------------------------------------- | ----------- | ----- |
| **RefreshTokenRequest** | [**RefreshTokenRequest**](../Models/RefreshTokenRequest.md) |             |       |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

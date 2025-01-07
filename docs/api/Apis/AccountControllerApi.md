# AccountControllerApi

All URIs are relative to _http://localhost:8080_

| Method                                                                       | HTTP request                               | Description |
| ---------------------------------------------------------------------------- | ------------------------------------------ | ----------- |
| [**create3**](AccountControllerApi.md#create3)                               | **POST** /api/employee-accounts            |             |
| [**getEmployeeAccountById**](AccountControllerApi.md#getEmployeeAccountById) | **GET** /api/employee-accounts/{accountId} |             |
| [**getEmployeeAccounts**](AccountControllerApi.md#getEmployeeAccounts)       | **GET** /api/employee-accounts             |             |
| [**updateEmployeeAccount**](AccountControllerApi.md#updateEmployeeAccount)   | **PUT** /api/employee-accounts/{accountId} |             |

<a name="create3"></a>

# **create3**

> String create3(AccountCreateRequest)

### Parameters

| Name                     | Type                                                          | Description | Notes |
| ------------------------ | ------------------------------------------------------------- | ----------- | ----- |
| **AccountCreateRequest** | [**AccountCreateRequest**](../Models/AccountCreateRequest.md) |             |       |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

<a name="getEmployeeAccountById"></a>

# **getEmployeeAccountById**

> AccountResponse getEmployeeAccountById(accountId)

### Parameters

| Name          | Type     | Description | Notes             |
| ------------- | -------- | ----------- | ----------------- |
| **accountId** | **Long** |             | [default to null] |

### Return type

[**AccountResponse**](../Models/AccountResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

<a name="getEmployeeAccounts"></a>

# **getEmployeeAccounts**

> PageResponseAccountPageItem getEmployeeAccounts(page, size, username, email, firstName, lastName)

### Parameters

| Name          | Type        | Description | Notes                        |
| ------------- | ----------- | ----------- | ---------------------------- |
| **page**      | **Integer** |             | [default to null]            |
| **size**      | **Integer** |             | [default to null]            |
| **username**  | **String**  |             | [optional] [default to null] |
| **email**     | **String**  |             | [optional] [default to null] |
| **firstName** | **String**  |             | [optional] [default to null] |
| **lastName**  | **String**  |             | [optional] [default to null] |

### Return type

[**PageResponseAccountPageItem**](../Models/PageResponseAccountPageItem.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

<a name="updateEmployeeAccount"></a>

# **updateEmployeeAccount**

> String updateEmployeeAccount(accountId, EmployeeUpdateRequest)

### Parameters

| Name                      | Type                                                            | Description | Notes             |
| ------------------------- | --------------------------------------------------------------- | ----------- | ----------------- |
| **accountId**             | **Long**                                                        |             | [default to null] |
| **EmployeeUpdateRequest** | [**EmployeeUpdateRequest**](../Models/EmployeeUpdateRequest.md) |             |                   |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: _/_

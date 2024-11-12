# SongControllerApiImplApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createSong**](SongControllerApiImplApi.md#createSong) | **POST** /songs |  |
| [**getSongs**](SongControllerApiImplApi.md#getSongs) | **GET** /songs |  |
| [**updateSong**](SongControllerApiImplApi.md#updateSong) | **PUT** /songs |  |


<a id="createSong"></a>
# **createSong**
> SongDTO createSong(songDTO)



### Example
```java
// Import classes:
import com.usj.musicquizz.invoker.ApiClient;
import com.usj.musicquizz.invoker.ApiException;
import com.usj.musicquizz.invoker.Configuration;
import com.usj.musicquizz.invoker.models.*;
import com.usj.musicquizz.api.SongControllerApiImplApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    SongControllerApiImplApi apiInstance = new SongControllerApiImplApi(defaultClient);
    SongDTO songDTO = new SongDTO(); // SongDTO | 
    try {
      SongDTO result = apiInstance.createSong(songDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SongControllerApiImplApi#createSong");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **songDTO** | [**SongDTO**](SongDTO.md)|  | |

### Return type

[**SongDTO**](SongDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a id="getSongs"></a>
# **getSongs**
> List&lt;SongDTO&gt; getSongs(limit, offset)



### Example
```java
// Import classes:
import com.usj.musicquizz.invoker.ApiClient;
import com.usj.musicquizz.invoker.ApiException;
import com.usj.musicquizz.invoker.Configuration;
import com.usj.musicquizz.invoker.models.*;
import com.usj.musicquizz.api.SongControllerApiImplApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    SongControllerApiImplApi apiInstance = new SongControllerApiImplApi(defaultClient);
    Integer limit = 56; // Integer | 
    Long offset = 56L; // Long | 
    try {
      List<SongDTO> result = apiInstance.getSongs(limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SongControllerApiImplApi#getSongs");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **limit** | **Integer**|  | [optional] |
| **offset** | **Long**|  | [optional] |

### Return type

[**List&lt;SongDTO&gt;**](SongDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a id="updateSong"></a>
# **updateSong**
> SongDTO updateSong(songDTO)



### Example
```java
// Import classes:
import com.usj.musicquizz.invoker.ApiClient;
import com.usj.musicquizz.invoker.ApiException;
import com.usj.musicquizz.invoker.Configuration;
import com.usj.musicquizz.invoker.models.*;
import com.usj.musicquizz.api.SongControllerApiImplApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    SongControllerApiImplApi apiInstance = new SongControllerApiImplApi(defaultClient);
    SongDTO songDTO = new SongDTO(); // SongDTO | 
    try {
      SongDTO result = apiInstance.updateSong(songDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SongControllerApiImplApi#updateSong");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **songDTO** | [**SongDTO**](SongDTO.md)|  | |

### Return type

[**SongDTO**](SongDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


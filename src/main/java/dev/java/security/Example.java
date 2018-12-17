package dev.java.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public final class Example {
    private static final String URL_LOGIN = "http://localhost:8080/login";
    private static final String URL_EMPLOYEES = "http://localhost:8080/candidates";

    private Example() {
    }

    // POST Login
    // @return "Authorization string".
    private static String postLogin(String username, String password) {

        // Request Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 "
                                  + "(KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 OPR/39.0.2256.71");

        // Request Body
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add("username", username);
        parametersMap.add("password", password);

        // Request Entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parametersMap, headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // POST Login
        ResponseEntity<String> response = restTemplate.exchange(URL_LOGIN, //
                HttpMethod.POST, requestEntity, String.class);

        HttpHeaders responseHeaders = response.getHeaders();

        List<String> list = responseHeaders.get("Authorization");
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private static void callRESTApi(String restUrl, String authorizationString) {
        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) "
                                  + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 "
                                  + "OPR/39.0.2256.71");

        //
        // Authorization string (JWT)
        //
        headers.set("Authorization", authorizationString);
        //
        MediaType[] mediaTypes = {MediaType.APPLICATION_JSON};
        headers.setAccept(Arrays.asList(mediaTypes));

        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity<String>: To get result as String.
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES, //
                HttpMethod.GET, entity, String.class);

        String result = response.getBody();

        System.out.println(result);
    }

    public static void main(String[] args) {
        String username = "k.pilyak96@tut.by";
        String password = "123456";

        String authorizationString = postLogin(username, password);

        System.out.println("Authorization String=" + authorizationString);

        // Call REST API:
        callRESTApi(URL_EMPLOYEES, authorizationString);
    }

}


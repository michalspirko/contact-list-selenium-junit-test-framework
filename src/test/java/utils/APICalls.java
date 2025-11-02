package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class APICalls {
    // Fields
    private  CloseableHttpClient httpClient;
    private String token;

    // Constructor
    public APICalls() throws InterruptedException {
        this.httpClient = HttpClientBuilder.create().build();
    }

    // Method for getting the Bearer token from a cookie
    public String getSingleCookieValue(WebDriver driver, String cookieName) throws InterruptedException {
        waitForCookieNamed(driver,"token",Duration.ofSeconds(5));
        Cookie cookie = driver.manage().getCookieNamed(cookieName);
        return cookie.getValue();
    }

    // Method for waiting fo the cookie
    public void waitForCookieNamed(WebDriver driver, String cookieName, Duration timeout) {
        new WebDriverWait(driver, timeout).until(d -> d.manage().getCookieNamed(cookieName) != null);
    }

    // Method for deleting the user via API
    public void deleteUser(WebDriver driver) throws IOException, InterruptedException {
        this.token = getSingleCookieValue(driver, "token");
        HttpDelete request = new HttpDelete("https://thinking-tester-contact-list.herokuapp.com/users/me");
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.token);
        // Create an instance of the HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Execute the request and get the response
        httpClient.execute(request);

    }

    // Method for creating a contact via API
    public void createContact(WebDriver driver, String firstName, String lastName, String birthdate, String email, String phone,
                              String street1, String street2, String city, String stateProvince, String postalCode,
                              String country) throws InterruptedException, IOException {
        // Get token from the method
        this.token = getSingleCookieValue(driver, "token");
        //Request
        HttpPost request = new HttpPost("https://thinking-tester-contact-list.herokuapp.com/contacts");
        JSONObject contact = new JSONObject();
        contact.put("firstName", firstName);
        contact.put("lastName", lastName);
        contact.put("birthdate", birthdate);
        contact.put("email", email);
        contact.put("phone", phone);
        contact.put("street1", street1);
        contact.put("street2", street2);
        contact.put("city", city);
        contact.put("stateProvince", stateProvince);
        contact.put("postalCode", postalCode);
        contact.put("country", country);
        StringEntity requestBody = new StringEntity(contact.toString(), ContentType.APPLICATION_JSON);
        request.setEntity(requestBody);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.token);
        HttpResponse response = httpClient.execute(request);

    }

    // Method for deleting a contact via API
    public void deleteContact(WebDriver driver, String contactID) throws Exception {
        String url = "https://thinking-tester-contact-list.herokuapp.com/contacts/" + contactID;

        // Get token from the method
        this.token = getSingleCookieValue(driver, "token");

        // Create an HttpClient instance
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // Create a DELETE request
            HttpDelete httpDelete = new HttpDelete(url);

            // Set headers
            httpDelete.setHeader("Authorization", "Bearer " + this.token);

            // Execute the request
            HttpResponse response = httpClient.execute(httpDelete);
            HttpEntity entity = response.getEntity();

            // Ensure successful response
            if (response.getStatusLine().getStatusCode() != 200) {
                // Optionally: Handle non-200 status codes
                String responseBody = EntityUtils.toString(entity);
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode() +
                        " - " + responseBody);
            }
        }
    }

    // Method for closing the http client
    public void closeHttpClient() throws IOException {
        this.httpClient.close();
    }
}

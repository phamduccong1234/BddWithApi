package stepdefinitions.CatApi;

import static org.testng.Assert.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CatApiSteps {
	String url, method, apiKey, apiValue, requestBody;
	HttpResponse<String> response;
	@Given("I have valid request")
	public void i_have_valid_request() {
		url = "https://api.thecatapi.com/v1/votes";
		method = "POST";
		apiKey = "x-api-key";
		apiValue = "DEMO-API-KEY";
		requestBody = "\"image_id\": \"0XYvRd7oD\",\n"
				+ "\"value\":1,\n"
				+ "\"sub_id\": \"tester_01\"";
	}

	@When("I send request")
	public void i_send_request() {
		HttpRequest request = HttpRequest.newBuilder()
				.POST(BodyPublishers.ofString(requestBody))
				.uri(URI.create(url))
				.header(apiKey, apiValue)
				.build();
		HttpClient client = HttpClient.newBuilder().build();
		
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (Exception e) {
			System.out.println("Request invalid");
			e.printStackTrace();
		}
	}

	@Then("Api response valid status code and success message")
	public void api_response_valid_status_code_and_success_message() {
		assertEquals(response.statusCode(), 200);
	}

}

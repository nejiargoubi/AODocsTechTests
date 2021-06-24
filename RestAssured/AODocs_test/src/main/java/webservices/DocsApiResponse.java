package webservices;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.output.WriterOutputStream;


public class DocsApiResponse {
	
	public enum RequestMethod {
		
		GET, POST, PUT
	}

	private RequestSpecification request;
	private Response response;


	
	//@param requestBodyParams: Can be null if no request body is needed, such as for GET requests.
		public DocsApiResponse(RequestMethod requestMethod, String url, JSONObject requestBodyParams) {
			StringWriter requestWriter = new StringWriter();
			PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter), true);

			request = RestAssured.given().filter(new RequestLoggingFilter(requestCapture));
			
			request.header("Accept", "application/json");
			request.header("Content-Type", "application/json");
			request.header("Authorization", "Bearer " + getAccessToken());
			
			request.body(requestBodyParams.toString());
			
			
			if (requestBodyParams != null) {
				request.header("Content-Type", "application/json");
				request.body(requestBodyParams.toString());
			}

			switch (requestMethod) {
			case GET:
				response = request.get(url);
				break;
			case POST:
				response = request.post(url);
				break;
			case PUT:
				response = request.put(url);
				break;
			}
				
			response.then().log().all();
		}



		private String getAccessToken() {

			String access_token = "ya29.a0AfH6SMDlplqRCJZNoOKhmiDouUnv66SNvBFJr5kfTRPCqquBWV4VkkihzkHgDmYjSk5IhmCLxs6R1a-KDwkFEdiQAXugcyXE2W56r32FbXFRHIvVsggQKUkGafHuUtdH-nnWtooYcxAFzThN2qjgYjtJy4nW";
			return access_token;
			
		}

		public JsonPath getJsonPath() {
			return response.jsonPath();
		}

		public void verifyResponseStatusCode(int expectedStatusCode) {

			Assert.assertTrue(response.statusCode() == expectedStatusCode);
		}

		public void verifyResponseTimeDoesntExceed(long timeout) {
			Assert.assertTrue(response.timeIn(TimeUnit.SECONDS) <= timeout);
		}
	
	

}

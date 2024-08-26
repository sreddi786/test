package assessment.test.api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.UUID;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserApiTest {

    private String baseURI = "https://gorest.co.in/";
    private String token = "34560b6deff5c1670c77ae8d3ad681bb13ef71d970e3e5e118e19cdc09cfd3f4"; 
    private int createdUserId;
    String uniqueEmail = "";
    String uniqueName = "";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseURI;
         uniqueEmail = generateUniqueEmail();
         uniqueName = generateUniqueName();
    }

    @Test(priority = 1)
    public void testCreateUser() {

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", uniqueName);
        requestBody.put("gender", "male");
        requestBody.put("email", uniqueEmail);
        requestBody.put("status", "active");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody.toJSONString())
                .post("/public/v2/users");

        Assert.assertEquals(response.getStatusCode(), 201, "Status code is not 201");

        // Extract the user ID from the response
        createdUserId = response.jsonPath().getInt("id");

        Assert.assertEquals(response.jsonPath().getString("name"), uniqueName, "Name mismatch");
        Assert.assertEquals(response.jsonPath().getString("gender"), "male", "Gender mismatch");
        Assert.assertEquals(response.jsonPath().getString("email"), uniqueEmail, "Email mismatch");
        Assert.assertEquals(response.jsonPath().getString("status"), "active", "Status mismatch");
   
    }

    @Test(priority = 2, dependsOnMethods = "testCreateUser")
    public void testGetUser() {
        // Send GET request to retrieve the created user
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .get("/public/v2/users/" + createdUserId);

        // Assert the response status code is 200 (OK)
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        Assert.assertEquals(response.jsonPath().getString("name"), uniqueName, "Name mismatch");
        Assert.assertEquals(response.jsonPath().getString("gender"), "male", "Gender mismatch");
        Assert.assertEquals(response.jsonPath().getString("email"), uniqueEmail, "Email mismatch");
        Assert.assertEquals(response.jsonPath().getString("status"), "active", "Status mismatch");
    }
    
    private static String generateUniqueEmail() {
        long timestamp = System.currentTimeMillis();
        return "tenali.ramakrishna." + timestamp + "@15ce.com";
    }
    
    private static String generateUniqueName() {
        String uuid = UUID.randomUUID().toString().substring(0, 8); // Shorten UUID for readability
        return "User_" + uuid;
    }
}


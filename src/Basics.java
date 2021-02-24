import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMEthods;
import files.payload;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
// validate if ADD place API working as expected
		// given - all input details
		// when - submit the API
		// then - validate the response
		//content of the file to string -->convert into Byte --> byte data to string
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-type","application/json")
		.body(GenerateStringFromResource("E:\\Masters Project Manament\\Wisdom assignements\\RestAssured\\staticpaylod.json")).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		//JsonPath is a class that take input as string and convert into Json
		JsonPath js = new JsonPath(response);
		String placeid=js.getString("place_id");
		
	//	System.out.println(placeid);
		
	//	String newPlace = "70 Summer walk, USA";
		
//		given().log().all().queryParam("Key", "qaclick123").header("Content-type","application/json")
//		.body("{\r\n"
//				+ "\"place_id\":\""+placeid+"\",\r\n"
//				+ "\"address\":\""+newPlace+"\",\r\n"
//				+ "\"key\":\"qaclick123\"\r\n"
//				+ "}\r\n"
//				+ "")
//		.when().put("maps/api/place/update/json")
//		.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		//GetPlace
		
//		String getPlace=given().log().all().queryParam("Key", "qaclick123")
//				.queryParam("place_id",placeid)
//		.when().get("maps/api/place/get/json")
//		.then().assertThat().log().all().statusCode(200)
//		.extract().response().asString();
//		
//		JsonPath js1=ReUsableMEthods.rawToJson(getPlace);
//		String actualAddress = js1.getString("address");
//		System.out.println(actualAddress);
//		
//		Assert.assertEquals(actualAddress, newPlace);

		

	}
	
	public static String GenerateStringFromResource(String path) throws IOException {



		  return new String(Files.readAllBytes(Paths.get(path)));



		}
}

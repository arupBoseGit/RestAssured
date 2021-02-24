package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="TestData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().header("Content-Type","application/json").body(payload.AddBook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js=ReUsableMEthods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id); 
	}
	//DeleteBook
	@Test(dataProvider="TestData")
		public void DeleteBook(String isbn, String aisle) {
			RestAssured.baseURI="http://216.10.245.166";
			String delete=given().queryParam("isbn",isbn)
			.when().delete("/Library/Addbook.php")
			.then().log().all().assertThat().statusCode(200)
			.extract().response().asString();
			
			JsonPath js=ReUsableMEthods.rawToJson(delete);
			String id = js.get("ID");
			System.out.println(id); 
		}
	
		
	
	@DataProvider(name="TestData")
	public Object[][] getData(){
		return new Object[][] {{"456798","lernu"},{"158705","maths"},{"372015","Engli"}};
	}

}

package files;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DynamicJson {
	
	
	
	//@Test(dataProvider="TestData", priority=1)
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String resp = given().header("Content-Type","application/json").body(payload.AddBook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js=ReUsableMEthods.rawToJson(resp);
		String id = js.get("ID");
		System.out.println("Id is "+id);
		
		//delter 
		
	}
	 
	//DeleteBook
	@Test(dataProvider="TestData",priority=2)
		public void DeleteBook(String isbn, String aisle) {
			
			RestAssured.baseURI="http://216.10.245.166";
			String resp = given().header("Content-Type","application/json").body(payload.AddBook(isbn,aisle))
			.when().post("/Library/Addbook.php")
			.then().log().all().assertThat().statusCode(200)
			.extract().response().asString();
			
			JsonPath js=ReUsableMEthods.rawToJson(resp);
			String id = js.get("ID");
			System.out.println("Id is "+id);
			
			RestAssured.baseURI="http://216.10.245.166";
			String getResponse=given().queryParam("isbn",isbn).header("Content-Type","application/json").body(payload.deletebok(id))
			.when().delete("/Library/DeleteBook.php")
			.then().log().all().assertThat().statusCode(200)
			.extract().response().asString();
			
			JsonPath js1=ReUsableMEthods.rawToJson(getResponse);
			String id1 = js1.get("ID");
			System.out.println("Requested id is "+id1); 
		}
	
		
	
	@DataProvider(name="TestData")
	public Object[][] getData(){
		return new Object[][] {{"789","uiyi"},{"4646","gfgf"},{"464","xfxfv"}};
	}

}

package files;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath; 

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Learnings - How to create Session Filter for Authentication in Rest Assured 
		//Automation in introducing Path Parameters and Query Parameters together Single Test Sending 
		//Files as Attachments using Rest Assured with Multipart method parsing Complex Json and limiting Json response
		// through query parameters handling HTTPS certification validation through automated case
	
		
		RestAssured.baseURI="http://localhost:8080/";
		//Login scenario
		//login to Jira to create session using login API
		//Add a comment to existing issue using Add comment API
		SessionFilter session=new SessionFilter();//session cookie can be used with filter method
		
		String response = given().header("Content-Type","application/json").body("{\r\n" +
		"\"username\": \"arupbose1992\",\r\n" +
		"\"password\": \"Study@2019\"\r\n"+
		"}")
		.log().all().filter(session).when().post("rest/auth/1/session").then().log().all().extract().response().asString();
		
		String expectedComment = "Hello there, defect retest and failed hence sending it back to developer.";
		String comment=given().pathParam("id", "10027").log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+expectedComment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath js = new JsonPath(comment);
		String commentID=js.getString("id");
	
		//Add an attachment to existing issue using add attachment API
		//file is for authentication
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id", "10027")
		.header("content-Type","multipart/form-data")
		.multiPart("file", new File("jira.txt")).when().post("/rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
	
		
		//get issue details and verify if added comment and attachment exists using Get issue API
		String issueDetail=given().filter(session).pathParam("id", "10027")
				.queryParam("fields", "comment").log().all()
		.when().get("rest/api/2/issue/{id}").then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(issueDetail);
		int NumberOfComments=js1.getInt("fields.comment.comments.size()");
		for(int i =0; i<NumberOfComments;i++) {
			String actualCommentId=js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentID.equalsIgnoreCase(actualCommentId)) {
			String Message = js1.get("fields.comment.comments["+i+"].body").toString();
			System.out.println(Message);
				Assert.assertEquals(expectedComment, Message);
			}
		}
		
		
	}
	
	


}

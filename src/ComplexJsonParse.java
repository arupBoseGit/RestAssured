import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		

		JsonPath js = new JsonPath(payload.CoursePrice());
		
	//	1. Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println("Number of Courses are "+count);
		//	2.Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Total Amount - "+totalAmount);
		
		//3. Print Title of the first course
		String course = js.getString("courses[0].title");
		System.out.println("Course Name is "+course);
		
		//4. Print All course titles and their respective Prices
		//5. Print no of copies sold by RPA Course
		
		
//		for (int i =0;i<count;i++) {
//			String coursetitle=js.getString("courses["+i+"].title");
//			if (coursetitle.equalsIgnoreCase("RPA")) {
//				int copies=js.get("courses["+i+"].copies");
//				System.out.println(copies);
//			}
//			System.out.println("Price of course "+i+" is "+js.getInt("courses["+i+"].price"));
//		}
		
		int sum=0;
		for(int j=0;j<count;j++) {
			int count1 = js.getInt("courses["+j+"].copies");
			System.out.println(count1);
			int price1 = js.getInt("courses["+j+"].price");
			System.out.println(price1);
			sum=sum+(count1*price1);
		}
		System.out.println("sum is "+sum);
		if (sum==totalAmount) {System.out.println("Sum of course is correct");}
		else System.out.println("Total price is incorrect");
	}
}

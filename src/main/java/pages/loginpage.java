package pages;


import java.sql.ResultSet;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import Utils.ConfigFileReader;
import Utils.DatabaseOperations;
import Utils.ExcelUtils;
import constants.Constants;
import executionEngine.DriverInti;

public class loginpage extends DriverInti {
	ConfigFileReader reader;
	WebElement email,password,loginbtn,errorup,errordown,addemp;
	String url= "http://dev-api-dopayroll.ztmz2r22rz.us-east-1.elasticbeanstalk.com/login";
	ExcelUtils data ;
//	SoftAssert softassert= new SoftAssert();

	public void getElements() {
		email=findByXpath(reader.getPropertyValue("email"));
		password=findByXpath(reader.getPropertyValue("password"));
		loginbtn=findByXpath(reader.getPropertyValue("loginbtn"));
	}
	
	public loginpage(String browser) {
		super(browser);
		// TODO Auto-generated constructor stub
		navigate(url);
		reader = new ConfigFileReader(Constants.configPath+"/LoginPageObject.properties");
//		getElements();
	}




public void setEmail(String value) {
	clear(email);
	setText(email,value);
}
public void setPass(String value) {
clear(password);
	setText(password,value);
}
public void clickbtn() {
	
	click(loginbtn);
}
public void submitbtn(){
	submit(loginbtn);
}

public void loginvalidation(String Email,String Password,String expected,String enc) throws Exception {
	SoftAssert softassert= new SoftAssert();
	String smail=" ";
	String spass=" ";
	
	data = new ExcelUtils("C:\\Users\\RoOoKa\\eclipse-workspace\\DoPayRollTasks\\src\\main\\java\\testData\\test.xlsx", "login sheet");
	DatabaseOperations.connectDB();
	
	for (int i =1; i <6; i++) {
		
	 if(expected.equals("Correct")) {
			ResultSet res = DatabaseOperations.executeQuery("select * from `db-dev-dopayroll`.users where email ='"+Email+"' and password='"+enc+"'");
			while(res.next()) {
					smail = res.getString("email");
					spass = res.getString("password");
					if(smail.isEmpty() ||spass.isEmpty()) 
					{
						softassert.assertTrue(false,"User is NOT Found in DB");
				//		System.out.println("User is NOT Found in DB");
						DatabaseOperations.closeDatabaseConnection();
					}
					else {
				//		Thread.sleep(10000);
				/*		new WebDriverWait(driver,1000).until(
						          webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));*/
						
						softassert.assertEquals(getCurrentURL().toLowerCase(),"http://dev-api-dopayroll.ztmz2r22rz.us-east-1.elasticbeanstalk.com/employees");
					}
			}	
			
		}
		else if((Email.isEmpty() )|| ((Email)== null )) {
			errorup=findByXpath(reader.getPropertyValue("errorup"));
			softassert.assertTrue(errorup.getText().equalsIgnoreCase(expected));}
		
		else if((Password.isEmpty() )|| ((Password)== null )) {
			errordown=findByXpath(reader.getPropertyValue("errordown"));
			softassert.assertTrue(errordown.getText().equalsIgnoreCase(expected));
		}
	
		else if((Email.isEmpty() )|| ((Email)== null ) && (Password.isEmpty() )|| ((Password)== null )) {
			errorup=findByXpath(reader.getPropertyValue("errorup"));
			errordown=findByXpath(reader.getPropertyValue("errordown"));
			softassert.assertTrue(errorup.getText().equalsIgnoreCase(expected) ||
					errorup.getText().equalsIgnoreCase(data.getCellDataasstring(i-1, 2)) ||
					errorup.getText().equalsIgnoreCase(data.getCellDataasstring(i+1, 2)) &&
					errordown.getText().equalsIgnoreCase(data.getCellDataasstring(i, 2)) || 
					errordown.getText().equalsIgnoreCase(data.getCellDataasstring(i-1, 2)) ||
					errordown.getText().equalsIgnoreCase(data.getCellDataasstring(i+1, 2)));}
		else {
			errorup=findByXpath(reader.getPropertyValue("errorup"));
			softassert.assertTrue(errorup.getText().equalsIgnoreCase(expected));}

		softassert.assertAll();

	}
	
}

}



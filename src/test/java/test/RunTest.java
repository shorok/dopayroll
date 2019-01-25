package test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Utils.ExcelUtils;
import executionEngine.DriverInti;
import pages.loginpage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;


public class RunTest {
	 loginpage page;
  
  @Test(dataProvider="Authentication" , priority=1)
  public void LoginTest(String email,String password,String expected,String encpass) throws Exception  {
	  page.getElements();
	  page.setEmail(email);
	  page.setPass(password);
	  page.submitbtn(); 
	  
	  new WebDriverWait(DriverInti.driver,1000).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	
	  page.loginvalidation(email, password, expected, encpass);
  }
  
  //(enabled=false)

@DataProvider
public Object[][] Authentication() throws Exception{
	 
    Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\RoOoKa\\eclipse-workspace\\DoPayRollTasks\\src\\main\\java\\testData\\test.xlsx","login sheet", 4);

    return (testObjArray);

}

  @Parameters("browser")
  @BeforeTest
  public void beforeTest(String browser) {
	 page = new loginpage(browser);
	 
  }
}
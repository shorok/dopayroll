package test;

import org.testng.annotations.Test;

import Utils.ExcelUtils;
import pages.AddEmpPage;
import pages.HomePage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class AddEmpPageTest {
	 AddEmpPage page;
	 HomePage hpage;
	 
  @Test(dataProvider="AddEmp",priority=6)
  public void ValidateEmployeeCreation(String first,String sec, String third,String last, String date, String title, String dep, String email, String salary, String expected) throws Exception {
	  	hpage= new HomePage();
	  	hpage.clickadd();
		page.createSteps( first, sec,  third, last,  date,  title, dep,  email,  salary, expected);
		
  }
  @DataProvider
  public Object[][] AddEmp() throws Exception{
		 
	    Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\RoOoKa\\eclipse-workspace\\DoPayRollTasks\\src\\main\\java\\testData\\test.xlsx","new employee", 10);
	    return (testObjArray);

	}

  
  @BeforeClass
  public void beforeClass() {
		page= new AddEmpPage();

  }

}

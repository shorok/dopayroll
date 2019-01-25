package test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Utils.ExcelUtils;
import pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

public class HomePageTest {
	 HomePage page;
	 String downloadFilepath ="C:\\Users\\RoOoKa\\Downloads" ;
	 SoftAssert softassert= new SoftAssert();
	 
	 @DataProvider
	 public Object[][] search() throws Exception{
	 	 
	     Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\RoOoKa\\eclipse-workspace\\DoPayRollTasks\\src\\main\\java\\testData\\test.xlsx","multisearch", 3);
	     return (testObjArray);

	 }
	 @Test(dataProvider="search" ,priority=5)
	 public void MultiFooterSearchTest(String dep, String date, String salary) throws Exception {
		 page.validateMultipleSearchtableWithDatabase( dep, date, salary);
	 }
	 
	@Test(priority=2)
	public void ValidatePDFDownloads() throws InterruptedException {
		Thread.sleep(2000);
		page.clickPDF();
		Thread.sleep(5000);
		softassert.assertTrue(page.isFileDownloaded_Exist(downloadFilepath, "Employees"));	
		softassert.assertEquals(page.getFileExtension(page.getLatestFilefromDir(downloadFilepath)), "pdf");
		System.out.println("Downloaded File name is: "+page.getFileName(page.getLatestFilefromDir(downloadFilepath)));
		softassert.assertAll();
	}

	@Test(priority=3)
	public void ValidateEXCELDownloads() throws InterruptedException {
		Thread.sleep(3000);
		page.clickEXCEL();
		Thread.sleep(5000);
		softassert.assertTrue(page.isFileDownloaded_Exist(downloadFilepath, "Employees"));	
		softassert.assertEquals(page.getFileExtension(page.getLatestFilefromDir(downloadFilepath)), "xlsx");
		System.out.println("Downloaded File name is: "+page.getFileName(page.getLatestFilefromDir(downloadFilepath)));
		softassert.assertAll();
	}

	@Test(priority=4)
	public void ValidateCSVDownloads() throws InterruptedException {
		Thread.sleep(3000);
		page.clickCSV();
		Thread.sleep(5000);
		softassert.assertTrue(page.isFileDownloaded_Exist(downloadFilepath, "Employees"));	
		softassert.assertEquals(page.getFileExtension(page.getLatestFilefromDir(downloadFilepath)), "csv");
		System.out.println("Downloaded File name is: "+page.getFileName(page.getLatestFilefromDir(downloadFilepath)));
		softassert.assertAll();
	}


  @BeforeMethod
  public void beforeMethod() {
	  
  }

  @BeforeClass
  public void beforeClass() {
	  page = new HomePage();
	 
  }

}

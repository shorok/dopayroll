package pages;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import Utils.ConfigFileReader;
import Utils.DatabaseOperations;
import Utils.ExcelUtils;
import constants.Constants;
import executionEngine.DriverInti;

public class AddEmpPage extends DriverInti {
	ConfigFileReader reader;
	WebElement fname, mname, lname, famname, newtitle,titlelist, email, salary, savebtn,
	empwindowform, createbtn, depmenu,date,calender,thead,tbody,errorfname,errormname,errorlname,errorsalary,erroremail,addemp,noti;
	ExcelUtils data ;
	HomePage hpage;
	int day,month,year;
	String url="http://dev-api-dopayroll.ztmz2r22rz.us-east-1.elasticbeanstalk.com/employees";
	 SoftAssert softassert= new SoftAssert();
	public AddEmpPage() {
		// TODO Auto-generated constructor stub
		reader = new ConfigFileReader(Constants.configPath + "/AddEmp.properties");
	
	}

	public void getElements() {
		fname = findByXpath(reader.getPropertyValue("fname"));
		mname = findByXpath(reader.getPropertyValue("mname"));
		lname = findByXpath(reader.getPropertyValue("lname"));
		famname = findByXpath(reader.getPropertyValue("famname"));
		newtitle = findByXpath(reader.getPropertyValue("newtitle"));
		titlelist = findByXpath(reader.getPropertyValue("titlelist"));
		email = findByXpath(reader.getPropertyValue("email"));
		salary = findByXpath(reader.getPropertyValue("salary"));
		savebtn = findByXpath(reader.getPropertyValue("savebtn"));
		empwindowform = findByXpath(reader.getPropertyValue("empwindowform"));
		createbtn = findByXpath(reader.getPropertyValue("createbtn"));
		depmenu = findByXpath(reader.getPropertyValue("depmenu"));
		date = findByXpath(reader.getPropertyValue("date"));


		/*calender = findByXpath(reader.getPropertyValue("calender"));
		thead = findByXpath(reader.getPropertyValue("thead"));
		tbody = findByXpath(reader.getPropertyValue("tbody"));*/
	}



	public void createSteps(String first,String sec, String third,String last, String HDate, String Title, String DDep, String Email, String Salary, String expected) throws Exception {
		getElements();
		data = new ExcelUtils("C:\\Users\\RoOoKa\\eclipse-workspace\\DoPayRollTasks\\src\\main\\java\\testData\\test.xlsx", "new employee");
		for (int i =1; i <6; i++) {
			timeOut(5);
			getElements();
			clear(fname);
			setText(fname,first);
			clear(mname);
			setText(mname,sec);
			clear(lname);
			setText(lname,third);
			clear(famname);
			setText(famname,last);
			clear(date);
			date.getText();
			setText(date, HDate);
			
			if(HDate.isEmpty()) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate localDate = LocalDate.now();
				click(fname);
				String datefield = getAttributeValue(date,"value");
				softassert.assertEquals(dtf.format(localDate),datefield );
				System.out.println(dtf.format(localDate)); 
			}
			String extract =HDate;
			String[]array=extract.split("/");
			
				 day =Integer.parseInt(array[0]);
				 month =Integer.parseInt(array[1]);
				 year =Integer.parseInt(array[2]);
			
			if(day>31 || day<1 || month >12 || month<1) {
				softassert.assertFalse(true,"Date is Wrong");
			}
			
			Select titleselect = new Select(titlelist);
			List<WebElement> tcount = titleselect.getOptions();
			tcount.size();
			boolean tfound = false;
			for(int b = 0 ; b<tcount.size();b++) {
				String tvalue = tcount.get(b).getText();
				if(tvalue.equalsIgnoreCase(Title)) {
					titleselect.selectByVisibleText(tvalue);
					tfound=true;}
			}
			if(tfound==false) {
				click(newtitle);
				timeOut(3);
				clear(empwindowform);
				setText(empwindowform,Title);
				click(createbtn);
				timeOut(3);
				List<WebElement> ttcount = titleselect.getOptions();
				ttcount.size();
				
				for(int c = 0 ; c<ttcount.size();c++) {
					String ttvalue = ttcount.get(c).getText();
					if(ttvalue.equalsIgnoreCase(Title)) {
						//titleselect.selectByVisibleText(ttvalue);
						softassert.assertTrue(true);
					}
			}
			}


			Select depselect = new Select(depmenu);
			List<WebElement> dcount = depselect.getOptions();
			dcount.size();
			for(int b = 0 ; b<dcount.size();b++) {
				String dvalue = dcount.get(b).getText();
				if(dvalue.equalsIgnoreCase(DDep)) {
					depselect.selectByVisibleText(dvalue);
					}
			}
			clear(email);
			setText(email,Email);
			clear(salary);
			setText(salary,Salary);
			click(savebtn);
	//		savebtn.submit();
		/*	WebDriverWait wait1 = new WebDriverWait(driver, 200);
			   ExpectedCondition<Boolean> urlIsCorrect = arg0 ->  driver.getCurrentUrl().toLowerCase().equals(url.toLowerCase());
			   wait1.until(urlIsCorrect);*/
			Thread.sleep(7000);
			   
			boolean emailformat=Email.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}");
			 
			if(driver.getCurrentUrl().equalsIgnoreCase("http://dev-api-dopayroll.ztmz2r22rz.us-east-1.elasticbeanstalk.com/employees/create")) {
				if((first.isEmpty()) || (first==null) ) {
				errorfname= findByXpath(reader.getPropertyValue("errorfname"));
				softassert.assertTrue(expected.equalsIgnoreCase(errorfname.getText()), "mandatory feild is empty");
			}
			
			else if((Salary.isEmpty()) || (Salary==null)) {
				errorsalary= findByXpath(reader.getPropertyValue("errorsalary"));
				softassert.assertTrue(expected.equalsIgnoreCase(errorsalary.getText()), "mandatory feild is empty");
			}
			else if((Email.isEmpty()) || (Email==null)) {
				erroremail= findByXpath(reader.getPropertyValue("erroremail"));
				softassert.assertTrue(expected.equalsIgnoreCase(erroremail.getText()),"email feild is empty");
				}
			else if(emailformat==false) {
				erroremail= findByXpath(reader.getPropertyValue("erroremail"));
				softassert.assertTrue(expected.equalsIgnoreCase(erroremail.getText()),"email format is wrong");
			}
			}
			
		
			else {
				
				//new WebDriverWait(driver, 1000).until(
				  //        webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	//*/		//	hpage.validateNoti(first, sec, third, last);
				noti = findByXpath(reader.getPropertyValue("noti"));
				String notif = noti.getText();
				System.out.println(notif);
				
				softassert.assertTrue(notif.equalsIgnoreCase("Employee "+first+" " +sec +" "+third+" "+last+" has been added successfully"));
			
				softassert.assertEquals(driver.getCurrentUrl().toLowerCase(),"http://dev-api-dopayroll.ztmz2r22rz.us-east-1.elasticbeanstalk.com/employees");
				
				 duplicateName(first,sec,third,last);
				 
				 Thread.sleep(3000);
				hpage.navigateaddemp();
			//driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
//			Thread.sleep(18000);
			
				
			}
				
		}
		softassert.assertAll();
	}
	public void duplicateName(String first,String second,String third,String last) throws SQLException, ClassNotFoundException {
		DatabaseOperations.connectDB();
		ResultSet res = DatabaseOperations.executeQuery("select count(*) AS rowcount from `db-dev-dopayroll`.employees where "
				+ "first_name ='"+first+"' and second_name='"+second+"'and "
						+ "third_name= '"+third+"' and last_name='"+last+"'");
		res.next() ;		
		int count = res.getInt("rowcount");
		if(count > 1) {
			softassert.assertTrue(false, "Full name is already in DB"); 
			System.out.println("Full name is already in DB");}
		else if(count == 1) {
			softassert.assertTrue(true, "Full Name is UNIQE");
			System.out.println("Full Name is UNIQE");}
	}
	

	
}



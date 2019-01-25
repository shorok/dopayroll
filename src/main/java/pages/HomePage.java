package pages;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import Utils.ConfigFileReader;
import Utils.DatabaseOperations;
import Utils.ExcelUtils;
import constants.Constants;
import executionEngine.DriverInti;

public class HomePage extends DriverInti{

	ConfigFileReader reader;
	WebElement company,position,addemp,tbody,next,nextdis,excelbtn,pdfbtn,csvbtn,datesearch,salarysearch,depsearch,emptyrow,noti;
	List<WebElement> rows,cols;
	String value;
	ExcelUtils data ;
	int day,month,year;
//	private ResultSet res;
	SoftAssert softassert= new SoftAssert();

	
	public HomePage() {
		// TODO Auto-generated constructor stub
		reader = new ConfigFileReader(Constants.configPath+"/HomePage.properties");
		getElements();
	}
	public void getElements() {
		company=findByXpath(reader.getPropertyValue("company"));
		position=findByXpath(reader.getPropertyValue("position"));
		next=findByXpath(reader.getPropertyValue("next"));
		nextdis=findByXpath(reader.getPropertyValue("nextdis"));
		excelbtn=findByXpath(reader.getPropertyValue("excelbtn"));
		pdfbtn=findByXpath(reader.getPropertyValue("pdfbtn"));
		csvbtn=findByXpath(reader.getPropertyValue("csvbtn"));
		datesearch=findByXpath(reader.getPropertyValue("datesearch"));
		salarysearch=findByXpath(reader.getPropertyValue("salarysearch"));
		depsearch=findByXpath(reader.getPropertyValue("depsearch"));
		emptyrow=findByXpath(reader.getPropertyValue("emptyrow"));
	}
	public void validateNoti(String first,String sec, String third,String last) {
		noti = findByXpath(reader.getPropertyValue("noti"));
		String notif = noti.getText();
		System.out.println(notif);
		softassert.assertTrue(notif.equalsIgnoreCase("Employee "+first+" " +sec +" "+third+" "+last+" has been added successfully"));
	}
	
public void clickcompany() {
		
		click(company);
	}
public void clickadd() {
	addemp=findByXpath(reader.getPropertyValue("addemp"));
	click(addemp);
}

public void clickposition() {
	
	click(position);
}
public void navigatecomp() {
	//timeOut(20);
	clickcompany();
	clickposition();
}
public void navigateaddemp() {
//	timeOut(20);
	clickadd();
}
public void clickPDF() {
	click(pdfbtn);
}
public void clickEXCEL() {
	click(excelbtn);
}
public void clickCSV() {
	click(csvbtn);
}
public void setDepartmentSearchBox(String item) {
	clear(depsearch);
	setText(depsearch, item);
}
public void setDateSearchBox(String item) {
	clear(datesearch);
	setText(datesearch, item);
}
public void setSalarySearchBox(String item) {
	clear(salarysearch);
	setText(salarysearch, item);
}

public String getFileExtension(File file) {
    String fileName = file.getName();
    if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
    return fileName.substring(fileName.lastIndexOf(".")+1);
    else return "";
}
public  String getFileName(File file) {
    String fileName = file.getName();
    
    return fileName;
}
public  File getLatestFilefromDir(String dirPath){
    File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
        return null;
    }

    File lastModifiedFile = files[0];
    for (int i = 1; i < files.length; i++) {
       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
           lastModifiedFile = files[i];
       }
    }
    return lastModifiedFile;
}
public  boolean isFileDownloaded_Exist(String dirPath, String ext){
	boolean flag=false;
    File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
        flag = false;
    }
    
    for (int i = 1; i < files.length; i++) {
    	if(files[i].getName().contains(ext)) {
    		flag=true;
    	}
    }
    return flag;
}


/*public void validateDepSearchtableWithDatabase() throws Exception {

	DatabaseOperations.connectDB();
	data = new ExcelUtils("C:\\Users\\RoOoKa\\eclipse-workspace\\DoPayRollTasks\\src\\main\\java\\testData\\test.xlsx", "search");
	for (int i =1; i <4; i++) {
		clear(depsearch);
		clear(datesearch);
		clear(salarysearch);
		ResultSet res = DatabaseOperations.executeQuery("select count(*) AS rowcount  FROM `db-dev-dopayroll`.employees inner join `db-dev-dopayroll`.departments \r\n" + 
				"on `db-dev-dopayroll`.employees.department_id = `db-dev-dopayroll`.departments.department_id \r\n" + 
				"where \r\n" + 
				"employees.company_id='40eb6956-9eb8-47cc-8d56-8ba556d2d11e' and departments.name like '"+data.getCellDataasstring(i, 0)+"%'");
		res.next();	
		int rcount = res.getInt("rowcount");
		
	setDepartmentSearchBox(data.getCellDataasstring(i, 0));
	timeOut(5);
	tbody=findByXpath(reader.getPropertyValue("tbody"));

	rows= tbody.findElements(By.xpath("tr"));
	while(true) {
		getElements();
		String classAttribute =DriverInti.getAttributeValue(nextdis, "class");
	if(classAttribute.equalsIgnoreCase("paginate_button next disabled"))
			break;
		else {
			click(next);
		rows.addAll(tbody.findElements(By.xpath("tr")));
		}
	}
	String empty=getText(emptyrow);
	if(empty.equalsIgnoreCase("No matching records found")) 
			softassert.assertEquals(rows.size(), rcount+1);
	else
			softassert.assertEquals(rows.size(),rcount);
	}
	softassert.assertAll();
}
public void validateDateSearchtableWithDatabase() throws Exception {

	DatabaseOperations.connectDB();
	clear(depsearch);
	clear(datesearch);
	clear(salarysearch);
	data = new ExcelUtils("C:\\Users\\RoOoKa\\eclipse-workspace\\DoPayRollTasks\\src\\main\\java\\testData\\test.xlsx", "search");
	
	for (int i =1; i <4; i++) {
		ResultSet res = DatabaseOperations.executeQuery("select count(*) AS rowcount from `db-dev-dopayroll`.employees where company_id='40eb6956-9eb8-47cc-8d56-8ba556d2d11e' and date(hiring_date) like '%"+data.getCellDataasstring(i, 2)+"%'");
		 	
	  res.next();	
		int rcount = res.getInt("rowcount");
		
		setDateSearchBox(data.getCellDataasstring(i,2));
	timeOut(5);
	tbody=findByXpath(reader.getPropertyValue("tbody"));

	rows= tbody.findElements(By.xpath("tr"));
	while(true) {
		getElements();
		String classAttribute =DriverInti.getAttributeValue(nextdis, "class");
	if(classAttribute.equalsIgnoreCase("paginate_button next disabled"))
			break;
		else {
			click(next);
		rows.addAll(tbody.findElements(By.xpath("tr")));
		}
	}
	String empty=getText(emptyrow);
	if(empty.equalsIgnoreCase("No matching records found")) 
			softassert.assertEquals(rows.size(), rcount+1);
	else
			softassert.assertEquals(rows.size(),rcount);

	}
	softassert.assertAll();
}

public void validateSalarySearchtableWithDatabase() throws Exception {

	DatabaseOperations.connectDB();
	data = new ExcelUtils("C:\\Users\\RoOoKa\\eclipse-workspace\\DoPayRollTasks\\src\\main\\java\\testData\\test.xlsx", "search");
	for (int i =1; i <4; i++) {
		clear(depsearch);
		clear(datesearch);
		clear(salarysearch);
		ResultSet res = DatabaseOperations.executeQuery("select count(*) AS rowcount from `db-dev-dopayroll`.employees where company_id='40eb6956-9eb8-47cc-8d56-8ba556d2d11e' and first_contracting_salary ='"+data.getCellDataasstring(i, 4)+"'");
		res.next();	
		int rcount = res.getInt("rowcount");
		setSalarySearchBox(data.getCellDataasstring(i, 4));
	timeOut(5);
	tbody=findByXpath(reader.getPropertyValue("tbody"));

	rows= tbody.findElements(By.xpath("tr"));
	while(true) {
		getElements();
		String classAttribute =DriverInti.getAttributeValue(nextdis, "class");
	if(classAttribute.equalsIgnoreCase("paginate_button next disabled"))
			break;
		else {
			click(next);
		rows.addAll(tbody.findElements(By.xpath("tr")));
		}
	}
	String empty=getText(emptyrow);
	if(empty.equalsIgnoreCase("No matching records found")) 
			softassert.assertEquals(rows.size(), rcount+1);
	else
			softassert.assertEquals(rows.size(),rcount);
	}
	softassert.assertAll();
}
*/



public void validateMultipleSearchtableWithDatabase(String Ddep,String HDate,String Salary) throws Exception {
	String start ="SELECT count(*) AS rowcount \r\n" + 
			"FROM `db-dev-dopayroll`.employees \r\n" + 
			"inner join `db-dev-dopayroll`.departments \r\n" + 
			"on `db-dev-dopayroll`.employees.department_id = `db-dev-dopayroll`.departments.department_id \r\n" + 
			"where \r\n" + 
			"employees.company_id='40eb6956-9eb8-47cc-8d56-8ba556d2d11e' ";
	
	DatabaseOperations.connectDB();
	data = new ExcelUtils("C:\\Users\\RoOoKa\\eclipse-workspace\\DoPayRollTasks\\src\\main\\java\\testData\\test.xlsx", "multisearch");
//	for (int i =1; i <5; i++) {
		clear(depsearch);
		clear(datesearch);
		clear(salarysearch);
		if (!Ddep.isEmpty()|| !(Ddep==null))
			start+=" and departments.name like'"+Ddep+"%' ";
		if(!HDate.isEmpty()|| !(HDate==null))
			start+=" and date(hiring_date) like '%"+HDate+"%' ";
		if(!Salary.isEmpty()|| !(Salary==null))
			start+=" and first_contracting_salary like '"+Salary+"%' ";
		ResultSet res = DatabaseOperations.executeQuery(start);
		res.next();	
		int rcount = res.getInt("rowcount");
		setDepartmentSearchBox(Ddep);
		setDateSearchBox(HDate);
		setSalarySearchBox(Salary);
	timeOut(5);
	tbody=findByXpath(reader.getPropertyValue("tbody"));

	rows= tbody.findElements(By.xpath("tr"));
	while(true) {
		getElements();
		String classAttribute =DriverInti.getAttributeValue(nextdis, "class");
	if(classAttribute.equalsIgnoreCase("paginate_button next disabled"))
			break;
		else {
			click(next);
		rows.addAll(tbody.findElements(By.xpath("tr")));
		}
	}
	String empty=getText(emptyrow);
	if(empty.equalsIgnoreCase("No matching records found")) 
			softassert.assertEquals(rows.size(), (rcount+1));
	else
			softassert.assertEquals(rows.size(),rcount);
//	}
	softassert.assertAll();
}




}


/*		if (!Ddep.isEmpty())
res = DatabaseOperations.executeQuery(start+"first_contracting_salary ='"+data.getCellDataasstring(i, 2)+"' and date(hiring_date) like '%"+data.getCellDataasstring(i, 1)+"%'");
else if(HDate.isEmpty()) {
res = DatabaseOperations.executeQuery("select count(*) AS rowcount  FROM `db-dev-dopayroll`.employees inner join `db-dev-dopayroll`.departments \r\n" + 
		"on `db-dev-dopayroll`.employees.department_id = `db-dev-dopayroll`.departments.department_id \r\n" + 
		"where \r\n" + 
		"employees.company_id='40eb6956-9eb8-47cc-8d56-8ba556d2d11e' and departments.name like '"+data.getCellDataasstring(i, 0)+"%' and first_contracting_salary ='"+data.getCellDataasstring(i, 2)+"'");
}
else if (Salary.isEmpty())
{
res = DatabaseOperations.executeQuery("select count(*) AS rowcount  FROM `db-dev-dopayroll`.employees inner join `db-dev-dopayroll`.departments \r\n" + 
		"on `db-dev-dopayroll`.employees.department_id = `db-dev-dopayroll`.departments.department_id \r\n" + 
		"where \r\n" + 
		"employees.company_id='40eb6956-9eb8-47cc-8d56-8ba556d2d11e' and departments.name like '"+data.getCellDataasstring(i, 0)+"%' and date(hiring_date) like '%"+data.getCellDataasstring(i, 2)+"%'");
}
else
{
res = DatabaseOperations.executeQuery("select count(*) AS rowcount  FROM `db-dev-dopayroll`.employees inner join `db-dev-dopayroll`.departments \r\n" + 
		"on `db-dev-dopayroll`.employees.department_id = `db-dev-dopayroll`.departments.department_id \r\n" + 
		"where \r\n" + 
		"employees.company_id='40eb6956-9eb8-47cc-8d56-8ba556d2d11e' and departments.name like '"+data.getCellDataasstring(i, 0)+"%' and date(hiring_date) like '%"+data.getCellDataasstring(i, 2)+"%' and  first_contracting_salary ='"+data.getCellDataasstring(i, 2)+"'");
}
*/


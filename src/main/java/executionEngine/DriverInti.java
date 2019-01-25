package executionEngine;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;

import constants.Constants;

public class DriverInti {
	public static WebDriver driver ;

	public DriverInti() {}
	public DriverInti(String browser) {
		if(browser.equalsIgnoreCase("firefox")) {
			System.setProperty(Constants.ffPropertyName, Constants.ffDriverPath);
			 FirefoxProfile profile = new FirefoxProfile();
			  profile.setPreference("browser.download.dir", "C:\\Users\\RoOoKa\\Downloads");

				 // Download files to the downloads folder
				 profile.setPreference("browser.download.folderList", 2);

				 // Don't show downloads window when download starts
				 profile.setPreference("browser.download.manager.showWhenStarting", false);
				 profile.setPreference( "pdfjs.disabled", true );
			//	 profile.setPreference( "exceljs.disabled", true );
				 // Prevent file download dialog to be shown for certain MIME-types
				 profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,text/csv,application/pdf, "
				 		+ "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				FirefoxOptions options = new FirefoxOptions();
				options.setProfile(profile);
				driver = new FirefoxDriver(options);
				timeOut(30);
		//	driver=new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("chrome")) {
				System.setProperty(Constants.chromePropertyName, Constants.ChromeDriverPath);
				driver=new FirefoxDriver();
		}
	}
	public static String getAttributeValue(WebElement elem, String attrName) {
		return elem.getAttribute(attrName);
		}
	
	public void navigate(String url) {
		driver.navigate().to(url);
	}
	public static void click(WebElement elm) {
		elm.click();
		
	}
	public static void submit(WebElement elm) {
		elm.submit();
		
	}
	public void clear(WebElement elm) {
		elm.clear();
	}
	public String getText(WebElement elm) {
		return elm.getText();
	}
	public void setText(WebElement elm, String value) {
		elm.sendKeys(value);

	}
	public static String getCurrentURL() {
		String url=driver.getCurrentUrl();
		return url;
		
	}

	
	public static void timeOut(int sec) {
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);        
	}
	public WebElement findByXpath(String xpath) {
	return driver.findElement(By.xpath(xpath));	
		
	}
	public static void selectValue(WebElement elem, String value) {
		Select selem = new Select(elem);
		selem.selectByVisibleText(value);
		
	}
	public static void closeDriver() {
		driver.close();
	}

}

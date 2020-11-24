package selenium;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


public class DriverExtrator {
	
	private WebDriver driver;
	
	public DriverExtrator(boolean headless, boolean disableImages, boolean ehFireFox) throws IOException {
		List<String> args = new ArrayList<>();
		
		if(headless) 
			args.add("--headless");
		else 
			args.add("--disable-notifications");
		
		if(disableImages) {
			args.add("--disable-gpu");
			args.add("--blink-settings=imagesEnabled=false");
		}
//		args.add("--no-proxy-server");
		
		if(ehFireFox) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments(args);
			driver = new FirefoxDriver(options);
		} else {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments(args);
			driver = new ChromeDriver(options);
		}
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
		driver.manage().window().maximize();
	}
	
	public void waitForLoad() throws InterruptedException {
	    new WebDriverWait(driver, 60).until((ExpectedCondition<Boolean>) wd ->
            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}
	
	public void hoverMouseJavaScript(WebElement webElement) {
		String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";

		((JavascriptExecutor)driver).executeScript(javaScript, webElement);
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public void sendKeysSlowly(WebElement element, String mensagem) throws InterruptedException {
		for(int i = 0; i < mensagem.toCharArray().length; i++) {
			element.sendKeys(mensagem.charAt(i) + "");
			TimeUnit.MILLISECONDS.sleep(15);
		}
	}

	public boolean existeElemento(String path) {
		return driver.findElements(By.xpath(path)).size() > 0;
	}
}

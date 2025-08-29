package br.com.core;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
	
	private static WebDriver driver;
	
	
	public static WebDriver getDriver(){
		if(driver == null) {

			System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
//			driver = new FirefoxDriver();
			driver = new ChromeDriver();
//			driver = new EdgeDriver();
			//driver.manage().window().setSize(new Dimension(1200, 765));	
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			System.setProperty("webdriver.edge.logfile", "caminho/para/arquivo_de_log.log");

		}
		return driver;
	}

	public static void killDriver(){
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
}

package tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverSetup {

	public WebDriver driver;
	String path = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\Selenium WebTables\\geckodriver.exe";

	public static FirefoxOptions options() {

		FirefoxOptions option = new FirefoxOptions();
		option.addArguments("--headless");
		return option;
	}

	public void setupDriver() {

		FirefoxOptions option = options();
		System.setProperty("webdriver.gecko.driver", path);
		driver = new FirefoxDriver(option);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	public void quitDriver() {

		driver.quit();
	}

}

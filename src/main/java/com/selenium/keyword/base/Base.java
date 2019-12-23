package com.selenium.keyword.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	public WebDriver driver;
	public Properties prop;

	public WebDriver init_driver(String browserName) {
		if (browserName.equals("chrome"))
			WebDriverManager.chromedriver().setup();
		
		//To block notification pop-ups
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		
		WebDriver driver = new ChromeDriver(options);
		return driver;
	}

	public Properties init_properties() throws IOException {
		prop = new Properties();
		FileInputStream ip = new FileInputStream(
				"/home/admin1/Desktop/Spring_Programs/Keyword/src/main/java/com/selenium/keyword/config/config.properties");
		prop.load(ip);

		return prop;
	}
}

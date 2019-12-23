package com.bridgelabz.keyword.Keyword;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.keyword.base.Base;

public class KeywordEngine {
	public WebDriver driver;
	public Properties prop;

	public WebElement element;
	public static Workbook book;
	public static Sheet sheet;

	public final String EXCEL_SHEET_PATH = "/home/admin1/Desktop/fbLogin.xlsx";

	public Base base;

	public void startExecution(String sheetName) throws IOException {
		FileInputStream file = null;

		String locatorName = null;
		String locatorValue = null;

		try {

			file = new FileInputStream(EXCEL_SHEET_PATH);
			book = WorkbookFactory.create(file);

		} catch (Exception e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);

		// To track column
		int k = 0;
		// Iterate till last row
		for (int i = 0; i < sheet.getLastRowNum(); i++) {

			try {
				String locatorColValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
				if (!locatorColValue.equalsIgnoreCase("NA")) {
					// Splitting id=email
					locatorName = locatorColValue.split("=")[0].trim(); // id
					locatorValue = locatorColValue.split("=")[1].trim(); // email
				}
				String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim(); // last but second column
				String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim(); // last column

				System.out.println("Before conversion Recieved value::::" + value);

				// To read value as string from excel
				if (sheet.getRow(i + 1).getCell(k + 3).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					value = NumberToTextConverter.toText(sheet.getRow(i + 1).getCell(k + 3).getNumericCellValue());
				}

				System.out.println("After conversion Recieved value::::" + value);
				// Switch statement for browser specific actions open browser,enter url,close
				// browser
				switch (action) {
				case "open browser":
					base = new Base();
					prop = base.init_properties();

					// If cell of browser in excel sheet is kept empty or written NA use property
					// file
					if (value.isEmpty() || value.equals("NA")) {
						driver = base.init_driver(prop.getProperty("browser"));
					} else {
						driver = base.init_driver(value);

					}
					break;

				case "enter url":

					if (value.isEmpty() || value.equals("NA")) {
						driver.get(prop.getProperty("url"));
					} else {
						driver.get(value);
					}

					break;

				case "quit":
					driver.quit();
					break;

				case "wait":
					Thread.sleep(Long.valueOf(value));
					break;
				default:
					break;
				}

				// Switch case for locators like "id", "name", "xpath" etc
				switch (locatorName) {
				case "id":
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("sendKeys")) {
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click"))
						element.click();
					// To use same locatorName for other cases
					locatorName = null;
					break;

				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

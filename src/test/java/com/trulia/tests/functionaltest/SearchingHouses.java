package com.trulia.tests.functionaltest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.trulia.pages.ResultPage;
import com.trulia.pages.TruliaPage;
import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.ConfigurationReader;
import com.trulia.utilities.Driver;
import com.trulia.utilities.TestBase;

// Sanjar did Ian's TestCases:

public class SearchingHouses extends TestBase {
	WebDriver driver = Driver.getDriver();
	TruliaPage trulia = new TruliaPage();
	ResultPage resultPage = new ResultPage();
	String city = "Gaithersburg";
	String expected = "Montgomery Village";

	@BeforeMethod
	public void navigate() {
		driver.get(ConfigurationReader.getProperty("url"));
	}

	@Test(priority = 0, description="TC005")
	public void searchUsaOnly() {
		assertTrue(trulia.isAt());
		assertTrue(driver.getTitle().contains("Trulia"));
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());

		trulia.searchField.clear();
		trulia.searchField.sendKeys(city);
		trulia.searchButton.click();
		BrowserUtils.waitFor(2);
		assertTrue(driver.getTitle().contains(city));

		System.out.println(resultPage.verifyAutoSuggestions(city));
	}

	@Test(priority = 1, description="TC006")
	public void selectMontgomery() {
		assertTrue(trulia.isAt());
		assertTrue(driver.getTitle().contains("Trulia"));
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());

		trulia.searchField.clear();
		trulia.searchField.sendKeys("Mon");
		BrowserUtils.waitFor(2);

		//NEED ATTENTION, this may not work always
		driver.findElement(By.id("react-autowhatever-1--item-0")).click(); 
		BrowserUtils.waitFor(2);
		System.out.println(resultPage.verifyAutoSuggestions(expected));

	}

	@Test(priority = 2, description="TC007")
	public void searchOutsideOfUsa() {

		assertTrue(trulia.isAt());
		assertTrue(driver.getTitle().contains("Trulia"));
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());

		trulia.searchField.clear();
		trulia.searchField.sendKeys("Khiva, Uzbekistan");
		BrowserUtils.waitFor(2);
		trulia.searchButton.click();
		BrowserUtils.waitFor(2);

		assertFalse(driver.getTitle().contains("LIMA, PERU"));
		assertEquals(resultPage.zeroResults.getText(), "0 Results.");
		assertEquals(resultPage.notMatchText.getText(), "Your search does not match any homes.");

	}

	@Test(priority = 3, description="TC008")
	public void checkPrice() {
		assertTrue(trulia.isAt());
		assertTrue(driver.getTitle().contains("Trulia"));
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());

		trulia.searchField.clear();
		BrowserUtils.waitFor(2);
		trulia.searchField.sendKeys(city);
		trulia.searchButton.click();
		BrowserUtils.waitFor(2);
		assertTrue(driver.getTitle().contains(city));

		assertTrue(resultPage.priceToggle.isDisplayed());
		resultPage.priceToggle.click();

		Select dropdown = new Select(driver.findElement(By.id("minPrice")));
		dropdown.selectByVisibleText("$50k");
		Select dropdown2 = new Select(driver.findElement(By.id("maxPrice")));
		dropdown2.selectByVisibleText("$100k");
		BrowserUtils.waitFor(2);

		List<WebElement> qq = driver.findElements(By.xpath
						("//span[@class='cardPrice h5 man pan typeEmphasize noWrap typeTruncate']"));
		for (WebElement e : qq)
			System.out.println(e.getText());
		BrowserUtils.waitFor(2);
		trulia.searchField.clear();

		// missing last step here
	}

	@Test(priority = 4, description = "TC009")
	public void lastVerification() {
		assertTrue(trulia.isAt());
		assertTrue(driver.getTitle().contains("Trulia"));
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());

		trulia.searchField.clear();
		trulia.searchField.sendKeys(city);
		trulia.searchButton.click();
		BrowserUtils.waitFor(2);
		assertTrue(driver.getTitle().contains(city));

	}

}

package com.trulia.tests.functionaltest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.trulia.pages.PASearchPage;
import com.trulia.pages.ResultPage;
import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.ConfigurationReader;
import com.trulia.utilities.TestBase;

public class SearchNegativeTest extends TestBase {
	PASearchPage search = new PASearchPage();
	ResultPage resultPage = new ResultPage();
	String city = "Philadelphia, PA";
	String homePageTitle = "Trulia: Real Estate Listings, Homes For Sale, Housing Data";

	String zipCode = "19116";

	@BeforeMethod
	public void navigate() {

		driver.get(ConfigurationReader.getProperty("url"));
	}

	@Test(priority = 0)
	public void searchByCity() {
		assertTrue(search.isAt());
		assertEquals(driver.getTitle(), homePageTitle);
		assertTrue(search.buttonBuy.isDisplayed());
		assertTrue(search.searchField.isDisplayed());
		assertTrue(search.searchField.isDisplayed());

		search.searchField.clear();
		assertTrue(search.searchField.getAttribute("value").equals(""));
		search.searchField.sendKeys("Urgench" + Keys.ENTER);
		BrowserUtils.waitForVisibility(resultPage.zeroResults, 5);
		assertEquals(resultPage.notMatchText.getText(), "Your search does not match any homes.");
		assertEquals(resultPage.found(), 0);

	}

	@Test(priority = 1)
	 public void searchByZipCode() {
		
     assertTrue(search.isAt());
	 assertEquals(driver.getTitle(), homePageTitle);
	 assertTrue(search.buttonBuy.isDisplayed());
	 assertTrue(search.searchField.isDisplayed());
	 assertTrue(search.searchField.isDisplayed());

	 search.searchField.clear();
	 assertTrue(search.searchField.getAttribute("value").equals(""));
	 search.searchField.sendKeys("000");
	 search.searchButton.click();
	 BrowserUtils.waitForVisibility(resultPage.zeroResults, 5);
	 assertEquals(resultPage.notMatchText.getText(), "Your search does not match any homes.");
	 assertEquals(resultPage.found(), 0);
	

	
	 }

}

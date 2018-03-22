package com.trulia.tests.functionaltest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.trulia.pages.ResultPage;
import com.trulia.pages.PASearchPage;
import com.trulia.pages.TruliaPage;
import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.ConfigurationReader;
import com.trulia.utilities.Driver;
import com.trulia.utilities.TestBase;

public class SearchPositiveTest extends TestBase{
	PASearchPage search = new PASearchPage();
	ResultPage resultPage = new ResultPage();
	String city = "Philadelphia, PA";	
	String zipCode = "19116";
	 
	@BeforeMethod
	public void navigate() {
		driver.get(ConfigurationReader.getProperty("url"));
	}
	
	@Test(priority = 0)
	public void searchByCity() throws InterruptedException  {
		assertTrue(search.isAt());
		assertTrue(search.verifyTitle("trulia"));
		assertTrue(search.verifyTitle("Trulia: Real Estate Listings, Homes For Sale, Housing Data"));
		assertTrue(search.searchField.isDisplayed());
		assertTrue(search.searchButton.isDisplayed());
		assertTrue(search.isButtonClickable());

		BrowserUtils.waitFor(5);
		assertTrue(search.verifyTheTitleSearchField());
		assertTrue(search.verifyListedResults());
		assertTrue(search.verifyAutoSuggestions(city));
		
	}
	
	@Test(priority = 1)
	public void searchByZipCode() {
		assertTrue(search.isAt());
		assertTrue(search.buttonBuy.isDisplayed());
		assertEquals(search.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(search.searchField.isDisplayed());
		assertTrue(search.searchButton.isDisplayed());
		
		search.searchField.clear();
		assertTrue(search.searchField.getAttribute("value").equals(""));
		search.searchField.sendKeys(zipCode);
		assertTrue(search.searchList.isDisplayed());
		BrowserUtils.waitForTextVisibility(search.searchExpected, 5, zipCode);
		search.selectCityOption(zipCode);
		
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(search.getDriver().getTitle(), "19116 Real Estate & Homes For Sale | Trulia");
		assertTrue(search.verifyAutoSuggestions("Philadelphia")); 	// ZIP code 19116 is located in Philadelphia, PA.
		
		
	}
	
	
}

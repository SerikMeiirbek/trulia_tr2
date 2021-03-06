package com.trulia.tests.functionaltest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.trulia.pages.ResultPage;
import com.trulia.pages.TruliaPage;
import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.ConfigurationReader;
import com.trulia.utilities.Driver;
import com.trulia.utilities.TestBase;


public class TruliaTest extends TestBase {
	TruliaPage trulia = new TruliaPage();
	ResultPage resultPage = new ResultPage();
	String city = "Boston, MA";	
	String zipCode = "02601";
	String city2 = "Washington, DC";
	String neighborhood = "Park Place Norfolk";

	@BeforeMethod
	public void navigate() {
		driver.get(ConfigurationReader.getProperty("url"));
	}
	
	@Test(priority = 0, description="TC001")
	public void searchBCity() {
		assertTrue(trulia.isAt());
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());	
		
		trulia.searchField.clear();
		assertTrue(trulia.searchField.getAttribute("value").equals(""));
		trulia.searchField.sendKeys(city);
		BrowserUtils.waitForVisibility(trulia.searchList, 5);
		BrowserUtils.waitForTextVisibility(trulia.searchExpected, 5, city);
		trulia.selectCityOption(city);
		
		// Verify all houses are located in Boston, MA
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "Boston, MA Real Estate & Homes For Sale | Trulia");
		assertTrue(resultPage.verifyAutoSuggestions(city));
		
		resultPage.homeType.click();
		resultPage.Condo.click();
		
		assertEquals(resultPage.homeType.getText(), "Condo");
	}
	
	@Test(priority = 1, description="TC002")
	public void searchByZipCode() {
		assertTrue(trulia.isAt());
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());
		
		trulia.searchField.clear();
		assertTrue(trulia.searchField.getAttribute("value").equals(""));
		trulia.searchField.sendKeys(zipCode);
		assertTrue(trulia.searchList.isDisplayed());
		BrowserUtils.waitForTextVisibility(trulia.searchExpected, 2, zipCode);
		trulia.selectCityOption(zipCode);
		
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "02601 Real Estate & Homes For Sale | Trulia");
		assertTrue(resultPage.verifyAutoSuggestions("Hyannis")); 	// ZIP code 02601 is located in Hyannis, Massachusetts.
		
		assertTrue(resultPage.homeType.isDisplayed());
		resultPage.homeType.click();
		resultPage.land.click();
		assertEquals(resultPage.homeType.getText(), "Land");
		
	}
	
	@Test(priority = 2, description="TC003")
	public void filterByNumberOfBeds() {
		assertTrue(trulia.isAt());
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());	
		
		trulia.searchField.clear();
		assertTrue(trulia.searchField.getAttribute("value").equals(""));
		trulia.searchField.sendKeys("Washington DC");
		BrowserUtils.waitForVisibility(trulia.searchList, 5);
		BrowserUtils.waitForTextVisibility(trulia.searchExpected, 2, city2);
		trulia.selectCityOption(city2);
		
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "Washington, DC Real Estate & Homes For Sale | Trulia");
		assertTrue(resultPage.verifyAutoSuggestions(city2));
		
		assertTrue(resultPage.homeType.isDisplayed());
		assertTrue(resultPage.allBeds.isDisplayed());
		resultPage.allBeds.click();
		assertEquals(resultPage.bedOptions.size(), 5);
		resultPage.moreThan2Beds.click();
		BrowserUtils.waitFor(2);
		assertTrue(resultPage.countBeds(2));
		
	}
	
	@Test(priority = 3, description="TC004")
	public void searchByNeighborhood() {
		assertTrue(trulia.isAt());
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());	
		
		trulia.searchField.clear();
		assertTrue(trulia.searchField.getAttribute("value").equals(""));
		trulia.searchField.sendKeys(neighborhood);
		BrowserUtils.waitForVisibility(trulia.searchList, 2);
		String expected = "Park Place, Norfolk, VA";
		BrowserUtils.waitForTextVisibility(trulia.searchExpected, 2, expected);
		trulia.selectCityOption(expected);
		
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "Park Place, Norfolk, VA Real Estate & Homes For Sale | Trulia");
		assertTrue(resultPage.verifyAutoSuggestions(expected));
		
		assertTrue(resultPage.homeType.isDisplayed());
		assertTrue(resultPage.allBeds.isDisplayed());
		resultPage.allBeds.click();
		assertEquals(resultPage.bedOptions.size(), 5);
		resultPage.moreThan4Beds.click();
		BrowserUtils.waitFor(2);
		assertTrue(resultPage.countBeds(4));
	}
	
}

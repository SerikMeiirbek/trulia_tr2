package com.trulia.tests.functionaltest;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	
	@Test(priority = 0)
	public void searchByCity() {
		assertTrue(trulia.isAt());
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());	
		
		trulia.searchField.clear();
		assertTrue(trulia.waitForEmptyField(trulia.searchField, 2));
		trulia.searchField.sendKeys(city);
		BrowserUtils.waitForVisibility(trulia.searchList, 5);
		trulia.selectCityOption(city);
		
		// Verify all houses are located in Boston, MA
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "Boston, MA Real Estate & Homes For Sale | Trulia");
		assertTrue(trulia.verifyAutoSuggestions(city));
		
		resultPage.homeType.click();
		resultPage.Condo.click();
		
		assertEquals(resultPage.homeType.getText(), "Condo");
	}
	
	@Test(priority = 1)
	public void searchByZipCode() {
		assertTrue(trulia.isAt());
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());
		
		trulia.searchField.clear();
		assertTrue(trulia.waitForEmptyField(trulia.searchField, 2));
		trulia.searchField.sendKeys(zipCode);
		assertTrue(trulia.searchList.isDisplayed());
		trulia.selectCityOption(zipCode);
		
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "02601 Real Estate & Homes For Sale | Trulia");
		assertTrue(trulia.verifyAutoSuggestions("Hyannis")); 	// ZIP code 02601 is located in Hyannis, Massachusetts.
		
		assertTrue(resultPage.homeType.isDisplayed());
		resultPage.homeType.click();
		resultPage.land.click();
		assertEquals(resultPage.homeType.getText(), "Land");
		
	}
	
	@Test(priority = 2)
	public void filterByNumberOfBeds() {
		assertTrue(trulia.isAt());
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());	
		
		trulia.searchField.clear();
		assertTrue(trulia.waitForEmptyField(trulia.searchField, 2));
		trulia.searchField.sendKeys("Washington DC");
		BrowserUtils.waitForVisibility(trulia.searchList, 5);
		trulia.selectCityOption(city2);
		
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "Washington, DC Real Estate & Homes For Sale | Trulia");
		assertTrue(trulia.verifyAutoSuggestions(city2));
		
		assertTrue(resultPage.homeType.isDisplayed());
		assertTrue(resultPage.allBeds.isDisplayed());
		resultPage.allBeds.click();
		assertEquals(resultPage.bedOptions.size(), 5);
		resultPage.moreThan2Beds.click();
//		BrowserUtils.waitForVisibility(resultPage.bedsResults, 5);
		BrowserUtils.waitFor(2);
		assertTrue(resultPage.countBeds(2));
		
	}
	
	@Test(priority = 3)
	public void searchByNeighborhood() {
		assertTrue(trulia.isAt());
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());	
		
		trulia.searchField.clear();
		assertTrue(trulia.waitForEmptyField(trulia.searchField, 2));
		trulia.searchField.sendKeys(neighborhood);
		BrowserUtils.waitForVisibility(trulia.searchList, 2);
		String expected = "Park Place, Norfolk, VA";
		trulia.selectCityOption(expected);
		
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "Park Place, Norfolk, VA Real Estate & Homes For Sale | Trulia");
		assertTrue(trulia.verifyAutoSuggestions(expected));
		
		assertTrue(resultPage.homeType.isDisplayed());
		assertTrue(resultPage.allBeds.isDisplayed());
		resultPage.allBeds.click();
		assertEquals(resultPage.bedOptions.size(), 5);
		resultPage.moreThan4Beds.click();
//		BrowserUtils.waitForVisibility(resultPage.bedsResults, 5);
		BrowserUtils.waitFor(2);
		assertTrue(resultPage.countBeds(4));
	}
	
}

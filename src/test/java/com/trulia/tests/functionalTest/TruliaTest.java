package com.trulia.tests.functionalTest;

import static org.testng.Assert.*;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.trulia.pages.ResultPage;
import com.trulia.pages.TruliaPage;
import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.TestBase;


public class TruliaTest extends TestBase {
	TruliaPage trulia = new TruliaPage();
	ResultPage resultPage = new ResultPage();
	String city = "Boston, MA";	
	String zipCode = "02601";
	String city2 = "Washington, DC";
	String neighborhood = "Park Place";
	
	@Test(priority = 0)
	public void searchByCity() {
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());	
		
		trulia.searchField.clear();
		assertTrue(trulia.waitForEmptyField(trulia.searchField, 2));
		trulia.searchField.sendKeys(city);
		trulia.waitForVisibility(trulia.options, 2);
		assertTrue(trulia.selectCityOption(city));
		trulia.selectedOption.click();
		
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
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());
		
		trulia.searchField.clear();
		assertTrue(trulia.waitForEmptyField(trulia.searchField, 2));
		trulia.searchField.sendKeys(zipCode);
		assertTrue(trulia.selectCityOption(zipCode));
		trulia.selectedOption.click();
		
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
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());	
		
		trulia.searchField.clear();
		assertTrue(trulia.waitForEmptyField(trulia.searchField, 2));
		trulia.searchField.sendKeys(city2);
		trulia.waitForVisibility(trulia.options, 2);
		assertTrue(trulia.selectCityOption(city2));
		trulia.selectedOption.click();
		
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "Washington, DC Real Estate & Homes For Sale | Trulia");
		assertTrue(trulia.verifyAutoSuggestions(city2));
		
		assertTrue(resultPage.homeType.isDisplayed());
		assertTrue(resultPage.allBeds.isDisplayed());
		resultPage.allBeds.click();
		assertEquals(resultPage.bedOptions.size(), 5);
		resultPage.moreThan2Beds.click();
		resultPage.waitForVisibility(resultPage.bedsResults, 5);
		assertTrue(resultPage.countBeds(2));
		
	}
	
	@Test(priority = 3)
	public void searchByNeighborhood() {
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertEquals(trulia.getDriver().getTitle(), "Trulia: Real Estate Listings, Homes For Sale, Housing Data");
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());	
		
		trulia.searchField.clear();
		assertTrue(trulia.waitForEmptyField(trulia.searchField, 2));
		trulia.searchField.sendKeys(neighborhood);
		trulia.waitForVisibility(trulia.options, 2);
		String expected = "Park Place, Norfolk, VA";
		assertTrue(trulia.selectCityOption(expected));
		trulia.selectedOption.click();
		
		resultPage.waitForVisibility(resultPage.homeType, 2);
		assertTrue(resultPage.isAt());
		assertEquals(trulia.getDriver().getTitle(), "Park Place, Norfolk, VA Real Estate & Homes For Sale | Trulia");
		assertTrue(trulia.verifyAutoSuggestions(expected));
		
		assertTrue(resultPage.homeType.isDisplayed());
		assertTrue(resultPage.allBeds.isDisplayed());
		resultPage.allBeds.click();
		assertEquals(resultPage.bedOptions.size(), 5);
		resultPage.moreThan4Beds.click();
		resultPage.waitForVisibility(resultPage.bedsResults, 5);
		assertTrue(resultPage.countBeds(4));
	}
	
}

package com.trulia.tests;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import com.trulia.pages.ResultPage;
import com.trulia.pages.TruliaPage;
import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.TestBase;


public class TruliaTest extends TestBase {
	TruliaPage trulia = new TruliaPage();
	ResultPage resultPage = new ResultPage();
	String city = "Boston, MA";	
	
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
}

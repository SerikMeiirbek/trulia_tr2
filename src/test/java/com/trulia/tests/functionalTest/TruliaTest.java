package com.trulia.tests.functionalTest;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import com.trulia.pages.TruliaPage;
import com.trulia.utilities.TestBase;


public class TruliaTest extends TestBase {
	TruliaPage trulia = new TruliaPage();
	@Test(priority = 0)
	public void searchByCity() {
		assertTrue(trulia.buttonBuy.isDisplayed());
		assertTrue(trulia.verifyTitle("Trulia: Real Estate Listings, Homes For Sale, Housing Data"));
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());
	//	assertTrue(trulia.verifyAutoSuggestions());
		trulia.searchField.clear();
		trulia.searchField.sendKeys("Boston");
		trulia.selectCityOption("Boston, MA");
	}
}

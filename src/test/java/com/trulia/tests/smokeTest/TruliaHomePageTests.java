package com.trulia.tests.smokeTest;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import com.trulia.pages.TruliaPage;
import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.TestBase;


public class TruliaHomePageTests extends TestBase{
	TruliaPage trulia =new TruliaPage();
	@Test(priority = 0)
	public void searchTest() throws InterruptedException  {
		
		assertTrue(trulia.isAt());
		assertTrue(trulia.verifyTitle());
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());
		assertTrue(trulia.isButtonClickable());
	
		BrowserUtils.waitFor(2);
		assertTrue(trulia.verifyTheTitleSearchField());

		assertTrue(trulia.verifyAutoSuggestions());
		
	}
	
	//@Test(priority = 1)
	public void AsUser() {
		trulia.getHomePage();
		BrowserUtils.waitFor(2);
		assertTrue(trulia.isAt());
		assertTrue(trulia.searchField.isDisplayed());
		assertTrue(trulia.searchButton.isDisplayed());
		assertTrue(trulia.isButtonClickable2());
		
		BrowserUtils.waitFor(2);
		assertTrue(trulia.verifyTheTitleSearchField2());

		assertTrue(trulia.verifySearchResults2());
		
		
	}
	
}

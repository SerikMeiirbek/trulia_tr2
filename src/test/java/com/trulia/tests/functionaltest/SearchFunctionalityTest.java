package com.trulia.tests.functionaltest;

import static org.testng.Assert.*;

import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.trulia.pages.ResultPage;
import com.trulia.pages.TruliaPage;
import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.ConfigurationReader;
import com.trulia.utilities.Driver;
import com.trulia.utilities.TestBase;

public class SearchFunctionalityTest extends TestBase {
	WebDriver driver = Driver.getDriver();
	TruliaPage truliaPage = new TruliaPage();
	ResultPage resultPage = new ResultPage();
	String homePageTitle = "Trulia: Real Estate Listings, Homes For Sale, Housing Data";
	String address1 = "11619 Vantage Hill Rd";
	String address2 = "Baltimore Highlands";

	@BeforeMethod
	public void navigate() {
		driver.get(ConfigurationReader.getProperty("url"));
	}

	// @Test(description = "TC0018")
	public void searchByAddressPositive() {
		// Step1
		assertTrue(truliaPage.isAt());
		assertEquals(driver.getTitle(), homePageTitle);
		assertTrue(truliaPage.buttonBuy.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());

		// Step2
		truliaPage.searchField.clear();
		assertTrue(truliaPage.searchField.getAttribute("value").equals(""));
		truliaPage.searchField.sendKeys(address1);

		// Step3
		BrowserUtils.waitForVisibility(truliaPage.searchList, 5);
		String expected = "11619 Vantage Hill Rd #22B Reston, VA 20190";
		BrowserUtils.waitForTextVisibility(truliaPage.searchExpected, 5, expected);
		truliaPage.selectCityOption(expected);

		// Step4
		BrowserUtils.waitForVisibility(resultPage.houseAddressText, 5);
		assertEquals(driver.getTitle(), "11619 Vantage Hill Rd #22B For Sale - Reston, VA | Trulia");
		assertTrue(resultPage.housrAddress.getText().contains(address1));
		assertTrue(resultPage.houseDetialsText.getText().contains("2 beds"));
	}

	// @Test(description = "TC0019")
	public void searchByAddressNegative() {
		// Step1
		assertTrue(truliaPage.isAt());
		assertEquals(driver.getTitle(), homePageTitle);
		assertTrue(truliaPage.buttonBuy.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());

		// Step2, Step3
		truliaPage.searchField.clear();
		assertTrue(truliaPage.searchField.getAttribute("value").equals(""));
		truliaPage.searchField.sendKeys("100 Hello World" + Keys.ENTER);
		BrowserUtils.waitForVisibility(resultPage.zeroResults, 5);
		assertEquals(resultPage.notMatchText.getText(), "Your search does not match any homes.");
		assertEquals(resultPage.found(), 0);
	}

	// @Test(description = "TC0020")
	public void searchByNeighborhoodPositive() {
		// Step1
		assertTrue(truliaPage.isAt());
		assertEquals(driver.getTitle(), homePageTitle);
		assertTrue(truliaPage.buttonBuy.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());

		// Step2
		truliaPage.searchField.clear();
		assertTrue(truliaPage.searchField.getAttribute("value").equals(""));
		truliaPage.searchField.sendKeys(address2);

		// Step3
		BrowserUtils.waitForVisibility(truliaPage.searchList, 5);
		String expected = "Baltimore Highlands, Baltimore, MD";
		BrowserUtils.waitForTextVisibility(truliaPage.searchExpected, 5, expected);
		assertEquals(truliaPage.propertyType.getText(), "Neighborhood");
		truliaPage.selectCityOption(expected);

		// Step4
		assertTrue(resultPage.isAt());
		assertEquals(driver.getTitle(), "Baltimore Highlands, Baltimore, MD Real Estate & Homes For Sale | Trulia");
		assertTrue(resultPage.verifyAutoSuggestions(expected));
	}

	// @Test(description = "TC0021")
	public void searchByNeighborhoodsNegative() {
		// Step1
		assertTrue(truliaPage.isAt());
		assertEquals(driver.getTitle(), homePageTitle);
		assertTrue(truliaPage.buttonBuy.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());

		// Step2, Step3
		truliaPage.searchField.clear();
		assertTrue(truliaPage.searchField.getAttribute("value").equals(""));
		truliaPage.searchField.sendKeys("Bye World");
		truliaPage.searchButton.click();
		BrowserUtils.waitForVisibility(resultPage.zeroResults, 5);
		assertEquals(resultPage.notMatchText.getText(), "Your search does not match any homes.");
		assertEquals(resultPage.found(), 0);
	}

	 @Test(description = "TC0014")
	public void searchByCityAndHauseByKeywords() {
		// Step1
		assertTrue(truliaPage.isAt());
		assertEquals(driver.getTitle(), homePageTitle);
		assertTrue(truliaPage.buttonBuy.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());

		// Step2, Step3
		truliaPage.searchField.clear();
		BrowserUtils.waitFor(2);
		assertTrue(truliaPage.searchField.getAttribute("value").equals(""));
		truliaPage.searchField.sendKeys("Fairfax");
		System.out.println(truliaPage.listedResuls());
		truliaPage.searchButton.click();
		BrowserUtils.waitFor(3);

		// step4
		assertTrue(Driver.getDriver().getTitle().contains("Fairfax, VA Real Estate & Homes For Sale | Trulia"));
		assertTrue(resultPage.verifySearchedResultAdresses("Fairfax"));

		// step5
		resultPage.moreToggle.click();
		resultPage.keywordInput.sendKeys("Pool , Parking" + Keys.ENTER);

	}

	// @Test(description = "TC015")
	public void searchByCityAndHauseByKeywordsNegative() {
		// Step1
		assertTrue(truliaPage.isAt());
		assertEquals(driver.getTitle(), homePageTitle);
		assertTrue(truliaPage.buttonBuy.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());

		// Step2, Step3
		truliaPage.searchField.clear();
		BrowserUtils.waitFor(2);
		assertTrue(truliaPage.searchField.getAttribute("value").equals(""));
		truliaPage.searchField.sendKeys("Fairfax");
		truliaPage.searchButton.click();
		BrowserUtils.waitFor(3);

		// step5
		resultPage.moreToggle.click();
		resultPage.keywordInput.sendKeys("0000 , _____" + Keys.ENTER);

		// Your search does not match any homes.
		BrowserUtils.waitFor(2);
		assertTrue(resultPage.result0.getText().contains("Your search does not match any homes"));

	}

	//@Test(description = "TC016")
	public void searchFilterByPriceRange() {
		// Step1-Step5
		assertTrue(truliaPage.isAt());
		assertEquals(driver.getTitle(), homePageTitle);
		assertTrue(truliaPage.buttonBuy.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());
		assertTrue(truliaPage.searchField.isDisplayed());

		//Step6
		
		truliaPage.isButtonClickable("Gaithersburg");
		
		//Step7
		assertTrue(truliaPage.gettitle().contains("Gaithersburg"));
		
		//Step8
		assertTrue(resultPage.priceToggle.isDisplayed());
		
		//Step9
		resultPage.priceToggle.click();
		assertTrue(resultPage.dropDownListByDefault(resultPage.minPrice).contains("No Min"));
		assertTrue(resultPage.dropDownListByDefault(resultPage.maxPrice).contains("No Max"));
		
		//Step10
		assertTrue(resultPage.dropDownList(resultPage.minPrice, "$20m"));
		
		//Step11
		assertTrue(resultPage.dropDownList(resultPage.minPrice, "$50k"));
		
		BrowserUtils.waitFor(2);
		
		//Step12
		assertTrue(resultPage.dropDownList(resultPage.maxPrice, "$20m"));
		
		//Step13
		assertTrue(resultPage.dropDownList(resultPage.maxPrice, "$100k"));
		
		//Step14
		
		BrowserUtils.waitFor(4);
		assertTrue(resultPage.checkPriceRange(resultPage.listedPrices, 50000, 100000));
		//BrowserUtils.getElementsText(resultPage.listedPrices);
		
		//System.out.println(resultPage.checkPriceRange1());
	}

}

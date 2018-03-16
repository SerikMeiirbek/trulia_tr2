package com.trulia.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.Driver;

public class TruliaPage {
	private WebDriver driver;
	String str = "Pittsburgh, PA";
	String str2 = "Gaithersburg";
	String str3 = "Boston";
	
	public TruliaPage() {
		this.driver = Driver.getDriver();
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="searchBox")
	public WebElement searchField;

	@FindBy(xpath = "//button[@class='css-ejw4np btn btnPrimary']")
	public WebElement searchButton;

	@FindBy(xpath = "//h1[@class='h3']")
	public WebElement searchedResult;

	@FindBy(xpath = "//button[@class='css-aks6px btn btnLrg btnSecondary baz typeEmphasize pvs btnSelected']")
	public WebElement buttonBuy;
	
	@FindBy(xpath ="//li[@role='option']")
	public List<WebElement> options;

	public boolean isAt() {
		// System.out.println(calculator.getMonthlyBillAmount());
	
		return driver.getTitle().equals("Trulia: Real Estate Listings, Homes For Sale, Housing Data");

	}

	public boolean isButtonClickable() {
		searchField.clear();
		searchField.sendKeys(str);

		try {
			searchButton.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public boolean verifyTitle(String str) {
		return driver.getTitle().toLowerCase().contains(str.toLowerCase());

	}

	public boolean verifyTheTitleSearchField() {
		return driver.getTitle().toLowerCase().contains(str.toLowerCase());

	}

	public boolean verifySearchResults() {
		return searchedResult.getText().contains(str);

	}

	public void getHomePage() {
		driver.findElement(By.xpath("//a[@class='floatLeft navbar__navbarLogo___2-cY']")).click();
	}

	public boolean isButtonClickable2() {
		searchField.clear();
		searchField.sendKeys(str2);

		try {
			searchButton.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean verifyTheTitleSearchField2() {
		return driver.getTitle().toLowerCase().contains(str2.toLowerCase());

	}

	public boolean verifySearchResults2() {
		return searchedResult.getText().contains(str2);

	}

	public boolean verifyListedResults() {
//		searchField.clear();
//		searchField.sendKeys(str3);
		BrowserUtils.waitFor(1);
		List<WebElement> elements = driver.findElements(By.xpath("//div[@class='addressDetail']"));
		for (WebElement webElement : elements) {
			if (webElement.getText().trim().toLowerCase().contains(str.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public boolean checkEachWord(String str, WebElement webElement) {
		String[] arr = str.split(" ");
		
		System.out.println(Arrays.toString(arr));
		
		if (arr.length > 1) {
			for (String string : arr) {
				if (string.toLowerCase().trim().contains(webElement.getText().toLowerCase())) {
					return true;
				}
			}
		} else {
			if (str.toLowerCase().contains(webElement.getText().toLowerCase().trim())) {
				return true;
			}

		}
		return false;
	}
	

	public String selectCityOption(String city) {
	
	for (WebElement webElement : options) {
		System.out.println(webElement.getText());
	}
	
	return null;
}

}

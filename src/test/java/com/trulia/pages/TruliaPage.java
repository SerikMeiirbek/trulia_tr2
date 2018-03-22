package com.trulia.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.trulia.utilities.BrowserUtils;
import com.trulia.utilities.Driver;

public class TruliaPage {
	private WebDriver driver;
	String str = "Boston, MA";
	String str2 = "Gaithersburg";
	String str3 = "Boston";
	String homePageTitle = "Trulia: Real Estate Listings, Homes For Sale, Housing Data";

	public TruliaPage() {
		this.driver = Driver.getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	@FindBy(id = "searchBox")
	public WebElement searchField;

	@FindBy(xpath = "//button[@class='css-ejw4np btn btnPrimary']")
	public WebElement searchButton;

	@FindBy(xpath = "//h1[@class='h3']")
	public WebElement searchedResult;

	@FindBy(xpath = "//button[@class='css-aks6px btn btnLrg btnSecondary baz typeEmphasize pvs btnSelected']")
	public WebElement buttonBuy;
	
	@FindBy(xpath ="//ul[@role='listbox']//div[@class='typeEmphasize typeTruncate']")
	public List<WebElement> options;

	public WebElement selectedOption;
	
	@FindBy(xpath = "//ul[@role='listbox']")
	public WebElement searchList;
	
	@FindBy(xpath = "//div[@class='typeEmphasize typeTruncate']")
	public WebElement searchExpected;
	
	@FindBy(xpath = "//div[@class='mediaImg mediaImgExt typeLowlight']")
	public WebElement propertyType;
	
	public boolean waitForEmptyField(WebElement element, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
		return wait.until(ExpectedConditions.attributeToBe(element, "value", ""));
	}

	public void selectCityOption(String city) {		
		for (WebElement element : options) {
//			System.out.println(element.getText());
			if (element.getText().equals(city)) {
				element.click();
				return;
			}
		}
	}

	public boolean isAt() {
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
	
	public boolean isButtonClickable(String string) {
		searchField.clear();
		BrowserUtils.waitFor(2);
		
		searchField.sendKeys(string);
		
		try {
			searchButton.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public boolean verifyTitle(String text) {
		return driver.getTitle().toLowerCase().contains(text.toLowerCase());

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
		if (searchField.getAttribute("value").equals(""))
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

	public String gettitle() {
		BrowserUtils.waitFor(2);
		return driver.getTitle();
	}
	public List<String> listedResuls() {
		List<String>str= new ArrayList<>(); 
		for (WebElement webElement : options) {
			str.add(webElement.getText());
		}
		return str;
	}
}

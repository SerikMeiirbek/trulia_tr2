package com.trulia.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.trulia.utilities.Driver;

public class TruliaPage {
	private WebDriver driver;
	
	public TruliaPage() {
		this.driver = Driver.getDriver();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@id='searchBox']")
	public WebElement searchField;
	
	@FindBy(xpath="//button[@class='css-ejw4np btn btnPrimary']")
	public WebElement searchButton;
	

	public boolean isAt() {
		// System.out.println(calculator.getMonthlyBillAmount());
		return driver.getTitle().equals("Trulia: Real Estate Listings, Homes For Sale, Housing Data");

	}
	String str = "Pittsburgh, PA";
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
	
	public boolean verifyTitle() {
		return driver.getTitle().toLowerCase().contains("trulia");
				
	}
	
	public boolean verifyTheTitleSearchField() {
		 return driver.getTitle().toLowerCase().contains(str.toLowerCase());
		
	}
	
	

}

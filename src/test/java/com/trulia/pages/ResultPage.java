package com.trulia.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.trulia.utilities.Driver;

public class ResultPage {
	
	private WebDriver driver;
	
	public ResultPage() {
		this.driver = Driver.getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	@FindBy(id = "homeTypeToggle")
	public WebElement homeType;
	
	@FindBy(id = "homeType1")
	public WebElement Condo;
	
	public boolean isAt() {
		return driver.getTitle().equals("Boston, MA Real Estate & Homes For Sale | Trulia");
	}
	
	public WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

}

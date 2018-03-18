package com.trulia.pages;

import java.util.List;

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
	
	@FindBy(tagName = "h2")
	public WebElement h2;
	
	@FindBy(id = "homeType4")
	public WebElement land;
	
	@FindBy(id = "bedroomsToggle") 
	public WebElement allBeds;
	
	@FindBy(xpath = "//button[text() = '2+']") 
	public WebElement moreThan2Beds;
	
	@FindBy(xpath = "//div[@id='bedroomsButtonGroup']//button") 
	public List<WebElement> bedOptions;
	
	@FindBy(xpath = "//div[@class='backgroundBasic']//li[1]") 
	public List<WebElement> bedsResults;
	
	@FindBy(xpath = "//button[text() = '4+']") 
	public WebElement moreThan4Beds;
	
	public boolean isAt() {
		return h2.getText().contains("homes available on Trulia");
	}
	
	public WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public List<WebElement> waitForVisibility(List<WebElement> elements, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
		return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	
	public int countOptions(List<WebElement> elements) {
		return elements.size();
	}
	
	public boolean countBeds(int number) {
		for(WebElement element : bedsResults) {
			String text = element.getText();
			if(text.length() == 3) {
				int i = Integer.parseInt(text.substring(0, 1));
				if (i < number)
					return false;
			}
		}
		
		return true;
	}

}

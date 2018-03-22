package com.trulia.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

	@FindBy(className = "addressDetail")
	public List<WebElement> addressDetails;

	@FindBy(tagName = "h1")
	public WebElement housrAddress;

	@FindBy(xpath = "//h3[@class='h3 mtn']")
	public WebElement houseAddressText;

	@FindBy(xpath = "//ul[@class='listInlineBulleted man pts ptXxsHidden pbsXxsVisible']")
	public WebElement houseDetialsText;

	@FindBy(tagName = "h2")
	public WebElement notMatchText;

	@FindBy(xpath = "//h6[@class='typeLowlight']")
	public WebElement zeroResults;

	@FindBy(xpath = "//div[@class='typeTruncate typeLowlight']")
	public List<WebElement> searchedResultAdresses;

	@FindBy(xpath = "//div[@class='plm']//h2")
	public WebElement result0;

	@FindBy(xpath = "//span[@class='cardPrice h5 man pan typeEmphasize noWrap typeTruncate']")
	public List<WebElement> listedPrices;

	public WebElement moreToggle;

	public WebElement keywordInput;

	public WebElement priceToggle;

	public WebElement minPrice;

	public WebElement maxPrice;

	public int found() {
		return Integer.parseInt(zeroResults.getText().substring(0, 1));

	}

	public boolean isAt() {
		return h2.getText().contains("homes available on Trulia");
	}

	public WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public int countOptions(List<WebElement> elements) {
		return elements.size();
	}

	public boolean countBeds(int number) {
		for (WebElement element : bedsResults) {
			String text = element.getText();
			if (text.length() == 3) {
				int i = Integer.parseInt(text.substring(0, 1));
				if (i < number)
					return false;
			}
		}

		return true;
	}

	public boolean verifyAutoSuggestions(String city) {
		boolean result = false;

		for (WebElement element : addressDetails) {
			if (element.getText().contains(city)) {
				result = true;
			}
		}
		return result;
	}

	public boolean verifySearchedResultAdresses(String city) {
		boolean result = false;

		for (WebElement element : searchedResultAdresses) {
			if (element.getText().contains(city)) {
				result = true;
			}
		}
		return result;
	}

	public boolean dropDownList(WebElement webElement, String string) {

		Select list = new Select(webElement);
		try {
			list.selectByVisibleText(string);

			return true;
		} catch (NoSuchElementException e) {
			System.out.println("dropDownn list does not contain " + string);
		}

		return false;

	}

	public String dropDownListByDefault(WebElement webElement1) {

		Select list = new Select(webElement1);
		return list.getFirstSelectedOption().getText();
	}

	public boolean checkPriceRange(List<WebElement> webElements, int i, int k) {
		List<String> elemTexts = new ArrayList<>();
		List<String> elemTextOnlyNumbers = new ArrayList<>();
		String str = "";
		for (WebElement el : webElements) {

			elemTexts.add(el.getText());

		}

		System.out.println(elemTexts);

		for (String string : elemTexts) {
			char[] ch = string.toCharArray();
			for (char c : ch) {
				if (Character.isDigit(c)) {
					str += c;
				}

			}
			elemTextOnlyNumbers.add(str);
			str = "";

		}

		for (String string : elemTextOnlyNumbers) {
			if (Integer.parseInt(string) >= i && Integer.parseInt(string) <= k) {
				return true;
			}

		}

		return false;

	}

}

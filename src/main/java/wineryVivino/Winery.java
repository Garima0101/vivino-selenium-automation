package wineryVivino;

import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Winery {

	WebDriver driver;
	Actions action;
	WebDriverWait wait;
	// Element List
	By Regions_Link = By.xpath("//span[@class='menuLink-module__text--1gfZD' and @title='Regions']");
	By acceptBtn = By.id("onetrust-accept-btn-handler");
	By usa = By.xpath("//span[normalize-space()='USA']");
	By source = By.xpath("//div[@class='rc-slider-handle rc-slider-handle-1']");
	By target = By.xpath("//div[@class='rc-slider-handle rc-slider-handle-2']");
	By slider = By.xpath("//div[@class='rc-slider-step']");
	By item = By.xpath("//*[contains(text(),'Antra Cabernet Sauvignon 2015')]");
	By alertRemove = By.xpath("//button[@class='ab-close-button']");// *[contains(@class,'ab-close-button')]
	By tableData = By.xpath("//table[@class='wineFacts__wineFacts--2Ih8B']/tbody/tr");
	By nextButtonPagination = By.xpath("//div[@class='searchPagination-module__next--1Lpry']/child::a[1]");
	By wineCard = By.xpath("//div[contains(@class,'wineCard__wineCard')]");
	@BeforeMethod
	public void init() throws InterruptedException {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.vivino.com/");

		// Wait for cookie blocker to disappear

		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		wait.until(ExpectedConditions.elementToBeClickable(acceptBtn)).click();
	}
	@Test
	public void infoWinery() throws InterruptedException {

	    WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(Regions_Link));
	    action = new Actions(driver);

	    // Hover Regions
	    action.moveToElement(menu).perform();

	    // Click USA
	    WebElement usaEl = wait.until(ExpectedConditions.elementToBeClickable(usa));
	    usaEl.click();

	    // Optional: move slider
	    WebElement slider_Element = wait.until(ExpectedConditions.visibilityOfElementLocated(slider));
	    action.clickAndHold(slider_Element).moveByOffset(180, 0).release().perform();

	  //  boolean hasNextPage = true;
	    Set uniqueWines = new HashSet<>();
	    int localCounter = 1;

	   // while (hasNextPage) {

	        // Wait for cards on current page
	        List<WebElement> wineCards = wait.until(
	                ExpectedConditions.visibilityOfAllElementsLocatedBy(wineCard));
//
	        System.out.println("Cards on current page: " + wineCards.size());

	        for (int i = 0; i < wineCards.size(); i++) {

	            try {
	                wineCards = driver.findElements(wineCard); // re-fetch to avoid stale
	                WebElement card = wineCards.get(i);

	                wait.until(ExpectedConditions.elementToBeClickable(card));
	                card.click();

	                // Close popup if present
	                List<WebElement> popups = driver.findElements(alertRemove);
	                if (!popups.isEmpty()) {
	                    popups.get(0).click();
	                }

	                // Extract table fields
	                String winery = "", grapes = "", region = "", wineStyle = "";

	                List<WebElement> tableRows = wait.until(
	                        ExpectedConditions.visibilityOfAllElementsLocatedBy(tableData)
	                );
	                

	          
	                for (WebElement row : tableRows) {
	                    List<WebElement> cells = row.findElements(By.xpath(".//th | .//td"));
	                   // cells.addAll(uniqueWines);
	                  
	                    if (cells.size() >= 2) {
	                    	
	                    	String header = cells.get(0).getText().trim();
	        				String value = cells.get(1).getText().trim();
	        				
	                    }
	                }
	                // ✅ FILTER ONLY US WINES
	                if (region.contains("United States")) {
	                    System.out.println("====== US WINE #" + localCounter + " ======");
	                    System.out.println("Winery: " + winery);
	                    System.out.println("Grapes: " + grapes);
	                    System.out.println("Region: " + region);
	                    System.out.println("Wine style: " + wineStyle);
	                    System.out.println("==============================");
	                    localCounter++;
	                } else {
	                    System.out.println("Skipping non-US wine: " + region);
	                }

	                driver.navigate().back();

	                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(wineCard));

	            } catch (StaleElementReferenceException e) {
	                System.out.println("Stale element, retrying same card...");
	                i--;   // retry same card index
	            } catch (Exception e) {
	                System.out.println("Error processing card, skipping...");
	            }
	           
	        }
		     System.out.println("This page is completed");

	        System.out.println("current page completed");

	        // ✅ Pagination
//	        try {
//	            WebElement nextBtn = driver.findElement(nextButtonPagination);
//
//	            if (nextBtn.isDisplayed() && nextBtn.isEnabled()) {
//	                List<WebElement> oldCards = driver.findElements(wineCard);
//	                nextBtn.click();
//	                wait.until(ExpectedConditions.stalenessOf(oldCards.get(0)));
//	            } else {
//	                hasNextPage = false;
//	            }
//
//	        } catch (NoSuchElementException e) {
	       
	        	
	        
	           
	          //  hasNextPage = false;
	   // }
	    }
	}

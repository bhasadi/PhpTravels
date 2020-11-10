package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.example.TestReports.TestLogger.LogResults;

public class BasePage {
    public WebDriver driver;
    int visibleTimeout = 20;
    int clickableTimeout = 10;

    //Constructor
    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    // ############################################################################################################
    // ############################################################################################################
    public WebElement waitToBePresentWithLocator(By controlLocator, int maxTimeout) {
        boolean rtnFlag = true;
        WebElement tmpWebElement = null;
        WebDriverWait webDriverWait = new WebDriverWait(driver, maxTimeout);
        try {
            tmpWebElement = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(controlLocator));
        }catch(TimeoutException ex){
            //String reportMsg = "Timed out waiting for element: " + controlLocator;
        }
        return tmpWebElement;
    }//waitToBePresentWithLocator

    // ############################################################################################################
    // ############################################################################################################
    public WebElement waitToBeVisibleWithLocator(By controlLocator, int maxTimeout) {
        boolean rtnFlag = true;
        WebElement tmpWebElement = null;
        WebDriverWait webDriverWait = new WebDriverWait(driver, maxTimeout);
        try {
            tmpWebElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(controlLocator));
        }catch(TimeoutException ex){
            //String reportMsg = "Timed out waiting for element: " + controlLocator;
        }
        return tmpWebElement;
    }//waitToBeVisibleWithLocator

    // ############################################################################################################
    // ############################################################################################################
    public WebElement waitToBeClickableWithLocator(By controlLocator, int maxTimeout) {
        boolean rtnFlag = true;
        WebElement tmpWebElement = null;
        WebDriverWait webDriverWait = new WebDriverWait(driver, maxTimeout);
        try {
            tmpWebElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(controlLocator));
        }catch(TimeoutException ex){
            //String reportMsg = "Timed out waiting for element: " + controlLocator;
        }
        return tmpWebElement;
    }//waitToBeClickable

    // ############################################################################################################
    // ############################################################################################################
    public List<WebElement> findAllElements (By elementBy) {
        List<WebElement> lstWebElements = driver.findElements(elementBy);
        return lstWebElements;
    }

    // ############################################################################################################
    // ############################################################################################################
    public void refreshPage() {
        driver.navigate().refresh();
    }

    // ############################################################################################################
    // ############################################################################################################
    public void scrollClickElement (By elementBy) throws InterruptedException {
        WebElement webElement = waitToBeVisibleWithLocator(elementBy, visibleTimeout);
        scrollToViewElement(webElement);
        webElement = waitToBeClickableWithLocator(elementBy, clickableTimeout);
        webElement.click();

        TimeUnit timeUnit = TimeUnit.SECONDS;
        timeUnit.sleep(1L);
    }

    // ############################################################################################################
    // ############################################################################################################
    public void clickElement (By elementBy) throws InterruptedException {
        WebElement webElement = waitToBeVisibleWithLocator(elementBy, visibleTimeout);
        webElement = waitToBeClickableWithLocator(elementBy, clickableTimeout);
        webElement.click();

        TimeUnit timeUnit = TimeUnit.SECONDS;
        timeUnit.sleep(1L);
    }

    // ############################################################################################################
    // ############################################################################################################
    public void hoverAndClick(By elementBy){
        WebElement webElement = waitToBeVisibleWithLocator(elementBy, visibleTimeout);
        scrollToViewElement(webElement);

        Actions actionBuilder = new Actions(driver);
        actionBuilder.moveToElement(webElement).build().perform();
        actionBuilder.click(webElement).build().perform();
    }//hoverAndClick

    // ############################################################################################################
    // ############################################################################################################
    public void setText (By elementBy, String text) {
        WebElement webElement = waitToBeVisibleWithLocator(elementBy, visibleTimeout);
        webElement.click();
        webElement.sendKeys(text);
    }

    // ############################################################################################################
    // ############################################################################################################
    public void sendKeys (By elementBy, String text) {
        WebElement webElement = waitToBeVisibleWithLocator(elementBy, visibleTimeout);
        webElement.sendKeys(text);
    }

    // ############################################################################################################
    // ############################################################################################################
    public void typeText (By elementBy, String text, boolean pressEnter) {
       try{
            WebElement webElement = waitToBeVisibleWithLocator(elementBy, visibleTimeout);
            webElement.click();

            Actions action = new Actions(driver);
            action.sendKeys(text).perform();
            Thread.sleep(3000);
            if(pressEnter) {
                action.sendKeys(Keys.ENTER).perform();
            }
            //move out of the field
            action.sendKeys(Keys.TAB).perform();
            } catch (Exception ex) {
                LogResults("FATAL", ex.getMessage(), ex, true);
            }
    }

    // ############################################################################################################
    // ############################################################################################################
    public String getText (By elementBy) {
        WebElement webElement = waitToBeVisibleWithLocator(elementBy, visibleTimeout);
        return webElement.getText();
    }

    // ############################################################################################################
    // ############################################################################################################
    public void scrollToViewElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-100)");

    }//scrollToViewElement

    // ############################################################################################################
    // ############################################################################################################
    public void assertEquals (By elementBy, String expectedText) {
        String actualText = getText(elementBy);
        Assert.assertEquals(actualText, expectedText);
    }

    // ############################################################################################################
    // ############################################################################################################
    public void assertWildcard (By elementBy, String expectedText) {
        String actualText = getText(elementBy);
        Assert.assertTrue(actualText.contains(expectedText));

    }
}

package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.example.TestReports.TestLogger.LogResults;

public class HomePage extends BasePage {

    //*********Constructor*********
    public HomePage(WebDriver driver) {
        super(driver);
    }

    //region*********Web Elements*********
    By cmbCurrency = By.cssSelector("a[id='dropdownCurrency']");
    By cmbLanguage = By.cssSelector("a[id='dropdownLangauge']");

    By tabHotels = By.cssSelector("a[data-name='hotels']");
    By tabFlights = By.cssSelector("a[data-name='flights'][class='text-center flights ']");


    By btnFlightSearch = By.cssSelector("button[class='btn-primary btn btn-block']");
    By btnHotelSearch = By.cssSelector("button[class='btn btn-primary btn-block']");
    By edtHotelName = By.xpath("//*[@id=\"s2id_autogen16\"]/a/span[1]");

    By edtFromPort = By.cssSelector("div[id = s2id_location_from]");
    By edtToPort = By.cssSelector("div[id = s2id_location_to]");


    //endregion
    public FlightsPage searchFlight(String fromPort , String toPort) {
        try{
            clickElement(tabFlights);
            Thread.sleep(1000);

            typeText( edtFromPort, fromPort, true);
            typeText( edtToPort, toPort, true);
            clickElement(btnFlightSearch);
            Thread.sleep(2000);

        } catch (Exception ex) {
            LogResults("FATAL", ex.getMessage(), ex, true);
        }

        return new FlightsPage(driver);
    }

    public HomePage verifyObjects() {
        try {
            boolean testResult = true;
            WebElement tmpWebElement = waitToBePresentWithLocator(cmbCurrency, 10);
            if(tmpWebElement == null) {
                testResult = false;
                LogResults("FAIL", "Currency object is not found", null, false);
            }

            tmpWebElement = waitToBePresentWithLocator(cmbLanguage, 10);
            if(tmpWebElement == null) {
                testResult = false;
                LogResults("FAIL", "Language object is not found", null, false);
            }

            // report the overall result
            if(testResult)
                LogResults("PASS", "All objects are found on Home Page", null, false);

        } catch (Exception ex) {
            LogResults("FATAL", ex.getMessage(), ex, true);
        }

        return this;
    }//verifyObjects

    public HotelsPage searchHotel(String hotelName) {
        try{
            typeText( edtHotelName, hotelName, true);
            clickElement(btnHotelSearch);
            Thread.sleep(2000);
        } catch (Exception ex) {
            LogResults("FATAL", ex.getMessage(), ex, true);
        }

        return new HotelsPage(driver);

    }


}//class

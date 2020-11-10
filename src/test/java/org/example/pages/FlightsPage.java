package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.example.TestReports.TestLogger.LogResults;

public class FlightsPage extends BasePage{

    //*********Constructor*********
    public FlightsPage(WebDriver driver) {
        super(driver);
    }

    //region*********Web Elements*********
    By btnFirstBookNow = By.xpath("//*[@id=\"LIST\"]/li[1]/div/div[1]/div[2]/form/div[2]/div/button");
    //endregion


    public PassengerDetailsPage selectFlight(int roomIndx){
        try{
            clickElement(btnFirstBookNow);
            Thread.sleep(3000);
        } catch (Exception ex) {
            LogResults("FATAL", ex.getMessage(), ex, true);
        }
        return new PassengerDetailsPage(driver);
    }


}

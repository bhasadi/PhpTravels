package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.example.TestReports.TestLogger.LogResults;

public class HotelsPage extends BasePage{

    //*********Constructor*********
    public HotelsPage(WebDriver driver) {
        super(driver);
    }

    //region*********Web Elements*********
    By chkFirstRoom = By.cssSelector("input[type='checkbox'][id='35']");

    //*[@id="detail-content-sticky-nav-02"]/form/div/div[2]/div/div[2]/div/div[2]/h5/div/label/text()
    By btnBookNow = By.cssSelector("input[type='submit'][class='book_button btn btn-success btn-block btn-lg chk']");


    //endregion


    public void selectRoom(int roomIndx){
        try{
            clickElement(chkFirstRoom);
            clickElement(btnBookNow);
        } catch (Exception ex) {
            LogResults("FATAL", ex.getMessage(), ex, true);
        }
    }
}

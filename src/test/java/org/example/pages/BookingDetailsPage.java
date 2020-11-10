package org.example.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.example.TestReports.TestLogger.LogResults;
public class BookingDetailsPage extends BasePage{

    //*********Constructor*********
    public BookingDetailsPage(WebDriver driver) {
        super(driver);
    }

    //region*********Web Elements*********
    By txtFirstName = By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[5]/div[2]/div/ul/li[3]/span[2]");
    //endregion


    public void verifyBookingDetails(){
        try{
            assertEquals(txtFirstName, "john smith");

            Thread.sleep(3000);
        } catch (Exception ex) {
            LogResults("FATAL", ex.getMessage(), ex, true);
        }
    }


}


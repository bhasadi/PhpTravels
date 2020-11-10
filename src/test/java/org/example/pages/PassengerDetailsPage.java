package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.example.TestReports.TestLogger.LogResults;

public class PassengerDetailsPage extends BasePage {

    //*********Constructor*********
    public PassengerDetailsPage(WebDriver driver) {
        super(driver);
    }

    //region*********Web Elements*********
    By edtFirstName = By.cssSelector("input[name='firstname']");
    By edtLastName = By.cssSelector("input[name='lastname']");
    By edtEmail = By.cssSelector("input[name='email']");
    By edtConfirmEmail = By.cssSelector("input[name='confirmemail']");
    By edtContactNumber = By.cssSelector("input[name='phone']");
    By edtAddress = By.cssSelector("input[name='address']");

    By edtPassengerName = By.cssSelector("input[id='passenger_name_0']");
    By edtPassengerAge = By.cssSelector("input[id='passenger_age_0']");
    By edtPassengerPassport = By.cssSelector("input[id='passenger_passport_0']");

    By drpCountry = By.xpath("//*[@id=\"guestform\"]/div[5]/div/div[2]/a/span");
    By btnConfirmBooking = By.cssSelector("button[type='submit'][class='btn btn-success btn-lg btn-block completebook']");

    //endregion


    public BookingDetailsPage EnterPassengerDetails(){
        try{
            sendKeys(edtFirstName, "john");
            sendKeys(edtLastName, "smith");
            sendKeys(edtEmail, "johnsmith@yahoo.com");
            sendKeys(edtConfirmEmail, "johnsmith@yahoo.com");
            sendKeys(edtContactNumber, "0401002003");
            sendKeys(edtAddress, "2 george street Sydney");

            typeText(drpCountry, "Aus", true);
            hoverAndClick(btnConfirmBooking);
            Thread.sleep(3000);

        } catch (Exception ex) {
            LogResults("FATAL", ex.getMessage(), ex, true);
        }

        return new BookingDetailsPage(driver);

    }


}

package org.example.TestScripts;

import org.example.TestHooks.TestNGHooks;
import org.example.pages.*;
import org.testng.annotations.Test;

public class PhpTravelsSmokeTests extends TestNGHooks {

    @Test
    public void VerifyHomePage() {

        // Create the Object for Default Page
        HomePage homePage = new HomePage(testVars.driver);
        homePage.verifyObjects();
    }

    @Test
    public void BookFlight() {

        // Create the Object for Default Page
        HomePage homePage = new HomePage(testVars.driver);

        // Search for a flight
        FlightsPage flightsPage = homePage.searchFlight("SYD", "ADEL");

        // Select the first flight from the list
        PassengerDetailsPage passengerDetailsPage = flightsPage.selectFlight(1);

        // Enter the details for booking
        BookingDetailsPage bookingDetailsPage = passengerDetailsPage.EnterPassengerDetails();

        // verify the booking confirmation
        bookingDetailsPage.verifyBookingDetails();
    }

}//end class

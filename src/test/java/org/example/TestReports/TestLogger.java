package org.example.TestReports;

import org.example.TestContext.TestVars;
import org.testng.Assert;

import static org.example.support.Generic.getTodayDate;
import static org.example.support.Generic.takesScreenshot;

public class TestLogger {

    public static void LogResults(String logTypes, String logContents, Exception exception, boolean takeScreenshot) {
        // local variables
        String imagePath = null;
        String imageTitle = null;
        String exTrace = "";
        String exMessage = "";

        StackTraceElement stackTraceElement;
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

        String callerMethod = stacktrace[4].getMethodName();
        logContents = "##" + callerMethod + "# Details - " + logContents;

        // take screenshot if required
        TestVars testVars = TestVars.getInstance();
        String dateString = getTodayDate("ddMMyy_hhmmss");
        if(takeScreenshot){
            imageTitle = "img_" + dateString;
            imagePath = "./target/reports/" + imageTitle + ".jpeg";
            takesScreenshot(testVars.driver, imagePath);
        }//takeScreenshot

        // Report the Exception
        if(exception!= null) {
            exMessage = exception.getMessage();
            exTrace = String.valueOf(exception.getStackTrace());
        }

        logContents = logContents +"\n"+ exMessage +"\n"+ exTrace;

        //  Log the message
        switch (logTypes) {
            case "FAIL":
                System.out.println(logContents);
                break;

            case "FATAL":
                System.out.println(logContents);
                // this will cause the Test Script to abort
                Assert.fail("Aborting Test Case - Please check above for the details");
                break;

            case "PASS":
                System.out.println(logContents);
                break;

            case "INFO":
                System.out.println(logContents);
                break;

            case "WARN":
                System.out.println(logContents);
                break;

            case "SKIP":
                System.out.println(logContents);
                break;

            default: // Debug
                System.out.println(logContents);
                break;
        }

        // Log to Extent Klov
        if(testVars.flagKlovReport)
            ExtentReportHelper.logExtentMessage(testVars.extentTest, logTypes, logContents, imagePath, imageTitle);

    }//LogResults

}


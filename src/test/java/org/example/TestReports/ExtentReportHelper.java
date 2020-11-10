package org.example.TestReports;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;

public class ExtentReportHelper {
    // ************************************************************************************************
    // ************************************************************************************************
    public static void logExtentMessage(ExtentTest extentTest, String logType, String logMessage, String imagePath, String imageTitle){

        switch (logType.toUpperCase())
        {
            case "FAIL":
                extentTest.log(Status.FAIL, logMessage);
                break;

            case "FATAL":
                extentTest.log(Status.FATAL, logMessage);
                break;

            case "PASS":
                extentTest.log(Status.PASS, logMessage);
                break;

            case "INFO":
                extentTest.log(Status.INFO, logMessage);
                break;

            case "WARN":
                extentTest.log(Status.WARNING, logMessage);
                break;

            case "SKIP":
                extentTest.log(Status.SKIP, logMessage);
                break;

            default:
                extentTest.log(Status.DEBUG, logMessage);
                break;
        }// Switch

        if(imagePath!=null) {
            attachScreenshot(extentTest, imagePath, imageTitle);

        }
    }//logExtentMessage

    // ************************************************************************************************
    // ************************************************************************************************
    public static ExtentReports getExtentReportsObject(String projectName, String reportName, String uriKlov, String mongoHostColonPort) {
        // create Klov reporter & set the properties
        ExtentKlovReporter klovReporter = new ExtentKlovReporter(projectName, reportName);
        klovReporter.initKlovServerConnection(uriKlov);
        klovReporter.initMongoDbConnection(mongoHostColonPort);// should be in host:port

        // attach the reporter to the Extent Reports Object
        com.aventstack.extentreports.ExtentReports extentReports = new com.aventstack.extentreports.ExtentReports();
        extentReports.attachReporter(klovReporter);
        return extentReports;
    }//getExtentReportsObject

    // ************************************************************************************************
    // ************************************************************************************************
    public static void attachScreenshot(ExtentTest extentTest, String imagePath, String imageTitle){
        if(imageTitle == null) imageTitle = "img";
        try{
            extentTest.addScreenCaptureFromPath(imagePath);//, imageTitle);

        }catch (Exception ex){
            TestLogger.LogResults("FATAL", ex.getMessage(), ex, false);
        }

    }//attachScreenshot


}//class


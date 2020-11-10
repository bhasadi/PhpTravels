package org.example.support;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.example.TestContext.TestVars;
import org.example.TestReports.TestLogger;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Generic {

    // ############################################################################################################
    // ############################################################################################################
    public static void takesScreenshot(WebDriver webdriver, String targetFilePath){
        TakesScreenshot takesScreenshot =((TakesScreenshot)webdriver);
        File SrcFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File(targetFilePath);

        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException ex) {
            TestLogger.LogResults("FATAL", "Failed To Take Screenshot", ex, false);
        }

    }//takesScreenshot


    // ############################################################################################################
    // ############################################################################################################
    public static String getTodayDate(String datePattern){
        Date parsedDate = null;
        DateFormat dateFormat = new SimpleDateFormat(datePattern);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String dateString = dateFormat.format(calendar.getTime());
//        try{
//            parsedDate = dateFormat.parse(dateString);
//        }catch (ParseException ex){
//            TestLogger.LogResults("FAIL", ex.getMessage(), ex, false);
//        }

        return dateString;
    }//getTodayDate




}//class


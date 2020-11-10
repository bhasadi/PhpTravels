package org.example.support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.example.TestContext.TestVars;
import org.example.TestReports.TestLogger;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Configurations {

    // ############################################################################################################
    // ############################################################################################################
    public static WebDriver initWebDriverObject(String driverType){
        WebDriver driver = null;

        // if driverType is chrome
        if(driverType.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-notifications");
            driver = new ChromeDriver(chromeOptions);

            // if driverType is firefox
        }else if(driverType.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--disable-notifications");
            driver = new FirefoxDriver(firefoxOptions);
        }

        return driver;
    }//initWebDriverObject

    // ############################################################################################################
    // ############################################################################################################
    public static WebDriver initRemoteWebDriverObject(){
        WebDriver driver = null;

        StringBuilder remoteUrl = new StringBuilder();
        DesiredCapabilities desiredCapabilities = remoteDesiredCapabilities(remoteUrl);
        URL remoteServerUrl = null;
        try {
            remoteServerUrl = new URL(remoteUrl.toString());
        } catch (MalformedURLException ex) {
            TestLogger.LogResults("FATAL", ex.getMessage(), ex, true);
        }
        driver = new RemoteWebDriver(remoteServerUrl, desiredCapabilities);

        return driver;
    }//initWebDriverObject

    // ############################################################################################################
    // ############################################################################################################
    public static DesiredCapabilities remoteDesiredCapabilities(StringBuilder remoteUrl){

        TestVars testVars = TestVars.getInstance();

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName(testVars.configProps.get("driverType"));
        //desiredCapabilities.setPlatform(Platform.WIN10);

        //http://192.168.0.100:4444/wd/hub
        //http://169.254.80.80:4444/wd/hub
        remoteUrl = remoteUrl.append(testVars.configProps.get("remoteHub"));
        return desiredCapabilities;
    }//buildDesiredCapabilities

    // ############################################################################################################
    // ############################################################################################################
    public static Map<String, String> getAllConfigProperties(String propFile){

        if(propFile.toLowerCase() == "default") {
            propFile = "./src/test/resources/config.properties";
        }

        Map<String, String> configMap  = new HashMap<>();

        Properties properties = getProperties(propFile);
        Set<Object> allKeys = properties.keySet();
        for(Object obKey:allKeys){
            String keyString = (String)obKey;
            String value = properties.getProperty(keyString);
            configMap.put(keyString, value);
        }// for

        return configMap;
    }//getAllConfigProperties

    // ############################################################################################################
    // ############################################################################################################
    public static Properties getProperties(String propFile) {

        Properties properties = null;
        try (InputStream inputStream = new FileInputStream(propFile)) {
            properties = new Properties();
            properties.load(inputStream);
            //inputStream.close();
        } catch (Exception ex) {
            TestLogger.LogResults("FATAL", ex.getMessage(), ex, false);
        }

        return properties;
    }//getProperties


}


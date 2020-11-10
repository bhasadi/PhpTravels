package org.example.TestContext;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static org.example.support.Configurations.getAllConfigProperties;

public class TestVars {
    private static TestVars instance;
    // **********************************************************
    //  Thread Safe Singleton Instance Using Double Locking
    //  Since every TestNG test is executed in its own thread
    //  TestVars will have one unique instance through the
    // test life time - No issues when Parallel Execution
    // **********************************************************
    public static TestVars getInstance() {
        if(instance == null){
            synchronized (TestVars.class) {
                if(instance == null){
                    instance = new TestVars();
                }
            }
        }//if condition
        return instance;
    }//getInstance

    // **********************************************************
    //  Private Constructor
    // **********************************************************
    private TestVars()  {
        userVars =  new HashMap<>();
        configProps = getAllConfigProperties("default");

        if(configProps.get("flagKlovReport").equalsIgnoreCase("true"))
            flagKlovReport = true;
    }//private Constructor TestVars

    // **********************************************************
    //  class properties
    // **********************************************************
    public ExtentTest extentTest;
    public boolean flagKlovReport = false;


    public WebDriver driver;
    //public String autURL = "https://amazon.com.au/";
    public String autDateFormat = "EEE MM dd yyyy";

    public String dateGenericFormat = "ddMMyy_hhmmss";
    public String extentReportPath = "target/TestReports/ExtentReport";
    public String resourcesPath = "./src/test/resources/";

    //region ========== Dictionary Objects Declaration ==========
    public Map<String, String> configProps;
    public Map<String, String> userVars;
    //endregion



}



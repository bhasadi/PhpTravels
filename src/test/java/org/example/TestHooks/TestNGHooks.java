package org.example.TestHooks;

import com.aventstack.extentreports.ExtentReports;
import org.example.TestContext.TestVars;
import org.example.TestReports.ExtentReportHelper;
import org.example.TestReports.TestLogger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.example.support.Configurations.*;
import static org.example.support.Generic.getTodayDate;

public class TestNGHooks {
    public TestVars testVars;
    public ExtentReports extentLogger;


    // ***************************************************************************************************************
    //  @BeforeSuite annotated method will run before the execution of all the test methods in the suite.
    // ***************************************************************************************************************
    @BeforeSuite(alwaysRun = true)
    public void tngBeforeSuite(){

        Map<String, String> configProps = getAllConfigProperties("default");

        // **************************************
        // if to flagKlovReport is True
        // **************************************
        if(configProps.get("flagKlovReport").equalsIgnoreCase("true")) {
            String testBuild = configProps.get("testbuild");
            String testProject = configProps.get("testproject");
            String uriKlov = configProps.get("klov.host") + ":" + configProps.get("klov.port");
            String mongoUri = configProps.get("mongodb.host") + ":" + configProps.get("mongodb.port");

            if (testBuild.toLowerCase().equals("default")) {
                testBuild = "Build_" + getTodayDate("ddMMyy_hhmmss");
            }

            //extentLogger = ExtentReportHelper.getExtentReportsObject("TestAssured","Build0002", "http://localhost:85","localhost:27017");
            extentLogger = ExtentReportHelper.getExtentReportsObject(testProject, testBuild, uriKlov, mongoUri);
        }
    }

    // ***************************************************************************************************************
    //  @AfterSuite annotated method will run after the execution of all the test methods in the suite.
    // ***************************************************************************************************************
    @AfterSuite(alwaysRun = true)
    public void tngAfterSuite() {
        testVars = TestVars.getInstance();
        if(testVars.flagKlovReport)
            extentLogger.flush();
    }

    // ***************************************************************************************************************
    //  @BeforeMethod annotated method will be executed before each test method will run.
    // ***************************************************************************************************************
    @BeforeMethod(alwaysRun = true)
    public void tngBeforeMethod(Method testMethod) {

        testVars = TestVars.getInstance();
        if(testVars.flagKlovReport)
            testVars.extentTest = extentLogger.createTest(testMethod.getName(), testMethod.getName());

        String runRemotely = testVars.configProps.get("runRemotely");
        String driverType = testVars.configProps.get("driverType");
        // **************************************
        // if to run test locally
        // **************************************
        if(runRemotely.equalsIgnoreCase("false")) {
            testVars.driver = initWebDriverObject(driverType);

            // **************************************
            // otherwise run remotely
            // **************************************
        }else{
           //todo
        }//else

        long implicitWait = Long.parseLong(testVars.configProps.get("driverImplicitWait"));
        testVars.driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
        testVars.driver.manage().window().maximize();
        testVars.driver.get(testVars.configProps.get("autUrl"));
    }

    // ***************************************************************************************************************
    //  @AfterMethod annotated method will run after the execution of each test method.
    // ***************************************************************************************************************
    @AfterMethod(alwaysRun = true)
    public void tngAfterMethod(Method testMethod, ITestResult testResult) {
        //ThreadUtils.printThreadInfo();

        String testName = testResult.getName();
        Exception exception = new Exception(testResult.getThrowable());

        switch (testResult.getStatus()) {
            //CREATED = -1; SUCCESS = 1; FAILURE = 2; SKIP = 3;
            //SUCCESS_PERCENTAGE_FAILURE = 4;  STARTED = 16;
            case ITestResult.SKIP:
                TestLogger.LogResults("SKIP", testName + (": Status = Skipped"),
                        null, false);
                break;

            case ITestResult.SUCCESS:
                TestLogger.LogResults("PASS", testName + (": Status = Passed"),
                        null, false);
                break;

            case ITestResult.FAILURE:
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
                TestLogger.LogResults("FAIL", testName + (": Status = Failed"),
                        exception, true);
                break;

            default:
                Throwable throwable =  testResult.getThrowable();
                TestLogger.LogResults("FATAL", testName + (": Status = Fatal Error"),
                        exception, true);
                //throw new RuntimeException("Invalid status");
        }// switch

        testVars.driver.close();
        testVars.driver.quit();
        testVars = null;
    }
}//class


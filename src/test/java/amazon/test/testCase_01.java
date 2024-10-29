package amazon.test;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import amazon.DP;
import amazon.DriverSingleton;
import amazon.Reporter;
import amazon.pages.Homepage;
import amazon.pages.LoginPage;

public class testCase_01 {
    private static WebDriver driver;
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;
    private static SoftAssert softAssert;

    public static void logStatus(String type, String message, String status) {
		System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeSuite(alwaysRun=true)
    public static void Driversetup() throws MalformedURLException, IOException {
		driver = DriverSingleton.getDriver();
        extentReports = Reporter.getInstance();
        softAssert = new SoftAssert();
		logStatus("driver", "Initializing driver", "Started");
	}

    @Test(dataProvider = "data-provider", dataProviderClass = DP.class, description = "Verify the search and filter flow", priority = 1, groups ={"SearchAndFilterflow"})
    public void TestCase01(String searchtext, String username, String password) throws InterruptedException, TimeoutException, IOException{
        extentTest = extentReports.createTest("search functionality check");
     softAssert = new SoftAssert();
     Homepage homePage = new Homepage(driver);
     softAssert.assertTrue(homePage.checkNavigation(), "Navigation of Home page is failed");
		Thread.sleep(3000);
        LoginPage loginPage = new LoginPage(driver);
        homePage.navigateTologinPage();
        softAssert.assertTrue(loginPage.checkLoginPageNavigation(), "Navigation to Login page failed");
        Thread.sleep(3000);
        loginPage.performExistingUserLogin(username, password);
        
        
        
        homePage.search(searchtext);
        Thread.sleep(3000); // Consider using WebDriverWait instead of Thread.sleep
    
        /* Check if the expected product is displayed
        boolean isProductDisplayed = homePage.areProductsDisplayed(searchtext);
        softAssert.assertTrue(isProductDisplayed, "Product containing '" + searchtext + "' is not displayed in the search results");

        // Optionally take a screenshot if the assertion fails
        if (!isProductDisplayed) {
            String screenshotPath = capture(driver);
            extentTest.addScreenCaptureFromPath(screenshotPath);
        }

        // Ensure that all assertions are checked at the end
        softAssert.assertAll(); */

        boolean isProductDisplayed = homePage.areProductsDisplayed(searchtext);
        softAssert.assertTrue(isProductDisplayed, "Product '" + searchtext + "' is not displayed in the search results");
        

        // Optionally take a screenshot if the assertion fails
        if (!isProductDisplayed) {
            String screenshotPath = capture(driver);
            extentTest.addScreenCaptureFromPath(screenshotPath);
            logStatus("screenshot", "Taking screenshot for failed assertion", "Failure");
            extentTest.fail("Failed to find the product: '" + searchtext + "' in search results.");
        } else {
            extentTest.pass("Product '" + searchtext + "' is successfully displayed in search results.");
        }

        // Ensure that all assertions are checked at the end
        softAssert.assertAll();

    }
    public static String capture(WebDriver driver) throws IOException{
   


     File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

     File Dest = new File(System.getProperty("user.dir")+"/AmazonScreenshots/" + System.currentTimeMillis()+ ".png");

      String errflpath = Dest.getAbsolutePath();
      FileUtils.copyFile(scrFile, Dest);
      return errflpath;
    }


    @AfterSuite(enabled = true)
	
	public static void quitDriver() throws MalformedURLException {
		DriverSingleton.quitDriver();
        Reporter.endTest(extentTest);
        Reporter.flush();
        logStatus("driver", "Quitting driver", "Success");
	}
}

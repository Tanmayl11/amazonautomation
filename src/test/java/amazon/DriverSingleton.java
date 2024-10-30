package amazon;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSingleton {
     // The static WebDriver instance (single instance)
     private static WebDriver driver;

     // Private constructor to prevent external instantiation
     private DriverSingleton() {
     }
 
     // Public method to provide the WebDriver instance
     public static WebDriver getDriver() {
         if (driver == null) {
             // Set up WebDriverManager and initialize the driver
             WebDriverManager.chromedriver().driverVersion("129.0.6668.59").setup();
             driver = new ChromeDriver();
 
             // Maximize the window and set an implicit wait if necessary
             driver.manage().window().maximize();
         }
         return driver;
     }
 
     // Method to quit the driver
     public static void quitDriver() {
         if (driver != null) {
             driver.quit();
             driver = null; // Set to null for future test execution
         }
     }
 }




package amazon;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumWrapper {
    private final WebDriver driver;

    // Constructor to initialize WebDriver
    public SeleniumWrapper(WebDriver driver) {
        this.driver = driver;
    }

    // Click method using WebDriver
    public static Boolean click(WebElement elementToClick, WebDriver driver) {
        if (elementToClick.isDisplayed()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", elementToClick);
            elementToClick.click();
            return true;
        } else {
            return false;
        }
    }

    // sendKeys method to input text in a field
    public static Boolean sendKeys(WebElement inputBox, String keysToSend) {
        inputBox.clear();
        inputBox.sendKeys(keysToSend);
        return true;
    }

    // Navigate to a specific URL if it's not already open
    public Boolean navigate(WebDriver driver, String url) {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
            return true;
        } else {
            return false;
        }
    }

    // Find an element with retry logic
    public static WebElement findElementWithRetry(WebDriver driver, By by, int retryCount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Using Duration instead of int
        for (int i = 0; i < retryCount; i++) {
            try {
                WebElement element = driver.findElement(by);
                if (element.isDisplayed()) {
                    return element;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Element not found");
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
        return null;
    }

    // Placeholder method (Optional, if you don't need it, remove it)
    // public WebElement findElementWithRetry(WebDriver driver, By xpath) {
    //     // This method seems unused, remove or implement if necessary
    //     return null;
    // }
}

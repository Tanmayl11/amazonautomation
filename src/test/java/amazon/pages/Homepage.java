package amazon.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import amazon.SeleniumWrapper;

public class Homepage {
    private WebDriver driver;
    private SeleniumWrapper seleniumwrapper;
    private String url = "https://www.amazon.in/";

    @FindBy(xpath = "//a[text()='Start here.']")
    private WebElement registerButton;

    @FindBy(xpath = "//input[@id='twotabsearchtextbox']")
    private WebElement searchBox;

    @FindBy(xpath = "//input[@id='nav-search-submit-button']")
    private WebElement searchButton;

    @FindBy(xpath = "//span[@class='a-size-medium a-color-base a-text-normal']")
    private WebElement productName;

    @FindBy(xpath = "//span[@class='nav-action-inner']")
    private WebElement loginButton;

    @FindBy(xpath = "//span[@id='nav-link-accountList-nav-line-1']")
    private WebElement isUserSignedIn;

    @FindBy(xpath="//span[text='Sign Out]")
    private WebElement logOutButton;

    public Homepage(WebDriver driver) {
        this.driver = driver;
        this.seleniumwrapper = new SeleniumWrapper(driver);
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);
        driver.manage().window().maximize();

    }

    public boolean checkNavigation() {
        return seleniumwrapper.navigate(driver, url);
    }

    public void search(String searchtext) throws InterruptedException {
        SeleniumWrapper.sendKeys(searchBox, searchtext);
        Thread.sleep(3000);
        SeleniumWrapper.click(searchButton, driver);
    }

    public void navigateTologinPage(){
        SeleniumWrapper.click(loginButton, driver);

    }

    // public boolean searchForProduct(String searchtext)throws InterruptedException
    // {
    // WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
    // wait.until(ExpectedConditions.visibilityOf(productName));
    // return true;
    // }
    public boolean areProductsDisplayed(String productName) {
        // Dynamically create XPath for the product locator
        By productLocator = By.xpath(
                "//span[@class='a-size-medium a-color-base a-text-normal' and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                        + productName.toLowerCase() + "')]");
        try {
            // Wait for the presence of all product elements
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productLocator));

            // Find matching product elements
            List<WebElement> productElements = driver.findElements(productLocator);
            if (productElements.isEmpty()) {
                System.out.println("No products found with name: '" + productName + "'");
                return false;
            } else {
                // Click the first matching product
                for (WebElement product : productElements) {
                    String productText = product.getText().trim();
                    System.out.println("Product is displayed: " + productText);

                    // Check for the matching product name
                    if (productText.toLowerCase().contains(productName.toLowerCase())) {
                        // Click the product link
                        product.click();
                        System.out.println("Clicked on product: " + productText);

                        // Switch to the new window that opens after clicking the product
                        for (String windowHandle : driver.getWindowHandles()) {
                            driver.switchTo().window(windowHandle);
                        }
                        System.out.println("Switched to the new window.");
                        return true;
                    }
                }
                return false;
            }
        } catch (TimeoutException e) {
            System.out.println("Timeout: No products with name '" + productName + "' are displayed.");
            return false;
        }
    }

    // public boolean isProductDisplayed(String productName) {
    // By productLocator = By.xpath("//span[@class='a-size-medium a-color-base
    // a-text-normal' and contains(text(), '" + productName + "')]");
    // WebElement productElement = SeleniumWrapper.findElementWithRetry(driver,
    // productLocator, 3);
    // return productElement != null && productElement.isDisplayed();
    // }

    public WebElement isAutoCompleteDisplayed(String searchtext) throws TimeoutException, InterruptedException {
        try {
            return SeleniumWrapper.findElementWithRetry(driver, By.xpath(String
                    .format("//span[@class='a-size-medium a-color-base a-text-normal']", searchtext.toLowerCase())),
                    30);
        } catch (Exception e) {
            System.out.println("Error checking the autocomplete display:" + e.getMessage());
            return null;
        }
    }
}
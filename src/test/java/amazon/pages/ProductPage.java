package amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import amazon.SeleniumWrapper;

public class ProductPage {
    private WebDriver driver;
    private SeleniumWrapper seleniumwrapper;

    @FindBy(xpath = "//span[@id='productTitle']")
    private WebElement productTitle;

    @FindBy(xpath = "(//input[@id='add-to-cart-button'])[2]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//input[@id='buy-now-button']")
    private WebElement buyNowButton;

    @FindBy(xpath = "//ul[@class='a-unordered-list a-nostyle a-button-list a-declarative a-button-toggle-group a-horizontal a-spacing-top-micro swatches swatchesSquare']//button")
    private WebElement sizeButton;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.seleniumwrapper = new SeleniumWrapper(driver);
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);
        driver.manage().window().maximize();

    }
}

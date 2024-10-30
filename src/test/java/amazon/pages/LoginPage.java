package amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import amazon.SeleniumWrapper;

public class LoginPage {
    private WebDriver driver;
    private SeleniumWrapper seleniumwrapper;
    private String url = "https://www.amazon.in/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.in%2Fgp%2Fyourstore%2Fhome%3Fpath%3D%252Fgp%252Fyourstore%252Fhome%26useRedirectOnSuccess%3D1%26signIn%3D1%26action%3Dsign-out%26ref_%3Dnav_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=inflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0";

    @FindBy(xpath="//input[@id='ap_email']")
    private WebElement emailInput;

    @FindBy(xpath="//input[@id='continue']")
    private WebElement continueButton;

    @FindBy(xpath="//input[@id='ap_password']")
    private WebElement passwordInput;

    @FindBy(xpath="//input[@id='signInSubmit']")
    private WebElement signInButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.seleniumwrapper = new SeleniumWrapper(driver);
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);
        driver.manage().window().maximize();

    }

    public boolean checkLoginPageNavigation() {
        return seleniumwrapper.navigate(driver, url);
    }

    public  void performExistingUserLogin(String username, String password)throws InterruptedException{
     
        SeleniumWrapper.sendKeys(emailInput, username);
        SeleniumWrapper.click(continueButton, driver);
        SeleniumWrapper.sendKeys(passwordInput, password);
        SeleniumWrapper.click(signInButton, driver);
       
    
       }
}

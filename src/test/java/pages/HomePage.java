package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    // locators
    By header = By.tagName("header");
    By headerLinks = By.cssSelector("header a");
    By signInButton = By.xpath("//header//a[text()='Sign in']");
    By signUpButton = By.xpath("//header//a[text()='Sign up']");
    By menuButton = By.cssSelector("header button[data-slot='sheet-trigger']");
    By mobileMenu = By.cssSelector("[role='dialog'] nav");
    By mobileMenuLinks = By.cssSelector("[role='dialog'] nav a");
    By heroHeading = By.xpath("//h3[text()='Crypto for everyone']");
    By downloadAppButton = By.xpath("//a[text()='Download the app']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(header)).isDisplayed();
    }

    // get nav link by its text, for example "Explore"
    public WebElement getNavLink(String name) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//header//a[text()='" + name + "']")));
    }

    public void clickNavLink(String name) {
        getNavLink(name).click();
    }

    public List<WebElement> getAllHeaderLinks() {
        return driver.findElements(headerLinks);
    }

    public boolean isSignInDisplayed() {
        return driver.findElement(signInButton).isDisplayed();
    }

    public boolean isSignUpDisplayed() {
        return driver.findElement(signUpButton).isDisplayed();
    }

    public boolean isMenuButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(menuButton)).isDisplayed();
    }

    public void openMobileMenu() {
        driver.findElement(menuButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(mobileMenu));
    }

    public List<WebElement> getMobileMenuLinks() {
        return driver.findElements(mobileMenuLinks);
    }

    public boolean isHeroDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heroHeading)).isDisplayed();
    }

    public String getDownloadAppLink() {
        return driver.findElement(downloadAppButton).getDomProperty("href");
    }

    // category section like "Hot", "Gainers", "Losers"
    public List<WebElement> getCategoryItems(String categoryName) {
        WebElement title = driver.findElement(By.xpath("//h2[text()='" + categoryName + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", title);
        By items = By.xpath("//h2[text()='" + categoryName + "']/following-sibling::div//a");
        wait.until(ExpectedConditions.visibilityOfElementLocated(items));
        return driver.findElements(items);
    }
}

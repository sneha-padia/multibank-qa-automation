package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

// Company page = About Us / Why MultiBank page
public class CompanyPage {

    WebDriver driver;
    WebDriverWait wait;

    By heading = By.tagName("h1");
    By sectionHeadings = By.tagName("h2");
    By getInTouchButton = By.xpath("//*[text()='Get in touch']");

    public CompanyPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open() {
        driver.get(BaseTest.baseUrl + "/company");
    }

    public String getHeading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heading)).getText();
    }

    public List<String> getSectionHeadings() {
        List<String> list = new ArrayList<>();
        for (WebElement e : driver.findElements(sectionHeadings)) {
            // getText() gives empty text for sections that are not scrolled into view yet (animation)
            list.add(e.getDomProperty("textContent").trim());
        }
        return list;
    }

    public String getPageText() {
        return driver.findElement(By.tagName("body")).getDomProperty("textContent");
    }

    public boolean isGetInTouchDisplayed() {
        WebElement button = driver.findElement(getInTouchButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
        wait.until(ExpectedConditions.visibilityOf(button));
        return button.isDisplayed();
    }
}

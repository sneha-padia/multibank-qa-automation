package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseTest;

import java.time.Duration;
import java.util.List;

public class ExplorePage {

    WebDriver driver;
    WebDriverWait wait;

    By pageHeading = By.tagName("h1");
    By spotMarketHeading = By.xpath("//h2[text()='Spot market']");
    By tableRows = By.cssSelector("table tbody tr");
    By downloadAppButton = By.xpath("//a[text()='Download the app']");

    // banners on top of the explore page
    By earnBanner = By.xpath("//p[text()='Earn interest on your assets']");
    By cardBanner = By.xpath("//p[text()='Get crypto with your card']");
    By depositBanner = By.xpath("//p[text()='Deposits using card or wire transfer']");

    public ExplorePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open() {
        driver.get(BaseTest.baseUrl + "/explore");
    }

    public String getHeading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading)).getText();
    }

    public boolean isSpotMarketDisplayed() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(spotMarketHeading));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        return el.isDisplayed();
    }

    // waits for the table to load (it shows loading skeleton first)
    public List<WebElement> getRows() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody td[id$='_price-td']")));
        return driver.findElements(tableRows);
    }

    // tabs are Hot, Gainers, Losers
    public void clickTab(String tabName) {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='" + tabName + "']")));
        tab.click();
        try {
            Thread.sleep(2000); // wait for table to refresh
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isTabDisplayed(String tabName) {
        return driver.findElement(By.xpath("//button[text()='" + tabName + "']")).isDisplayed();
    }

    // NOTE: prices update live and the table re-renders, so old elements become stale.
    // so we find the cell again every time using row index (cell ids are like "-0_price-td")
    public String getCellText(String cssLocator) {
        for (int i = 0; i < 5; i++) {
            try {
                return driver.findElement(By.cssSelector(cssLocator)).getText();
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                System.out.println("Table is refreshing, trying again... " + cssLocator);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        throw new RuntimeException("Could not read table cell after 5 tries: " + cssLocator);
    }

    public String getSymbol(int index) {
        return getCellText("td[id='-" + index + "_displayName-td'] span:nth-of-type(1)");
    }

    public String getName(int index) {
        return getCellText("td[id='-" + index + "_displayName-td'] span:nth-of-type(2)");
    }

    public String getPrice(int index) {
        return getCellText("td[id='-" + index + "_price-td']");
    }

    public String getChange(int index) {
        return getCellText("td[id='-" + index + "_change-td'] span");
    }

    public boolean isChartDisplayed(int index) {
        return driver.findElements(By.cssSelector("td[id='-" + index + "_week-chart-td'] svg")).size() > 0;
    }

    public WebElement getEarnBanner() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(earnBanner));
    }

    public WebElement getCardBanner() {
        return driver.findElement(cardBanner);
    }

    public WebElement getDepositBanner() {
        return driver.findElement(depositBanner);
    }

    public String getDownloadAppLink() {
        return driver.findElement(downloadAppButton).getDomProperty("href");
    }
}

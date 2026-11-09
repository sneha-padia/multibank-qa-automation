package tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HomePage;
import utils.LinkUtil;

public class NegativeTest extends BaseTest {

    @Test
    public void verifyInvalidPageReturns404() {
        String badUrl = baseUrl + "/this-page-does-not-exist-123";

        int code = LinkUtil.getStatusCode(badUrl);
        System.out.println("Status code for invalid page: " + code);
        Assert.assertEquals(code, 404, "Invalid page should return HTTP 404");

        // browser should show "Page not found" message and a button to go back
        driver.get(badUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText();
        Assert.assertEquals(heading, "Page not found");
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Sorry we can't find the page"), "Apology message missing on 404 page");
        driver.findElement(By.xpath("//*[text()='Back to Homepage']")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("this-page-does-not-exist")));
        System.out.println("After back to homepage: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().matches("https://mb\\.io(/en(-AE)?)?/?"), "Not on home page");
    }

    @Test
    public void verifyNoBrokenLinksInNavigation() {
        HomePage home = new HomePage(driver);
        home.isHeaderDisplayed();

        List<String> brokenLinks = new ArrayList<>();
        for (WebElement link : home.getAllHeaderLinks()) {
            String url = link.getDomProperty("href");
            if (url == null || url.isEmpty()) {
                continue;
            }
            int code = LinkUtil.getStatusCode(url);
            System.out.println(url + " -> " + code);
            if (code >= 400 || code == -1) {
                brokenLinks.add(url + " (" + code + ")");
            }
        }
        Assert.assertTrue(brokenLinks.isEmpty(), "Broken links: " + brokenLinks);
    }

    @Test
    public void verifyNavigationOnMobile() {
        driver.manage().window().setSize(new Dimension(390, 844));
        driver.navigate().refresh();
        HomePage home = new HomePage(driver);

        // desktop links should be hidden and hamburger menu should show
        Assert.assertTrue(home.isMenuButtonDisplayed(), "Menu button not shown on mobile");
        Assert.assertFalse(driver.findElement(By.xpath("//header//a[text()='Explore']")).isDisplayed(),
                "Desktop nav still visible on mobile");

        home.openMobileMenu();
        List<WebElement> links = home.getMobileMenuLinks();
        List<String> names = new ArrayList<>();
        for (WebElement l : links) {
            names.add(l.getText());
        }
        System.out.println("Mobile menu: " + names);
        Assert.assertTrue(names.contains("Explore"), "Explore missing from mobile menu: " + names);
        Assert.assertTrue(names.contains("Features"), "Features missing from mobile menu: " + names);
        Assert.assertTrue(names.contains("Company"), "Company missing from mobile menu: " + names);
        Assert.assertTrue(names.contains("Support"), "Support missing from mobile menu: " + names);
        Assert.assertTrue(names.contains("Blog"), "Blog missing from mobile menu: " + names);

        // page should not scroll sideways on mobile
        Long pageWidth = (Long) ((JavascriptExecutor) driver)
                .executeScript("return document.documentElement.scrollWidth;");
        Long screenWidth = (Long) ((JavascriptExecutor) driver)
                .executeScript("return window.innerWidth;");
        Assert.assertTrue(pageWidth <= screenWidth, "Page has horizontal scroll on mobile");
    }
}

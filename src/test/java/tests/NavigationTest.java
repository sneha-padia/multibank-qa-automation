package tests;

import base.BaseTest;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

import java.time.Duration;

public class NavigationTest extends BaseTest {

    String[] navItems = {"Explore", "Features", "OTC Desk", "Company", "Support", "Blog", "$MBG"};

    @Test
    public void verifyTopNavigationItemsVisible() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHeaderDisplayed(), "Header is not displayed");

        for (String item : navItems) {
            Assert.assertTrue(home.getNavLink(item).isDisplayed(), item + " is not visible in nav");
            System.out.println("Nav item visible: " + item);
        }
        Assert.assertTrue(home.isSignInDisplayed(), "Sign in button not visible");
        Assert.assertTrue(home.isSignUpDisplayed(), "Sign up button not visible");
    }

    @Test
    public void verifyNavigationLinksHaveCorrectUrl() {
        HomePage home = new HomePage(driver);

        Assert.assertEquals(home.getNavLink("Explore").getDomProperty("href"), baseUrl + "/explore", "Explore link is wrong");
        Assert.assertEquals(home.getNavLink("Features").getDomProperty("href"), baseUrl + "/features", "Features link is wrong");
        Assert.assertEquals(home.getNavLink("OTC Desk").getDomProperty("href"), baseUrl + "/features/otc-desk", "OTC Desk link is wrong");
        Assert.assertEquals(home.getNavLink("Company").getDomProperty("href"), baseUrl + "/company", "Company link is wrong");
        Assert.assertEquals(home.getNavLink("Support").getDomProperty("href"), baseUrl + "/support", "Support link is wrong");
        Assert.assertEquals(home.getNavLink("Blog").getDomProperty("href"), baseUrl + "/blog", "Blog link is wrong");
        Assert.assertTrue(home.getNavLink("$MBG").getDomProperty("href").contains("token.multibankgroup.com"), "$MBG should link to the token site");
        Assert.assertTrue(home.getNavLink("Sign in").getDomProperty("href").contains("trade.mb.io/login"), "Sign in should link to the login page");
        Assert.assertTrue(home.getNavLink("Sign up").getDomProperty("href").contains("trade.mb.io/register"), "Sign up should link to the register page");
    }

    @Test
    public void verifyClickingNavLinksOpensCorrectPage() {
        HomePage home = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        String[] names = {"Explore", "Features", "OTC Desk", "Company", "Support", "Blog"};
        String[] urls = {"/explore", "/features", "/features/otc-desk", "/company", "/support", "/blog"};
        
        for (int i = 0; i < names.length; i++) {
            home.clickNavLink(names[i]);
            wait.until(ExpectedConditions.urlContains(urls[i]));
            System.out.println("Clicked " + names[i] + " -> " + driver.getCurrentUrl());
            Assert.assertTrue(driver.getCurrentUrl().endsWith(urls[i]), "Wrong page for " + names[i]);
            Assert.assertTrue(home.isHeaderDisplayed(), "Header missing on " + names[i] + " page");
        }
    }

    @Test
    public void verifyNavigationOnDifferentDesktopSizes() {
        int[][] sizes = {{1920, 1080}, {1366, 768}, {1280, 800}};

        for (int[] size : sizes) {
            driver.manage().window().setSize(new Dimension(size[0], size[1]));
            driver.navigate().refresh();
            HomePage home = new HomePage(driver);

            for (String item : navItems) {
                Assert.assertTrue(home.getNavLink(item).isDisplayed(),
                        item + " not visible at " + size[0] + "x" + size[1]);
            }
            System.out.println("Nav OK at " + size[0] + "x" + size[1]);
        }
    }
}

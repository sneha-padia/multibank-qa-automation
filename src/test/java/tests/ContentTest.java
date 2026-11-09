package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CompanyPage;
import pages.ExplorePage;
import pages.HomePage;
import utils.LinkUtil;

public class ContentTest extends BaseTest {

    String iphoneAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148";
    String androidAgent = "Mozilla/5.0 (Linux; Android 14; Pixel 8) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0 Mobile Safari/537.36";

    @Test
    public void verifyMarketingBanners() {
        ExplorePage explore = new ExplorePage(driver);
        explore.open();

        WebElement earn = explore.getEarnBanner();
        WebElement card = explore.getCardBanner();
        WebElement deposit = explore.getDepositBanner();

        Assert.assertTrue(earn.isDisplayed(), "'Earn interest' banner not displayed");
        Assert.assertTrue(card.isDisplayed(), "'Get crypto with your card' banner not displayed");
        Assert.assertTrue(deposit.isDisplayed(), "'Deposits' banner not displayed");

        // banners should be at top of page, below the main heading
        int headingY = driver.findElement(By.tagName("h1")).getLocation().getY();
        int windowHeight = driver.manage().window().getSize().getHeight();
        System.out.println("Heading y = " + headingY + ", banner y = " + earn.getLocation().getY());

        Assert.assertTrue(earn.getLocation().getY() > headingY, "Banner is above heading");
        Assert.assertTrue(earn.getLocation().getY() < windowHeight, "Banner is not in first screen");
        Assert.assertEquals(card.getLocation().getY(), earn.getLocation().getY(), 50, "Banners are not in same row");
        Assert.assertEquals(deposit.getLocation().getY(), earn.getLocation().getY(), 50, "Banners are not in same row");
    }

    @Test
    public void verifyHomeHeroBanner() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHeroDisplayed(), "Hero banner not displayed");
        Assert.assertTrue(home.getDownloadAppLink().startsWith("https://"), "Download app link is wrong");
    }

    @Test
    public void verifyAppStoreLink() {
        HomePage home = new HomePage(driver);
        String link = home.getDownloadAppLink();
        System.out.println("Download link: " + link);

        // same link is used for both stores, it redirects based on the device
        String redirect = LinkUtil.getRedirectUrl(link, iphoneAgent);
        System.out.println("iPhone redirect: " + redirect);
        Assert.assertNotNull(redirect, "Download link did not redirect on iPhone");
        Assert.assertTrue(redirect.contains("apps.apple.com"), "Not going to App Store");
    }

    @Test
    public void verifyGooglePlayLink() {
        HomePage home = new HomePage(driver);
        String link = home.getDownloadAppLink();

        String redirect = LinkUtil.getRedirectUrl(link, androidAgent);
        System.out.println("Android redirect: " + redirect);
        Assert.assertNotNull(redirect, "Download link did not redirect on Android");
        Assert.assertTrue(redirect.contains("com.multibank.app"), "Wrong android app");
        Assert.assertTrue(redirect.contains("play.google.com"), "Not going to Google Play");
    }

    @Test
    public void verifyDownloadLinkOnExplorePageIsSame() {
        HomePage home = new HomePage(driver);
        String homeLink = home.getDownloadAppLink();

        ExplorePage explore = new ExplorePage(driver);
        explore.open();
        explore.isSpotMarketDisplayed();
        Assert.assertEquals(explore.getDownloadAppLink(), homeLink, "Download link on Explore page differs from Home page");
    }

    @Test
    public void verifyWhyMultiBankPage() {
        CompanyPage company = new CompanyPage(driver);
        company.open();

        Assert.assertEquals(company.getHeading(), "Why MultiBank Group?");
        Assert.assertTrue(driver.getTitle().contains("About"), "Title is wrong: " + driver.getTitle());

        List<String> headings = company.getSectionHeadings();
        System.out.println("Section headings: " + headings);
        Assert.assertTrue(headings.contains("A tradition of global leadership"), "Missing section heading. Found: " + headings);
        Assert.assertTrue(headings.contains("Innovation with purpose"), "Missing section heading. Found: " + headings);
        Assert.assertTrue(headings.contains("Integrity built into every decision"), "Missing section heading. Found: " + headings);

        String text = company.getPageText();
        Assert.assertTrue(text.contains("Founded in 2005"), "Founded text missing");
        Assert.assertTrue(text.contains("Annual turnover"), "'Annual turnover' missing on Company page");
        Assert.assertTrue(text.contains("Customers worldwide"), "'Customers worldwide' missing on Company page");
        Assert.assertTrue(text.contains("Offices globally"), "'Offices globally' missing on Company page");
        Assert.assertTrue(text.contains("The strength behind MultiBank Group"), "'The strength behind MultiBank Group' missing on Company page");
        Assert.assertTrue(text.contains("Regulation at our core"), "'Regulation at our core' missing on Company page");
        Assert.assertTrue(text.contains("Proven track record"), "'Proven track record' missing on Company page");
        Assert.assertTrue(text.contains("Secure & trusted"), "'Secure & trusted' missing on Company page");
        Assert.assertTrue(company.isGetInTouchDisplayed(), "Get in touch button missing");
    }
}

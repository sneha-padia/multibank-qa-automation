package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ExplorePage;
import pages.HomePage;

import java.util.List;

public class TradingTest extends BaseTest {

    @Test
    public void verifySpotMarketShowsTradingPairs() {
        ExplorePage explore = new ExplorePage(driver);
        explore.open();

        Assert.assertTrue(explore.isSpotMarketDisplayed(), "Spot market section not displayed");
        List<WebElement> rows = explore.getRows();
        System.out.println("Number of coins in spot market: " + rows.size());
        Assert.assertTrue(rows.size() > 0, "No trading pairs shown");
    }

    @Test
    public void verifySpotMarketCategoryTabs() {
        ExplorePage explore = new ExplorePage(driver);
        explore.open();
        explore.isSpotMarketDisplayed();

        String[] tabs = {"Hot", "Gainers", "Losers"};
        for (String tab : tabs) {
            Assert.assertTrue(explore.isTabDisplayed(tab), tab + " tab not displayed");
            explore.clickTab(tab);
            int count = explore.getRows().size();
            System.out.println(tab + " tab rows: " + count);
            Assert.assertTrue(count > 0, "No rows in " + tab + " tab");
        }
    }

    @Test
    public void verifyTradingPairHasAllFields() {
        ExplorePage explore = new ExplorePage(driver);
        explore.open();
        explore.isSpotMarketDisplayed();

        int count = explore.getRows().size();
        for (int i = 0; i < count; i++) {
            String symbol = explore.getSymbol(i);
            String name = explore.getName(i);
            String price = explore.getPrice(i);
            String change = explore.getChange(i);
            System.out.println(symbol + " | " + name + " | " + price + " | " + change);

            Assert.assertFalse(symbol.isEmpty(), "Symbol is empty");
            Assert.assertFalse(name.isEmpty(), "Name is empty for " + symbol);
            Assert.assertTrue(price.startsWith("$"), "Price is wrong for " + symbol + ": " + price);
            Assert.assertTrue(change.endsWith("%"), "Change is wrong for " + symbol + ": " + change);
            Assert.assertTrue(explore.isChartDisplayed(i), "Chart missing for " + symbol);
        }
    }

    @Test
    public void verifyHomePageCategories() {
        HomePage home = new HomePage(driver);

        String[] categories = {"Top Gainers", "Trending Now", "Top Losers"};
        for (String category : categories) {
            List<WebElement> items = home.getCategoryItems(category);
            System.out.println(category + " has " + items.size() + " coins");
            Assert.assertTrue(items.size() > 0, "No coins in " + category);

            // every coin should link to its explore page and show price
            for (WebElement item : items) {
                Assert.assertTrue(item.getDomProperty("href").contains("/explore/"));
                Assert.assertTrue(item.getText().contains("$"), "Price missing: " + item.getText());
            }
        }
    }
}

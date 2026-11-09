# MultiBank UI Automation

Selenium + Java + TestNG + Maven project for the QA coding challenge (Task 1).

## Note about the site
`https://trade.multibank.io/` now redirects to `https://trade.mb.io/login` which is only a login page.
The public website (navigation, spot market, banners, app links, about page) has moved to `https://mb.io`,
so the tests run on https://mb.io/en-AE by default (set in BaseTest; override with -DbaseUrl=...).

- About Us > Why MultiBank is now the **Company** page (`/company`, heading "Why MultiBank Group?")
- Spot trading section is on the **Explore** page (`/explore`)
- There is one "Download the app" link, it redirects to App Store on iPhone and Google Play on Android

## Requirements
- Java 17 or above
- Maven 3.9 or above
- Google Chrome (default). Microsoft Edge and Mozilla Firefox are also supported.
  Drivers are downloaded automatically by Selenium Manager.
- Selenium 4.35 or newer (older versions can't download EdgeDriver because Microsoft retired the old download site)

## How to run
```
mvn test
```
To run on another browser:
```
mvn test -Dbrowser=edge
mvn test -Dbrowser=firefox
```
To test another region of the site (mb.io redirects by location):
```
mvn test -DbaseUrl=https://mb.io/en-GB
```
Reports are created in `target/surefire-reports` (open `index.html` or `emailable-report.html`). Chrome and Edge are saved
in evidence folder.

To run one class only:
```
mvn test -Dtest=NavigationTest
```

## Project structure
```
src/test/java
  base/BaseTest.java        - opens and closes the browser for every test
  pages/HomePage.java       - home page + header locators
  pages/ExplorePage.java    - explore page (spot market table, banners)
  pages/CompanyPage.java    - company / why multibank page
  utils/LinkUtil.java       - checks http status code and redirects of links
  tests/NavigationTest.java - navigation & layout
  tests/TradingTest.java    - trading pairs and categories
  tests/ContentTest.java    - banners, app store links, why multibank page
  tests/NegativeTest.java   - invalid page (404), broken links, mobile view
testng.xml                  - test suite
```
## Design decisions
- **Selenium + Java + TestNG + Maven:** widely used, runs from one command (`mvn test`), and TestNG
  gives built-in HTML reports.
- **Page Object Model:** each page has its own class holding its locators and actions; tests only
  call page methods and assert. If the site changes, the fix is in one place.
- **One base URL and a browser switch in BaseTest:** both can be changed from the command line
  (`-DbaseUrl=...`, `-Dbrowser=...`) without editing code.
- **A fresh browser for every test** (`@BeforeMethod` / `@AfterMethod`), so tests don't depend on each other.
- **Format checks for live data:** prices change every second, so tests check the shape of the data
  (`$` price, `%` change), never exact values.
- **Link checks over HTTP** (`LinkUtil`): status codes and app-store redirects are checked directly,
  which is faster than opening each link in the browser.

## Assumptions
- The public site `https://mb.io` is the system under test, because `trade.multibank.io` now only shows a login page.
- Tests are run from the UAE (`/en-AE`). From another region, use `-DbaseUrl=`.
- No login or account creation is needed, as required by the brief.

## Test cases

| Area | Test | What it checks |
|---|---|---|
| Navigation | verifyTopNavigationItemsVisible | all nav items + Sign in / Sign up are visible |
| Navigation | verifyNavigationLinksHaveCorrectUrl | every nav link has correct href |
| Navigation | verifyClickingNavLinksOpensCorrectPage | clicking each link opens correct page |
| Navigation | verifyNavigationOnDifferentDesktopSizes | nav visible on 1920, 1366, 1280 widths |
| Trading | verifySpotMarketShowsTradingPairs | spot market table has coins |
| Trading | verifySpotMarketCategoryTabs | Hot / Gainers / Losers tabs show coins |
| Trading | verifyTradingPairHasAllFields | each coin has symbol, name, price ($), change (%), chart |
| Trading | verifyHomePageCategories | Top Gainers / Trending Now / Top Losers have coins |
| Content | verifyMarketingBanners | 3 banners shown below heading in first screen |
| Content | verifyHomeHeroBanner | hero section and download button shown |
| Content | verifyAppStoreLink | download link goes to App Store on iPhone |
| Content | verifyGooglePlayLink | download link goes to Google Play on Android |
| Content | verifyDownloadLinkOnExplorePageIsSame | same app link on home and explore |
| Content | verifyWhyMultiBankPage | heading, sections and text on company page |
| Negative | verifyInvalidPageReturns404 | wrong url gives 404, shows "Page not found", back button goes home |
| Negative | verifyNoBrokenLinksInNavigation | no nav link returns 4xx/5xx |
| Negative | verifyNavigationOnMobile | hamburger menu works on mobile, no side scroll |

## Problems I faced
- mb.io redirects `/en` to a regional path (e.g. `/en-AE` in the UAE). URLs are built from one
`baseUrl` in BaseTest so the region can be changed in one place.
- Prices update live so the table re-renders and elements become stale. I find the cells again by
  row index each time and retry a few times.
- Some sections on company page fade in on scroll, so `getText()` returns empty. I used `textContent` there.
- Market data is live so tests only check format (like price starts with `$`), not exact values.

## Findings (issues found on the live site)
- **Gainers tab shows falling coins:** some coins with a negative change (red ▼) appear under
  "Gainers", and some coins appear in both Gainers and Losers. Verified manually (LINK/LTC)
- **404 page shows a different legal disclaimer:** UAE pages show the VARA (UAE) licensing text,
  but the 404 page shows an Australian entity disclaimer. 

## Test execution evidence
Runs on 9 Nov 2026 against https://mb.io/en-AE (full logs and TestNG reports in `docs/evidence/`).

| Browser | Version | Tests | Passed | Failed | Duration |
|---|---|---|---|---|---|
| Microsoft Edge | 156.0.4314.58 | 17 | 17 | 0 | 2m 06s |
| Google Chrome  | 156.0.8078.79 | 17 | 17 | 0 | 2m 12s |

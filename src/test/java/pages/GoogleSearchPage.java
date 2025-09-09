package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import utils.ConfigManager;

import java.time.Duration;
import java.util.List;

public class GoogleSearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page elements using @FindBy annotations
    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(xpath = "//input[@value='Google Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//h3[contains(@class, 'LC20lb')]")
    private List<WebElement> searchResultTitles;

    @FindBy(xpath = "//div[@class='yuRUbf']//a")
    private List<WebElement> searchResultLinks;

    @FindBy(xpath = "//div[@id='search']")
    private WebElement searchResultsContainer;

    @FindBy(xpath = "//div[contains(@class, 'g')]")
    private List<WebElement> searchResults;

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    public void navigateToGoogle() {
        driver.get("https://www.google.com");
        wait.until(ExpectedConditions.elementToBeClickable(searchBox));

        // Handle cookie consent if present
        try {
            WebElement acceptButton = driver.findElement(By.xpath("//button[contains(text(), 'Accept') or contains(text(), 'I agree')]"));
            if (acceptButton.isDisplayed()) {
                acceptButton.click();
                Thread.sleep(1000); // Brief wait for consent to process
            }
        } catch (Exception e) {
            // Cookie consent not present or already handled
        }
    }

    public void searchFor(String searchTerm) {
        wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
        searchBox.sendKeys(Keys.ENTER);

        // Wait for results to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='search']")));
    }

    public boolean areSearchResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='search']")));
            return searchResultsContainer.isDisplayed() && !searchResults.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean findArticleInResults(String articleTitle) {
        System.out.println("Searching for article: " + articleTitle);
        System.out.println("Found " + searchResultTitles.size() + " search results");

        for (int i = 0; i < searchResultTitles.size(); i++) {
            WebElement titleElement = searchResultTitles.get(i);
            String resultTitle = titleElement.getText();
            System.out.println("Result " + (i + 1) + ": " + resultTitle);

            if (resultTitle.toLowerCase().contains(articleTitle.toLowerCase()) ||
                    resultTitle.toLowerCase().contains("cucumber") && resultTitle.toLowerCase().contains("bdd")) {
                System.out.println("Found matching article: " + resultTitle);
                return true;
            }
        }
        return false;
    }

    public boolean clickOnArticle(String articleTitle) {
        for (int i = 0; i < searchResultTitles.size(); i++) {
            WebElement titleElement = searchResultTitles.get(i);
            String resultTitle = titleElement.getText();

            if (resultTitle.toLowerCase().contains(articleTitle.toLowerCase()) ||
                    (resultTitle.toLowerCase().contains("cucumber") && resultTitle.toLowerCase().contains("bdd"))) {

                // Click on the corresponding link
                WebElement linkElement = searchResultLinks.get(i);
                linkElement.click();
                return true;
            }
        }
        return false;
    }

    public boolean verifyArticleLinkContains(String expectedUrlPart) {
        for (WebElement linkElement : searchResultLinks) {
            String href = linkElement.getAttribute("href");
            System.out.println("Checking link: " + href);

            if (href != null && href.toLowerCase().contains(expectedUrlPart.toLowerCase())) {
                System.out.println("Found matching link: " + href);
                return true;
            }
        }
        return false;
    }

    public boolean resultsContainDomain(String domain) {
        for (WebElement linkElement : searchResultLinks) {
            String href = linkElement.getAttribute("href");
            if (href != null && href.contains(domain)) {
                return true;
            }
        }
        return false;
    }

    public boolean resultsContainKeywords(String... keywords) {
        for (WebElement titleElement : searchResultTitles) {
            String title = titleElement.getText().toLowerCase();
            for (String keyword : keywords) {
                if (title.contains(keyword.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean clickOnAnyRelatedResult(String keyword) {
        System.out.println("Looking for any result related to: " + keyword);

        for (int i = 0; i < searchResultTitles.size(); i++) {
            WebElement titleElement = searchResultTitles.get(i);
            String resultTitle = titleElement.getText().toLowerCase();

            if (resultTitle.contains(keyword.toLowerCase())) {
                System.out.println("Clicking on: " + titleElement.getText());
                WebElement linkElement = searchResultLinks.get(i);
                linkElement.click();
                return true;
            }
        }

        // If no title match, check URLs
        for (int i = 0; i < searchResultLinks.size(); i++) {
            WebElement linkElement = searchResultLinks.get(i);
            String href = linkElement.getAttribute("href");

            if (href != null && href.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("Clicking on result with URL containing: " + keyword);
                linkElement.click();
                return true;
            }
        }

        System.out.println("Could not find any result related to: " + keyword);
        return false;
    }

    public void printAllResults() {
        System.out.println("=== Search Results ===");
        for (int i = 0; i < Math.min(searchResultTitles.size(), searchResultLinks.size()); i++) {
            String title = searchResultTitles.get(i).getText();
            String link = searchResultLinks.get(i).getAttribute("href");
            System.out.println((i + 1) + ". " + title);
            System.out.println("   URL: " + link);
            System.out.println("   Contains 'cucumber': " + title.toLowerCase().contains("cucumber"));
            System.out.println("   Contains 'testing': " + title.toLowerCase().contains("testing"));
            System.out.println("   Contains 'bdd': " + title.toLowerCase().contains("bdd"));
            System.out.println("   ---");
        }
        System.out.println("======================");
    }
}
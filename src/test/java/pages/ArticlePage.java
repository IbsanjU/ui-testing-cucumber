package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigManager;

import java.time.Duration;

public class ArticlePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ArticlePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getExplicitWait()));
    }

    public boolean isOnArticlePage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("revanab.com") &&
                (currentUrl.contains("ui-testing-with-cucumber-bdd") ||
                        currentUrl.contains("cucumber"));
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean titleContains(String expectedText) {
        String pageTitle = getPageTitle();
        System.out.println("Page title: " + pageTitle);
        return pageTitle.toLowerCase().contains(expectedText.toLowerCase());
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
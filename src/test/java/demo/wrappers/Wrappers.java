package demo.wrappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dev.failsafe.internal.util.Durations;

import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    public static void launchURL(WebDriver driver) {
        driver.get("https://www.scrapethissite.com/pages/");
    }

    public static void navigateToRespectiveTable(WebDriver driver, By locator) {
        WebElement respectiveURL = driver.findElement(locator);
        respectiveURL.click();
    }

    public static List<WebElement> tableHeaders(WebDriver driver, By locator) {
        List<WebElement> headers = driver.findElements(locator);
        return headers;
    }

    public static List<WebElement> tableBody(WebDriver driver, By locator) {
        List<WebElement> rows = driver.findElements(locator);
        return rows;
    }

    public static String cellData(WebDriver driver, WebElement row, int cellIndex) {
        WebElement cell = row.findElement(By.xpath("td[" + (cellIndex + 1) + "]")); // XPath index is 1-based
        return cell.getText(); // Return the text of the specified cell
    }

    public static void clickOnNextPage(WebDriver driver, int page) {
        // WebElement nextButton =
        // driver.findElement(By.xpath("//a[@href='/pages/forms/?page_num=2']"));
        String nextPageXPath = "//a[@href='/pages/forms/?page_num=" + page + "']";
        WebElement nextButton = driver.findElement(By.xpath(nextPageXPath));
        nextButton.click();
    }

    public static List<WebElement> clickOnYears(WebDriver driver, By locator) {
        List<WebElement> years = driver.findElements(locator);
        return years;
    }

    public static WebElement bestFilm(WebDriver driver, WebElement row, int cellIndex) {
        WebElement cell = row.findElement(By.xpath("td[" + (cellIndex + 1) + "]"));
        return cell;
    }

    public static void waitUntilAllElementsLocated(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4l));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
}

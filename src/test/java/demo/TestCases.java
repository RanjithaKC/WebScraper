package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.base.Equivalence.Wrapper;

import java.util.List;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import java.io.File;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */
    @Test(enabled = true)
    public void testCase01() throws InterruptedException {
        System.out.println("Start of testCase01");
        Wrappers.launchURL(driver);
        Wrappers.navigateToRespectiveTable(driver, By.linkText("Hockey Teams: Forms, Searching and Pagination"));
        // calling method to extract table headers
        List<WebElement> headers = Wrappers.tableHeaders(driver, By.xpath("//table[@class='table']/tbody/tr/th"));
        // Initializing Map to store table header name with index
        Map<String, Integer> headerMap = new HashMap<>();
        // to store the index of headers
        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i).getText();
            headerMap.put(header, i);
        }
        // making sure headerMap has all headers with index
        // for(Map.Entry<String,Integer> entry : headerMap.entrySet()){
        // System.out.println("reading headerMap : "+ entry.getKey()+"
        // "+entry.getValue());
        // }
        // Making sure that the header expected by us is present
        if (!headerMap.containsKey("Team Name") || !headerMap.containsKey("Year") || !headerMap.containsKey("Win %")) {
            try {
                throw new Exception("Expected headers not found");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // get the index of header we required
        int teamNameIndex = headerMap.get("Team Name");
        int yearIndex = headerMap.get("Year");
        int winPercentageIndex = headerMap.get("Win %");

        // List to store hocheyTeam Object
        List<HockeyTeam> hockeyTeamList = new ArrayList<>();

        // get the body by iterating from page 1 to 4
        for (int i = 1; i <= 4; i++) {
            List<WebElement> rows = Wrappers.tableBody(driver,
                    By.xpath("//table[@class='table']/tbody/tr[@class='team']"));
            // iterate through each row to exptact required cell data
            for (WebElement row : rows) {
                String teamName = Wrappers.cellData(driver, row, teamNameIndex);
                // System.out.println("teamName : "+teamName);
                String year = Wrappers.cellData(driver, row, yearIndex);
                String winPercentageString = Wrappers.cellData(driver, row, winPercentageIndex);
                double winPercentage = Double.parseDouble(winPercentageString.replace("%", "").trim()) / 100;
                // epoch time
                long currentEpochTime = System.currentTimeMillis();
                if (winPercentage < 0.40) {
                    HockeyTeam hockeyTeamData = new HockeyTeam(currentEpochTime, teamName, year, winPercentage);
                    hockeyTeamList.add(hockeyTeamData);
                }
            }
            // click on next page till pageno 4
            if (i < 4) {
                Wrappers.clickOnNextPage(driver, i + 1);
                Thread.sleep(1000);
            }
        }
        // write data to json
        try {
            JsonUtils.writeHockeyTeamDataToJason(hockeyTeamList);
        } catch (Exception IOException) {
            System.err.println("Failed to write the JSON file: " + IOException.getMessage());
        }
        System.out.println("End of testCase01");
    }

    @Test(enabled = true)
    public void testCase02() throws InterruptedException {
        System.out.println("Start of testCase01");
        Wrappers.launchURL(driver);
        Wrappers.navigateToRespectiveTable(driver, By.xpath("//a[contains(text(),'Oscar Winning Films')]"));
        Thread.sleep(3000);
        // Wrappers.waitUntilAllElementsLocated(driver,
        // By.xpath("//div[@class='col-md-12 text-center']/a"));
        // fetching list of years
        List<WebElement> years = Wrappers.clickOnYears(driver, By.xpath("//a[@class='year-link']"));
        // Arraylist to store object
        List<OscarFilm> oscarList = new ArrayList<>();
        // iterate through list of years and click to fetch top 5 data from table
        Map<String, Integer> headerMap = new HashMap<>();
        for (WebElement yearElement : years) {
            int year = Integer.parseInt(yearElement.getText());
            // System.out.println("year : " + year);
            yearElement.click();
            Thread.sleep(4000);
            // Wrappers.waitUntilAllElementsLocated(driver,
            // By.xpath("//table[@class='table']/tbody/tr/td"));
            // Extract headers only once
            if (headerMap.isEmpty()) {
                List<WebElement> headers = Wrappers.tableHeaders(driver,
                        By.xpath("//table[@class='table']/thead/tr/th"));
                for (int i = 0; i < headers.size(); i++) {
                    String header = headers.get(i).getText();
                    headerMap.put(header, i);
                }
            }
            // making sure headerMap has all headers with index
            // for(Map.Entry<String,Integer> entry : headerMap.entrySet()){
            // System.out.println("reading headerMap : "+ entry.getKey()+"
            // "+entry.getValue());
            // }
            // Extracting the header index
            int titleIndex = headerMap.get("Title");
            int nominationsIndex = headerMap.get("Nominations");
            int awardsInndex = headerMap.get("Awards");
            int bestPictureIndex = headerMap.get("Best Picture");

            // find list of table rows
            List<WebElement> tableRows = Wrappers.tableBody(driver, By.xpath("//table[@class='table']/tbody/tr"));

            // iterate only till 5 rows
            for (int i = 0; i < 5; i++) {
                long epochTime = System.currentTimeMillis();
                // extract title, nominations, award and best picture
                String title = Wrappers.cellData(driver, tableRows.get(i), titleIndex);
                // System.out.println("title :" + title);
                String nominationsString = Wrappers.cellData(driver, tableRows.get(i), nominationsIndex);
                int nominations = Integer.parseInt(nominationsString);
                // System.out.println("nominations :" + nominations);
                String awardsString = Wrappers.cellData(driver, tableRows.get(i), awardsInndex);
                int awards = Integer.valueOf(awardsString);
                // System.out.println("awards :" + awards);

                // Check for the Best Picture winner
                WebElement bestPicture = Wrappers.bestFilm(driver, tableRows.get(i), bestPictureIndex);
                // boolean isWinner = !bestPicture.findElements(By.xpath(".//i[contains(@class,
                // 'glyphicon') and contains(@class, 'glyphicon-flag')]")).isEmpty();
                boolean isWinner = !bestPicture.findElements(By.xpath("./i[@class='glyphicon glyphicon-flag']"))
                        .isEmpty();
                // System.out.println("bestPicture : " + isWinner);
                // add all extracted data to arraylist
                oscarList.add(new OscarFilm(epochTime, year, title, nominations, awards, isWinner));
            }
        }
        // convert to json
        try {
            JsonUtils.writeOscarDataToJson(oscarList);
        } catch (Exception IOException) {
            System.err.println("Failed to write the JSON file: " + IOException.getMessage());
        }
        // Assert that the JSON file is created and not empty
        File file = new File("oscar-winner-data.json");
        Assert.assertTrue(file.exists(), "file doesnot exist");
        Assert.assertTrue(file.length() > 0, "Empty json file");
        System.out.println("End of testCase02");
    }

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}